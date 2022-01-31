package be.vdab.fietsen.domain;

import javax.persistence.*;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "verantwoordelijkheden")
public class Verantwoordelijkheid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String naam;
    @ManyToMany
    //naam van tussentable
    @JoinTable(
            name = "docentenverantwoordelijkheden",
            //kolom in tussentable die de FK naar de PK van de huidige (verantwoordelijkheden) table is
            joinColumns = @JoinColumn(name = "verantwoordelijkheidId"),
            //kolom in tussentable die de FK naar de PK van de entity aan de andere associatie kant (docenten table) is
            inverseJoinColumns = @JoinColumn(name = "docentId"))
    private Set<Docent> docenten = new LinkedHashSet<>();

    public Verantwoordelijkheid(String naam) {
        this.naam = naam;
    }

    protected Verantwoordelijkheid() {
    }

    public boolean add(Docent docent) {
        var toegevoegd = docenten.add(docent);
        if (!docent.getVerantwoordelijkheden().contains(this)) {
            docent.add(this);
        }
        return toegevoegd;
    }

    public boolean remove(Docent docent) {
        var verwijderd = docenten.remove(docent);
        if (docent.getVerantwoordelijkheden().contains(this)) {
            docent.remove(this);
        }
        return verwijderd;
    }

    public long getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }

    public Set<Docent> getDocenten() {
        return Collections.unmodifiableSet(docenten);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Verantwoordelijkheid)) return false;
        Verantwoordelijkheid that = (Verantwoordelijkheid) o;
//        return Objects.equals(naam, that.naam);
        return naam.equalsIgnoreCase(that.naam);
    }

    @Override
    public int hashCode() {
        return Objects.hash(naam.toLowerCase());
    }
}
