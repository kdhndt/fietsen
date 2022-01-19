package be.vdab.fietsen.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;
import static org.junit.jupiter.api.Assertions.*;

class CampusTest {
    private Docent docent1;
    private Campus campus1;
    private Campus campus2;
    @BeforeEach
    void beforeEach() {
        campus1 = new Campus("test", new Adres("test", "test", "test", "test"));
        campus2 = new Campus("test2", new Adres("test2", "test2", "test2", "test2"));
        docent1 = new Docent("test", "test", BigDecimal.TEN, "test@test.be", Geslacht.MAN, campus1);
    }

    @Test
    void campus1IsDeCampusVanDocent1() {
        assertThat(campus1.getDocenten()).containsOnly(docent1);
        assertThat(docent1.getCampus()).isEqualTo(campus1);
    }

    @Test void docent1VerhuistVanCampus1NaarCampus2() {
        assertThat(campus2.add(docent1)).isTrue();
//        docent1.setCampus(campus2);
        assertThat(campus2.getDocenten()).containsOnly(docent1);
        assertThat(docent1.getCampus()).isEqualTo(campus2);
        assertThat(campus1.getDocenten()).doesNotContain(docent1);
    }

    @Test void eenNullCampusInDeSetterMislukt() {
        assertThatNullPointerException().isThrownBy(() -> campus1.add(null));
    }
}