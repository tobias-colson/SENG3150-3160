package seng3150.group4.entity;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "planetype")
public class PlaneTypeEntity implements Serializable {

    @Id
    @Column(name = "PlaneCode")
    private String planeCode;

    @Column(name = "Details")
    private String details;

    @Column(name = "NumFirstClass")
    private int numFirstClass;

    @Column(name = "NumBusiness")
    private int numBusiness;

    @Column(name = "NumPremiumEconomy")
    private int numPremiumEconomy;

    @Column(name = "Economy")
    private int economy;

    public PlaneTypeEntity() {}

    public PlaneTypeEntity(String planeCode, String details, int numFirstClass, int numBusiness, int numPremiumEconomy, int economy) {
        this.planeCode = planeCode;
        this.details = details;
        this.numFirstClass = numFirstClass;
        this.numBusiness = numBusiness;
        this.numPremiumEconomy = numPremiumEconomy;
        this.economy = economy;
    }

    public String getPlaneCode() {
        return planeCode;
    }

    public void setPlaneCode(String planeCode) {
        this.planeCode = planeCode;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getNumFirstClass() {
        return numFirstClass;
    }

    public void setNumFirstClass(int numFirstClass) {
        this.numFirstClass = numFirstClass;
    }

    public int getNumBusiness() {
        return numBusiness;
    }

    public void setNumBusiness(int numBusiness) {
        this.numBusiness = numBusiness;
    }

    public int getNumPremiumEconomy() {
        return numPremiumEconomy;
    }

    public void setNumPremiumEconomy(int numPremiumEconomy) {
        this.numPremiumEconomy = numPremiumEconomy;
    }

    public int getEconomy() {
        return economy;
    }

    public void setEconomy(int economy) {
        this.economy = economy;
    }
}
