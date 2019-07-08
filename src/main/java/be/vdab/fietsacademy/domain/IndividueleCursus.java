package be.vdab.fietsacademy.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "individuelecursussen")
public class IndividueleCursus extends Cursus{
    private static final long serialVersionUID = 1;
    private int duurtijd;

    //CONSTRUCTORS
    public IndividueleCursus(String naam, int duurtijd) {
        super(naam);
        this.duurtijd = duurtijd;
    }

    protected IndividueleCursus() {
    }

    //GETTERS
    public int getDuurtijd() {
        return duurtijd;
    }
}
