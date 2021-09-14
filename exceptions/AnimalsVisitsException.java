package exceptions;

import controller.AssistMethods;

public class AnimalsVisitsException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public AnimalsVisitsException() {
		super("this Animal need to get her treatment,and need to rest");
		AssistMethods.showAlert(getMessage(), "");

	}

}
