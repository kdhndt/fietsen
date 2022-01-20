package be.vdab.fietsen.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class VerantwoordelijkheidTest {
    private final static BigDecimal WEDDE = BigDecimal.valueOf(200);
    private Docent docent1;
    private Campus campus1;
    private Verantwoordelijkheid verantwoordelijkheid1;

    @BeforeEach
    void beforeEach() {
        campus1 = new Campus("test", new Adres("test", "test", "test", "test"));
        docent1 = new Docent("test", "test", WEDDE, "test@test.be", Geslacht.MAN, campus1);
        verantwoordelijkheid1 = new Verantwoordelijkheid("EHBO");
    }

    @Test void docentToevoegen() {
        assertThat(verantwoordelijkheid1.getDocenten()).isEmpty();
        assertThat(verantwoordelijkheid1.add(docent1)).isTrue();
        assertThat(verantwoordelijkheid1.getDocenten()).containsOnly(docent1);
        assertThat(docent1.getVerantwoordelijkheden()).contains(verantwoordelijkheid1);
    }

    @Test void docentVerwijderen() {
        assertThat(verantwoordelijkheid1.add(docent1)).isTrue();
        assertThat(verantwoordelijkheid1.remove(docent1)).isTrue();
        assertThat(verantwoordelijkheid1.getDocenten()).isEmpty();
        assertThat(docent1.getVerantwoordelijkheden()).isEmpty();

    }
}