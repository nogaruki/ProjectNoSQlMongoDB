package fr.projetNosql;

import static com.mongodb.client.model.Filters.eq;

import fr.projetNosql.lib.Cluster;
import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import java.util.Scanner;

public class Main {
    public static void main( String[] args ) {
        Cluster cluster = new Cluster();
        System.out.println("Bienvenue sur le site de location de bateau");
        System.out.println("Veuillez sélectionner une option :");
        System.out.println("1 - Afficher les bateaux disponibles");
        System.out.println("2 - Gestion admin");
        Scanner sc = new Scanner(System.in);
         int choix = 0;
         while (choix < 1 || choix > 2) {
             choix = sc.nextInt();
             if (choix < 1 || choix > 2) {
                 System.out.println("Veuillez saisir un nombre entre 1 et 2");
             }
         }
         if(choix == 1) cluster.afficherBateauxDispo();
         else if (choix == 2) {
             System.out.println("Gestion administrateur :");
                System.out.println("1 - Ajout");
                System.out.println("2 - Suppression");
                System.out.println("3 - Modification");
                System.out.println("4 - Affichage");
                 sc = new Scanner(System.in);
                 choix = 0;
                 while (choix < 1 || choix > 4) {
                     choix = sc.nextInt();
                     if (choix < 1 || choix > 4) {
                         System.out.println("Veuillez saisir un nombre entre 1 et 4");
                     }
                 }

             if(choix == 1) {
                 System.out.println("1 - Ajouter un bateau");
                    System.out.println("2 - Ajouter un skipper");
                    System.out.println("3 - Ajouter un client");
                    System.out.println("4 - Ajouter un matériel");
                    System.out.println("5 - Ajouter une promotion");
                    System.out.println("6 - Ajouter une location");
                        sc = new Scanner(System.in);
                        choix = 0;
                        while (choix < 1 || choix > 6) {
                            choix = sc.nextInt();
                            if (choix < 1 || choix > 6) {
                                System.out.println("Veuillez saisir un nombre entre 1 et 6");
                            }
                        }
                    if(choix == 1) {
                        cluster.ajouterBateau();
                    } else if(choix == 2) {
                        cluster.ajouterSkipper();
                    } else if(choix == 3) {
                        cluster.ajouterClient();
                    } else if(choix == 4) {
                        cluster.ajouterMateriel();
                    } else if(choix == 5) {
                        cluster.ajouterPromotion();
                    } else if(choix == 6) {
                        cluster.ajouterLocation();
                    }

             } else if(choix == 2) {
                    System.out.println("1 - Supprimer un bateau");
                        System.out.println("2 - Supprimer un skipper");
                        System.out.println("3 - Supprimer un client");
                        System.out.println("4 - Supprimer un matériel");
                        System.out.println("5 - Supprimer une promotion");
                        System.out.println("6 - Supprimer une location");
                            sc = new Scanner(System.in);
                            choix = 0;
                            while (choix < 1 || choix > 6) {
                                choix = sc.nextInt();
                                if (choix < 1 || choix > 6) {
                                    System.out.println("Veuillez saisir un nombre entre 1 et 6");
                                }
                            }
                        if(choix == 1) {
                            cluster.supprimerBateau();
                        } else if(choix == 2) {
                            cluster.supprimerSkipper();
                        } else if(choix == 3) {
                            cluster.supprimerClient();
                        } else if(choix == 4) {
                            cluster.supprimerMateriel();
                        } else if(choix == 5) {
                            cluster.supprimerPromotion();
                        } else if(choix == 6) {
                            cluster.supprimerLocation();
                        }

             } else if(choix == 3) {
                    System.out.println("1 - Modifier un bateau");
                        System.out.println("2 - Modifier un skipper");
                        System.out.println("3 - Modifier un client");
                        System.out.println("4 - Modifier un matériel");
                        System.out.println("5 - Modifier une promotion");
                            sc = new Scanner(System.in);
                            choix = 0;
                            while (choix < 1 || choix > 5) {
                                choix = sc.nextInt();
                                if (choix < 1 || choix > 5) {
                                    System.out.println("Veuillez saisir un nombre entre 1 et 5");
                                }
                            }
                        if(choix == 1) {
                            cluster.modifierBateau();
                        } else if(choix == 2) {
                            cluster.modifierSkipper();
                        } else if(choix == 3) {
                            cluster.modifierClient();
                        } else if(choix == 4) {
                            cluster.modifierMateriel();
                        } else if(choix == 5) {
                            cluster.modifierPromotion();
                        }

             } else if(choix == 4) {
                    System.out.println("1 - Afficher les bateaux");
                    System.out.println("2 - Afficher les skippers");
                    System.out.println("3 - Afficher les clients");
                    System.out.println("4 - Afficher les matériels");
                    System.out.println("5 - Afficher les promotions");
                    System.out.println("6 - Afficher les locations");
                        sc = new Scanner(System.in);
                        choix = 0;
                        while (choix < 1 || choix > 6) {
                            choix = sc.nextInt();
                            if (choix < 1 || choix > 6) {
                                System.out.println("Veuillez saisir un nombre entre 1 et 6");
                            }
                        }
                    if(choix == 1) {
                        cluster.afficherBateaux();
                    } else if(choix == 2) {
                        cluster.afficherSkippers();
                    } else if(choix == 3) {
                        cluster.afficherClients();
                    } else if(choix == 4) {
                        cluster.afficherMateriels();
                    }  else if(choix == 5) {
                        cluster.afficherPromotion();
                    } else if(choix == 6) {
                        cluster.affficherLocation();
                    }
             }
         }

    }
}