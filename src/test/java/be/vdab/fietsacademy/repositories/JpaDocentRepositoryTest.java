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
}
