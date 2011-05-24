package de.lbb.mentoring.example.model;

import org.junit.Before;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class AbstractEntityTest
{

    protected EntityManagerFactory emf;
    protected EntityManager em;

    @Before
    public void setup()
    {
//        emf = Persistence.createEntityManagerFactory("DataModel_MYSQL");
        emf = Persistence.createEntityManagerFactory("DataModel");
        em = emf.createEntityManager();
    }

}
