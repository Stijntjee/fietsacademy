package be.vdab.fietsacademy.repositories;

import be.vdab.fietsacademy.domain.Cursus;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.swing.text.html.parser.Entity;
import java.util.Optional;

@Repository
public class JpaCursusRepository implements CursusRepository {
    private final EntityManager manager;

    //CONSTRUCTORS
    public JpaCursusRepository(EntityManager manager) {
        this.manager = manager;
    }

    //METHODS
    @Override
    public Optional<Cursus> findById(String id) {
        return  Optional.ofNullable(manager.find(Cursus.class, id));
    }


    @Override
    public void create(Cursus cursus) {
        manager.persist(cursus);
    }
}
