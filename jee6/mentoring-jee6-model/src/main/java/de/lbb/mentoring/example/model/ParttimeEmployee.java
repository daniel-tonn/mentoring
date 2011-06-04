package de.lbb.mentoring.example.model;


import de.lbb.mentoring.example.model.Employee;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity
public class ParttimeEmployee extends Employee
{
    @Temporal(value = TemporalType.DATE)
    // Try propertyname of 'from'
    private Date fromDate;
    @Temporal(value = TemporalType.DATE)
    private Date toDate;

    /**
     * to be JPA conform
     */
    public ParttimeEmployee() {}

    public Date getFrom() {
        return fromDate;
    }

    public void setFrom(Date from) {
        this.fromDate = from;
    }

    public Date getTo() {
        return toDate;
    }

    public void setTo(Date to) {
        this.toDate = to;
    }
}
