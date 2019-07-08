package be.vdab.fietsacademy.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "docenten")
public class Docent implements Serializable
{
    private static final long serialVersionUID = 1L;
    @GeneratedValue(strategy = GenerationType.IDENTITY) //DATABASE VULT DEZE KOLOM IN (id)
    @Id
    private long id;
    private String voornaam;
    @Column(name = "familienaam") // --> ALS DE VARIABELE ANDERS HEET ALS DE KOLOM
    private String familienaam;
    private BigDecimal wedde;
    private String emailAdres;
    @Enumerated(EnumType.STRING)
    private Geslacht geslacht;
    @ElementCollection
    @CollectionTable(name = "docentenbijnamen", joinColumns = @JoinColumn(name = "docentid"))
    @Column(name = "bijnaam")
    private Set<String> bijnamen;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "campusId")
    private Campus campus;
    //@Transient --> variabele zonder kolom

    //CONSTRUCTORS
    protected Docent()  //ENTITYMANAGER HEEFT DEFAULT CONSTRUCTOR NODIG
    {
    }

    public Docent(String voornaam, String familienaam, BigDecimal wedde, String emailAdres, Geslacht geslacht, Campus campus) {
        this.voornaam = voornaam;
        this.familienaam = familienaam;
        this.wedde = wedde;
        this.emailAdres = emailAdres;
        this.geslacht = geslacht;
        this.bijnamen = new LinkedHashSet<>();
        setCampus(campus);
    }

    //METHODS
    public void opslag(BigDecimal percentage)
    {
        if (percentage.compareTo(BigDecimal.ZERO) <= 0)
        {
            throw new IllegalArgumentException();
        }
        BigDecimal factor = BigDecimal.ONE.add(percentage.divide(BigDecimal.valueOf(100)));
        wedde = wedde.multiply(factor, new MathContext(2, RoundingMode.HALF_UP));
    }

    public boolean addBijnaam(String bijnaam) {
        if (bijnaam.trim().isEmpty()) //TRIM VERWIJDERT SPATIES VOOR EN NA
        {
            throw new IllegalArgumentException();
        }
        return  bijnamen.add(bijnaam);
    }

    public boolean removeBijnaam(String bijnaam) {
        return bijnamen.remove(bijnaam);
    }

    @Override
    public boolean equals(Object obj) {
        if ( ! (obj instanceof Docent)) {
            return false;
        }
        if (emailAdres == null) {
            return false;
        }
        return emailAdres.equalsIgnoreCase(((Docent) obj).emailAdres);
    }
    @Override
    public int hashCode() {
        return emailAdres == null ? 0 : emailAdres.toLowerCase().hashCode();
    }

    //GETTERS
    public long getId() {
        return id;
    }

    public String getVoornaam() {
        return voornaam;
    }

    public String getFamilienaam() {
        return familienaam;
    }

    public BigDecimal getWedde() {
        return wedde;
    }

    public String getEmailAdres() {
        return emailAdres;
    }

    public Geslacht getGeslacht() {
        return geslacht;
    }

    public Set<String> getBijnamen() {
        //return een READ ONLY collection
        return Collections.unmodifiableSet(bijnamen);
    }

    public Campus getCampus() {
        return campus;
    }

    //SETTERS
    public void setCampus(Campus campus) {
        if (!campus.getDocenten().contains(this)) {
            campus.add(this);
        }
        this.campus = campus;
    }
}
