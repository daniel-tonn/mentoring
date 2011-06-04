package de.lbb.mentoring.example.model;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Requirements for a JPA-Entity from the spec:
 *
 * The entity class must be annotated with the Entity annotation or denoted in the XML descriptor as an
 * entity.
 * The entity class must have a no-arg constructor. The entity class may have other constructors as well.
 * The no-arg constructor must be public or protected.
 * The entity class must be a top-level class. An enum or interface should not be designated as an entity.
 * The entity class must not be final. No methods or persistent instance variables of the entity class may be
 * final.
 * If an entity instance is to be passed by value as a detached object (e.g., through a remote interface), the
 * entity class must implement the Serializable interface.
 */
@Entity
@Cacheable(true)
public class Employee extends AbstractEntity
{
    private String name;
    private String surename;
    private int age;

    @ManyToOne(optional = true, cascade = CascadeType.ALL)
    private Department department;

    /**
     * to be JPA conform
     */
    public Employee() {}

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
}
