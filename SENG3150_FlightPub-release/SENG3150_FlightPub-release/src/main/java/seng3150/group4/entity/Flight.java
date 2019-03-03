package seng3150.group4.entity;

import java.util.Date;

public class Flight {

    private String flightNumber;
    private String airlineName;
    private String airlineCode;
    private String departure_airport;
    private String arrival_airport;
    private int duration;
    private int stop_overs;
    private String stopover_airport;
    private String stopover_arrtime;
    private String stopover_deptime;
    private double cost;
    private String depdate;
    private String class_type;
    private String plane_code;
    private String dep_time;
    private String arr_date;
    private String arr_time;
    private Date departureTime;
    private boolean display;

    public Flight() {}

    public Flight(String flightNumber, String airlineName, String airlineCode, String departure_airport, String arrival_airport, int duration, int stopOvers,
                  double cost, String depdate, String class_type, String plane_code, String dep_time, String arr_date, String arr_time,
                  String stopover_airport, String stopover_arrtime, String stopover_deptime, Date departureTime) {


        this.departure_airport = departure_airport;
        this.arrival_airport = arrival_airport;
        this.flightNumber = flightNumber;
        this.airlineName = airlineName;
        this.duration = duration;
        this.stop_overs = stopOvers;
        this.cost = cost;
        this.depdate = depdate;
        this.class_type = class_type;
        this.plane_code = plane_code;
        this.dep_time = dep_time;
        this.arr_date = arr_date;
        this.arr_time = arr_time;
        this.stopover_airport = stopover_airport;
        this.stopover_arrtime = stopover_arrtime;
        this.stopover_deptime = stopover_deptime;
        this.airlineCode = airlineCode;
        this.departureTime = departureTime;

    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getAirlineName() {
        return airlineName;
    }

    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }

    public String getAirlineCode() {
        return airlineCode;
    }

    public void setAirlineCode(String airlineCode) {
        this.airlineCode = airlineCode;
    }

    public String getDeparture_airport() {
        return departure_airport;
    }

    public void setDeparture_airport(String departure_airport) {
        this.departure_airport = departure_airport;
    }

    public String getArrival_airport() {
        return arrival_airport;
    }

    public void setArrival_airport(String arrival_airport) {
        this.arrival_airport = arrival_airport;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getStop_overs() {
        return stop_overs;
    }

    public void setStop_overs(int stop_overs) {
        this.stop_overs = stop_overs;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getDepdate() {
        return depdate;
    }

    public void setDepdate(String depdate) {
        this.depdate = depdate;
    }

    public String getClass_type() {
        return class_type;
    }

    public void setClass_type(String class_type) {
        this.class_type = class_type;
    }

    public String getPlane_code() {
        return plane_code;
    }

    public void setPlane_code(String plane_code) {
        this.plane_code = plane_code;
    }

    public String getDep_time() {
        return dep_time;
    }

    public void setDep_time(String dep_time) {
        this.dep_time = dep_time;
    }

    public String getArr_date() {
        return arr_date;
    }

    public void setArr_date(String arr_date) {
        this.arr_date = arr_date;
    }

    public String getArr_time() {
        return arr_time;
    }

    public void setArr_time(String arr_time) {
        this.arr_time = arr_time;
    }

    public String getStopover_airport() {
        return stopover_airport;
    }

    public void setStopover_airport(String stopover_airport) {
        this.stopover_airport = stopover_airport;
    }

    public String getStopover_arrtime () { return stopover_arrtime; }

    public void setStopover_arrtime(String stopover_arrtime) {
        this.stopover_arrtime = stopover_arrtime;
    }

    public String getStopover_deptime () { return stopover_deptime; }

    public void setStopover_deptime(String stopover_deptime) {
        this.stopover_deptime = stopover_deptime;
    }

    public Date getDepartureTime () { return departureTime; }

    public void setDepartureTime(Date departureTime) { this.departureTime = departureTime; }

    public boolean isDisplay() {
        return display;
    }

    public void setDisplay(boolean display) {
        this.display = display;
    }


}
