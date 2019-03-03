package seng3150.group4.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

@Embeddable
public class AvailabilityId implements Serializable
{
    @Column(name = "airlineCode")
    private String airlineCode;

    @Column(name = "flightNumber")
    private String flightNumber;

    // Helps with the conversion between java.util.Date and MySQL DATETIME
    @Column(name = "departureTime", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date departureTime;

    @Column(name = "classCode")
    private String classCode;

    @Column(name = "ticketCode")
    private String ticketCode;

    public AvailabilityId() {}

    public AvailabilityId(String airlineCode, String flightNumber, Date departureTime, String classCode, String ticketCode)
    {
        this.airlineCode = airlineCode;
        this.flightNumber = flightNumber;
        this.departureTime = departureTime;
        this.classCode = classCode;
        this.ticketCode = ticketCode;
    }

    public String getAirlineCode()
    {
        return airlineCode;
    }

    public String getFlightNumber()
    {
        return flightNumber;
    }

    public Date getDepartureTime()
    {
        return departureTime;
    }

    public String getClassCode()
    {
        return classCode;
    }

    public String getTicketCode()
    {
        return ticketCode;
    }

    public void setAirlineCode(String airlineCode)
    {
        this.airlineCode = airlineCode;
    }

    public void setFlightNumber(String flightNumber)
    {
        this.flightNumber = flightNumber;
    }

    public void setDepartureTime(Date departureTime)
    {
        this.departureTime = departureTime;
    }

    public void setClassCode(String classCode)
    {
        this.classCode = classCode;
    }

    public void setTicketCode(String ticketCode)
    {
        this.ticketCode = ticketCode;
    }

}