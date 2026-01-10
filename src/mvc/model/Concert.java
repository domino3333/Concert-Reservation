package mvc.model;

import mvc.model.genre.ConcertGenre;

import java.util.Objects;

public class Concert {
    protected int accessAge;
    private int concertId;
    private String name;
    private ConcertGenre genre;

    public int getConcertId() {
        return concertId;
    }

    public void setConcertId(int concertId) {
        this.concertId = concertId;
    }

    public Concert() {
        this(null, null, 0);
    }

    public Concert(String name, ConcertGenre genre, int accessAge) {
        this.concertId = -1;
        this.name = name;
        this.genre = genre;
        this.accessAge = accessAge;
    }


    @Override
    public int hashCode() {
        return Objects.hash(genre, name, concertId);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Concert)) {
            return false;
        }
        Concert temp = (Concert) obj;

        return this.name.equals(temp.name) && this.genre.equals(temp.genre) &&
                this.concertId == temp.concertId;

    }

    @Override
    public String toString() {
        return "   제목: " + name + "\n   장르: " + genre;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ConcertGenre getGenre() {
        return genre;
    }

    public void setGenre(ConcertGenre genre) {
        this.genre = genre;
    }

    public int getAccessAge() {
        return accessAge;
    }

    public void setAccessAge(int accessAge) {
        this.accessAge = accessAge;
    }
}
