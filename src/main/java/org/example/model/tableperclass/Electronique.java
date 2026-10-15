package org.example.model.tableperclass;

import javax.persistence.Entity;

@Entity
public class Electronique extends Produit {

    private int garantieMois;

    public Electronique() {
    }

    public Electronique(String nom, int garantieMois) {
        super(nom);
        this.garantieMois = garantieMois;
    }

    public int getGarantieMois() {
        return garantieMois;
    }

    public void setGarantieMois(int garantieMois) {
        this.garantieMois = garantieMois;
    }
}
