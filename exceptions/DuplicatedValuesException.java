package exceptions;

import controller.AssistMethods;

public class DuplicatedValuesException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public DuplicatedValuesException() {
		super("Cannot be an dupliacted value: cannot be employee and visitor the same person");
		AssistMethods.showAlert(getMessage(), "");
	}
	

}
