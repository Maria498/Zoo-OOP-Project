package Model;
import java.io.Serializable;
import java.time.LocalDate;

import Utils.AnimalFood;
import Utils.Gender;
import exceptions.BirthDateException;

public abstract class Animal implements Comparable<Animal>, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private LocalDate birthDay;
	private AnimalFood food;
	private int visitCounter;
	private Gender gender;
	private Section section;
	public String s = this.toString();
	
	public Animal(int id,String name, LocalDate date, AnimalFood food, Gender gender, Section section) {
		super();
		this.id = id;
		this.name = name;
		this.birthDay = date;
		this.food = food;
		this.visitCounter = 0;
		this.gender = gender;
		this.section = section;
	}
	
	public Animal(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(LocalDate birthDay) throws BirthDateException {
		if(birthDay.isBefore(LocalDate.of(2020, 10, 10)))
			this.birthDay = birthDay;
		else
			throw new BirthDateException();
	}

	public AnimalFood getFood() {
		return food;
	}

	public void setFood(AnimalFood food) {
		this.food = food;
	}

	public int getVisitCounter() {
		return visitCounter;
	}

	public void setVisitCounter(int visitCounter) {
		this.visitCounter = visitCounter;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Section getSection() {
		return section;
	}

	public void setSection(Section section) {
		this.section = section;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Animal other = (Animal) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName()+ " id: " + id + ", name: " + name + ", birthDay: " + birthDay + ", food: " + food + ", visitCounter: "
				+ visitCounter + ",\n gender: " + gender + "";
	}

	@Override
	public int compareTo( Animal o2) {
		return this.getBirthDay().compareTo(o2.getBirthDay());
	}
	
	
	
	
}
