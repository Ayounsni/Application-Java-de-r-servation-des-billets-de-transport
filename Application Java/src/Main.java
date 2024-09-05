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
import utils.InputValidator;

import java.math.BigDecimal;
import java.sql.Date;
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
            System.out.println("\nMain Menu:");
            System.out.println("1. Partner Manager");
            System.out.println("2. Contract Manager");
            System.out.println("3. Promo Manager");
            System.out.println("4. Ticket Manager");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int mainChoice = scanner.nextInt();
            scanner.nextLine(); // Consommer le reste de la ligne


            switch (mainChoice) {
                case 1:
                    partnerManagerMenu(scanner, manager);
                    break;
                case 2:
                    contractManagerMenu(scanner, contractManager);
                    break;
                case 3:
                    promoManagerMenu(scanner, promoManager);
                    break;
                case 4:
                    ticketManagerMenu(scanner, ticketManager);
                    break;
                case 5:
                    running = false;
                    System.out.println("Exiting the system.");
                    break;
                default:
                    System.out.println("Invalid choice. Please choose again.");
            }
        }
        scanner.close();
        db.closeConnection();
    }

    public static void partnerManagerMenu(Scanner scanner, PartnershipManager manager) {
        boolean partnerRunning = true;
        while (partnerRunning) {
            System.out.println("\nPartner Manager:");
            System.out.println("1. Add Partner");
            System.out.println("2. Modify Partner");
            System.out.println("3. Delete Partner");
            System.out.println("4. Display All Partners");
            System.out.println("5. Return to Main Menu");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            String companyName, businessContact, geographicZone, specialConditions;
            Date creationDate;
            TransportType transportType;
            PartnerStatus partnerStatus;
            UUID partnerId;

            switch (choice) {
                case 1:
                    System.out.println("Enter partner details:");


                    do {
                        System.out.print("Company Name: ");
                        companyName = scanner.nextLine();
                    } while (!InputValidator.validateNonEmpty(companyName));


                    do {
                        System.out.print("Business Contact: ");
                        businessContact = scanner.nextLine();
                    } while (!InputValidator.validateNonEmpty(businessContact));


                    while (true) {
                        System.out.print("Transport Type (airplane, train , bus ): ");
                        String transportTypeInput = scanner.nextLine();
                        if(!InputValidator.validateNonEmpty(transportTypeInput)) {
                            continue;
                        }
                        try {
                            transportType = TransportType.valueOf(transportTypeInput.toLowerCase());
                            break;
                        } catch (IllegalArgumentException e) {
                            System.out.println("Invalid Transport Type. Please enter a valid type (airplane, train , bus ).");
                        }
                    }

                    do {
                        System.out.print("Geographic Zone: ");
                        geographicZone = scanner.nextLine();
                    } while (!InputValidator.validateNonEmpty(geographicZone));


                    do {
                        System.out.print("Special Conditions: ");
                        specialConditions = scanner.nextLine();
                    } while (!InputValidator.validateNonEmpty(specialConditions));


                    while (true) {
                        System.out.print("Partner Status (e.g., active, inactive): ");
                        String partnerStatusInput = scanner.nextLine();
                        if(!InputValidator.validateNonEmpty(partnerStatusInput)) {
                            continue;
                        }
                        try {
                            partnerStatus = PartnerStatus.valueOf(partnerStatusInput.toLowerCase());
                            break;
                        } catch (IllegalArgumentException e) {
                            System.out.println("Invalid Partner Status. Please enter a valid status (e.g., active, inactive).");
                        }
                    }

                    while (true) {
                        System.out.print("Creation Date (YYYY-MM-DD): ");
                        String creationDateInput = scanner.nextLine();

                        if(!InputValidator.validateNonEmpty(creationDateInput)) {
                            continue;
                        }
                        try {
                            creationDate = Date.valueOf(creationDateInput);
                            break;
                        } catch (IllegalArgumentException e) {
                            System.out.println("Invalid date format. Please enter the date in YYYY-MM-DD format.");
                        }

                    }

                    Partner partner = new Partner(companyName, businessContact, transportType, geographicZone, specialConditions, partnerStatus, creationDate);
                    manager.addPartner(partner);
                    break;
                case 2:
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
                    creationDate = Date.valueOf(scanner.next());

                    manager.modifyPartner(partnerId, companyName, businessContact, transportType, geographicZone, specialConditions, partnerStatus, creationDate);
                    break;
                case 3:
                    System.out.print("Enter Partner ID to delete: ");
                    partnerId = UUID.fromString(scanner.nextLine());
                    manager.deletePartner(partnerId);
                    break;
                case 4:
                    manager.displayPartners();
                    break;
                case 5:
                    partnerRunning = false;
                    break;
                case 6:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    public static void contractManagerMenu(Scanner scanner, ContractManager contractManager) {
        boolean contractRunning = true;
        while (contractRunning) {
            System.out.println("\nContract Manager:");
            System.out.println("1. Add Contract");
            System.out.println("2. Modify Contract");
            System.out.println("3. Delete Contract");
            System.out.println("4. Display All Contracts");
            System.out.println("5. Return to Main Menu");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            PartnershipManager manager = new PartnershipManager();
            UUID partnerId;
            Date startDate;
            Date endDate;
            BigDecimal specialRate;
            String agreementConditions;
            boolean renewable;
            ContractStatus contractStatus;
            UUID contractId;


            switch (choice) {
                case 1:
                    System.out.print("Enter Partner ID to add a contract: ");
                    partnerId = UUID.fromString(scanner.nextLine());
                    Partner partnerToUpdate = manager.getPartnerById(partnerId);

                    if (partnerToUpdate != null) {
                        System.out.println("Enter contract details:");
                        while (true) {
                            System.out.print("Contract Start Date (YYYY-MM-DD): ");
                            String startDateInput = scanner.nextLine();

                            if(!InputValidator.validateNonEmpty(startDateInput)) {
                                continue;
                            }
                            try {
                                startDate = Date.valueOf(startDateInput);
                                break;
                            } catch (IllegalArgumentException e) {
                                System.out.println("Invalid date format. Please enter the date in YYYY-MM-DD format.");
                            }

                        }
                        do {
                            System.out.print("Contract End Date (YYYY-MM-DD): ");
                            String endDateInput = scanner.nextLine();

                            if (!InputValidator.validateNonEmpty(endDateInput)) {
                                continue;
                            }
                            try {
                                endDate = Date.valueOf(endDateInput);

                                if (InputValidator.validateDateRange(startDate, endDate)) {
                                    break;
                                }
                            } catch (IllegalArgumentException e) {
                                System.out.println("Error: Invalid date format. Please enter the date in the format YYYY-MM-DD.");
                            }

                        } while (true);

                        System.out.print("Special Rate: ");
                        specialRate = scanner.nextBigDecimal();
                        scanner.nextLine();
                        do {
                            System.out.print("Agreement Conditions: ");
                            agreementConditions = scanner.nextLine();
                        } while (!InputValidator.validateNonEmpty(agreementConditions));
                        System.out.print("Is Renewable (true/false): ");
                        renewable = scanner.nextBoolean();
                        scanner.nextLine();

                        while (true) {
                            System.out.print("Contrat Statuts (ongoing, terminated , suspended ): ");
                            String contractStatusInput = scanner.nextLine();
                            if(!InputValidator.validateNonEmpty(contractStatusInput)) {
                                continue;
                            }
                            try {
                                contractStatus = ContractStatus.valueOf(contractStatusInput.toLowerCase());
                                break;
                            } catch (IllegalArgumentException e) {
                                System.out.println("Invalid Contrat Status. Please enter a valid status (ongoing, terminated , suspended).");
                            }
                        }

                        Contract newContract = new Contract(startDate, endDate, specialRate, agreementConditions, renewable, contractStatus, partnerId);
                        partnerToUpdate.addContract(newContract);
                        contractManager.addContract(newContract);
                    } else {
                        System.out.println("Partner not found with ID: " + partnerId);
                    }
                    break;
                case 2:
                    System.out.print("Enter Contract ID: ");
                    contractId = UUID.fromString(scanner.next());

                    System.out.print("Enter new Start Date (yyyy-mm-dd): ");
                    startDate = Date.valueOf(scanner.next());

                    System.out.print("Enter new End Date (yyyy-mm-dd): ");
                    endDate = Date.valueOf(scanner.next());

                    System.out.print("Enter new Special Rate: ");
                    specialRate = scanner.nextBigDecimal();

                    System.out.print("Enter new Agreement Conditions: ");
                    scanner.nextLine(); 
                    agreementConditions = scanner.nextLine();

                    System.out.print("Is the contract renewable? (true/false): ");
                    renewable = scanner.nextBoolean();

                    System.out.print("Enter new Contract Status (e.g., active, pending): ");
                    contractStatus = ContractStatus.valueOf(scanner.next().toLowerCase());

                    contractManager.updateContract(contractId, startDate, endDate, specialRate, agreementConditions, renewable, contractStatus);
                    break;
                case 3:
                    System.out.print("Enter Contract ID to delete: ");
                    contractId = UUID.fromString(scanner.next());
                    contractManager.deleteContract(contractId);
                    break;
                case 4:
                    contractManager.displayContracts();
                    break;
                case 5:
                    contractRunning = false;
                    break;
                case 6:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    public static void promoManagerMenu(Scanner scanner, PromoManager promoManager) {
        boolean promoRunning = true;
        while (promoRunning) {
            System.out.println("\nPromo Manager:");
            System.out.println("1. Add Promo");
            System.out.println("2. Modify Promo");
            System.out.println("3. Delete Promo");
            System.out.println("4. Display All Promos");
            System.out.println("5. Return to Main Menu");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            ContractManager contractManager = new ContractManager();
            UUID contractId , promoId;
            String offerName,description,conditions;
            Date startDate , endDate;
            DiscountType discountType;
            OfferStatus offerStatus;

            switch (choice) {
                case 1:
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
                case 2:
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

                    promoManager.updatePromo(promoId, offerName, description, startDate, endDate,  discountType, conditions, offerStatus);
                    break;
                case 3:
                    System.out.print("Enter Promo ID to delete: ");
                    promoId = UUID.fromString(scanner.nextLine());
                    promoManager.deletePromo(promoId);
                    break;
                case 4:
                    promoManager.displayPromos();
                    break;
                case 5:
                    promoRunning = false;
                    break;
                case 6:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    public static void ticketManagerMenu(Scanner scanner, TicketManager ticketManager) {
        boolean ticketRunning = true;
        while (ticketRunning) {
            System.out.println("\nTicket Manager:");
            System.out.println("1. Add Ticket");
            System.out.println("2. Modify Ticket");
            System.out.println("3. Delete Ticket");
            System.out.println("4. Display All Tickets");
            System.out.println("5. Return to Main Menu");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            ContractManager contractManager = new ContractManager();
            UUID contractId, ticketId;
            BigDecimal purchasePrice , salePrice;
            TransportType transportType;
            TicketStatus ticketStatus;
            Date saleDate;

            switch (choice) {
                case 1:
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
                case 2:
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
                case 3:
                    System.out.print("Enter Ticket ID to delete: ");
                    ticketId = UUID.fromString(scanner.nextLine());
                    ticketManager.deleteTicket(ticketId);
                    break;
                case 4:
                    ticketManager.displayTickets();
                    break;
                case 5:
                    ticketRunning = false;
                    break;
                case 6:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice.");

            }

        }



    }

}
