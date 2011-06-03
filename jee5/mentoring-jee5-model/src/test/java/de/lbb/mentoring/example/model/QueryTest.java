package de.lbb.mentoring.example.model;

import de.lbb.mentoring.example.model.testdatabuilder.EmployeeBuilder;
import de.lbb.mentoring.example.model.valueobjects.EmployeeVO;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.Query;

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
}
