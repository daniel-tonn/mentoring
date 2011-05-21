package de.lbb.mentoring.example.model;

import javax.persistence.*;

@Entity
public class Employee
{
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String surename;
    private int age;

    @ManyToOne
    private Department department;

    @Version
    private Long version;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurename() {
        return surename;
    }

    public void setSurename(String surename) {
        this.surename = surename;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Long getId() {
        return id;
    }

}
