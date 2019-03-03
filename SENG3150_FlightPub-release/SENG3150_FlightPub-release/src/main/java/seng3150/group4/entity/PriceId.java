package seng3150.group4.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

@Embeddable
public class PriceId implements Serializable
{
    @Column(name = "AirlineCode")
    private String airlineCode;

    @Column(name = "FlightNumber")
    private String flightNumber;

    @Column(name = "ClassCode")
    private String classCode;

    @Column(name = "TicketCode")
    private String ticketCode;

    // Helps with the conversion between java.util.Date and MySQL DATETIME
    @Column(name = "StartDate", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    public PriceId() {}

    public PriceId(String airlineCode, String flightNumber, String classCode, String ticketCode, Date startDate) {
        this.airlineCode = airlineCode;
        this.flightNumber = flightNumber;
        this.classCode = classCode;
        this.ticketCode = ticketCode;
        this.startDate = startDate;
    }

    public String getAirlineCode() {
        return airlineCode;
    }

    public void setAirlineCode(String airlineCode) {
        this.airlineCode = airlineCode;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public String getTicketCode() {
        return ticketCode;
    }

    public void setTicketCode(String ticketCode) {
        this.ticketCode = ticketCode;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
}
