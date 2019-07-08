package be.vdab.fietsacademy.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name = "cursussen")
public abstract class Cursus implements Serializable {
    @Id
    private String id;
    private String naam;

    //CONSTRUCTORS
    public Cursus(String naam) {
        id = UUID.randomUUID().toString();
        this.naam = naam;
    }

    protected Cursus() {
    }

    //GETTERS
    public String getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }
}
