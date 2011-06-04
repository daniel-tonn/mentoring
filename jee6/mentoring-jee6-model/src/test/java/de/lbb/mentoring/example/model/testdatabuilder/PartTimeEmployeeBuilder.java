package de.lbb.mentoring.example.model.testdatabuilder;

import de.lbb.mentoring.example.model.ParttimeEmployee;

import java.util.Date;


public class PartTimeEmployeeBuilder extends EmployeeBuilder
{
    protected Date fromDate = null;
    protected Date toDate = null;


    public PartTimeEmployeeBuilder()
    {
    }

    public PartTimeEmployeeBuilder withFromDate(Date fromDate)
    {
        this.fromDate = fromDate;
        return this;
    }

    public PartTimeEmployeeBuilder withToDate(Date toDate)
    {
        this.toDate = toDate;
        return this;
    }

    public ParttimeEmployee build()
    {
        ParttimeEmployee employee = new ParttimeEmployee();
        employee.setAge(this.age);
        if (this.name != null)
            employee.setName(this.name);
        if (this.surename != null)
            employee.setSurename(this.surename);

        return employee;
    }
}
