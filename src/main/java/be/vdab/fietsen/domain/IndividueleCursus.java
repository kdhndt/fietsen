package be.vdab.fietsen.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
//@DiscriminatorValue("I")
@Table(name = "individuelecursussen")
public class IndividueleCursus extends Cursus {
    private int duurtijd;

    public IndividueleCursus(String naam, int duurtijd) {
        super(naam);
        this.duurtijd = duurtijd;
    }

    protected IndividueleCursus() {}

    public int getDuurtijd() {
        return duurtijd;
    }
}
