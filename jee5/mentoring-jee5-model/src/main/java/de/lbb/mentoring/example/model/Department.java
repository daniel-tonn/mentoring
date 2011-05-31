package de.lbb.mentoring.example.model;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Department extends AbstractEntity
{
    private String name;

    @OneToMany(mappedBy = "department")
    private List<Employee> employees;

    /**
     * to be JPA conform
     */
    public Department() {}

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
