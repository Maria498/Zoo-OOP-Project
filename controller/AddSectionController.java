package controller;

import java.net.URL;
import java.util.Collections;
import java.util.ResourceBundle;

import Model.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

//Controller class of Add Section fxml where new sections can be added to Zoo object data.
//Assumption - All new added object getting Id number by finding the maximum of the existing objects add 1 (max(Zoo.getInstance().....keySet());
public class AddSectionController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private ImageView image;

    @FXML
    private URL location;

    @FXML
    private TextField maxCapField;

    @FXML
    private Button addSectionButton;

    @FXML
    private TextField sectionNameField;

    @FXML
    private Button backButton;

    @FXML
    private Button saveButton;

    @FXML
    void initialize() {
    	backButton.setOnAction(event -> {
    		openScene("/view/MainMenu.fxml", backButton);
    	});
    	saveButton.setOnAction(event -> {
			Zoo.serialize();
			AssistMethods.showAlert("Complete", "Changes saved");
        });
    	addSectionButton.setOnAction(event -> {
    	// validation check of entered data
    		boolean valid = true;
    		String name = sectionNameField.getText().trim();
    		String str = maxCapField.getText().trim();
    		if(!str.equals("") && !name.equals("")) {
    			int maxCap = 0;
        		try {
        			maxCap = Integer.parseInt(maxCapField.getText().trim());
         		} catch (NumberFormatException e) {
         			AssistMethods.shakeThat(maxCapField, sectionNameField, null, null, null, null, null, null, null, null, null, null);
         			valid = false;
         			AssistMethods.showAlert("Incorrect max capacity input", "Please enter a number");
        		} //  check if section already exists
    			for(Section s: Zoo.getInstance().getSections().values()) {
    				if(s.getSectionName().equals(name)) {
    					AssistMethods.shakeThat(maxCapField, sectionNameField, null, null, null, null, null, null, null, null, null, null);
    					valid = false;
    					AssistMethods.showAlert("Failed!", "Section with name: " +s.getSectionName()+" already exists");
    				}
    			}
    			int id = (int) Collections.max(Zoo.getInstance().getSections().keySet());
    			Section s = new Section(name,maxCap);
    			s.setId(id+1);
    			if(valid) {
    				if(Zoo.getInstance().addSection(s))
    					AssistMethods.showAlert("Complete", "Section " +s.getSectionName()+" added successfully!");
    			}
    		}
    		else {
    			AssistMethods.shakeThat(maxCapField, sectionNameField, null, null, null, null, null, null, null, null, null, null);
    			AssistMethods.showAlert("One or more fields are empty or incorrect", "Fill in all required fields correctly");
    		}
    	});

    }
    public void openScene(String window, Button button) {
  		button.getScene().getWindow().hide();
  		FXMLLoader loader = new FXMLLoader();
  		loader.setLocation(getClass().getResource(window));
  		AssistMethods.loader(loader);
  	}
 
}
