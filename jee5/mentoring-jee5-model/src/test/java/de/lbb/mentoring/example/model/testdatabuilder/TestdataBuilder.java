package de.lbb.mentoring.example.model.testdatabuilder;

import de.lbb.mentoring.example.model.Department;
import de.lbb.mentoring.example.model.Employee;
import de.lbb.mentoring.example.model.ParttimeEmployee;

public class TestdataBuilder
{
    public static Employee createEmployee(String name, String surename) {
        Employee employee = new Employee();
        employee.setName(name);
        employee.setSurename(surename);

        return employee;
    }

    public static Employee createParttimeEmployee(String name, String surename) {
        Employee employee = new ParttimeEmployee();
        employee.setName(name);
        employee.setSurename(surename);

        return employee;
    }

    public static Department createDepartment(String name) {
        Department department = new Department();
        department.setName(name);

        return department;
    }


}
