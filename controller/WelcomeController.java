package controller;

import java.net.URL;
import java.util.ResourceBundle;

import Model.Zoo;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;


// Controller class of Login screen by username and password
public class WelcomeController {


	private static String USER;
	
    public static String getUser() {
		return USER;
	}
    
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

	@FXML
    private TextField loginField;

    @FXML
    private ImageView image;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button authSignInButton;

    @FXML
    void initialize() {
    	// Sign in button
    	authSignInButton.setOnAction(event -> {
         	String loginText = loginField.getText().trim();
         	String loginPassword = passwordField.getText().trim();
         	if(!loginText.equals("") && !loginPassword.equals("")) 
         		loginUser(loginText, loginPassword); 
         	else { // Animation - shaking while incorrect data is entered
         		AssistMethods.shakeThat(loginField, passwordField, null, null, null, null, null, null, null, null, null, null);
         	}
         		
         	});
     }
    // Admin user can perform all the options in the system including all the queries and view all the data. The user
    // identified by a username and password that are "admin"
    // A Zoo employee is identified by a username (last name)  and password (unique ID number)
 	public void loginUser(String lastName, String password) throws NumberFormatException {
 		boolean valid = true;
 		if (lastName.equals("admin") && password.equals("admin")) {
 			USER = password;
 			openScene("/view/MainMenu.fxml", authSignInButton);
 		}
 		else {
 			int id = 0;
 			try {
 				id = Integer.parseInt(password);
 				
 			} catch (NumberFormatException e) {
 				AssistMethods.showAlert("Incorrect password input", "Try again");
 				valid = false;
 			}
 			if(Zoo.getInstance().getEmployees().containsKey(id)
 					&& (Zoo.getInstance().getRealEmployee(id).getLastName().equals(lastName))) {
 				USER = password;
 				openScene("/view/MainMenu.fxml", authSignInButton);
 			}
 			else {
 				if(valid)
 					AssistMethods.showAlert("There is no account associated with the credentials you've entered.", "Try again");
 			}
 		}
 	}
 	// Opens next scene
 	 public void openScene(String window, Button button) {
  		button.getScene().getWindow().hide();
  		FXMLLoader loader = new FXMLLoader();
  		loader.setLocation(getClass().getResource(window));
  		AssistMethods.loader(loader);
  	}
		
}
