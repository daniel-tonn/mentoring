package de.lbb.mentoring.example.model;

import de.lbb.mentoring.example.model.testdatabuilder.EmployeeBuilder;
import de.lbb.mentoring.example.model.valueobjects.EmployeeVO;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class FlushModeTest extends AbstractEntityTest
{

    /**
     * Showing how automatic flushing should be used to get consistent data
     */
    @Test
    public void testDefaultFlushmode()
    {
        // Using a builder for Entities to test
        Employee employee = new EmployeeBuilder().withAge(30).withName("Hans").withSurename("Mueller").build();

        // saving Employee
        em.getTransaction().begin();
        em.persist(employee);
        em.getTransaction().commit();


        em.getTransaction().begin();
        // Changing age of Employee
        employee.setAge(41);
        
        // Doing a query for Employees under 40
        Query query = em.createQuery("select e from Employee e where e.age < 40");

        // Default Flushmode is now flushing to the Database and persisting our Employee with
        // age of 41
        List result = query.getResultList();
        // No Employee under 40 has been found
        Assert.assertTrue(result.isEmpty());
        em.getTransaction().commit();
    }

    /**
     * Showing how the Hibernate Manual flushmode can introduce bad behavior
     */
    @Test
    public void testHibernateManualFlushmode()
    {
        // Using a builder for Entities to test
        Employee employee = new EmployeeBuilder().withAge(30).withName("Hans").withSurename("Mueller").build();

        // saving Employee
        em.getTransaction().begin();
        em.persist(employee);
        em.getTransaction().commit();


        // Now we are setting flushmode to Hibernate-MANUAL
        Session session = (Session) em.getDelegate();
        session.setFlushMode(FlushMode.MANUAL);
        em.getTransaction().begin();
        // Changing age of Employee
        employee.setAge(41);

        // Doing a query for Employees under 40
        Query query = em.createQuery("select e from Employee e where e.age < 40");

        // Since we are not flushing automaticly here, our new aged Employee is not flushed
        List result = query.getResultList();

        // One Employee under 40 has been found, which is actually over 40 now
        Assert.assertEquals(1, result.size());
        Employee reloadedEmployee = (Employee) result.get(0);
        Assert.assertEquals(41, reloadedEmployee.getAge());

        session.flush();
        em.getTransaction().commit();
    }

    /**
     * Showing how the Hibernate Manual flushmode can override commit
     */
    @Test
    public void testHibernateManualFlushmodeAndCommit()
    {
        // Using a builder for Entities to test
        Employee employee = new EmployeeBuilder().withAge(30).withName("Hans").withSurename("Mueller").build();

        // saving Employee
        em.getTransaction().begin();
        em.persist(employee);
        em.getTransaction().commit();


        // Now we are setting flushmode to Hibernate-MANUAL
        Session session = (Session) em.getDelegate();
        session.setFlushMode(FlushMode.MANUAL);
        em.getTransaction().begin();
        // Changing age of Employee
        employee.setAge(41);

        // explicit do not flush, but commit
        em.getTransaction().commit();

        // Doing a query for Employees under 40
        EntityManager newEM = emf.createEntityManager();
        Query query = newEM.createQuery("select e from Employee e where e.age < 40");

        // Since we have not flushed the changed Employee is missing in the database
        List result = query.getResultList();

        // One Employee under 40 has been found, which is true
        // Our changes in the last transaction have not been materialized into the DB
        Assert.assertEquals(1, result.size());
        Employee reloadedEmployee = (Employee) result.get(0);
        Assert.assertEquals(30, reloadedEmployee.getAge());
    }
}
