package be.vdab.fietsacademy.domain;



import javax.persistence.*;
import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "campussen")
public class Campus  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String naam;
    @Embedded
    private Adres adres;
    @ElementCollection
    @CollectionTable(name = "campussentelefoonnrs", joinColumns = @JoinColumn(name = "campusId"))
    @OrderBy("fax")
    private Set<TelefoonNr> telefoonNrs;
    @OneToMany
    @JoinColumn(name = "campusid")
    @OrderBy("voornaam, familienaam")
    private Set<Docent> docenten;

    //CONSTRUCTORS
    public Campus(String naam, Adres adres) {
        this.naam = naam;
        this.adres = adres;
        this.docenten = new LinkedHashSet<>();
    }

    protected Campus() {
    }

    //METHODS
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Campus)) return false;
        Campus campus = (Campus) o;
        return id == campus.id &&
                Objects.equals(naam.toUpperCase(), campus.naam.toUpperCase()) &&
                Objects.equals(adres, campus.adres) &&
                Objects.equals(telefoonNrs, campus.telefoonNrs) &&
                Objects.equals(docenten, campus.docenten);
    }

    @Override
    public int hashCode() {
        return Objects.hash(naam.toUpperCase());
    }

    public boolean add(Docent docent)
    {
        if (docent == null) {
            throw new NullPointerException();
        }
        return docenten.add(docent);
    }

    //GETTERS
    public long getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }

    public Adres getAdres() {
        return adres;
    }

    public Set<TelefoonNr> getTelefoonNrs() {
        return Collections.unmodifiableSet(telefoonNrs);
    }

    //SETTERS
    public Set<Docent> getDocenten()
    {
        return Collections.unmodifiableSet(docenten);
    }
}
