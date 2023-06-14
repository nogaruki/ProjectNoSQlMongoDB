package fr.projetNosql.lib;

import org.bson.types.ObjectId;

public class Materiel {

    ObjectId id;
    String nom;
    int prix;
    int caution;
    public Materiel(ObjectId id, String nom, int prix, int caution) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.caution = caution;
    }

    public Materiel(String nom, int prix, int caution) {
        this.nom = nom;
        this.prix = prix;
        this.caution = caution;
    }

    public ObjectId getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }


    public int getPrix() {
        return prix;
    }

    public int getCaution() {
        return caution;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public void setCaution(int caution) {
        this.caution = caution;
    }
}
