package de.lbb.mentoring.example.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.io.Serializable;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class AbstractEntity implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE) // Skipping Table strategy makes isolationlevel test on hsqldb fail
    private Long id;

    @Version
    private Long version;

    public Long getId() {
        return id;
    }
}
