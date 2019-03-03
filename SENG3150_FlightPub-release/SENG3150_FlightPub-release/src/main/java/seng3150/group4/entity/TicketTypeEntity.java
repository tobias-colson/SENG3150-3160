package seng3150.group4.entity;



import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tickettype")
public class TicketTypeEntity implements Serializable
{
    @Id
    @Column(name = "TicketCode")
    private String ticketCode;

    @Column(name = "Name")
    private String name;

    @Column(name = "Transferrable")
    private boolean transferrable;

    @Column(name = "Refundable")
    private boolean refundable;

    @Column(name = "Exchangeable")
    private boolean exchangeable;

    @Column(name = "FrequentFlyerPoints")
    private boolean frequentFlyerPoints;

    public TicketTypeEntity() {}

    public TicketTypeEntity(String ticketCode, String name, boolean transferrable, boolean refundable, boolean exchangeable, boolean frequentFlyerPoints) {
        this.ticketCode = ticketCode;
        this.name = name;
        this.transferrable = transferrable;
        this.refundable = refundable;
        this.exchangeable = exchangeable;
        this.frequentFlyerPoints = frequentFlyerPoints;
    }

    public String getTicketCode() {
        return ticketCode;
    }

    public void setTicketCode(String ticketCode) {
        this.ticketCode = ticketCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isTransferrable() {
        return transferrable;
    }

    public void setTransferrable(boolean transferrable) {
        this.transferrable = transferrable;
    }

    public boolean isRefundable() {
        return refundable;
    }

    public void setRefundable(boolean refundable) {
        this.refundable = refundable;
    }

    public boolean isExchangeable() {
        return exchangeable;
    }

    public void setExchangeable(boolean exchangeable) {
        this.exchangeable = exchangeable;
    }

    public boolean isFrequentFlyerPoints() {
        return frequentFlyerPoints;
    }

    public void setFrequentFlyerPoints(boolean frequentFlyerPoints) {
        this.frequentFlyerPoints = frequentFlyerPoints;
    }
}
