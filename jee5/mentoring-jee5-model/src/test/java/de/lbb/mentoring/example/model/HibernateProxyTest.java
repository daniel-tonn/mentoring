package de.lbb.mentoring.example.model;

import de.lbb.mentoring.example.model.testdatabuilder.TestdataBuilder;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * Tests for trying to reproduce hibernate proxy generation and the instanceof problem with it.
 *
 * (Un)fortunatly this behavior is not reproducable any more ...
 */
public class HibernateProxyTest extends AbstractEntityTest
{
    @Test
    public void testHibernateProxy()
    {
        Employee employee = TestdataBuilder.createParttimeEmployee("Hans", "Mueller");
        // saving Employee
        em.getTransaction().begin();
        em.persist(employee);
        em.getTransaction().commit();

        // Creating Department
        Department department = TestdataBuilder.createDepartment("Abteilung 1");
        employee.setDepartment(department);
        em.getTransaction().begin();
        em.persist(employee);
        em.getTransaction().commit();

        // loading Department from new EM
        EntityManager otherEM = emf.createEntityManager();
        Department reloadedDepartment = otherEM.find(Department.class, department.getId());
        Assert.assertNotNull(reloadedDepartment);
        Assert.assertEquals(1, reloadedDepartment.getEmployees().size());

        Employee reloadedEmployee = reloadedDepartment.getEmployees().get(0);
        Assert.assertTrue(reloadedEmployee instanceof ParttimeEmployee);

    }


    @Test
    public void testHibernateProxy2()
    {
        Employee employee = TestdataBuilder.createParttimeEmployee("Hans", "Mueller");
        // saving Employee
        em.getTransaction().begin();
        em.persist(employee);
        em.getTransaction().commit();
        em.close();

        // loading Employee from new EM
        EntityManager otherEM = emf.createEntityManager();
        Query query = otherEM.createQuery("select e from Employee e where e.id = :id");
        query.setParameter("id", employee.getId());
        AbstractEntity reloadedEmployee = (AbstractEntity) query.getSingleResult();

        Assert.assertNotNull(reloadedEmployee);
        Assert.assertTrue(reloadedEmployee instanceof Employee);
        Assert.assertTrue(reloadedEmployee instanceof ParttimeEmployee);

    }

}
