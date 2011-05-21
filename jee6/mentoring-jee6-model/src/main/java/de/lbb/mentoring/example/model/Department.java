package de.lbb.mentoring.example.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Department
{
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToMany
    private List<Employee> employees;

    @Version
    private Long version;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

}
