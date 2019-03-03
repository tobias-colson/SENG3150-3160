package seng3150.group4.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

@Embeddable
public class FlightHistoryId implements Serializable
{
    @Column(name = "userID")
    private int userID;

    @Column(name = "AirlineCode")
    private String airlineCode;

    @Column(name = "FlightNumber")
    private String flightNumber;

    // Helps with the conversion between java.util.Date and MySQL DATETIME
    @Column(name = "DepartureTime", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date departureTime;

    public FlightHistoryId() {}

    public FlightHistoryId(int userID, String airlineCode, String flightNumber, Date departureTime)
    {
        this.userID = userID;
        this.airlineCode = airlineCode;
        this.flightNumber = flightNumber;
        this.departureTime = departureTime;
    }

    public int getUserID() { return userID; }
    public void setUserID(int userID) { this.userID = userID; }

    public String getAirlineCode()
    {
        return airlineCode;
    }
    public void setAirlineCode(String airlineCode) { this.airlineCode = airlineCode; }

    public String getFlightNumber()
    {
        return flightNumber;
    }
    public void setFlightNumber(String flightNumber) { this.flightNumber = flightNumber; }

    public Date getDepartureTime()
    {
        return departureTime;
    }
    public void setDepartureTime(Date departureTime) { this.departureTime = departureTime; }

}
