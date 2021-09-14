package Model;

import exceptions.AnimalsVisitsException;

public interface AnimalsVisits {
	
	public void visitcount(Person p) throws AnimalsVisitsException ;
	public boolean hasVistedAnimal(Person p);

	
	

}
