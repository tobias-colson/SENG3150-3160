package seng3150.group4.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "FlightHistory")
public class FlightHistoryEntity implements Serializable
{
    @EmbeddedId
    private FlightHistoryId id;

    public FlightHistoryEntity() { }

    public FlightHistoryEntity(FlightHistoryId id) {
        this.id = id;
    }

    public FlightHistoryId getId() { return id; }

    public void setId(FlightHistoryId id) { this.id = id; }

}