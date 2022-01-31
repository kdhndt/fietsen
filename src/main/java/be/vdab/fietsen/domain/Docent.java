package be.vdab.fietsen.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/*@NamedQuery(name = "Docent.findByWeddeBetween",
        query = """
                select d from Docent d
                where d.wedde between :van and :tot
                order by d.wedde, d.id
                """)*/

@Entity
@Table(name = "docenten")
//bij het lezen van een Docent entity uit de Database leest JPA nu ook meteen de bijbehorende Campus entity via een join
//JPA doet dit echter pas als je dit vraagt, bij het oproepen van een named query?
@NamedEntityGraph(name = "Docent.metCampus", attributeNodes = @NamedAttributeNode("campus"))
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
    @ElementCollection
    //primary key uit je docenten table wordt als foreign key gebruikt in de nieuwe, aparte table met als kolom naam docentId
    @CollectionTable(name = "docentenbijnamen", joinColumns = @JoinColumn(name = "docentId"))
    @Column(name = "bijnaam")
    private Set<String> bijnamen;
    //een campus heeft meerdere docenten
    //optional = false throwt exception als campus niet ingevuld is
    //lazy loading ipv eager, zoekt enkel bijbehorende campus informatie wanneer nodig
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "campusId")
    //datatype Campus, groot verschil met Database (campusId), maar zo heb je toegang tot de campus eigenschappen
    private Campus campus;
    //associatie wordt voorgesteld aan de andere kant (Verantwoordelijkheid) door de docenten variabele met alle details van de tussentable, dus geef die hier mee met mappedBy
    @ManyToMany(mappedBy = "docenten")
    private Set<Verantwoordelijkheid> verantwoordelijkheden = new LinkedHashSet<>();

    //zonder id, database maakt die aan, JPA vult hiermee daarna de variabele
    public Docent(String voornaam, String familienaam, BigDecimal wedde, String emailAdres, Geslacht geslacht, Campus campus) {
        this.voornaam = voornaam;
        this.familienaam = familienaam;
        this.wedde = wedde;
        this.emailAdres = emailAdres;
        this.geslacht = geslacht;
        this.bijnamen = new LinkedHashSet<>();
        setCampus(campus);
    }

    //default constructor (constructor zonder parameters) is nodig omdat we zelf een constructor typen, JPA heeft deze nodig voor zijn interne werking
    //protected volstaat, public kan zorgen voor per ongeluk fouten maken, bv. een leeg Docent object aanmaken
    protected Docent() {
    }

    public boolean add(Verantwoordelijkheid verantwoordelijkheid) {
        var toegevoegd = verantwoordelijkheden.add(verantwoordelijkheid);
        if (!verantwoordelijkheid.getDocenten().contains(this)) {
            verantwoordelijkheid.add(this);
        }
        return toegevoegd;
    }

    public boolean remove(Verantwoordelijkheid verantwoordelijkheid) {
        var verwijderd = verantwoordelijkheden.remove(verantwoordelijkheid);
        if (verantwoordelijkheid.getDocenten().contains(this)) {
            verantwoordelijkheid.remove(this);
        }
        return verwijderd;
    }

    public Set<Verantwoordelijkheid> getVerantwoordelijkheden() {
        return Collections.unmodifiableSet(verantwoordelijkheden);
    }

    public Campus getCampus() {
        return campus;
    }

    public void setCampus(Campus campus) {
        //er gebeuren twee dingen, beide private variabelen in 2 verschillende entities worden aangepast!
        if (!campus.getDocenten().contains(this)) {
            campus.add(this);
        }
        this.campus = campus;
    }

    public boolean addBijnaam(String bijnaam) {
        if (bijnaam.trim().isEmpty()) {
            throw new IllegalArgumentException();
        }
        return bijnamen.add(bijnaam);
    }

    public boolean removeBijnaam(String bijnaam) {
        return bijnamen.remove(bijnaam);
    }

    public Set<String> getBijnamen() {
//        return bijnamen;
        //getter geeft op deze manier een read-only voorstelling terug van de verzameling, zo kan je niet per ongeluk een .add of .remove erna typen
    return Collections.unmodifiableSet(bijnamen);
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

    @Override
    public boolean equals(Object object) {
        return object instanceof Docent docent &&
                emailAdres.equalsIgnoreCase(docent.emailAdres);
    }
    @Override
    public int hashCode() {
        return emailAdres == null ? 0 : emailAdres.toLowerCase().hashCode();
    }

}
