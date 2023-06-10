package fr.projetNosql.lib;

import org.bson.types.ObjectId;

public class Promotion {

    ObjectId id;
    String code;
    int montant;

    public Promotion(ObjectId id, String code, int montant) {
        this.id = id;
        this.code = code;
        this.montant = montant;
    }

    public ObjectId getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public int getMontant() {
        return montant;
    }
}
