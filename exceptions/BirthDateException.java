package exceptions;

import controller.AssistMethods;

public class BirthDateException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BirthDateException() {
		super("Invalid date of birth, try again");
		AssistMethods.showAlert(getMessage(), "Enter a valid date");

	}
}
