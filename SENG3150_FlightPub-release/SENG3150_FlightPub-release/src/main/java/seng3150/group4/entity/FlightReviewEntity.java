package seng3150.group4.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "flight_reviews")
public class FlightReviewEntity implements Serializable {

    @EmbeddedId
    private FlightReviewId id;

    @Column(name = "rating")
    private BigDecimal rating;

    @Column(name = "comments")
    private String comments;

    public FlightReviewEntity() {}

    public FlightReviewEntity(FlightReviewId id, BigDecimal rating, String comments)
    {
        this.id = id;
        this.rating = rating;
        this.comments = comments;
    }

    public FlightReviewId getId() {
        return id;
    }

    public void setId(FlightReviewId id) {
        this.id = id;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
