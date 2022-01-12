package be.vdab.fietsen.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

/*@NamedQuery(name = "Docent.findByWeddeBetween",
        query = """
                select d from Docent d
                where d.wedde between :van and :tot
                order by d.wedde, d.id
                """)*/
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
    protected Docent() {
    }

    public void opslag(BigDecimal percentage) {
        if (percentage.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException();
        }
        var factor = BigDecimal.ONE.add(percentage.divide(BigDecimal.valueOf(100)));
        wedde = wedde.multiply(factor).setScale(2, RoundingMode.HALF_UP);
    }

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
