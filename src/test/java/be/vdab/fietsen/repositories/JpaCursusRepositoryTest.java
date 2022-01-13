package be.vdab.fietsen.repositories;

import be.vdab.fietsen.domain.GroepsCursus;
import be.vdab.fietsen.domain.IndividueleCursus;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(showSql = false)
@Import(JpaCursusRepository.class)
@Sql("/insertCursus.sql")
class JpaCursusRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {
    private final JpaCursusRepository repository;
//    private static final String CURSUSSEN = "cursussen";
    private static final LocalDate EEN_DATUM = LocalDate.of(2019, 1, 1);
    private static final String GROEPS_CURSUSSEN = "groepscursussen";
    private static final String INDIVIDUELE_CURSUSSEN = "individuelecursussen";
    private final EntityManager manager;


    public JpaCursusRepositoryTest(JpaCursusRepository repository, EntityManager manager) {
        this.repository = repository;
        this.manager = manager;
    }

    private UUID idVanTestGroepCursus() {
        return jdbcTemplate.queryForObject("select id from groepscursussen where naam = 'testGroep'", UUID.class);
    }

    private UUID idVanTestIndividueelCursus() {
        return jdbcTemplate.queryForObject("select id from individuelecursussen where naam = 'testIndividueel'", UUID.class);
    }

    @Test
    void findGroepCursusById() {
        assertThat(repository.findById(idVanTestGroepCursus()))
                .containsInstanceOf(GroepsCursus.class)
                .hasValueSatisfying(cursus -> assertThat(cursus.getNaam()).isEqualTo("testGroep"));
    }

    @Test
    void findIndividueleCursusById() {
        assertThat(repository.findById(idVanTestIndividueelCursus()))
                .containsInstanceOf(IndividueleCursus.class)
                .hasValueSatisfying(cursus -> assertThat(cursus.getNaam()).isEqualTo("testIndividueel"));
    }

    @Test
    void findByOnbestaandeId() {
        assertThat(repository.findById(UUID.randomUUID())).isNotPresent();
    }

    @Test
    void createGroepsCursus() {
        //er wordt automatisch een id gegenereerd bij het aanmaken van object
        var cursus = new GroepsCursus("testGroep2", EEN_DATUM, EEN_DATUM);
        repository.create(cursus);
        manager.flush();
//        assertThat(countRowsInTableWhere(CURSUSSEN, "id =" + cursus.getId())).isOne();
        assertThat(countRowsInTableWhere(GROEPS_CURSUSSEN, "id = uuid_to_bin('" + cursus.getId() + "')")).isOne();
    }

    @Test
    void createIndividueleCursus() {
        //er wordt automatisch een id gegenereerd bij het aanmaken van object
        var cursus = new IndividueleCursus("testIndividueel2", 7);
        repository.create(cursus);
        manager.flush();
//        assertThat(countRowsInTableWhere(CURSUSSEN, "id =" + cursus.getId())).isOne();
        assertThat(countRowsInTableWhere(INDIVIDUELE_CURSUSSEN, "id = uuid_to_bin('" + cursus.getId() + "')")).isOne();
    }
}