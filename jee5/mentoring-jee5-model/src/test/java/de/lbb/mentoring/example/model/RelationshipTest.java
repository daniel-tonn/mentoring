package de.lbb.mentoring.example.model;

import de.lbb.mentoring.example.model.testdatabuilder.DepartmentBuilder;
import de.lbb.mentoring.example.model.testdatabuilder.EmployeeBuilder;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class RelationshipTest extends AbstractEntityTest
{

    /**
     * Bidrectional relationship:
     * handling owning side successfully
     * handling inverse side with failures
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

        // loading Employee
        // employee is aware about his new Department
        Employee reloadedEmployee = em.find(Employee.class, employee.getId());
        Assert.assertNotNull(reloadedEmployee.getDepartment());


        // Reloading the department
        // department knows nothing about its employees
        Department reloadedDepartment = em.find(Department.class, department.getId()) ;
        Assert.assertNull(reloadedDepartment.getEmployees());

        // But loading same Department from different EM will work
        EntityManager otherEM = emf.createEntityManager();
        reloadedDepartment = otherEM.find(Department.class, department.getId()) ;
        Assert.assertNotNull(reloadedDepartment.getEmployees());
        Assert.assertEquals(1, reloadedDepartment.getEmployees().size());
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

        // loading Department
        Department reloadedDepartment = em.find(Department.class, department.getId());
        Assert.assertNotNull(reloadedDepartment.getEmployees());
        Assert.assertEquals(1, reloadedDepartment.getEmployees().size());

        // loading the Department from a new EM
        EntityManager otherEM = emf.createEntityManager();
        reloadedDepartment = otherEM.find(Department.class, department.getId());
        Assert.assertEquals(0, reloadedDepartment.getEmployees().size());
    }
}
