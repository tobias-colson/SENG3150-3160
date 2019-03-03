package seng3150.group4.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

@Embeddable
public class FlightId implements Serializable
{
    @Column(name = "AirlineCode")
    private String airlineCode;

    @Column(name = "FlightNumber")
    private String flightNumber;

    // Helps with the conversion between java.util.Date and MySQL DATETIME
    @Column(name = "DepartureTime", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date departureTime;

    public FlightId() {}

    public FlightId(String airlineCode, String flightNumber, Date departureTime) {
        this.airlineCode = airlineCode;
        this.flightNumber = flightNumber;
        this.departureTime = departureTime;
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

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }
}
