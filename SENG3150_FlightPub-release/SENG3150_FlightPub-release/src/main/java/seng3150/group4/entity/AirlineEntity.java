package seng3150.group4.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "airlines")
public class AirlineEntity implements Serializable {

    @Id
    @Column(name = "AirlineCode")
    private String airlineCode;

    @Column(name = "AirlineName")
    private String airlineName;

    @Column(name = "CountryCode3")
    private String countryCode;

    public AirlineEntity() {}

    public AirlineEntity(String airlineCode, String airlineName, String countryCode)
    {
        this.airlineCode = airlineCode;
        this.airlineName = airlineName;
        this.countryCode = countryCode;
    }

    public void setAirlineCode(String airlineCode)
    {
        this.airlineCode = airlineCode;
    }

    public void setAirlineName(String airlineName)
    {
        this.airlineName = airlineName;
    }

    public void setCountryCode(String countryCode)
    {
        this.countryCode = countryCode;
    }

    public String getAirlineCode()
    {
        return airlineCode;
    }

    public String getAirlineName()
    {
        return airlineName;
    }

    public String getCountryCode()
    {
        return countryCode;
    }


}
