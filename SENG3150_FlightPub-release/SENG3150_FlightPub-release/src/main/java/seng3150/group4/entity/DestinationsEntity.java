package seng3150.group4.entity;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Destinations")
public class DestinationsEntity {


    @Id
    @Column(name = "DestinationCode")
    private String destinationCode;


    @Column(name = "Airport")
    private String airport;


    @Column(name = "CountryCode3")
    private String countryCode3;

    public DestinationsEntity()
    {
        destinationCode = "";
        airport = "";
        countryCode3 = "";
    }

    public DestinationsEntity(String destinationCode, String airport, String countryCode3)
    {
        this.destinationCode = destinationCode;
        this.airport = airport;
        this.countryCode3 = countryCode3;
    }

    public void setDestinationCode(String destinationCode)
    {
        this.destinationCode = destinationCode;
    }

    public void setAirport(String airport)
    {
        this.airport = airport;
    }

    public void setCountryCode3(String countryCode3)
    {
        this.countryCode3 = countryCode3;
    }

    public String getDestinationCode()
    {
        return destinationCode;
    }

    public String getAirport()
    {
        return airport;
    }

    public String getCountryCode3()
    {
        return countryCode3;
    }


}
