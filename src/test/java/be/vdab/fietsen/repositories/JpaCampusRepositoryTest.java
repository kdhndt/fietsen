package be.vdab.fietsen.repositories;

import be.vdab.fietsen.domain.Adres;
import be.vdab.fietsen.domain.Campus;
import be.vdab.fietsen.domain.TelefoonNr;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(showSql = false)
@Import(JpaCampusRepository.class)
@Sql("/insertCampus.sql")
class JpaCampusRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {
    private static final String CAMPUSSEN = "campussen";
    private JpaCampusRepository repository;
    private final EntityManager manager;
    private static final String CAMPUSSENTELEFOONNRS = "campussentelefoonnrs";


    public JpaCampusRepositoryTest(JpaCampusRepository repository, EntityManager manager) {
        this.repository = repository;
        this.manager = manager;
    }

    private long idVanTestCampus() {
        return jdbcTemplate.queryForObject("select id from campussen where naam='test'", Long.class);
    }

    @Test
    void create() {
        var campus = new Campus("test", new Adres("test", "test", "test", "test"));
        repository.create(campus);
        assertThat(countRowsInTableWhere(CAMPUSSEN, "id = " + campus.getId())).isOne();
    }

    @Test
    void findById() {
        assertThat(repository.findById(idVanTestCampus()))
                //lambda met meerdere asserts
                .hasValueSatisfying(campus -> {
                    assertThat(campus.getNaam()).isEqualTo("test");
                    assertThat(campus.getAdres().getGemeente()).isEqualTo("test");
                });
    }

    @Test void telefoonNrsLezen() {
        assertThat(repository.findById(idVanTestCampus()))
                .hasValueSatisfying(campus -> assertThat(campus.getTelefoonNrs()).containsOnly(new TelefoonNr("1", false, "test")));
    }

    //zelf toegevoegd
    @Test void telefoonNrToevoegen() {
        var campus = new Campus("test", new Adres("test", "test", "test", "test"));
        repository.create(campus);
        campus.addTelefoonNr(new TelefoonNr("1", false, "test"));
        manager.flush();
        assertThat(countRowsInTableWhere(CAMPUSSENTELEFOONNRS, "opmerking = 'test' and campusId=" + campus.getId())).isOne();
    }
}