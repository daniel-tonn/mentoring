package de.lbb.mentoring.example.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.io.Serializable;

@MappedSuperclass
@Inheritance(strategy = InheritanceType.JOINED)
public class AbstractEntity implements Serializable
{
    /**
     * Beware: primitive types for ID have a default value (e.g. int = 0), so having an ID without
     * @GeneratedValue but with a primitve type will successfully be inserted into the db ... at least
     * for the very first time :-)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE) // Skipping Table strategy makes isolationlevel test on hsqldb fail
    private Long id;

    @Version
    private Long version;

    public Long getId() {
        return id;
    }
}
