package seng3150.group4.entity;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "availability")
public class AvailabilityEntity implements Serializable {

    @EmbeddedId
    private AvailabilityId id;

    @Column(name = "NumberAvailableSeatsLeg1")
    private int availableSeatsLeg1;

    @Column(name = "NumberAvailableSeatsLeg2")
    private int availableSeatsLeg2;

    public AvailabilityId getId() { return id; }

    public void setId(AvailabilityId id) { this.id = id; }

    public void setAvailableSeatsLeg1(int availableSeatsLeg1)
    {
        this.availableSeatsLeg1 = availableSeatsLeg1;
    }

    public void setAvailableSeatsLeg2(int availableSeatsLeg2)
    {
        this.availableSeatsLeg2 = availableSeatsLeg2;
    }

    public int getAvailableSeatsLeg1()
    {
        return availableSeatsLeg1;
    }

    public int getAvailableSeatsLeg2()
    {
        return availableSeatsLeg2;
    }
}