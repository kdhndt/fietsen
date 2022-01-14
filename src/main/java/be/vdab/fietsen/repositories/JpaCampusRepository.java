package be.vdab.fietsen.repositories;

import be.vdab.fietsen.domain.Campus;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
class JpaCampusRepository implements CampusRepository {
    private final EntityManager manager;

    JpaCampusRepository(EntityManager manager) {
        this.manager = manager;
    }

    @Override
    public void create(Campus campus) {
        manager.persist(campus);
    }

    @Override
    public Optional<Campus> findById(long id) {
        return Optional.ofNullable(manager.find(Campus.class, id));
    }
}
