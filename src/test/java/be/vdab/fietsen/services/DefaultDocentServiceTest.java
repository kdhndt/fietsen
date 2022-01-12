package be.vdab.fietsen.services;

import be.vdab.fietsen.domain.Docent;
import be.vdab.fietsen.domain.Geslacht;
import be.vdab.fietsen.exceptions.DocentNietGevondenException;
import be.vdab.fietsen.repositories.DocentRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
//unit test met mock, we maken een mock (dummy) die DocentRepository implementeert en trainen hem
//de mock spreekt geen database aan
class DefaultDocentServiceTest {
    private DefaultDocentService service;
    @Mock
    //implementatie van DocentRepository interface wordt gemockt
    private DocentRepository repository;
    private Docent docent;

    @BeforeEach
    void beforeEach() {
        //injecteer de mock
        service = new DefaultDocentService(repository);
        docent = new Docent("test", "test", BigDecimal.valueOf(100), "test@test.be", Geslacht.MAN);
    }

    @Test
    void opslag() {
        //train de mock
        when(repository.findById(1)).thenReturn(Optional.of(docent));
        //bovenstaande method wordt uitgevoerd bij onderstaande method
        service.opslag(1, BigDecimal.TEN);
        assertThat(docent.getWedde()).isEqualByComparingTo("110");
        //controle op uitvoering van method
        verify(repository).findById(1);
    }
    @Test
    void opslagVoorOnbestaandeDocent() {
        assertThatExceptionOfType(DocentNietGevondenException.class).isThrownBy(() -> service.opslag(-1, BigDecimal.TEN));
        verify(repository).findById(-1);
    }
}