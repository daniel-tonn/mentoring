package de.lbb.mentoring.example.model.valueobjects;

/**
 * A ValueObject for the Employee
 */
public class EmployeeVO
{
    private String name;
    private String surename;

    public EmployeeVO(String name, String surename)
    {
        this.name = name;
        this.surename = surename;
    }

    public String getName() {
        return name;
    }

    public String getSurename() {
        return surename;
    }

}
