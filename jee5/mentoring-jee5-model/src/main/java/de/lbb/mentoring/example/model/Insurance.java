package de.lbb.mentoring.example.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

@Entity
public class Insurance extends AbstractEntity
{
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY) // LAZY is default in JPA
    private Employee employee;

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
