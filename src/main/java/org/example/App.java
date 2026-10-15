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

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class App {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hibernate-inheritance");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        Voiture voiture = new Voiture("Peugeot", 5);
        Moto moto = new Moto("Yamaha", false);
        em.persist(voiture);
        em.persist(moto);

        Developpeur dev = new Developpeur("Taha", "Python");
        Manager manager = new Manager("Karim", 2);
        em.persist(dev);
        em.persist(manager);

        Livre livre = new Livre("Effective Java", "Joshua Bloch");
        Electronique ecran = new Electronique("Ecran Samsung", 36);
        em.persist(livre);
        em.persist(ecran);

        em.getTransaction().commit();

        System.out.println("--- Vehicules ---");
        List<Vehicule> vehicules = em.createQuery("SELECT v FROM Vehicule v", Vehicule.class).getResultList();
        for (Vehicule v : vehicules) {
            System.out.println(v.getClass().getSimpleName() + " : " + v.getMarque());
        }

        System.out.println("--- Employes ---");
        List<Employe> employes = em.createQuery("SELECT e FROM Employe e", Employe.class).getResultList();
        for (Employe e : employes) {
            System.out.println(e.getClass().getSimpleName() + " : " + e.getNom());
        }

        System.out.println("--- Produits ---");
        List<Produit> produits = em.createQuery("SELECT p FROM Produit p", Produit.class).getResultList();
        for (Produit p : produits) {
            System.out.println(p.getClass().getSimpleName() + " : " + p.getNom());
        }

        em.close();
        emf.close();
    }
}
