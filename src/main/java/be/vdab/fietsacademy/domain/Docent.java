package be.vdab.fietsacademy.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

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
    //@Transient --> variabele zonder kolom

    //CONSTRUCTORS
    protected Docent()  //ENTITYMANAGER HEEFT DEFAULT CONSTRUCTOR NODIG
    {
    }

    public Docent(String voornaam, String familienaam, BigDecimal wedde, String emailAdres, Geslacht geslacht) {
        this.voornaam = voornaam;
        this.familienaam = familienaam;
        this.wedde = wedde;
        this.emailAdres = emailAdres;
        this.geslacht = geslacht;
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
}
