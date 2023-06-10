package fr.projetNosql.lib;

import org.bson.types.ObjectId;

public class Skipper {

    ObjectId id;
    String nom;
    String prenom;
    int tarif;

    public Skipper(ObjectId id, String nom, String prenom, int tarif) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.tarif = tarif;
    }
    public ObjectId getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public int getTarif() {
        return tarif;
    }

}
