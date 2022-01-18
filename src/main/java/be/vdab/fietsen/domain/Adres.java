package be.vdab.fietsen.domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;

//value object class
@Embeddable
@Access(AccessType.FIELD)
public class Adres {
    //geen variabele die verwijst naar bijbehorende object(en), zo is deze class herbruikbaar bij andere entity classes
    private String straat;
    private String huisNr;
    private String postcode;
    private String gemeente;

    public Adres(String straat, String huisNr, String postcode, String gemeente) {
        this.straat = straat;
        this.huisNr = huisNr;
        this.postcode = postcode;
        this.gemeente = gemeente;
    }

    protected Adres() {
    }

    public String getStraat() {
        return straat;
    }

    public String getHuisNr() {
        return huisNr;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getGemeente() {
        return gemeente;
    }
}
