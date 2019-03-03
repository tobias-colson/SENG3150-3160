package seng3150.group4.entity;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ticketclass")
public class TicketClassEntity implements Serializable
{
    @Id
    @Column(name = "ClassCode")
    private String classCode;

    @Column(name = "Details")
    private String details;

    public TicketClassEntity() {}

    public TicketClassEntity(String classCode, String details) {
        this.classCode = classCode;
        this.details = details;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}

