package be.vdab.fietsen.domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
@Access(AccessType.FIELD)
public class TelefoonNr {
    private String nummer;
    private boolean fax;
    private String opmerking;

    public TelefoonNr(String nummer, boolean fax, String opmerking) {
        this.nummer = nummer;
        this.fax = fax;
        this.opmerking = opmerking;
    }

    protected TelefoonNr() {
    }

    public String getNummer() {
        return nummer;
    }

    public boolean isFax() {
        return fax;
    }

    public String getOpmerking() {
        return opmerking;
    }

    //telnr1.equals.telnr2
    @Override
    public boolean equals(Object o) {
        return o instanceof TelefoonNr telefoonNr && nummer.equalsIgnoreCase(telefoonNr.nummer);
/*        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TelefoonNr that = (TelefoonNr) o;
        return Objects.equals(nummer, that.nummer);*/
    }

    @Override
    public int hashCode() {
//        return Objects.hash(nummer);
        return nummer.toUpperCase().hashCode();
    }
}
