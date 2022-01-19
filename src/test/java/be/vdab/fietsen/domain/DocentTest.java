package be.vdab.fietsen.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

//geen annotations nodig, test van domain logica met public visibility
class DocentTest {
    private final static BigDecimal WEDDE = BigDecimal.valueOf(200);
    private Docent docent1;
    private Docent docent2;
    private Docent docent3;
    private Campus campus1;
    private Campus campus2;

    @BeforeEach
    void beforeEach() {
        campus1 = new Campus("test", new Adres("test", "test", "test", "test"));
        docent1 = new Docent("test", "test", WEDDE, "test@test.be", Geslacht.MAN, campus1);
        docent2 = new Docent("test2", "test2", WEDDE, "test2@test.be", Geslacht.MAN, campus1);
//        docent3 = new Docent("test2", "test2", WEDDE, "TEst2@test.be", Geslacht.MAN, campus1);
        campus2 = new Campus("test2", new Adres("test2", "test2", "test2", "test2"));
    }

    @Test void docent1KomtVoorInCampus1() {
        assertThat(campus1.getDocenten()).contains(docent1);
        assertThat(docent1.getCampus()).isEqualTo(campus1);
    }

    @Test void docent1VerhuistVanCampus1NaarCampus2() {
        docent1.setCampus(campus2);
        assertThat(campus2.getDocenten()).containsOnly(docent1);
        assertThat(docent1.getCampus()).isEqualTo(campus2);
        assertThat(campus1.getDocenten()).doesNotContain(docent1);
    }

    @Test void eenNullCampusInDeSetterMislukt() {
        assertThatNullPointerException().isThrownBy(() -> docent1.setCampus(null));
    }

    @Test void meerdereDocentenKunnenTotDezelfdeCampusBehoren() {
//        assertThat(campus1.add(docent1)).isTrue();
//        assertThat(campus1.add(docent2)).isTrue();
//        assertThat(campus1.add(docent3)).isFalse();
        assertThat(campus1.getDocenten()).containsOnly(docent1, docent2);
    }

    @Test
    void opslag() {
        docent1.opslag(BigDecimal.TEN);
        assertThat(docent1.getWedde()).isEqualByComparingTo("220");
    }

    @Test
    void opslagMetNullMislukt() {
        assertThatNullPointerException().isThrownBy(() -> docent1.opslag(null));
    }
    @Test
    void opslagMet0Mislukt() {
        assertThatIllegalArgumentException().isThrownBy(() -> docent1.opslag(BigDecimal.ZERO));
    }

    @Test
    void negatieveOpslagMislukt() {
        assertThatIllegalArgumentException().isThrownBy(() -> docent1.opslag(BigDecimal.valueOf(-1)));
    }

    //verzameling value objects (basistype zoals String, Integer, etc.)

    @Test void eenNieuweDocentHeeftGeenBijnamen() {
        assertThat(docent1.getBijnamen()).isEmpty();
    }
    @Test void bijnaamToevoegen() {
        assertThat(docent1.addBijnaam("test")).isTrue();
        assertThat(docent1.getBijnamen()).containsOnly("test");
    }
    @Test void tweeKeerDezelfdeBijnaamMislukt() {
        docent1.addBijnaam("test");
        assertThat(docent1.addBijnaam("test")).isFalse();
        assertThat(docent1.getBijnamen()).containsOnly("test");
    }
    @Test void nullAlsBijnaamMislukt() {
        assertThatNullPointerException().isThrownBy(() -> docent1.addBijnaam(null));
    }
    @Test void eenLegeBijnaamMislukt() {
        assertThatIllegalArgumentException().isThrownBy(() -> docent1.addBijnaam(""));
    }
    @Test void eenBijnaamMetEnkelSpatiesMislukt() {
        assertThatIllegalArgumentException().isThrownBy(() -> docent1.addBijnaam("   "));
    }
    @Test void bijnaamVerwijderen() {
        docent1.addBijnaam("test");
        assertThat(docent1.removeBijnaam("test")).isTrue();
        assertThat(docent1.getBijnamen()).isEmpty();
    }
    @Test void eenBijnaamVerwijderenDieJeNietToevoegdeMislukt() {
        docent1.addBijnaam("test");
        assertThat(docent1.removeBijnaam("test2")).isFalse();
        assertThat(docent1.getBijnamen()).containsOnly("test");
    }
}