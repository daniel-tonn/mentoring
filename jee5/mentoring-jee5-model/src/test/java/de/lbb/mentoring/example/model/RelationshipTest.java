package de.lbb.mentoring.example.model;

import de.lbb.mentoring.example.model.testdatabuilder.DepartmentBuilder;
import de.lbb.mentoring.example.model.testdatabuilder.EmployeeBuilder;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class RelationshipTest extends AbstractEntityTest
{

    /**
     * Bidrectional relationship: handling owning side successfully
     */
    @Test
    public void testEmployeeDepartment()
    {
        Employee employee = new EmployeeBuilder().withAge(30).withName("Hans").withSurename("Mueller").build();
        // saving Employee
        em.getTransaction().begin();
        em.persist(employee);
        em.getTransaction().commit();

        // Creating Department
        Department department = new DepartmentBuilder().withName("Abteilung 1").build();
        employee.setDepartment(department);
        em.getTransaction().begin();
        em.persist(employee);
        em.getTransaction().commit();

        // loading Employee from new EM
        EntityManager otherEM = emf.createEntityManager();
        Employee reloadedEmployee = otherEM.find(Employee.class, employee.getId());
        Assert.assertNotNull(reloadedEmployee.getDepartment());

    }

    /**
     * Bidrectional relationship: handling inverse side with failures
     */
    @Test
    public void testDepartmentEmployees()
    {
        // Creating Department
        Department department = new DepartmentBuilder().withName("Abteilung 1").build();
        // saving Department
        em.getTransaction().begin();
        em.persist(department);
        em.getTransaction().commit();

        Employee employee = new EmployeeBuilder().withAge(30).withName("Hans").withSurename("Mueller").build();

        List<Employee> empls = new ArrayList<Employee>();
        empls.add(employee);
        department.setEmployees(empls);
        em.getTransaction().begin();
        em.persist(department);
        em.getTransaction().commit();

        // loading Department from new EM
        EntityManager otherEM = emf.createEntityManager();
        Department reloadedDepartment = otherEM.find(Department.class, department.getId());
        // Employee-Relationship not maintained by jpa
        Assert.assertTrue("No employees expected!", reloadedDepartment.getEmployees().isEmpty());

    }
}
