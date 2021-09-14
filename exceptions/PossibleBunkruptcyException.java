package exceptions;

import controller.AssistMethods;

public class PossibleBunkruptcyException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public PossibleBunkruptcyException() {
		super("greater that 50% percentage may cause Snack bar bunkruptcy ");
		AssistMethods.showAlert("Attention!",getMessage());

	}

}
