package de.lbb.mentoring.example.model;

import de.lbb.mentoring.example.model.testdatabuilder.DepartmentBuilder;
import de.lbb.mentoring.example.model.testdatabuilder.InsuranceBuilder;
import de.lbb.mentoring.example.model.testdatabuilder.PartTimeEmployeeBuilder;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * Tests for trying to reproduce hibernate proxy generation and the instanceof problem with it.
 *
 */
public class HibernateProxyTest extends AbstractEntityTest
{
    @Test
    public void testHibernateNoProxygeneration()
    {
        Employee employee = new PartTimeEmployeeBuilder().withAge(30).withName("Hans").withSurename("Mueller").build();
        Department department = new DepartmentBuilder().withName("Abteilung 1").build();
        employee.setDepartment(department);

        // saving Employee
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
    public void testHibernateProxyGeneration()
    {
        Employee employee = new PartTimeEmployeeBuilder().withAge(30).withName("Hans").withSurename("Mueller").build();
        Insurance insurance = new InsuranceBuilder().build();
        insurance.setEmployee(employee);
        // saving
        em.getTransaction().begin();
        em.persist(insurance);
        em.getTransaction().commit();

        // loading Insurance from new EM
        EntityManager otherEM = emf.createEntityManager();
        Insurance reloadedInsurance = otherEM.find(Insurance.class, insurance.getId());

        // Since reloadedEmployee is proxied from Hibernate, the following instanceof
        // fails
        Assert.assertFalse(reloadedInsurance.getEmployee() instanceof ParttimeEmployee);

    }

}
