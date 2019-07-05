package be.vdab.fietsacademy.repositories;

import be.vdab.fietsacademy.domain.Docent;
import be.vdab.fietsacademy.queryresults.IdEnEmailAdres;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;
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
        return Optional.ofNullable(manager.find(Docent.class, id)); //UIT DATABASE HALEN
    }

    @Override
    public void create(Docent docent) {
        manager.persist(docent);
    }

    @Override
    public void delete(long id) {
        findById(id).ifPresent(docent -> manager.remove(docent));
    }

    @Override
    public List<Docent> findAll()
    {
        return manager.createQuery("SELECT d FROM Docent d order by d.wedde", Docent.class)
                .getResultList();
    }

    @Override
    public List<Docent> findByWeddeBetween(BigDecimal van, BigDecimal tot){
       return manager.createQuery("SELECT d FROM Docent d where d.wedde between :van and :tot", Docent.class)
               .setParameter("van", van)
               .setParameter("tot", tot)
               .getResultList();
    }

    @Override
    public List<String> findEmailAdressen() {
        return manager.createQuery("SELECT d.emailAdres FROM Docent d", String.class).getResultList();
    }

    @Override
    public List<IdEnEmailAdres> findIdsEnEmailAdressen()
    {
        throw new UnsupportedOperationException();
    }
}
