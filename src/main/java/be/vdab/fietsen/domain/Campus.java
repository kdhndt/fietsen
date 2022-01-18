package be.vdab.fietsen.domain;

import javax.persistence.*;
import java.util.Collections;
import java.util.LinkedHashSet;
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

    public Campus(String naam, Adres adres) {
        this.naam = naam;
        this.adres = adres;
        this.telefoonNrs = new LinkedHashSet<>();
    }

    protected Campus() {
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
}
