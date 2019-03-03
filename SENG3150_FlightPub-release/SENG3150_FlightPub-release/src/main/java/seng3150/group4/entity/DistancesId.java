package seng3150.group4.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class DistancesId implements Serializable
{
    @Column(name = "DestinationCode1")
    private String destinationCode1;

    @Column(name = "DestinationCode2")
    private String destinationCode2;

    public DistancesId() {}

    public DistancesId(String destinationCode1, String destinationCode2)
    {
        this.destinationCode1 = destinationCode1;
        this.destinationCode2 = destinationCode2;
    }


    public String getDestinationCode1() {
        return destinationCode1;
    }

    public void setDestinationCode1(String destinationCode1) {
        this.destinationCode1 = destinationCode1;
    }

    public String getDestinationCode2() {
        return destinationCode2;
    }

    public void setDestinationCode2(String destinationCode2) {
        this.destinationCode2 = destinationCode2;
    }
}