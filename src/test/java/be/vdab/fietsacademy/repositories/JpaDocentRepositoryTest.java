package be.vdab.fietsacademy.repositories;

import be.vdab.fietsacademy.domain.Docent;
import be.vdab.fietsacademy.domain.Geslacht;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql("/insertDocent.sql")
@Import(JpaDocentRepository.class)
public class JpaDocentRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests
{
    private static final String DOCENTEN = "docenten";
    private Docent docent;

    @Autowired
    private JpaDocentRepository repository;

    @Autowired
    private EntityManager manager;

    @Before
    public void before()
    {
        docent = new Docent("test", "test", BigDecimal.TEN, "test@fietsacademy.be", Geslacht.MAN);
    }

    private long idVanTestMan()
    {
        return super.jdbcTemplate.queryForObject("select id from docenten where voornaam = 'testM'", Long.class);
    }
    private long idVanTestVrouw() {
        return super.jdbcTemplate.queryForObject("select id from docenten where voornaam='testV'", Long.class);
    }

    @Test
    public void findById() {
        assertThat(repository.findById(idVanTestMan()).get().getVoornaam()).isEqualTo("testM");
    }

    @Test
    public void findByOnbestaandeId() {
        assertThat(repository.findById(-1)).isNotPresent();
    }

    @Test
    public void man()
    {
        assertThat(repository.findById(idVanTestMan()).get().getGeslacht()).isEqualTo(Geslacht.MAN);
    }

    @Test
    public void vrouw()
    {
        assertThat(repository.findById(idVanTestVrouw()).get().getGeslacht()).isEqualTo(Geslacht.VROUW);
    }
    @Test
    public void create() {
        repository.create(docent);
        assertThat(docent.getId()).isPositive();
        assertThat(super.countRowsInTableWhere(DOCENTEN, "id=" + docent.getId())).isOne();
    }

    @Test
    public void delete()
    {
        long id = idVanTestMan();
        repository.delete(id);
        manager.flush();
        assertThat(super.countRowsInTableWhere(DOCENTEN, "id=" + docent.getId())).isZero();
    }

    @Test
    public void findAll() {
        assertThat(repository.findAll()).hasSize(super.countRowsInTable(DOCENTEN))
                .extracting(docent->docent.getWedde()).isSorted();
    }

    @Test
    public void findByWeddeBetween()
    {
        BigDecimal duizend = BigDecimal.valueOf(1_000);
        BigDecimal tweeduizend=BigDecimal.valueOf(2_000);
        List<Docent> docenten = repository.findByWeddeBetween(duizend, tweeduizend);

        assertThat(docenten).hasSize(super.countRowsInTableWhere(DOCENTEN, "wedde between 1000 and 2000"))
                .allSatisfy(docent -> assertThat(docent.getWedde()).isBetween(duizend, tweeduizend));
    }
}
