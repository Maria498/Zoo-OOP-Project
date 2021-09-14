package exceptions;

import controller.AssistMethods;

public class DiscountCheckException  extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public DiscountCheckException() {

		super("Cant be more then 25% discount your reach the max amount");
		AssistMethods.showAlert(getMessage(), "Exception: Hever discount (30%)");
	}
}
