package controller;

import java.net.URL;
import java.util.ResourceBundle;

import Model.*;
import exceptions.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

//Controller class of MoveVisitor fxml with reference to moveVisitortoSection() method 

public class MoveVisitorController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button moveButton;

    @FXML
    private ImageView image;

    @FXML
    private ComboBox<Visitor> visitorsBox;

    @FXML
    private ComboBox<Section> newSectionBox;

    @FXML
    private TextField oldSectionField;

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
    	if(!WelcomeController.getUser().equals("admin")) {
			int id = Integer.parseInt(WelcomeController.getUser());
			ZooEmployee e = Zoo.getInstance().getRealEmployee(id);
			Section s = Zoo.getInstance().getSections().get(e.getSection().getId());
			visitorsBox.setItems(FXCollections.observableArrayList(s.getVisitors()));
    	}
    	else
    		visitorsBox.setItems(FXCollections.observableArrayList(Zoo.getInstance().getVisitors().values()));
    	 visitorsBox.setOnAction(event -> {
       	 newSectionBox.getItems().clear();
         oldSectionField.clear();
    	 Visitor v = visitorsBox.getValue();
       	 if(!visitorsBox.getSelectionModel().isEmpty()) {
       		 oldSectionField.setText(v.getSection().toString());
       		 oldSectionField.setDisable(true);
       		 newSectionBox.setItems(FXCollections.observableArrayList(Zoo.getInstance().getSections().values()));
       	  }
       	  else {
       		AssistMethods.shakeThat(newSectionBox, oldSectionField, visitorsBox, null, null, null, null, null, null, null, null, null);
       		  AssistMethods.showAlert("Choose a visitor", "Then choose a section");
       	  }
         });
         moveButton.setOnAction(event -> {
         boolean valid = true;
         boolean alreadyMoved = false;
       	  Visitor v = visitorsBox.getSelectionModel().getSelectedItem();
       	  Section s = newSectionBox.getSelectionModel().getSelectedItem();
       	  if(v!=null && s!=null) {
    		try {
    			if(!s.getVisitors().contains(v)) {
    				v.moveVisitorToSection(s);
    			}
    			else{
    				AssistMethods.shakeThat(newSectionBox, oldSectionField, visitorsBox, null, null, null, null, null, null, null, null, null);
        			alreadyMoved = true;
        			valid = false;
        			AssistMethods.showAlert("Failed!", "Visitor "+ v.getFirstName()+ " " +v.getLastName()+" already moved to "+s.getSectionName());
        		}
			} catch (MaximumCapcityException e) {
				valid = false;
				AssistMethods.shakeThat(newSectionBox, oldSectionField, visitorsBox, null, null, null, null, null, null, null, null, null);
				e.getMessage();
			}
    		if(v.getSection().equals(s) && valid)
    			AssistMethods.showAlert("Complete", "Visitor "+ v.getFirstName()+ " " +v.getLastName()+" moved to "+s.getSectionName());
    		else if(!alreadyMoved && !valid) {
    			AssistMethods.shakeThat(newSectionBox, oldSectionField, visitorsBox, null, null, null, null, null, null, null, null, null);
    			AssistMethods.showAlert("Action is not completed", "Visitor "+ v.getFirstName()+ " " +v.getLastName()+" couldn't move to "+s.getSectionName());
    		}
       	  }
       	  else 	{
    		  AssistMethods.shakeThat(newSectionBox, oldSectionField, visitorsBox, null, null, null, null, null, null, null, null, null);
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
