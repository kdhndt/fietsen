package be.vdab.fietsen.domain;

import javax.persistence.*;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "campussen")

public class Campus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String naam;
    @Embedded
    private Adres adres;
    //deze annotations moeten bij je membervariabelen komen, niet bij de class!
    @ElementCollection
    @CollectionTable(name = "campussentelefoonnrs", joinColumns = @JoinColumn(name = "campusId"))
    @OrderBy("fax")
    private Set<TelefoonNr> telefoonNrs;
    //campus verwijst naar de member variable in Docent
    @OneToMany(mappedBy = "campus")
    @OrderBy("voornaam, familienaam")
    private Set<Docent> docenten;

    public Campus(String naam, Adres adres) {
        this.naam = naam;
        this.adres = adres;
        this.telefoonNrs = new LinkedHashSet<>();
        this.docenten = new LinkedHashSet<>();
    }

    protected Campus() {
    }

    public Set<Docent> getDocenten() {
        return Collections.unmodifiableSet(docenten);
    }

    public boolean add(Docent docent) {
        var toegevoegd = docenten.add(docent);
        var oudeCampus = docent.getCampus();
        if (oudeCampus != null && oudeCampus != this) {
            oudeCampus.docenten.remove(docent);
        }
        if (this != oudeCampus) {
            docent.setCampus(this);
        }
        return toegevoegd;
    }

    //zelf toegevoegd
    public boolean addTelefoonNr(TelefoonNr telefoonNr) {
        //controle?
        return telefoonNrs.add(telefoonNr);
    }

    public long getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }

    public Adres getAdres() {
        return adres;
    }

    public Set<TelefoonNr> getTelefoonNrs() {
        return Collections.unmodifiableSet(telefoonNrs);
    }

    //equals werking getest, wanneer je equals/hashCode op een string baseert zorg er dan voor dat case genegeerd wordt
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Campus)) return false;
        Campus campus = (Campus) o;
//        return Objects.equals(naam, campus.naam);
        return naam.equalsIgnoreCase(campus.naam);
    }

    @Override
    public int hashCode() {
//        return Objects.hash(naam);
        return Objects.hash(naam.toLowerCase());
    }


}
