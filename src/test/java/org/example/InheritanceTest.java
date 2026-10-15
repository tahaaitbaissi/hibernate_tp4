package org.example;

import org.example.model.joined.Developpeur;
import org.example.model.joined.Employe;
import org.example.model.joined.Manager;
import org.example.model.singletable.Moto;
import org.example.model.singletable.Vehicule;
import org.example.model.singletable.Voiture;
import org.example.model.tableperclass.Electronique;
import org.example.model.tableperclass.Livre;
import org.example.model.tableperclass.Produit;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

import static org.junit.Assert.*;

public class InheritanceTest {

    private static EntityManagerFactory emf;

    @BeforeClass
    public static void init() {
        emf = Persistence.createEntityManagerFactory("hibernate-inheritance");
    }

    @AfterClass
    public static void close() {
        if (emf != null) {
            emf.close();
        }
    }

    @Test
    public void testSingleTable() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(new Voiture("Renault", 3));
        em.persist(new Moto("Honda", true));
        em.getTransaction().commit();

        List<Vehicule> list = em.createQuery("SELECT v FROM Vehicule v", Vehicule.class).getResultList();
        assertTrue(list.size() >= 2);

        long voitures = list.stream().filter(v -> v instanceof Voiture).count();
        long motos = list.stream().filter(v -> v instanceof Moto).count();
        assertTrue(voitures >= 1);
        assertTrue(motos >= 1);
        em.close();
    }

    @Test
    public void testJoined() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(new Developpeur("Salma", "TypeScript"));
        em.persist(new Manager("Omar", 4));
        em.getTransaction().commit();

        List<Employe> list = em.createQuery("SELECT e FROM Employe e", Employe.class).getResultList();
        assertTrue(list.size() >= 2);

        Employe dev = em.createQuery("SELECT e FROM Employe e WHERE e.nom = :nom", Employe.class)
                .setParameter("nom", "Salma")
                .getSingleResult();
        assertTrue(dev instanceof Developpeur);
        assertEquals("TypeScript", ((Developpeur) dev).getLangage());
        em.close();
    }

    @Test
    public void testTablePerClass() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(new Livre("DDD", "Eric Evans"));
        em.persist(new Electronique("Ecran LG", 12));
        em.getTransaction().commit();

        List<Produit> list = em.createQuery("SELECT p FROM Produit p", Produit.class).getResultList();
        assertTrue(list.size() >= 2);

        List<Livre> livres = em.createQuery("SELECT l FROM Livre l", Livre.class).getResultList();
        assertFalse(livres.isEmpty());

        Electronique found = em.createQuery("SELECT e FROM Electronique e WHERE e.nom = :nom", Electronique.class)
                .setParameter("nom", "Ecran LG")
                .getSingleResult();
        assertEquals(12, found.getGarantieMois());
        em.close();
    }
}
