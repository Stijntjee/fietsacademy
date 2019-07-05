package be.vdab.fietsacademy.queryresults;

public class IdEnEmailAdres
{
    private final long id;
    private final String emailAdres;

    //CONSTRUCTORS
    public IdEnEmailAdres(long id, String emailAdres) {
        this.id = id;
        this.emailAdres = emailAdres;
    }

    //GETTERS
    public long getId() {
        return id;
    }

    public String getEmailAdres() {
        return emailAdres;
    }
}
