package de.lbb.mentoring.example.model;

import de.lbb.mentoring.example.model.testdatabuilder.DepartmentBuilder;
import de.lbb.mentoring.example.model.testdatabuilder.EmployeeBuilder;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.RollbackException;

public class TransactionTest extends AbstractEntityTest
{
    /**
     * Try with different ISOLATIONLEVELs ... have a look into persistence.xml under tests
     * property: hibernate.connection.isolation
     *
     * BEWARE: If you want to check actual db insertions with f.i. MySQL you have to set the isolation
     * level on the db browser/shell aswell
     *
     * For MySQL this is done the following way:
     *
     *  SET [GLOBAL | SESSION] TRANSACTION ISOLATION LEVEL
     *  {
     *      READ UNCOMMITTED
     *    | READ COMMITTED
     *    | REPEATABLE READ
     *    | SERIALIZABLE
     *   }
     */
    @Test
    public void testIsolationLevel()
    {
Employee employee = new EmployeeBuilder().withAge(30).withName("Hans").withSurename("Mueller").build();
        Department department = new DepartmentBuilder().withName("Abteilung 1").build();
        em.getTransaction().begin();

        // From the spec:
        // If FlushModeType.COMMIT is specified,
        //flushing will occur at transaction commit; the persistence provider is permitted, but not required, to perform
        //to flush at other times.
        em.setFlushMode(FlushModeType.COMMIT);

        employee.setDepartment(department);
        em.persist(department);

        // partial flush should be visible to same EM
        Department reloadedDepartment = em.find(Department.class, department.getId());
        Assert.assertNotNull(reloadedDepartment);

        // check for flushed content in the db via second EM
        // Will fail on hsqldb, because of lack of isolationlevel implementation
        EntityManager em2 = emf.createEntityManager();
        reloadedDepartment = em2.find(Department.class, department.getId());
        Assert.assertNull("This one is failing if HSQLDB is used (here READ_UNCOMMITTED is actually used!) ...", reloadedDepartment);

        em.getTransaction().commit();
    }

    /**
     * Optimistic Locking expecting Exception
     */
    @Test(expected = RollbackException.class)
    public void testOptimisticLocking()
    {
        Employee employee = new EmployeeBuilder().withAge(30).withName("Hans").withSurename("Mueller").build();

        // saving Employee
        em.getTransaction().begin();
        em.persist(employee);
        em.getTransaction().commit();

        // fresh start
        em = emf.createEntityManager();
        EntityManager em2 = emf.createEntityManager();


        Employee reloadedEmployeInstance1 = em.find(Employee.class, employee.getId());
        Employee reloadedEmployeInstance2 = em2.find(Employee.class, employee.getId());

        // --- client 1 ---
        // saving employee1
        reloadedEmployeInstance1.setAge(100);
        em.getTransaction().begin();
        em.persist(reloadedEmployeInstance1);
        em.getTransaction().commit();

        // --- client 2 ---
        // saving employe2
        reloadedEmployeInstance2.setAge(99);
        em2.getTransaction().begin();
        em2.persist(reloadedEmployeInstance2);
        em2.getTransaction().commit();
    }

    /**
     * PessimisticLocking: setting explizit lock and causing some deadlock
     */
    @Test
    public void testPessimisticLocking()
    {
        Employee employee = new EmployeeBuilder().withAge(30).withName("Hans").withSurename("Mueller").build();

        // saving Employee
        em.getTransaction().begin();
        em.persist(employee);
        em.getTransaction().commit();

        // fresh start
        em = emf.createEntityManager();
        EntityManager em2 = emf.createEntityManager();

        em.getTransaction().begin();
        Employee reloadedEmployeInstance1 = em.find(Employee.class, employee.getId());

        // Enable to get a deadlock
//        em.lock(reloadedEmployeInstance1, LockModeType.READ);

        em2.getTransaction().begin();
        Employee reloadedEmployeInstance2 = em2.find(Employee.class, employee.getId());
        reloadedEmployeInstance2.setAge(99);
        em2.persist(reloadedEmployeInstance2);
        em2.getTransaction().commit();


        em.getTransaction().commit();
    }

}
