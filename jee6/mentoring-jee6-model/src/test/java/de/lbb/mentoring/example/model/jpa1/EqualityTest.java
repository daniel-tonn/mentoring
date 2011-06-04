package de.lbb.mentoring.example.model.jpa1;

import de.lbb.mentoring.example.model.AbstractEntityTest;
import de.lbb.mentoring.example.model.Employee;
import de.lbb.mentoring.example.model.testdatabuilder.EmployeeBuilder;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.EntityManager;

public class EqualityTest extends AbstractEntityTest
{

    /**
     * Showing equality problem when loading same entity from different em
     */
    @Test
    public void testEmployeeIdentity()
    {
        Employee employee = new EmployeeBuilder().withAge(30).withName("Hans").withSurename("Mueller").build();

        // saving Employee
        em.getTransaction().begin();
        em.persist(employee);
        em.getTransaction().commit();

        // loading Employee from same EM
        Employee reloadedEmployee = em.find(Employee.class, employee.getId());
        Assert.assertTrue(employee == reloadedEmployee);
        Assert.assertTrue(employee.equals(reloadedEmployee));

        // loading Employee from new EM
        EntityManager otherEM = emf.createEntityManager();
        reloadedEmployee = otherEM.find(Employee.class, employee.getId());
        Assert.assertFalse(employee == reloadedEmployee);
        Assert.assertFalse(employee.equals(reloadedEmployee));

        // Implementing .equals and .hashcode to get equality again ...
    }

    /**
     * Showing equality problem when loading same entity from different em
     */
    @Test
    public void testEmployeeAttachDetachment()
    {
        Employee employee = new EmployeeBuilder().withAge(30).withName("Hans").withSurename("Mueller").build();

        // saving Employee
        em.getTransaction().begin();
        em.persist(employee);
        em.getTransaction().commit();

        // loading Employee from same EM
        Employee employeeFromEM = em.find(Employee.class, employee.getId());
        Assert.assertTrue(em.contains(employeeFromEM));

        // loading Employee from new EM
        EntityManager em2 = emf.createEntityManager();
        Employee employeeFromEM2 = em2.find(Employee.class, employee.getId());
        Assert.assertTrue(em2.contains(employeeFromEM2));

        // no cross-attachments to the ems
        Assert.assertFalse(em.contains(employeeFromEM2));
        Assert.assertFalse(em2.contains(employeeFromEM));

        // manual merging
        Employee mergedEmployeeFromEM2 = em.merge(employeeFromEM2);
        // merge is returning the attached entity !!!
        Assert.assertTrue(mergedEmployeeFromEM2 != employeeFromEM2);

        // equality of merged entities (same entity from the same EM must be the same instance!)
        Assert.assertTrue(employeeFromEM == mergedEmployeeFromEM2);
    }

}
