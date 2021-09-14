package exceptions;

import controller.AssistMethods;

public class OverflowCapacityException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public OverflowCapacityException() {
		super("Maximum section capacity has been reached.");
		AssistMethods.showAlert(getMessage(), "Choose another section");
	}



}
