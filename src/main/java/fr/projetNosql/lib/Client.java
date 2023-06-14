package fr.projetNosql.lib;

import org.bson.types.ObjectId;

public class Client {

    private ObjectId id;
    private String nom;
    private String prenom;
    private String adresse;
    private String codePostal;
    private String ville;
    private String complement;
    private String telephone;
    private String email;
    private String motDePasse;

    public Client(ObjectId id,String nom, String prenom, String adresse, String codePostal, String ville, String complement, String telephone, String email, String motDePasse) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.codePostal = codePostal;
        this.ville = ville;
        this.complement = complement;
        this.telephone = telephone;
        this.email = email;
        this.motDePasse = motDePasse;
    }

    public Client(String nom, String prenom, String adresse, String codePostal, String ville, String complement, String telephone, String email, String motDePasse) {
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.codePostal = codePostal;
        this.ville = ville;
        this.complement = complement;
        this.telephone = telephone;
        this.email = email;
        this.motDePasse = motDePasse;
    }

    public ObjectId getId() {
        return id;
    }
    public String getPrenom() {
        return prenom;
    }

    public String getNom() {
        return nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public String getVille() {
        return ville;
    }

    public String getComplement() {
        return complement;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getEmail() {
        return email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.nom = prenom;
    }

    public void setAdresse(String adresse) {
        this.nom = adresse;
    }

    public void setTelephone(String telephone) {
        this.nom = telephone;
    }

    public void setEmail(String email) {
        this.nom = email;
    }
}
