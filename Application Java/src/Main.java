import db.DbFunctions;
import models.Partner;
import services.PartnershipManager;

import java.util.Scanner;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        // Connexion à la base de données
        DbFunctions db = DbFunctions.getInstance(); // Utiliser le singleton pour la connexion
        db.connect_to_db("Java", "postgres", "0000");

        // Initialiser le gestionnaire de partenariat
        PartnershipManager manager = new PartnershipManager();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            // Menu principal
            System.out.println("\nPartnership Management System");
            System.out.println("1. Add models.Partner");
            System.out.println("2. Modify models.Partner");
            System.out.println("3. Delete models.Partner");
            System.out.println("4. Display All Partners");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consommer la nouvelle ligne après l'entrée numérique

            String companyName, businessContact, transportType, geographicZone, specialConditions, partnerStatus, creationDate;
            UUID partnerId;

            switch (choice) {
                case 1:
                    // Ajouter un partenaire
                    System.out.println("Enter partner details:");
                    System.out.print("Company Name: ");
                    companyName = scanner.nextLine();
                    System.out.print("Business Contact: ");
                    businessContact = scanner.nextLine();
                    System.out.print("Transport Type: ");
                    transportType = scanner.nextLine();
                    System.out.print("Geographic Zone: ");
                    geographicZone = scanner.nextLine();
                    System.out.print("Special Conditions: ");
                    specialConditions = scanner.nextLine();
                    System.out.print("models.Partner Status: ");
                    partnerStatus = scanner.nextLine();
                    System.out.print("Creation Date (YYYY-MM-DD): ");
                    creationDate = scanner.nextLine();

                    // Création et ajout du partenaire
                    Partner partner = new Partner(companyName, businessContact, transportType, geographicZone, specialConditions, partnerStatus, creationDate);
                    manager.addPartner(partner);
                    break;

                case 2:
                    // Modifier un partenaire
                    System.out.print("Enter models.Partner ID to modify: ");
                    partnerId = UUID.fromString(scanner.nextLine());
                    System.out.println("Enter new details:");
                    System.out.print("Company Name: ");
                    companyName = scanner.nextLine();
                    System.out.print("Business Contact: ");
                    businessContact = scanner.nextLine();
                    System.out.print("Transport Type: ");
                    transportType = scanner.nextLine();
                    System.out.print("Geographic Zone: ");
                    geographicZone = scanner.nextLine();
                    System.out.print("Special Conditions: ");
                    specialConditions = scanner.nextLine();
                    System.out.print("models.Partner Status: ");
                    partnerStatus = scanner.nextLine();
                    System.out.print("Creation Date (YYYY-MM-DD): ");
                    creationDate = scanner.nextLine();

                    // Appeler la méthode de modification
                    manager.modifyPartner(partnerId, companyName, businessContact, transportType, geographicZone, specialConditions, partnerStatus, creationDate);
                    break;

                case 3:
                    // Supprimer un partenaire
                    System.out.print("Enter models.Partner ID to delete: ");
                    partnerId = UUID.fromString(scanner.nextLine());
                    manager.deletePartner(partnerId);
                    break;

                case 4:
                    // Afficher tous les partenaires
                    manager.displayPartners();
                    break;

                case 5:
                    // Quitter
                    running = false;
                    System.out.println("Exiting the system.");
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
        scanner.close();
        db.closeConnection(); // Assurez-vous de fermer la connexion à la base de données
    }
}
