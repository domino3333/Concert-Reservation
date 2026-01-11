package mvc.model;

import mvc.model.genre.ConcertGenre;

public class DanceConcert extends Concert {


	public DanceConcert() {
		this(null,null,0);
	}

	public DanceConcert(String name, ConcertGenre genre,int accessAge) {
		super(name, genre,accessAge);
	}

	public int getAccessAge() {
		return accessAge;
	}

	public void setAccessAge(int accessAge) {
		this.accessAge = accessAge;
	}

	@Override
	public String toString() {
		return super.toString() + "\n   연령 제한: " + accessAge+"\n"+" --------------------------";
	}
}
