package fr.projetNosql.lib;

import org.bson.types.ObjectId;

public class Bateau extends TypeBateau {

    private ObjectId idBateau;
    private int nbPlace;
    private int puissance;
    private int caution;

    public Bateau(ObjectId idBateau, int nbPlace, int puissance, int caution, ObjectId idTypeBateau, String nomTypeBateau) {
        super(idTypeBateau, nomTypeBateau);
        this.idBateau = idBateau;
        this.nbPlace = nbPlace;
        this.puissance = puissance;
        this.caution = caution;
    }

    public Bateau(int nbPlace, int puissance, int caution, ObjectId idTypeBateau, String nomTypeBateau) {
        super(idTypeBateau, nomTypeBateau);
        this.nbPlace = nbPlace;
        this.puissance = puissance;
        this.caution = caution;
    }

    public ObjectId getIdBateau() {
        return idBateau;
    }

    public int getNbPlace() {
        return nbPlace;
    }

    public int getPuissance() {
        return puissance;
    }

    public int getCaution() {
        return caution;
    }

    public void setNbPersonne(int nbPersonne) {
        this.nbPlace = nbPersonne;
    }

    public void setPuissance(int puissance) {
        this.puissance = puissance;
    }

    public void setCaution(int caution) {
        this.caution = caution;
    }

    public void setTypeBateau(TypeBateau typeBateau) {
        this.idTypeBateau = typeBateau.getIdTypeBateau();
        this.nomTypeBateau = typeBateau.getNomTypeBateau();
    }
}
