package de.lbb.mentoring.example.model;

import de.lbb.mentoring.example.model.testdatabuilder.EmployeeBuilder;
import de.lbb.mentoring.example.model.valueobjects.EmployeeVO;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class QueryTest extends AbstractEntityTest
{

    /**
     * JPQL Query retrieving a valueobject directly
     */
    @Test
    public void testEmployeeQueryValueObjects()
    {
        // Using a builder for Entities to test
        Employee employee = new EmployeeBuilder().withAge(30).withName("Hans").withSurename("Mueller").build();


        // saving Employee
        em.getTransaction().begin();
        em.persist(employee);
        em.getTransaction().commit();

        // loading Employee from new EM
        EntityManager otherEM = emf.createEntityManager();
        Query query = otherEM.createQuery("select new de.lbb.mentoring.example.model.valueobjects.EmployeeVO(e.name, e.surename) from Employee e where e.id = :id");
        query.setParameter("id", employee.getId());
        EmployeeVO emplVO = (EmployeeVO) query.getSingleResult();
        Assert.assertEquals(employee.getName(), emplVO.getName());
        Assert.assertEquals(employee.getSurename(), emplVO.getSurename());
    }


    /**
     * Query with "null" in where clause
     */
    @Test
    public void testNullTest()
    {
        Employee employee = new EmployeeBuilder().withAge(30).withName("Hans").withSurename("Mueller").build();

        // saving Employee
        em.getTransaction().begin();
        em.persist(employee);
        em.getTransaction().commit();

        // loading Employee from new EM
        EntityManager otherEM = emf.createEntityManager();
        Employee reloadedEmployee = otherEM.find(Employee.class, employee.getId());
        Assert.assertNull(reloadedEmployee.getDepartment());

        Query query = otherEM.createQuery("select e from Employee e where e.department = :department");
        query.setParameter("department", null);
        List result = query.getResultList();

        // result is empty, because comparing to null is always false
        Assert.assertTrue(result.isEmpty());
    }

    @Test
    public void testImplicitInnerJoin()
    {
        Employee employee = new EmployeeBuilder().withAge(30).withName("Hans").withSurename("Mueller").build();

        // saving Employee
        em.getTransaction().begin();
        em.persist(employee);
        em.getTransaction().commit();

        EntityManager otherEM = emf.createEntityManager();

        Query query = otherEM.createQuery("select e.department, e.name from Employee e");
        List result = query.getResultList();

        // result is empty, because e.department is creating an implizit inner-join, ommitting all
        // Employees without an Department
        Assert.assertTrue(result.isEmpty());

        // Using an explicit Outer-Join to get the desired result
        query = otherEM.createQuery("select d, e.name from Employee e left outer join e.department d");

        result = query.getResultList();
        // Thanks to the outer-join our Employee is finally found
        Assert.assertFalse(result.isEmpty());
    }
}
