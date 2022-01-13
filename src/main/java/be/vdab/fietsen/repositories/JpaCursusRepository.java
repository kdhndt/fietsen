package be.vdab.fietsen.repositories;

import be.vdab.fietsen.domain.Cursus;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;
import java.util.UUID;

@Repository
class JpaCursusRepository implements CursusRepository {
    private final EntityManager manager;

    JpaCursusRepository(EntityManager manager) {
        this.manager = manager;
    }

    @Override
    public Optional<Cursus> findById(UUID id) {
        return Optional.ofNullable(manager.find(Cursus.class, id));
    }

    //de kolom soort wordt ingevuld met G of I op bsis van de @DiscriminatorValue regel
    @Override
    public void create(Cursus cursus) {
        manager.persist(cursus);
    }
}
