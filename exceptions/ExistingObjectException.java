package exceptions;

import controller.AssistMethods;

public class ExistingObjectException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ExistingObjectException() {
		super("Cannot add an object that already exists");
		AssistMethods.showAlert(getMessage(), "");

	}

}
