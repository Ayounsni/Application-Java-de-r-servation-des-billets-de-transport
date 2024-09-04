import db.DbFunctions;
import models.Contract;
import models.Partner;
import models.Promo;
import models.Ticket;
import models.enums.ContractStatus;
import models.enums.PartnerStatus;
import models.enums.TransportType;
import models.enums.DiscountType;
import models.enums.OfferStatus;
import models.enums.TicketStatus;
import services.ContractManager;
import services.PartnershipManager;
import services.PromoManager;
import services.TicketManager;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {

        DbFunctions db = DbFunctions.getInstance();
        db.connect_to_db("Java", "postgres", "0000");

        PartnershipManager manager = new PartnershipManager();
        ContractManager contractManager = new ContractManager();
        PromoManager promoManager = new PromoManager();
        TicketManager ticketManager = new TicketManager();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {

            System.out.println("\nPartnership Management System");
            System.out.println("1. Add Partner");
            System.out.println("2. Modify Partner");
            System.out.println("3. Delete Partner");
            System.out.println("4. Display All Partners");
            System.out.println("5. Exit");
            System.out.println("6. Retrieve Partner by ID");
            System.out.println("7. Add Contract");
            System.out.println("8. Display Contracts");
            System.out.println("9. Delete Contract");
            System.out.println("10. Display All Contracts");
            System.out.println("11. Update Contract");
            System.out.println("12. Add Promo");
            System.out.println("13. Delete Promo");
            System.out.println("14. Display Promos");
            System.out.println("15. Update Promo");
            System.out.println("16. Add Ticket");
            System.out.println("17. Delete Ticket");
            System.out.println("18. Display Tickets");
            System.out.println("19. Update Ticket");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            String companyName, businessContact, geographicZone, specialConditions, creationDate;
            UUID partnerId, contractId, promoId, ticketId;
            TransportType transportType;
            PartnerStatus partnerStatus;
            Date startDate, endDate;
            BigDecimal specialRate;
            String agreementConditions;
            boolean renewable;
            ContractStatus contractStatus;
            String offerName, description, conditions;
            DiscountType discountType;
            OfferStatus offerStatus;
            BigDecimal purchasePrice, salePrice;
            Date saleDate;
            TicketStatus ticketStatus;

            switch (choice) {
                case 1:
                    // Ajouter un partenaire
                    System.out.println("Enter partner details:");
                    System.out.print("Company Name: ");
                    companyName = scanner.nextLine();
                    System.out.print("Business Contact: ");
                    businessContact = scanner.nextLine();
                    System.out.print("Transport Type: ");
                    transportType = TransportType.valueOf(scanner.nextLine().toLowerCase());
                    System.out.print("Geographic Zone: ");
                    geographicZone = scanner.nextLine();
                    System.out.print("Special Conditions: ");
                    specialConditions = scanner.nextLine();
                    System.out.print("Partner Status: ");
                    partnerStatus = PartnerStatus.valueOf(scanner.nextLine().toLowerCase());
                    System.out.print("Creation Date (YYYY-MM-DD): ");
                    creationDate = scanner.nextLine();

                    Partner partner = new Partner(companyName, businessContact, transportType, geographicZone, specialConditions, partnerStatus, creationDate);
                    manager.addPartner(partner);
                    break;

                case 2:
                    // Modifier un partenaire
                    System.out.print("Enter Partner ID to modify: ");
                    partnerId = UUID.fromString(scanner.nextLine());
                    System.out.println("Enter new details:");
                    System.out.print("Company Name: ");
                    companyName = scanner.nextLine();
                    System.out.print("Business Contact: ");
                    businessContact = scanner.nextLine();
                    System.out.print("Transport Type: ");
                    transportType = TransportType.valueOf(scanner.nextLine().toLowerCase());
                    System.out.print("Geographic Zone: ");
                    geographicZone = scanner.nextLine();
                    System.out.print("Special Conditions: ");
                    specialConditions = scanner.nextLine();
                    System.out.print("Partner Status: ");
                    partnerStatus = PartnerStatus.valueOf(scanner.nextLine().toLowerCase());
                    System.out.print("Creation Date (YYYY-MM-DD): ");
                    creationDate = scanner.nextLine();

                    manager.modifyPartner(partnerId, companyName, businessContact, transportType, geographicZone, specialConditions, partnerStatus, creationDate);
                    break;

                case 3:
                    // Supprimer un partenaire
                    System.out.print("Enter Partner ID to delete: ");
                    partnerId = UUID.fromString(scanner.nextLine());
                    manager.deletePartner(partnerId);
                    break;

                case 4:
                    // Afficher tous les partenaires
                    manager.displayPartners();
                    break;

                case 5:
                    running = false;
                    System.out.println("Exiting the system.");
                    break;

                case 6:
                    // Récupérer un partenaire par ID
                    System.out.print("Enter Partner ID to retrieve: ");
                    partnerId = UUID.fromString(scanner.nextLine());
                    Partner retrievedPartner = manager.getPartnerById(partnerId);

                    if (retrievedPartner != null) {
                        System.out.println("Partner found:");
                        System.out.println("Company Name: " + retrievedPartner.getCompanyName());
                        System.out.println("Business Contact: " + retrievedPartner.getBusinessContact());
                        System.out.println("Transport Type: " + retrievedPartner.getTransportType());
                        System.out.println("Geographic Zone: " + retrievedPartner.getGeographicZone());
                        System.out.println("Special Conditions: " + retrievedPartner.getSpecialConditions());
                        System.out.println("Partner Status: " + retrievedPartner.getPartnerStatus());
                        System.out.println("Creation Date: " + retrievedPartner.getCreationDate());
                    } else {
                        System.out.println("Partner not found with ID: " + partnerId);
                    }
                    break;

                case 7:
                    // Ajouter un contrat
                    System.out.print("Enter Partner ID to add a contract: ");
                    partnerId = UUID.fromString(scanner.nextLine());
                    Partner partnerToUpdate = manager.getPartnerById(partnerId);

                    if (partnerToUpdate != null) {
                        System.out.println("Enter contract details:");
                        System.out.print("Contract Start Date (YYYY-MM-DD): ");
                        startDate = Date.valueOf(scanner.nextLine());
                        System.out.print("Contract End Date (YYYY-MM-DD): ");
                        endDate = Date.valueOf(scanner.nextLine());
                        System.out.print("Special Rate: ");
                        specialRate = scanner.nextBigDecimal();
                        scanner.nextLine();
                        System.out.print("Agreement Conditions: ");
                        agreementConditions = scanner.nextLine();
                        System.out.print("Is Renewable (true/false): ");
                        renewable = scanner.nextBoolean();
                        scanner.nextLine();
                        System.out.print("Contract Status: ");
                        contractStatus = ContractStatus.valueOf(scanner.nextLine().toLowerCase());

                        Contract newContract = new Contract(startDate, endDate, specialRate, agreementConditions, renewable, contractStatus, partnerId);
                        partnerToUpdate.addContract(newContract);
                        contractManager.addContract(newContract);
                    } else {
                        System.out.println("Partner not found with ID: " + partnerId);
                    }
                    break;

                case 8:
                    // Afficher les contrats d'un partenaire
                    System.out.print("Enter Partner ID to display contracts: ");
                    partnerId = UUID.fromString(scanner.nextLine());
                    Partner partnerForContracts = manager.getPartnerById(partnerId);

                    if (partnerForContracts != null) {
                        List<Contract> contracts = partnerForContracts.getContracts();
                        if (!contracts.isEmpty()) {
                            System.out.println("Contracts for Partner ID: " + partnerId);
                            for (Contract contract : contracts) {
                                System.out.println("Contract ID: " + contract.getId());
                                System.out.println("Start Date: " + contract.getStartDate());
                                System.out.println("End Date: " + contract.getEndDate());
                                System.out.println("Special Rate: " + contract.getSpecialRate());
                                System.out.println("Agreement Conditions: " + contract.getAgreementConditions());
                                System.out.println("Renewable: " + contract.isRenewable());
                                System.out.println("Contract Status: " + contract.getContractStatus());
                                System.out.println("-----");
                            }
                        } else {
                            System.out.println("No contracts found for this partner.");
                        }
                    } else {
                        System.out.println("Partner not found with ID: " + partnerId);
                    }
                    break;

                case 9:
                    // Supprimer un contrat
                    System.out.print("Enter Contract ID to delete: ");
                    contractId = UUID.fromString(scanner.next());
                    contractManager.deleteContract(contractId);
                    break;

                case 10:
                    // Afficher tous les contrats
                    contractManager.displayContracts();
                    break;

                case 11:
                    // Mettre à jour un contrat
                    System.out.print("Enter Contract ID: ");
                    contractId = UUID.fromString(scanner.next());

                    System.out.print("Enter new Start Date (yyyy-mm-dd): ");
                    startDate = Date.valueOf(scanner.next());

                    System.out.print("Enter new End Date (yyyy-mm-dd): ");
                    endDate = Date.valueOf(scanner.next());

                    System.out.print("Enter new Special Rate: ");
                    specialRate = scanner.nextBigDecimal();

                    System.out.print("Enter new Agreement Conditions: ");
                    scanner.nextLine(); // Pour consommer la ligne restante
                    agreementConditions = scanner.nextLine();

                    System.out.print("Is the contract renewable? (true/false): ");
                    renewable = scanner.nextBoolean();

                    System.out.print("Enter new Contract Status (e.g., active, pending): ");
                    contractStatus = ContractStatus.valueOf(scanner.next().toLowerCase());

                    contractManager.updateContract(contractId, startDate, endDate, specialRate, agreementConditions, renewable, contractStatus);
                    break;

                case 12:
                    // Ajouter une promotion
                    System.out.print("Enter Contract ID to add a promo: ");
                    contractId = UUID.fromString(scanner.nextLine());
                    Contract contractForPromo = contractManager.getContractById(contractId);

                    if (contractForPromo != null) {
                        System.out.println("Enter promo details:");
                        System.out.print("Offer Name: ");
                        offerName = scanner.nextLine();
                        System.out.print("Description: ");
                        description = scanner.nextLine();
                        System.out.print("Contract Start Date (YYYY-MM-DD): ");
                        startDate = Date.valueOf(scanner.nextLine());
                        System.out.print("Contract End Date (YYYY-MM-DD): ");
                        endDate = Date.valueOf(scanner.nextLine());
                        System.out.print("Conditions: ");
                        conditions = scanner.nextLine();
                        System.out.print("Discount Type: ");
                        discountType = DiscountType.valueOf(scanner.nextLine().toLowerCase());
                        System.out.print("Offer Status: ");
                        offerStatus = OfferStatus.valueOf(scanner.nextLine().toLowerCase());

                        Promo newPromo = new Promo(offerName, description, startDate,endDate, discountType, conditions,  offerStatus, contractId);
                        promoManager.addPromo(newPromo);
                    } else {
                        System.out.println("Contract not found with ID: " + contractId);
                    }
                    break;

                case 13:
                    // Supprimer une promotion
                    System.out.print("Enter Promo ID to delete: ");
                    promoId = UUID.fromString(scanner.nextLine());
                    promoManager.deletePromo(promoId);
                    break;

                case 14:
                    // Afficher toutes les promotions
                    promoManager.displayPromos();
                    break;

                case 15:
                    // Mettre à jour une promotion
                    System.out.print("Enter Promo ID to update: ");
                    promoId = UUID.fromString(scanner.nextLine());

                    System.out.print("Enter new Offer Name: ");
                    offerName = scanner.nextLine();

                    System.out.print("Enter new Description: ");
                    description = scanner.nextLine();

                    System.out.print("Enter new Start Date (yyyy-mm-dd): ");
                    startDate = Date.valueOf(scanner.nextLine());

                    System.out.print("Enter new End Date (yyyy-mm-dd): ");
                    endDate = Date.valueOf(scanner.nextLine());

                    System.out.print("Enter new Conditions: ");
                    conditions = scanner.nextLine();

                    System.out.print("Enter new Discount Type (e.g., percentage, fixed): ");
                    discountType = DiscountType.valueOf(scanner.nextLine().toLowerCase());

                    System.out.print("Enter new Offer Status (e.g., active, expired): ");
                    offerStatus = OfferStatus.valueOf(scanner.nextLine().toLowerCase());


                    // Mise à jour de la promotion avec tous les paramètres requis
                    promoManager.updatePromo(promoId, offerName, description, startDate, endDate,  discountType, conditions, offerStatus);
                    break;
                case 16:
                    // Ajouter un ticket
                    System.out.print("Enter Contract ID to add a ticket: ");
                    contractId = UUID.fromString(scanner.nextLine());
                    Contract contractForTicket = contractManager.getContractById(contractId);

                    if (contractForTicket != null) {
                        System.out.println("Enter ticket details:");
                        System.out.print("Transport Type: ");
                        transportType = TransportType.valueOf(scanner.nextLine().toLowerCase());
                        System.out.print("Purchase Price: ");
                        purchasePrice = scanner.nextBigDecimal();
                        scanner.nextLine();
                        System.out.print("Sale Price: ");
                        salePrice = scanner.nextBigDecimal();
                        scanner.nextLine();
                        System.out.print("Sale Date (YYYY-MM-DD): ");
                        saleDate = Date.valueOf(scanner.nextLine());
                        System.out.print("Ticket Status: ");
                        ticketStatus = TicketStatus.valueOf(scanner.nextLine().toLowerCase());

                        Ticket newTicket = new Ticket(transportType, purchasePrice, salePrice, saleDate, ticketStatus, contractId);
                        ticketManager.addTicket(newTicket);
                    } else {
                        System.out.println("Contract not found with ID: " + contractId);
                    }
                    break;

                case 17:
                    // Supprimer un ticket
                    System.out.print("Enter Ticket ID to delete: ");
                    ticketId = UUID.fromString(scanner.nextLine());
                    ticketManager.deleteTicket(ticketId);
                    break;

                case 18:
                    // Afficher tous les tickets
                    ticketManager.displayTickets();
                    break;

                case 19:
                    // Mettre à jour un ticket
                    System.out.print("Enter Ticket ID to update: ");
                    ticketId = UUID.fromString(scanner.nextLine());

                    System.out.print("Enter new Transport Type: ");
                    transportType = TransportType.valueOf(scanner.nextLine().toLowerCase());

                    System.out.print("Enter new Purchase Price: ");
                    purchasePrice = scanner.nextBigDecimal();
                    scanner.nextLine(); // Pour consommer la ligne restante

                    System.out.print("Enter new Sale Price: ");
                    salePrice = scanner.nextBigDecimal();
                    scanner.nextLine();

                    System.out.print("Enter new Sale Date (yyyy-mm-dd): ");
                    saleDate = Date.valueOf(scanner.nextLine());

                    System.out.print("Enter new Ticket Status: ");
                    ticketStatus = TicketStatus.valueOf(scanner.nextLine().toLowerCase());

                    ticketManager.updateTicket(ticketId, transportType, purchasePrice, salePrice, saleDate, ticketStatus);
                    break;


                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
        scanner.close();
        db.closeConnection();
    }
}
