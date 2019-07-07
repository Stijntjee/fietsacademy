package be.vdab.fietsacademy.queryresults;

import java.math.BigDecimal;

public class AantalDocentenPerWedde
{
    private final BigDecimal wedde;
    private final long aantal;

    //CONSTRUCTORS
    public AantalDocentenPerWedde(BigDecimal wedde, long aantal) {
        this.wedde = wedde;
        this.aantal = aantal;
    }


    //GETTERS
    public BigDecimal getWedde() {
        return wedde;
    }

    public long getAantal() {
        return aantal;
    }
}
