package seng3150.group4.entity;



import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "distances")
public class DistancesEntity {

    @EmbeddedId
    private DistancesId id;

    @Column(name = "DistancesInKms")
    private String distancesInKms;

    public DistancesEntity(DistancesId id, String distancesInKms)
    {
        this.id = id;
        this.distancesInKms = distancesInKms;
    }

    public DistancesEntity() {}

    public DistancesId getId()
    {
        return id;
    }

    public void setId(DistancesId id)
    {
        this.id = id;
    }

    public String getDistancesInKms()
    {
        return distancesInKms;
    }

    public void setDistancesInKms(String distancesInKms)
    {
        this.distancesInKms = distancesInKms;
    }
}
