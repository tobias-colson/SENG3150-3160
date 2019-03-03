package seng3150.group4.entity;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "country")
public class CountryEntity {

    @Id
    @Column(name = "countryCode3")
    private String countryCode3;


    @Column(name = "countryCode2")
    private String countryCode2;


    @Column(name = "countryName")
    private String countryName;


    @Column(name = "alternateName1")
    private String alternateName1;


    @Column(name = "alternateName2")
    private String alternateName2;


    @Column(name = "motherCountryCode3")
    private String motherCountryCode3;


    @Column(name = "motherCountryComment")
    private String motherCountryComment;

    public CountryEntity()
    {
        countryCode2 = "";
        countryCode3 = "";
        countryName = "";
        alternateName1 = "";
        alternateName2 = "";
        motherCountryCode3 = "";
        motherCountryComment = "";
    }

    public CountryEntity(String countryCode2, String countryCode3, String countryName,
                   String alternateName1, String alternateName2, String motherCountryCode3, String motherCountryComment)
    {
        this.countryCode2 = countryCode2;
        this.countryCode3 = countryCode3;
        this.countryName = countryName;
        this.alternateName1 = alternateName1;
        this.alternateName2 = alternateName2;
        this.motherCountryCode3 = motherCountryCode3;
        this.motherCountryComment = motherCountryComment;
    }

    public void setCountryCode2(String countryCode2)
    {
        this.countryCode2 = countryCode2;
    }

    public void setCountryCode3(String countryCode3)
    {
        this.countryCode3 = countryCode3;
    }

    public void setCountryName(String countryName)
    {
        this.countryName = countryName;
    }

    public void setAlternateName1(String alternateName1)
    {
        this.alternateName1 = alternateName1;
    }

    public void setAlternateName2(String alternateName2)
    {
        this.alternateName2 = alternateName2;
    }

    public void setMotherCountryCode3(String motherCountryCode3)
    {
        this.motherCountryCode3 = motherCountryCode3;
    }

    public void setMotherCountryComment(String motherCountryComment)
    {
        this.motherCountryComment = motherCountryComment;
    }

    public String getCountryCode2()
    {
        return countryCode2;
    }

    public String getCountryCode3()
    {
        return countryCode3;
    }

    public String getCountryName()
    {
        return countryName;
    }

    public String getAlternateName1()
    {
        return alternateName1;
    }

    public String getAlternateName2()
    {
        return alternateName2;
    }

    public String getMotherCountryCode3()
    {
        return motherCountryCode3;
    }

    public String getMotherCountryComment()
    {
        return motherCountryComment;
    }
}
