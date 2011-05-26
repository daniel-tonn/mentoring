package de.lbb.mentoring.example.model.testdatabuilder;

import de.lbb.mentoring.example.model.Employee;


public class EmployeeBuilder
{
    private String name = null;
    private String surename = null;
    private int age = 0;

    public EmployeeBuilder()
    {
    }

    public EmployeeBuilder withName(String name)
    {
        this.name = name;
        return this;
    }

    public EmployeeBuilder withSurename(String surename)
    {
        this.surename = surename;
        return this;
    }

    public EmployeeBuilder withAge(int age)
    {
        this.age = age;
        return this;
    }

    public Employee build()
    {
        Employee employee = new Employee();
        employee.setAge(this.age);
        if (this.name != null)
            employee.setName(this.name);
        if (this.surename != null)
            employee.setSurename(this.surename);

        return employee;
    }
}
