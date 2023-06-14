package fr.projetNosql.lib;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Indexes;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

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

    public void afficherBateauxDispo() {
        ArrayList<Bateau> bateaux = getAllBateau();
        ArrayList<Location> locations = getAllLocation();
        for (Bateau bateau : bateaux) {
            boolean dispo = true;
            for (Location location : locations) {
                if (location.getBateau().equals(bateau)) {
                    dispo = false;
                }
            }
            if (dispo) {
                System.out.println(bateau);
            }
        }

    }


    public void ajouterBateau() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Nombre de personne : ");
        int nbPersonne = sc.nextInt();
        System.out.println("Prix : ");
        int prix = sc.nextInt();
        System.out.println("Caution : ");
        int caution = sc.nextInt();
        int index = -1;
        do {
            System.out.println("Type du bateau : ");
            String typeBateau = sc.nextLine();
            index= getAllTypeBateau().indexOf(typeBateau);
        } while (index == -1);
        TypeBateau typeBateau = getAllTypeBateau().get(index);
        Bateau bateau = new Bateau(nbPersonne, prix, caution, typeBateau.idTypeBateau, typeBateau.nomTypeBateau);
        insertBateau(bateau);

        System.out.println("Bateau ajouté");
    }

    public void ajouterSkipper() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Nom: ");
        String nbPersonne = sc.nextLine();
        System.out.println("Prenom : ");
        String prix = sc.nextLine();
        System.out.println("Tarif : ");
        int caution = sc.nextInt();
        Skipper skipper = new Skipper(nbPersonne, prix, caution);
        insertSkipper(skipper);
        System.out.println("Skipper ajouté");
    }

    public void ajouterClient() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Nom: ");
        String nom = sc.nextLine();
        System.out.println("Prenom : ");
        String prenom = sc.nextLine();
        System.out.println("Adresse : ");
        String adresse = sc.nextLine();
        System.out.println("Code postal : ");
        String codePostal = sc.nextLine();
        System.out.println("Ville : ");
        String ville = sc.nextLine();
        System.out.println("Complement : ");
        String complement = sc.nextLine();
        System.out.println("Telephone : ");
        String telephone = sc.nextLine();
        System.out.println("Email : ");
        String email = sc.nextLine();
        System.out.println("Mot de passe : ");
        String motDePasse = sc.nextLine();
        Client client = new Client(nom, prenom, adresse, codePostal, ville, complement, telephone, email, motDePasse);
        insertClient(client);
        System.out.println("Client ajouté");
        
    }

    public void ajouterMateriel() {
        Scanner sc = new Scanner(System.in);


        System.out.println("Nom: ");
        String nom = sc.nextLine();
        System.out.println("Prix : ");
        int prix = sc.nextInt();
        System.out.println("Caution : ");
        int caution = sc.nextInt();
        Materiel materiel = new Materiel(nom, prix, caution);
        insertMateriel(materiel);
        System.out.println("Materiel ajouté");
    }

    public void ajouterPromotion() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Code: ");
        String code = sc.nextLine();
        System.out.println("Montant : ");
        int montant = sc.nextInt();
        Promotion promotion = new Promotion(code, montant);
        insertPromotion(promotion);
        System.out.println("Promotion ajouté");
    }

    public void ajouterLocation() {

        Scanner sc = new Scanner(System.in);
        System.out.println("Nombre de personne : ");
        int nbPersonne = sc.nextInt();
        Date dateDebut = null;
        Date dateFin = null;

        do {
            System.out.println("Date de debut (dd/MM/yyyy): ");
            String dateDebutString = sc.nextLine();
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            try {
                dateDebut = formatter.parse(dateDebutString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }while (dateDebut == null);

        do {
            System.out.println("Date de debut (dd/MM/yyyy): ");
            String dateFinString = sc.nextLine();
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            try {
                dateFin = formatter.parse(dateFinString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }while (dateFin == null);
        System.out.println("Forfait : ");
        int forfait = sc.nextInt();
        System.out.println("Prix : ");
        int prix = sc.nextInt();
        Promotion promotion = null;
        String codePromotion = null;
        do {
            System.out.println("Promotion (indiquer l'id): ");
            codePromotion= sc.nextLine();
            ObjectId idPromotion = new ObjectId(codePromotion);
            promotion = getOnePromotion(idPromotion);
            if(promotion == null && !codePromotion.equals("")){
                System.out.println("Erreur : la promotion n'existe pas");
            }
        }while (promotion == null && !codePromotion.equals(""));
        Skipper skipper = null;
        do {
            System.out.println("Skipper (indiquer l'id): ");
            String nomSkipper = sc.nextLine();
            ObjectId idSkipper = new ObjectId(nomSkipper);
            skipper = getOneSkipper(idSkipper);
            if(skipper == null){
                System.out.println("Erreur : le skipper n'existe pas");
            }
        }while (skipper == null);
        Bateau bateau = null;
        do {
            System.out.println("Bateau (indiquer l'id): ");
            String nomBateau = sc.nextLine();
            ObjectId idBateau = new ObjectId(nomBateau);
            bateau = getOneBateau(idBateau);
            if(bateau == null){
                System.out.println("Erreur : le bateau n'existe pas");
            }
        }while (bateau == null);
        Client client = null;
        do {
            System.out.println("Client (indiquer l'id): ");
            String emailClient = sc.nextLine();
            ObjectId idClient = new ObjectId(emailClient);
            client = getOneClient(idClient);
            if(client == null){
                System.out.println("Erreur : le client n'existe pas");
            }
        }while (client == null);
        String codeMateriels = null;
        HashMap<Materiel, Integer> listMateriel = new HashMap<Materiel, Integer>();
        do {
            System.out.println("Materiel (indiquer les ids séparé par ',') : ");
            System.out.println("Quantite  (indiquer les ids séparé par ',') : ");

            codeMateriels= sc.nextLine();
            ArrayList<String> listCodeMateriel = new ArrayList<String>(Arrays.asList(codeMateriels.split(",")));
            ArrayList<String> listQuantite = new ArrayList<String>(Arrays.asList(codeMateriels.split(",")));
            if(listCodeMateriel.size() != listQuantite.size()){
                System.out.println("Erreur : le nombre de materiel et de quantite ne correspond pas");
            } else {
                for (int i = 0; i < listCodeMateriel.size(); i++) {
                    Materiel materiel = null;
                    ObjectId idMateriel = new ObjectId(listCodeMateriel.get(i));
                    materiel = getOneMateriel(idMateriel);
                    if (materiel == null) {
                        System.out.println("Erreur : le materiel: '" + listCodeMateriel.get(i) + "' n'existe pas");
                        break;
                    }
                    listMateriel.put(materiel, Integer.parseInt(listQuantite.get(i)));
                }
            }

        }while (listMateriel.size() == 0 && !codeMateriels.equals(""));
        Location location = new Location(nbPersonne, dateDebut, dateFin, forfait, prix, promotion, skipper, bateau, client, listMateriel);
        insertLocation(location);
        System.out.println("Location ajouté");

    }

    public void supprimerBateau() {
        Scanner sc = new Scanner(System.in);
        Bateau bateau = null;

        do {
            System.out.println("Id du bateau à supprimer : ");
            String id = sc.nextLine();
            bateau = getOneBateau(new ObjectId(id));
            if(bateau == null){
                System.out.println("Erreur : le bateau n'existe pas");
                return;
            }
        }while (bateau == null);

        deleteBateau(bateau);
        System.out.println("Bateau supprimé");
    }

    public void supprimerSkipper() {
        Scanner sc = new Scanner(System.in);
        Skipper skipper = null;
        do {
             System.out.println("Id du skipper à supprimer : ");
            String id = sc.nextLine();
            skipper = getOneSkipper(new ObjectId(id));
            if(skipper == null){
                System.out.println("Erreur : le skipper n'existe pas");
                return;
            }
        }while (skipper == null);
        deleteSkipper(skipper);
        System.out.println("Skipper supprimé");
    }

    public void supprimerClient() {
        Scanner sc = new Scanner(System.in);
        Client client = null;
        do {
            System.out.println("Id du client à supprimer : ");
            String id = sc.nextLine();
            client = getOneClient(new ObjectId(id));
            if(client == null){
                System.out.println("Erreur : le client n'existe pas");
                return;
            }
        }while (client == null);
        deleteClient(client);
        System.out.println("Client supprimé");
    }

    public void supprimerMateriel() {
        Scanner sc = new Scanner(System.in);
        Materiel materiel = null;
        do {
            System.out.println("Id du materiel à supprimer : ");
            String id = sc.nextLine();
            materiel = getOneMateriel(new ObjectId(id));
            if(materiel == null){
                System.out.println("Erreur : le materiel n'existe pas");
                return;
            }
        }while (materiel == null);
        deleteMateriel(materiel);
        System.out.println("Materiel supprimé");
    }

    public void supprimerPromotion() {
        Scanner sc = new Scanner(System.in);
        Promotion promotion = null;
        do {
            System.out.println("Id de la promotion à supprimer : ");
            String id = sc.nextLine();
            promotion = getOnePromotion(new ObjectId(id));
            if(promotion == null){
                System.out.println("Erreur : la promotion n'existe pas");
                return;
            }
        }while (promotion == null);
        deletePromotion(promotion);
        System.out.println("Promotion supprimé");
    }

    public void supprimerLocation() {
        Scanner sc = new Scanner(System.in);
        Location location = null;
        do {
            System.out.println("Id de la location à supprimer : ");
            String id = sc.nextLine();
            location = getOneLocation(new ObjectId(id));
            if(location == null){
                System.out.println("Erreur : la location n'existe pas");
                return;
            }
        }while (location == null);
        deleteLocation(location);
        System.out.println("Location supprimé");
    }

    public void modifierBateau() {
        Scanner sc = new Scanner(System.in);
        Bateau bateau = null;
        do {
            System.out.println("Id du bateau à modifier : ");
            String id = sc.nextLine();
            bateau = getOneBateau(new ObjectId(id));
            if(bateau == null){
                System.out.println("Erreur : le bateau n'existe pas");
                return;
            }
        }while (bateau == null);

        System.out.println("Nombre de personne ("+bateau.getNbPlace()+") : ");
        int nbPersonne = sc.nextInt();
        System.out.println("Puissance ("+bateau.getPuissance()+"): ");
        int puisssance = sc.nextInt();
        System.out.println("Caution ("+bateau.getCaution()+"): ");
        int caution = sc.nextInt();
        int index = -1;
        do {
            System.out.println("Type du bateau ("+bateau.getIdTypeBateau()+"): ");
            String typeBateau = sc.nextLine();
            index= getAllTypeBateau().indexOf(typeBateau);
        } while (index == -1);
        TypeBateau typeBateau = getAllTypeBateau().get(index);
        bateau.setNbPersonne(nbPersonne);
        bateau.setPuissance(puisssance);
        bateau.setCaution(caution);
        bateau.setTypeBateau(typeBateau);
        updateBateau(bateau);
        System.out.println("Bateau mis à jour");

    }

    public void modifierSkipper() {
        Scanner sc = new Scanner(System.in);
        Skipper skipper = null;
        do {
            System.out.println("Id du skipper à modifier : ");
            String id = sc.nextLine();
            skipper = getOneSkipper(new ObjectId(id));
            if(skipper == null){
                System.out.println("Erreur : le skipper n'existe pas");
                return;
            }
        }while (skipper == null);

        System.out.println("Nom ("+skipper.getNom()+") : ");
        String nom = sc.nextLine();
        System.out.println("Prenom ("+skipper.getPrenom()+"): ");
        String prenom = sc.nextLine();
        System.out.println("Tarif("+skipper.getTarif()+"): ");
        int prix = sc.nextInt();
        skipper.setNom(nom);
        skipper.setPrenom(prenom);
        skipper.setTarif(prix);
        updateSkipper(skipper);
        System.out.println("Skipper mis à jour");

    }

    public void modifierClient() {

        Scanner sc = new Scanner(System.in);
        Client client = null;
        do {
            System.out.println("Id du client à modifier : ");
            String id = sc.nextLine();
            client = getOneClient(new ObjectId(id));
            if(client == null){
                System.out.println("Erreur : le client n'existe pas");
                return;
            }
        }while (client == null);

        System.out.println("Nom ("+client.getNom()+") : ");
        String nom = sc.nextLine();
        System.out.println("Prenom ("+client.getPrenom()+"): ");
        String prenom = sc.nextLine();
        System.out.println("Adresse ("+client.getAdresse()+"): ");
        String adresse = sc.nextLine();
        System.out.println("Telephone ("+client.getTelephone()+"): ");
        String telephone = sc.nextLine();
        System.out.println("Email ("+client.getEmail()+"): ");
        String email = sc.nextLine();
        client.setNom(nom);
        client.setPrenom(prenom);
        client.setAdresse(adresse);
        client.setTelephone(telephone);
        client.setEmail(email);
        updateClient(client);
        System.out.println("Client mis à jour");

    }

    public void modifierMateriel() {
        Scanner sc = new Scanner(System.in);
        Materiel materiel = null;
        do {
            System.out.println("Id du materiel à modifier : ");
            String id = sc.nextLine();
            materiel = getOneMateriel(new ObjectId(id));
            if(materiel == null){
                System.out.println("Erreur : le materiel n'existe pas");
                return;
            }
        }while (materiel == null);

        System.out.println("Nom ("+materiel.getNom()+") : ");
        String nom = sc.nextLine();
        System.out.println("Prix ("+materiel.getPrix()+"): ");
        int prix = sc.nextInt();
        System.out.println("Caution ("+materiel.getCaution()+"): ");
        int caution = sc.nextInt();
        materiel.setNom(nom);
        materiel.setPrix(prix);
        materiel.setCaution(caution);
        updateMateriel(materiel);
        System.out.println("Materiel mis à jour");

    }

    public void modifierPromotion() {
        Scanner sc = new Scanner(System.in);
        Promotion promotion = null;
        do {
            System.out.println("Id de la promotion à modifier : ");
            String id = sc.nextLine();
            promotion = getOnePromotion(new ObjectId(id));
            if(promotion == null){
                System.out.println("Erreur : la promotion n'existe pas");
                return;
            }
        }while (promotion == null);

        System.out.println("Code ("+promotion.getCode()+") : ");
        String code = sc.nextLine();
        System.out.println("Montant ("+promotion.getMontant()+"): ");
        int pourcentage = sc.nextInt();
        promotion.setCode(code);
        promotion.setMontant(pourcentage);
        updatePromotion(promotion);
        System.out.println("Promotion mis à jour");

    }

    public void afficherBateaux() {
        List<Bateau> bateaux = getAllBateau();
        for (Bateau bateau : bateaux) {
            System.out.println(bateau);
        }
    }

    public void afficherSkippers() {
        List<Skipper> skippers = getAllSkipper();
        for (Skipper skipper : skippers) {
            System.out.println(skipper);
        }
    }

    public void afficherClients() {
        List<Client> clients = getAllClient();
        for (Client client : clients) {
            System.out.println(client);
        }
    }

    public void afficherMateriels() {
        List<Materiel> materiels = getAllMateriel();
        for (Materiel materiel : materiels) {
            System.out.println(materiel);
        }
    }

    public void afficherPromotion() {
        List<Promotion> promotions = getAllPromotion();
        for (Promotion promotion : promotions) {
            System.out.println(promotion);
        }
    }

    public void affficherLocation() {
        List<Location> locations = getAllLocation();
        for (Location location : locations) {
            System.out.println(location);
        }
    }
}
