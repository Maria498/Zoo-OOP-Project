package Model;

import java.io.Serializable;
import java.time.LocalDate;

import Utils.AnimalFood;
import Utils.Gender;

public class Reptile extends Animal implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static int idCounter = 1;
	
	private boolean isDangerous;
	private boolean seasonSleep;

	
	public Reptile(String name, LocalDate date, AnimalFood food, Gender gender, Section section
			, boolean isDangerous,boolean seasonSleep) {
		super(idCounter++, name, date, food, gender, section);
		this.isDangerous = isDangerous;
		this.seasonSleep= seasonSleep;
	}
	
	public Reptile(int id) {
		super(id);
	}

	public boolean isDangerous() {
		return isDangerous;
	}

	public void setDangerous(boolean isDangerous) {
		this.isDangerous = isDangerous;
	}
	
	@Override
	public String toString() {
		return  super.toString()+ " name: "+getName()+", dangerous: "+isDangerous+", sleep at the sesson: "+this.isSeasonSleep();
	}

	public boolean isSeasonSleep() {
		return seasonSleep;
	}

	public void setSeasonSleep(boolean seasonSleep) {
		this.seasonSleep = seasonSleep;
	}
}
