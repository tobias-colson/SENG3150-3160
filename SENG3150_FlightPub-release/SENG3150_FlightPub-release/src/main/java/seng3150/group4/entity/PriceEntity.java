package seng3150.group4.entity;


import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "price")
public class PriceEntity implements Serializable{

    @EmbeddedId
    private PriceId id;

    // Helps with the conversion between java.util.Date and MySQL DATETIME
    @Column(name = "EndDate", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    @Column(name = "Price")
    private BigDecimal price;

    @Column(name = "PriceLeg1")
    private BigDecimal priceLeg1;

    @Column(name = "PriceLeg2")
    private BigDecimal priceLeg2;

    public PriceEntity() {}

    public PriceEntity(PriceId id, Date endDate, BigDecimal price, BigDecimal priceLeg1, BigDecimal priceLeg2) {
        this.id = id;
        this.endDate = endDate;
        this.price = price;
        this.priceLeg1 = priceLeg1;
        this.priceLeg2 = priceLeg2;
    }

    public PriceId getId() {
        return id;
    }

    public void setId(PriceId id) {
        this.id = id;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPriceLeg1() {
        return priceLeg1;
    }

    public void setPriceLeg1(BigDecimal priceLeg1) {
        this.priceLeg1 = priceLeg1;
    }

    public BigDecimal getPriceLeg2() {
        return priceLeg2;
    }

    public void setPriceLeg2(BigDecimal priceLeg2) { this.priceLeg2 = priceLeg2; }
}
