package be.vdab.fietsen.services;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.persistence.EntityManager;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(showSql = false)
@Import(DefaultDocentService.class)
//JpaDocentRepository bean kan niet worden geladen met @Import vanwege package visibility
//value = package, resourcePattern = naam van de class van de bean
@ComponentScan(value = "be.vdab.fietsen.repositories", resourcePattern = "JpaDocentRepository.class")
@Sql({"/insertCampus.sql", "/insertDocent.sql"})
//integration test = test de samenwerking tussen service en database (JpaDocentRepository)
class DefaultDocentServiceIntegrationTest extends AbstractTransactionalJUnit4SpringContextTests {
    private final static String DOCENTEN = "docenten";
    private final DefaultDocentService service;
    private final EntityManager manager;

    DefaultDocentServiceIntegrationTest(DefaultDocentService service, EntityManager manager) {
        this.service = service;
        this.manager = manager;
    }

    private long idVanTestMan() {
        return jdbcTemplate.queryForObject("select id from docenten where voornaam = 'testM'", Long.class);
    }

    @Test
    void opslag() {
        var id = idVanTestMan();
        service.opslag(id, BigDecimal.TEN);
        //JPA doet op deze manier de wijzigingen direct (anders worden ze door de EntityManager gespaard om JDBC batch updates samen uit te voeren voor performantie)
        manager.flush();
        assertThat(countRowsInTableWhere(DOCENTEN, "wedde = 1100 and id =" + id)).isOne();
    }
}