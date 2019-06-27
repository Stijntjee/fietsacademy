package be.vdab.fietsacademy.repositories;

import be.vdab.fietsacademy.domain.Docent;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
public class JpaDocentRepository implements DocentRepository
{
    private final EntityManager manager;

    //CONSTRUCTORS
    JpaDocentRepository(EntityManager manager)
    {
        this.manager = manager;
    }

    //METHODS
    @Override
    public Optional<Docent> findById(long id)
    {
        return Optional.ofNullable(manager.find(Docent.class, id));
    }
}