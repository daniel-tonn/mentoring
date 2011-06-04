package de.lbb.mentoring.example.model.jpa2;

import de.lbb.mentoring.example.model.AbstractEntityTest;
import de.lbb.mentoring.example.model.Employee;
import de.lbb.mentoring.example.model.Employee_;
import de.lbb.mentoring.example.model.testdatabuilder.EmployeeBuilder;
import org.hibernate.cfg.annotations.QueryBinder;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class QueryTest extends AbstractEntityTest
{

    /**
     * JPA2 introduced typed queries
     */
    @Test
    public void testTypedQuery()
    {
        Employee employee1 = new EmployeeBuilder().withAge(30).withName("Hans").withSurename("Mueller").build();
        Employee employee2 = new EmployeeBuilder().withAge(30).withName("Klaus").withSurename("Schulze").build();
        // saving Employee
        em.getTransaction().begin();
        em.persist(employee1);
        em.persist(employee2);
        em.getTransaction().commit();

        TypedQuery<Employee> query = em.createQuery("from Employee", Employee.class);
        List<Employee> employees = query.getResultList();
        Assert.assertEquals(2, employees.size());

    }

    /**
     * Examples of the new and cool CriteriaBuilder
     * Using the jpa2 Metamodel API
     */
    @Test
    public void testCriteriaBuilder()
    {
        Employee employee1 = new EmployeeBuilder().withAge(30).withName("Hans").withSurename("Mueller").build();
        Employee employee2 = new EmployeeBuilder().withAge(30).withName("Klaus").withSurename("Schulze").build();
        // saving Employee
        em.getTransaction().begin();
        em.persist(employee1);
        em.persist(employee2);
        em.getTransaction().commit();

        CriteriaBuilder cb = em.getCriteriaBuilder();

        // building the query
        CriteriaQuery<Employee> criteria = cb.createQuery(Employee.class);
        Root<Employee> employeeRoot = criteria.from(Employee.class);

        // using the jpa2 metamodel              ..............
        criteria.where(cb.equal(employeeRoot.get(Employee_.name), "Klaus"));

        // running the query
        TypedQuery<Employee> query = em.createQuery(criteria);
        List<Employee> employees = query.getResultList();
        Assert.assertEquals(1, employees.size());
        Assert.assertEquals(employee2, query.getSingleResult());
    }

}
