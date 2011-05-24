package de.lbb.mentoring.example.model;


import javax.persistence.*;
import java.util.Date;

@Entity
public class ParttimeEmployee extends Employee
{
    @Temporal(value = TemporalType.DATE)
    // Try propertyname of 'from'
    private Date fromDate;
    @Temporal(value = TemporalType.DATE)
    private Date toDate;

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
