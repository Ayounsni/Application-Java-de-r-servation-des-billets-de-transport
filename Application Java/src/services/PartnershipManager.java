package services;

import db.DbFunctions;
import models.Partner;

import java.sql.Connection;
import java.sql.Date; // Importer java.sql.Date pour la conversion
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.UUID;


public class PartnershipManager {
    private final DbFunctions db;

    public PartnershipManager() {
        this.db = DbFunctions.getInstance();
        // List<models.Partner> partners = new ArrayList<>(); // Cette ligne est inutile si vous ne l'utilisez pas
    }

    // Méthode pour ajouter un partenaire
    public void addPartner(Partner partner) {
        String query = "INSERT INTO partner (id, companyName, businessContact, transportType, geographicZone, specialConditions, partnerStatus, creationDate) VALUES (?, ?, ?, ?::transporttype, ?, ?, ?::partnerstatus, ?)";
        try {
            int rowsInserted = getRowsInserted(partner, query);
            if (rowsInserted > 0) {
                System.out.println("A new partner was inserted successfully!");
            }
        } catch (Exception e) {
            System.err.println("An error occurred while inserting the partner: " + e.getMessage());
        }
    }

    // Méthode pour modifier un partenaire
    public void modifyPartner(UUID partnerId, String companyName, String businessContact, String transportType, String geographicZone, String specialConditions, String partnerStatus, String creationDate) {
        String query = "UPDATE partner SET companyName = ?, businessContact = ?, transportType = ?::transporttype, geographicZone = ?, specialConditions = ?, partnerStatus = ?::partnerstatus, creationDate = ? WHERE id = ?";
        try {
            int rowsUpdated = getRowsUpdated(partnerId, companyName, businessContact, transportType, geographicZone, specialConditions, partnerStatus, creationDate, query);
            if (rowsUpdated > 0) {
                System.out.println("models.Partner updated successfully!");
            }
        } catch (Exception e) {
            System.err.println("An error occurred while updating the partner: " + e.getMessage());
        }
    }

    // Méthode pour supprimer un partenaire
    public void deletePartner(UUID partnerId) {
        String query = "DELETE FROM partner WHERE id = ?";
        try (Connection conn = db.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setObject(1, partnerId);
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("models.Partner deleted successfully!");
            }
        } catch (SQLException e) {
            System.err.println("An error occurred while deleting the partner: " + e.getMessage());
        }
    }

    // Méthode pour afficher tous les partenaires
    public void displayPartners() {
        String query = "SELECT * FROM partner";
        try (Connection conn = db.getConnection(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                UUID id = (UUID) rs.getObject("id");
                String companyName = rs.getString("companyName");
                String businessContact = rs.getString("businessContact");
                String transportType = rs.getString("transportType"); // Assurez-vous que le type est correct pour le type enum
                String geographicZone = rs.getString("geographicZone");
                String specialConditions = rs.getString("specialConditions");
                String partnerStatus = rs.getString("partnerStatus"); // Assurez-vous que le type est correct pour le type enum
                Date creationDate = rs.getDate("creationDate");

                System.out.println("models.Partner ID: " + id);
                System.out.println("Company Name: " + companyName);
                System.out.println("Business Contact: " + businessContact);
                System.out.println("Transport Type: " + transportType);
                System.out.println("Geographic Zone: " + geographicZone);
                System.out.println("Special Conditions: " + specialConditions);
                System.out.println("models.Partner Status: " + partnerStatus);
                System.out.println("Creation Date: " + creationDate);
                System.out.println("--------------------------");
            }
        } catch (SQLException e) {
            System.err.println("An error occurred while retrieving partners: " + e.getMessage());
        }
    }

    private int getRowsInserted(Partner partner, String query) throws SQLException {
        try (Connection conn = db.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setObject(1, partner.getId());
            stmt.setString(2, partner.getCompanyName());
            stmt.setString(3, partner.getBusinessContact());
            stmt.setObject(4, partner.getTransportType()); // Assurez-vous que le type est correct pour le type enum
            stmt.setString(5, partner.getGeographicZone());
            stmt.setString(6, partner.getSpecialConditions());
            stmt.setObject(7, partner.getPartnerStatus()); // Assurez-vous que le type est correct pour le type enum
            stmt.setDate(8, convertStringToDate(partner.getCreationDate()));
            return stmt.executeUpdate();
        }
    }

    private int getRowsUpdated(UUID partnerId, String companyName, String businessContact, String transportType, String geographicZone, String specialConditions, String partnerStatus, String creationDate, String query) throws SQLException {
        try (Connection conn = db.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, companyName);
            stmt.setString(2, businessContact);
            stmt.setObject(3, transportType); // Assurez-vous que le type est correct pour le type enum
            stmt.setString(4, geographicZone);
            stmt.setString(5, specialConditions);
            stmt.setObject(6, partnerStatus); // Assurez-vous que le type est correct pour le type enum
            stmt.setDate(7, convertStringToDate(creationDate));
            stmt.setObject(8, partnerId);
            return stmt.executeUpdate();
        }
    }

    private Date convertStringToDate(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            java.util.Date parsedDate = sdf.parse(dateString);
            return new Date(parsedDate.getTime());
        } catch (ParseException e) {
            System.err.println("Failed to convert string to date: " + e.getMessage());
            return null; // Gérer les erreurs de conversion ici si nécessaire
        }
    }
}