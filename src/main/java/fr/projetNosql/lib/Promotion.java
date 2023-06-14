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

    public Promotion(String code, int montant) {
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

    public void setCode(String code) {
        this.code = code;
    }

    public void setMontant(int montant) {
        this.montant = montant;
    }
}
