package de.lbb.mentoring.example.model.jpa2;

import de.lbb.mentoring.example.model.AbstractEntityTest;
import de.lbb.mentoring.example.model.Employee;
import de.lbb.mentoring.example.model.testdatabuilder.EmployeeBuilder;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.Cache;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class CacheTest extends AbstractEntityTest
{

    /**
     * JPA2 introduced typed queries
     */
    @Test
    public void testSharedCache()
    {
        Employee employee1 = new EmployeeBuilder().withAge(30).withName("Hans").withSurename("Mueller").build();
        Employee employee2 = new EmployeeBuilder().withAge(30).withName("Klaus").withSurename("Schulze").build();
        // saving Employee
        em.getTransaction().begin();
        em.persist(employee1);
        em.persist(employee2);
        em.getTransaction().commit();

        // Cache is not active by default
        Cache cache = emf.getCache();
        Assert.assertFalse(cache.contains(Employee.class, employee1));
        Assert.assertFalse(cache.contains(Employee.class, employee2));
    }
}
