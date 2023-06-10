package fr.projetNosql.lib;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Indexes;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.mongodb.client.model.Filters.eq;

public class Cluster {

    // Replace the placeholder with your MongoDB deployment's connection string
    private String uri = "mongodb+srv://johann:PEmuDpCycGt0Eco1@clusterprojetnosql.qwvo5gy.mongodb.net/";
    private MongoClient mongoClient;

    private MongoDatabase database;

    public Cluster() {
        connect();
    }

    private void connect() {
        try {
            MongoClient mongoClient = MongoClients.create(uri);
            database = mongoClient.getDatabase("LocationBateaux");
            System.out.println("Connected to the database successfully");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    public MongoClient getMongoClient() {
        return mongoClient;
    }

    public MongoDatabase getDatabase() {
        return database;
    }

    // Client
    public void insertClient(Client client) {
        MongoCollection<Document> collection = database.getCollection("Client");
        Document document = new Document("nom", client.getNom())
                .append("prenom", client.getPrenom())
                .append("adresse", client.getAdresse())
                .append("codePostal", client.getCodePostal())
                .append("ville", client.getVille())
                .append("complement", client.getComplement())
                .append("telephone", client.getTelephone())
                .append("email", client.getEmail())
                .append("motDePasse", client.getMotDePasse());
        collection.insertOne(document);
    }

    public void deleteClient(Client client) {
        MongoCollection<Document> collection = database.getCollection("Client");
        collection.deleteOne(eq("email", client.getEmail()));
    }

    public void updateClient(Client client) {
        MongoCollection<Document> collection = database.getCollection("Client");
        collection.updateOne(eq("email", client.getEmail()), new Document("$set", new Document("nom", client.getNom())
                .append("prenom", client.getPrenom())
                .append("adresse", client.getAdresse())
                .append("codePostal", client.getCodePostal())
                .append("ville", client.getVille())
                .append("complement", client.getComplement())
                .append("telephone", client.getTelephone())
                .append("email", client.getEmail())
                .append("motDePasse", client.getMotDePasse())));
    }

    public Client getOneClient(ObjectId clientID) {
        MongoCollection<Document> collection = database.getCollection("Client");
        Document document = collection.find(eq("_id", clientID)).first();
        return new Client(document.getObjectId("_id"), document.getString("nom"), document.getString("prenom"), document.getString("adresse"), document.getString("codePostal"), document.getString("ville"), document.getString("complement"), document.getString("telephone"), document.getString("email"), document.getString("motDePasse"));

    }

    public ArrayList<Client> getAllClient() {
        MongoCollection<Document> collection = database.getCollection("Client");

        ArrayList<Client> clients = new ArrayList<>();
        for (Document document : collection.find()) {
            clients.add(new Client(document.getObjectId("_id"), document.getString("nom"), document.getString("prenom"), document.getString("adresse"), document.getString("codePostal"), document.getString("ville"), document.getString("complement"), document.getString("telephone"), document.getString("email"), document.getString("motDePasse")));
        }
        return clients;
    }

    //Type Bateau
    public void insertTypeBateau(TypeBateau typeBateau) {
        MongoCollection<Document> collection = database.getCollection("TypeBateau");
        Document document = new Document("nomTypeBateau", typeBateau.getNomTypeBateau());
        collection.insertOne(document);
    }

    public void deleteTypeBateau(TypeBateau typeBateau) {
        MongoCollection<Document> collection = database.getCollection("TypeBateau");
        collection.deleteOne(eq("_id", typeBateau.getIdTypeBateau()));
    }

    public void updateTypeBateau(TypeBateau typeBateau) {
        MongoCollection<Document> collection = database.getCollection("TypeBateau");
        collection.updateOne(eq("_id", typeBateau.getIdTypeBateau()), new Document("$set", new Document("nomTypeBateau", typeBateau.getNomTypeBateau())));
    }

    public TypeBateau getOneTypeBateau(ObjectId typeBateauID) {
        MongoCollection<Document> collection = database.getCollection("TypeBateau");
        Document document = collection.find(eq("_id", typeBateauID)).first();
        return new TypeBateau(document.getObjectId("_id"), document.getString("nomTypeBateau"));
    }

    public ArrayList<TypeBateau> getAllTypeBateau() {
        MongoCollection<Document> collection = database.getCollection("TypeBateau");
        ArrayList<TypeBateau> typeBateaux = new ArrayList<>();
        for (Document document : collection.find()) {
            typeBateaux.add(new TypeBateau(document.getObjectId("_id"), document.getString("nomTypeBateau")));
        }
        return typeBateaux;
    }

    //Bateau

    public void insertBateau(Bateau bateau) {
        MongoCollection<Document> collection = database.getCollection("Bateau");
        Document document = new Document("nbPlace", bateau.getNbPlace())
                .append("caution", bateau.getCaution())
                .append("puissanceMoteur", bateau.getPuissance());
        collection.insertOne(document);
    }

    public void deleteBateau(Bateau bateau) {
        MongoCollection<Document> collection = database.getCollection("Bateau");
        collection.deleteOne(eq("_id", bateau.getIdBateau()));
    }

    public void updateBateau(Bateau bateau) {
        MongoCollection<Document> collection = database.getCollection("Bateau");
        collection.updateOne(eq("_id", bateau.getIdBateau()), new Document("$set", new Document("nbPlace", bateau.getNbPlace())
                .append("caution", bateau.getCaution())
                .append("puissanceMoteur", bateau.getPuissance())
                .append("idTypeBateau", bateau.getIdTypeBateau())));
    }

    public Bateau getOneBateau(ObjectId bateauID) {
        MongoCollection<Document> collection = database.getCollection("Bateau");
        MongoCollection<Document> collectionTypeBateau = database.getCollection("TypeBateau");
        Document document = collection.find(eq("_id", bateauID)).first();
        Document documentTypeBateau = collectionTypeBateau.find(eq("_id", document.getObjectId("idTypeBateau"))).first();
        return new Bateau(document.getObjectId("_id"), document.getInteger("nbPlace"), document.getInteger("caution"), document.getInteger("puissanceMoteur"), documentTypeBateau.getObjectId("_id"), documentTypeBateau.getString("nomTypeBateau"));
    }

    public ArrayList<Bateau> getAllBateau() {
        MongoCollection<Document> collection = database.getCollection("Bateau");
        MongoCollection<Document> collectionTypeBateau = database.getCollection("TypeBateau");

        ArrayList<Bateau> bateaux = new ArrayList<>();
        for (Document document : collection.find()) {
            Document documentTypeBateau = collectionTypeBateau.find(eq("_id", document.getObjectId("idTypeBateau"))).first();
            bateaux.add(new Bateau(document.getObjectId("_id"), document.getInteger("nbPlace"), document.getInteger("caution"), document.getInteger("puissanceMoteur"), documentTypeBateau.getObjectId("_id"), documentTypeBateau.getString("nomTypeBateau")));
        }
        return bateaux;
    }

    //Skipper

    public void insertSkipper(Skipper skipper) {
        MongoCollection<Document> collection = database.getCollection("Skipper");
        Document document = new Document("nom", skipper.getNom())
                .append("prenom", skipper.getPrenom())
                .append("tarif", skipper.getTarif());
        collection.insertOne(document);
    }

    public void deleteSkipper(Skipper skipper) {
        MongoCollection<Document> collection = database.getCollection("Skipper");
        collection.deleteOne(eq("_id", skipper.getId()));
    }

    public void updateSkipper(Skipper skipper) {
        MongoCollection<Document> collection = database.getCollection("Skipper");
        collection.updateOne(eq("_id", skipper.getId()), new Document("$set", new Document("nom", skipper.getNom())
                .append("prenom", skipper.getPrenom())
                .append("tarif", skipper.getTarif())));
    }

    public Skipper getOneSkipper(ObjectId skipperID) {
        MongoCollection<Document> collection = database.getCollection("Skipper");
        Document document = collection.find(eq("_id", skipperID)).first();
        Skipper skipper = new Skipper(document.getObjectId("_id"),document.getString("nom"), document.getString("prenom"), document.getInteger("tarif"));
        return skipper;
    }

    public ArrayList<Skipper> getAllSkipper() {
        MongoCollection<Document> collection = database.getCollection("Skipper");
        ArrayList<Skipper> skipperList = new ArrayList<>();
        for (Document document : collection.find()) {
            Skipper skipper = new Skipper(document.getObjectId("_id"),document.getString("nom"), document.getString("prenom"), document.getInteger("tarif"));
            skipperList.add(skipper);
        }
        return skipperList;
    }

    //Promotion
    public void insertPromotion(Promotion promotion) {
        MongoCollection<Document> collection = database.getCollection("Promotion");
        Document document = new Document("nom", promotion.getCode())
                .append("montant", promotion.getMontant());
        collection.insertOne(document);
    }

    public void deletePromotion(Promotion promotion) {
        MongoCollection<Document> collection = database.getCollection("Promotion");
        collection.deleteOne(eq("_id", promotion.getId()));
    }

    public void updatePromotion(Promotion promotion) {
        MongoCollection<Document> collection = database.getCollection("Promotion");
        collection.updateOne(eq("_id", promotion.getId()), new Document("$set", new Document("nom", promotion.getCode())
                .append("montant", promotion.getMontant())));
    }

    public Promotion getOnePromotion(ObjectId promotionID) {
        MongoCollection<Document> collection = database.getCollection("Promotion");
        Document document = collection.find(eq("_id", promotionID)).first();
        Promotion promotion1 = new Promotion(document.getObjectId("_id"),document.getString("nom"), document.getInteger("montant"));
        return promotion1;

    }

    public ArrayList<Promotion> getAllPromotion() {
        MongoCollection<Document> collection = database.getCollection("Promotion");
        ArrayList<Promotion> promotionList = new ArrayList<>();
        for (Document document : collection.find()) {
            Promotion promotion = new Promotion(document.getObjectId("_id"),document.getString("nom"), document.getInteger("montant"));
            promotionList.add(promotion);
        }
        return promotionList;
    }

    //Matériel

    public void insertMateriel(Materiel materiel) {
        MongoCollection<Document> collection = database.getCollection("Materiel");
        Document document = new Document("nom", materiel.getNom())
                .append("prix", materiel.getPrix())
                .append("caution", materiel.getCaution());
        collection.insertOne(document);
    }

    public void deleteMateriel(Materiel materiel) {
        MongoCollection<Document> collection = database.getCollection("Materiel");
        collection.deleteOne(eq("_id", materiel.getId()));
    }

    public void updateMateriel(Materiel materiel) {
        MongoCollection<Document> collection = database.getCollection("Materiel");
        collection.updateOne(eq("_id", materiel.getId()), new Document("$set", new Document("nom", materiel.getNom())
                .append("prix", materiel.getPrix())
                .append("caution", materiel.getCaution())));
    }

    public Materiel getOneMateriel(ObjectId materielID) {
        MongoCollection<Document> collection = database.getCollection("Materiel");
        Document document = collection.find(eq("_id", materielID)).first();
        return new Materiel(document.getObjectId("_id"),document.getString("nom"), document.getInteger("prix"), document.getInteger("caution"));
    }

    public ArrayList<Materiel> getAllMateriel() {
        MongoCollection<Document> collection = database.getCollection("Materiel");
        ArrayList<Materiel> materielList = new ArrayList<>();
        for (Document document : collection.find()) {
            Materiel materiel = new Materiel(document.getObjectId("_id"),document.getString("nom"), document.getInteger("prix"), document.getInteger("caution"));
            materielList.add(materiel);
        }
        return materielList;
    }

    //Location
    public void insertLocation(Location location) {
        MongoCollection<Document> collection = database.getCollection("Location");
        MongoCollection<Document> collectionAvoir = database.getCollection("Avoir");

        Document docLoc = new Document("dateDebut", location.getDateDebut())
                .append("dateFin", location.getDateFin())
                .append("prix", location.getPrix())
                .append("idClient", location.getClient().getId())
                .append("idBateau", location.getBateau().getIdBateau())
                .append("idSkipper", location.getSkipper().getId())
                .append("idPromotion", location.getPromotion().getId());
        collection.insertOne(docLoc);

        // get id location
        ObjectId idLocation = docLoc.getObjectId("_id");

        // insert avoir
        for(Map.Entry<Materiel, Integer> entry : location.getMateriels().entrySet()) {
            Document docAvoir = new Document("idLocation", idLocation)
                    .append("idMateriel", entry.getKey().getId())
                    .append("quantite", entry.getValue());
            collectionAvoir.insertOne(docAvoir);
        }
    }

    public void deleteLocation(Location location) {
        MongoCollection<Document> collection = database.getCollection("Location");
        MongoCollection<Document> collectionAvoir = database.getCollection("Avoir");
        collectionAvoir.deleteMany(eq("idLocation", location.getId()));
        collection.deleteOne(eq("_id", location.getId()));
    }

    public void updateLocation(Location location) {
        MongoCollection<Document> collection = database.getCollection("Location");
        MongoCollection<Document> collectionAvoir = database.getCollection("Avoir");
        collectionAvoir.deleteMany(eq("idLocation", location.getId()));
        collection.deleteOne(eq("_id", location.getId()));
        insertLocation(location);
    }

    public Location getOneLocation(ObjectId locationID) {
        MongoCollection<Document> collection = database.getCollection("Location");
        MongoCollection<Document> collectionAvoir = database.getCollection("Avoir");
        Document document =  collection.find(eq("_id", locationID)).first();
        Client client = getOneClient(document.getObjectId("idClient"));
        Bateau bateau = getOneBateau(document.getObjectId("idBateau"));
        Skipper skipper = getOneSkipper(document.getObjectId("idSkipper"));
        Promotion promotion = getOnePromotion(document.getObjectId("idPromotion"));
        HashMap<Materiel, Integer> materiels = new HashMap<>();
        for (Document doc : collectionAvoir.find(eq("idLocation", locationID))) {
            ObjectId idMateriel = doc.getObjectId("idMateriel");
            int quantite = doc.getInteger("quantite");
            Materiel materiel = getOneMateriel(idMateriel);
            materiels.put(materiel, quantite);
        }
        return new Location(document.getObjectId("_id"),document.getInteger("nbPersonne"),document.getDate("dateDebut"), document.getDate("dateFin"), document.getInteger("forfait"), document.getInteger("prix"), promotion, skipper, bateau, client, materiels);
    }

    public ArrayList<Location> getAllLocation() {
        MongoCollection<Document> collection = database.getCollection("Location");
        ArrayList<Location> locations = new ArrayList<>();
        MongoCollection<Document> collectionAvoir = database.getCollection("Avoir");
        for (Document doc : collection.find()) {
            ObjectId idLocation = doc.getObjectId("_id");
            Client client = getOneClient(doc.getObjectId("idClient"));
            Bateau bateau = getOneBateau(doc.getObjectId("idBateau"));
            Skipper skipper = getOneSkipper(doc.getObjectId("idSkipper"));
            Promotion promotion = getOnePromotion(doc.getObjectId("idPromotion"));
            HashMap<Materiel, Integer> materiels = new HashMap<>();
            for (Document docAvoir : collectionAvoir.find(eq("idLocation", idLocation))) {
                ObjectId idMateriel = docAvoir.getObjectId("idMateriel");
                int quantite = docAvoir.getInteger("quantite");
                Materiel materiel = getOneMateriel(idMateriel);
                materiels.put(materiel, quantite);
            }
            Location location = new Location(doc.getObjectId("_id"),doc.getInteger("nbPersonne"),doc.getDate("dateDebut"), doc.getDate("dateFin"), doc.getInteger("forfait"), doc.getInteger("prix"), promotion, skipper, bateau, client, materiels);
            locations.add(location);
        }
        return locations;
    }
}
