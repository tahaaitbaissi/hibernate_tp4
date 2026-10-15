package org.example.model.joined;

import javax.persistence.Entity;

@Entity
public class Manager extends Employe {

    private int nbEquipes;

    public Manager() {
    }

    public Manager(String nom, int nbEquipes) {
        super(nom);
        this.nbEquipes = nbEquipes;
    }

    public int getNbEquipes() {
        return nbEquipes;
    }

    public void setNbEquipes(int nbEquipes) {
        this.nbEquipes = nbEquipes;
    }
}
