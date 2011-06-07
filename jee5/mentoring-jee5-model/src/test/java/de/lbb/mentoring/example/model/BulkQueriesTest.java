package de.lbb.mentoring.example.model;

import de.lbb.mentoring.example.model.testdatabuilder.EmployeeBuilder;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.Query;

public class BulkQueriesTest extends AbstractEntityTest
{

    /**
     * Showing how bulk queries does not effect running entitymanager
     */
    @Test
    public void testBulkQueries()
    {
        Employee employee = new EmployeeBuilder().withAge(30).withName("Hans").withSurename("Mueller").build();

        // saving Employee
        em.getTransaction().begin();
        em.persist(employee);
        em.getTransaction().commit();

        em.getTransaction().begin();
        Query query = em.createQuery("delete from Employee");
        query.executeUpdate();
        em.getTransaction().commit();

        // Entitymanager does not know anything about the deleted Employee
        Employee reloadedEmployee = em.find(Employee.class, employee.getId());
        Assert.assertNotNull(reloadedEmployee);

        // Detaching all entities
        em.clear();

        // After a clear, the Entitymanager is forced to reload the Employee
        reloadedEmployee = em.find(Employee.class, employee.getId());
        Assert.assertNull(reloadedEmployee);
    }
}
