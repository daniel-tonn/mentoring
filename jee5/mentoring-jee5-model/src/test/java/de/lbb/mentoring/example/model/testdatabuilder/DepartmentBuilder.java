package de.lbb.mentoring.example.model.testdatabuilder;

import de.lbb.mentoring.example.model.Department;


public class DepartmentBuilder
{
    protected String name = null;
    protected String surename = null;
    protected int age = 0;

    public DepartmentBuilder()
    {
    }

    public DepartmentBuilder withName(String name)
    {
        this.name = name;
        return this;
    }

    public Department build()
    {
        Department department = new Department();
        if (this.name != null)
            department.setName(this.name);

        return department;
    }
}
