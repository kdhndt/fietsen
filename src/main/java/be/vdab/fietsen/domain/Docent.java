package be.vdab.fietsen.domain;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "docenten")
public class Docent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String voornaam;
    private String familienaam;
    private BigDecimal wedde;
    private String emailAdres;
    @Enumerated(EnumType.STRING)
    private Geslacht geslacht;

    //zonder id, database maakt die aan, JPA vult hiermee daarna de variabele
    public Docent(String voornaam, String familienaam, BigDecimal wedde, String emailAdres, Geslacht geslacht) {
        this.voornaam = voornaam;
        this.familienaam = familienaam;
        this.wedde = wedde;
        this.emailAdres = emailAdres;
        this.geslacht = geslacht;
    }

    //default constructor (constructor zonder parameters) is nodig omdat we zelf een constructor typen, JPA heeft deze nodig voor zijn interne werking
    //protected volstaat, public kan zorgen voor per ongeluk fouten maken, bv. een leeg Docent object aanmaken
    protected Docent() {}

    public long getId() {
        return id;
    }

    public String getVoornaam() {
        return voornaam;
    }

    public String getFamilienaam() {
        return familienaam;
    }

    public BigDecimal getWedde() {
        return wedde;
    }

    public String getEmailAdres() {
        return emailAdres;
    }

    public Geslacht getGeslacht() {
        return geslacht;
    }
}