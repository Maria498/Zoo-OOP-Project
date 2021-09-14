package controller;

import java.net.URL;
import java.util.Collections;
import java.util.ResourceBundle;

import Model.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

//Controller class of Add Snack bar fxml where new bars added to Zoo object data.
//Assumption - All new added object getting Id number by finding the maximum of the existing objects add 1 (max(Zoo.getInstance().....keySet());
public class AddSnackbarController {

	
    @FXML
    private ResourceBundle resources;

    @FXML
    private ImageView image;

    @FXML
    private URL location;

    @FXML
    private Button addSnackBarButton;

    @FXML
    private TextField snackBarNameField;

    @FXML
    private ComboBox<Section> sectionBox;

    @FXML
    private Button backButton;

    @FXML
    private Button saveButton;

    @FXML
    void initialize() {
    	sectionBox.setItems(AssistMethods.sections);
    	backButton.setOnAction(event -> {
    		openScene("/view/MainMenu.fxml", backButton);
    	});
    	saveButton.setOnAction(event -> {
			Zoo.serialize();
			AssistMethods.showAlert("Complete", "Changes saved");
        });
    	addSnackBarButton.setOnAction(event -> {
    		String barName = snackBarNameField.getText().trim();
    		// validation check of entered data
    		if(!barName.equals(" ") && sectionBox.getValue()!=null) {
    			Section sec = sectionBox.getValue();
    			int id = (int) Collections.max(Zoo.getInstance().getBars().keySet());
    			SnackBar sb = new SnackBar(barName,sec);
    			sb.setId(id+1);
    			if(Zoo.getInstance().addSnackBar(sb, sec))
    				AssistMethods.showAlert("Complete", "Snack bar " +sb.getBarName()+" added to section "+sec.getSectionName());
    		}
    		else {
    			AssistMethods.shakeThat(snackBarNameField, sectionBox, null, null, null, null, null, null, null, null, null, null);
    			AssistMethods.showAlert("One or more fields are empty", "Fill in all required fields");
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
