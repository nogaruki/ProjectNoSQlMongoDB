package fr.projetNosql.lib;

import org.bson.types.ObjectId;

public class TypeBateau {

    ObjectId idTypeBateau;
    String nomTypeBateau;

    public TypeBateau(ObjectId idTypeBateau, String nomTypeBateau) {
        this.idTypeBateau = idTypeBateau;
        this.nomTypeBateau = nomTypeBateau;
    }

    public ObjectId getIdTypeBateau() {
        return idTypeBateau;
    }

    public String getNomTypeBateau() {
        return nomTypeBateau;
    }

}

