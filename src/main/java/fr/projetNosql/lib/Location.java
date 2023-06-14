package fr.projetNosql.lib;

import org.bson.types.ObjectId;

import java.util.*;

public class Location {

    ObjectId id;
    int nbPersonne;
    Date dateDebut;
    Date dateFin;
    int forfait;
    int prix;
    Promotion promotion;
    Skipper skipper;
    Bateau bateau;
    Client client;
    HashMap<Materiel, Integer> materiels = new HashMap<>();

    public Location(ObjectId id, int nbPersonne, Date dateDebut, Date dateFin, int forfait, int prix, Promotion promotion, Skipper skipper, Bateau bateau, Client client, HashMap<Materiel, Integer> materiels) {
        this.id = id;
        this.nbPersonne = nbPersonne;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.forfait = forfait;
        this.prix = prix;
        this.promotion = promotion;
        this.skipper = skipper;
        this.bateau = bateau;
        this.client = client;
        this.materiels = materiels;
    }

    public Location(int nbPersonne, Date dateDebut, Date dateFin, int forfait, int prix, Promotion promotion, Skipper skipper, Bateau bateau, Client client, HashMap<Materiel, Integer> materiels) {
        this.nbPersonne = nbPersonne;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.forfait = forfait;
        this.prix = prix;
        this.promotion = promotion;
        this.skipper = skipper;
        this.bateau = bateau;
        this.client = client;
        this.materiels = materiels;
    }

    public ObjectId getId() {
        return id;
    }

    public int getNbPersonne() {
        return nbPersonne;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public int getForfait() {
        return forfait;
    }

    public int getPrix() {
        return prix;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public Skipper getSkipper() {
        return skipper;
    }

    public Bateau getBateau() {
        return bateau;
    }

    public Client getClient() {
        return client;
    }

    public HashMap<Materiel, Integer> getMateriels() {
        return materiels;
    }

}
