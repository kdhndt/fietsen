package be.vdab.fietsen.domain;

import javax.persistence.*;
import java.util.UUID;

@Entity
//table strategie voor database, je gebruikt Inheritance in Java maar hoe het er uit ziet in je Database kan varieren
//SINGLE_TABLE, JOINED, TABLE_PER_CLASS
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name = "cursussen")
//@DiscriminatorColumn(name = "soort")

//abstract keyword zorgt ervoor dat hier geen objecten van kunnen gemaakt worden, doel van deze class is om als baseclass te dienen
public abstract class Cursus {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "binary(16)")
    private UUID id;
    private String naam;

    public Cursus(String naam) {
        this.naam = naam;
        id = UUID.randomUUID();
    }

    protected Cursus() {}

    public UUID getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }
}
