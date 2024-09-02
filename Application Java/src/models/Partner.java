package models;

import java.util.UUID;

public class Partner {
    private final UUID id;
    private String companyName;
    private String businessContact;
    private String transportType;
    private String geographicZone;
    private String specialConditions;
    private String partnerStatus;
    private String creationDate;

    // Constructeur
    public Partner(String companyName, String businessContact, String transportType, String geographicZone, String specialConditions, String partnerStatus, String creationDate) {
        this.id = UUID.randomUUID(); // Génère un ID unique
        this.companyName = companyName;
        this.businessContact = businessContact;
        this.transportType = transportType;
        this.geographicZone = geographicZone;
        this.specialConditions = specialConditions;
        this.partnerStatus = partnerStatus;
        this.creationDate = creationDate;
    }

    // Getters et Setters
    public UUID getId() {
        return id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getBusinessContact() {
        return businessContact;
    }

    public void setBusinessContact(String businessContact) {
        this.businessContact = businessContact;
    }

    public String getTransportType() {
        return transportType;
    }

    public void setTransportType(String transportType) {
        this.transportType = transportType;
    }

    public String getGeographicZone() {
        return geographicZone;
    }

    public void setGeographicZone(String geographicZone) {
        this.geographicZone = geographicZone;
    }

    public String getSpecialConditions() {
        return specialConditions;
    }

    public void setSpecialConditions(String specialConditions) {
        this.specialConditions = specialConditions;
    }

    public String getPartnerStatus() {
        return partnerStatus;
    }

    public void setPartnerStatus(String partnerStatus) {
        this.partnerStatus = partnerStatus;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "models.Partner{" +
                "id=" + id +
                ", companyName='" + companyName + '\'' +
                ", businessContact='" + businessContact + '\'' +
                ", transportType='" + transportType + '\'' +
                ", geographicZone='" + geographicZone + '\'' +
                ", specialConditions='" + specialConditions + '\'' +
                ", partnerStatus='" + partnerStatus + '\'' +
                ", creationDate='" + creationDate + '\'' +
                '}';
    }
}
