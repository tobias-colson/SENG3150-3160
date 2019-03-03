package seng3150.group4.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class FlightReviewId implements Serializable
{
    @Column(name = "userID")
    private int userID;

    @Column(name = "FlightNumber")
    private String flightNumber;

    public FlightReviewId() {}

    public FlightReviewId(int userID, String flightNumber)
    {
        this.userID = userID;
        this.flightNumber = flightNumber;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }
}
