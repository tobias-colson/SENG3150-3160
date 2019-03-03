package seng3150.group4.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "Flights")
public class FlightEntity implements Serializable
{
    @EmbeddedId
    private FlightId id;

    @Column(name = "DepartureCode")
    private String departureCode;

    @Column(name = "StopOverCode")
    private String stopOverCode;

    @Column(name = "DestinationCode")
    private String destinationCode;

    // Helps with the conversion between java.util.Date and MySQL DATETIME
    @Column(name = "ArrivalTimeStopOver", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date arrivalTimeStopOver;

    // Helps with the conversion between java.util.Date and MySQL DATETIME
    @Column(name = "DepartureTimeStopOver", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date departureTimeStopOver;

    // Helps with the conversion between java.util.Date and MySQL DATETIME
    @Column(name = "ArrivalTime", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date arrivalTime;

    @Column(name = "PlaneCode")
    private String planeCode;

    @Column(name = "Duration")
    private int duration;

    @Column(name = "DurationSecondLeg")
    private Integer durationSecondLeg;

    public FlightEntity() {}

    public FlightEntity(FlightId id, String departureCode, String stopOverCode, String destinationCode, Date arrivalTimeStopOver, Date departureTimeStopOver, Date arrivalTime, String planeCode, int duration, Integer durationSecondLeg) {
        this.id = id;
        this.departureCode = departureCode;
        this.stopOverCode = stopOverCode;
        this.destinationCode = destinationCode;
        this.arrivalTimeStopOver = arrivalTimeStopOver;
        this.departureTimeStopOver = departureTimeStopOver;
        this.arrivalTime = arrivalTime;
        this.planeCode = planeCode;
        this.duration = duration;
        this.durationSecondLeg = durationSecondLeg;
    }

    public FlightId getId() {
        return id;
    }

    public void setId(FlightId id) {
        this.id = id;
    }

    public String getDepartureCode() {
        return departureCode;
    }

    public void setDepartureCode(String departureCode) {
        this.departureCode = departureCode;
    }

    public String getStopOverCode() {
        return stopOverCode;
    }

    public void setStopOverCode(String stopOverCode) {
        this.stopOverCode = stopOverCode;
    }

    public String getDestinationCode() {
        return destinationCode;
    }

    public void setDestinationCode(String destinationCode) {
        this.destinationCode = destinationCode;
    }

    public Date getArrivalTimeStopOver() {
        return arrivalTimeStopOver;
    }

    public void setArrivalTimeStopOver(Date arrivalTimeStopOver) {
        this.arrivalTimeStopOver = arrivalTimeStopOver;
    }

    public Date getDepartureTimeStopOver() {
        return departureTimeStopOver;
    }

    public void setDepartureTimeStopOver(Date departureTimeStopOver) {
        this.departureTimeStopOver = departureTimeStopOver;
    }

    public Date getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getPlaneCode() {
        return planeCode;
    }

    public void setPlaneCode(String planeCode) {
        this.planeCode = planeCode;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Integer getDurationSecondLeg() {
        return durationSecondLeg;
    }

    public void setDurationSecondLeg(Integer durationSecondLeg) {
        this.durationSecondLeg = durationSecondLeg;
    }
}
