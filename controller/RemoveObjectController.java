package controller;

import java.net.URL;
import java.util.ResourceBundle;

import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;

// Controller class of Remove objects fxml where all objects can be removed from Zoo object data.
// Depending on the object type actual comboboxes displays data
// Employee can remove objects from the same section only
// Admin can remove every object from every section
public class RemoveObjectController{

	ObservableList<Object> objectTypeList = FXCollections.observableArrayList("Mammal",
			"Reptile", "Bird", "Zoo employee", "Visitor", "Section", "Snack", "SnackBar");
	
	ObservableList<Object> objectTypeEmployeeList = FXCollections.observableArrayList(
			"Zoo employee", "Visitor", "Snack");
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button removeObjectButton;

    @FXML
    private ImageView image;

    @FXML
    private ComboBox<Object> objectsBox;

    @FXML
    private ComboBox<Object> objectTypeBox;

    @FXML
    private TextArea objectDataField;

    @FXML
    private Button backButton;

    @FXML
    private Button saveButton;
    
    @FXML
    private ComboBox<Section> newSectionBox;

    @FXML
    void initialize() {
    	newSectionBox.setVisible(false);
    	backButton.setOnAction(event -> {
    		openScene("/view/MainMenu.fxml", backButton);
    	});
    	saveButton.setOnAction(event -> {
			Zoo.serialize();
			AssistMethods.showAlert("Complete", "Changes saved");
        });
		
    	if(!WelcomeController.getUser().equals("admin")) 
    		objectTypeBox.setItems(objectTypeEmployeeList);
    	else
    		objectTypeBox.setItems(objectTypeList);
    	objectTypeBox.setOnAction(event -> {
    		
    		newSectionBox.setVisible(false);
    		Object choice = objectTypeBox.getValue();
    		if(choice.equals("Mammal")) {
         		dataPerform(choice, Zoo.getInstance().getMammals().values().toArray());
    		}
    		if(choice.equals("Bird")) {
    			dataPerform(choice, Zoo.getInstance().getBirds().values().toArray());
    		}
    		if(choice.equals("Reptile")) {
    			dataPerform(choice, Zoo.getInstance().getReptiles().values().toArray());
    		}
    		if(choice.equals("Zoo employee")) {
    			if(!WelcomeController.getUser().equals("admin")) {
        			int id = Integer.parseInt(WelcomeController.getUser());
    				ZooEmployee e = Zoo.getInstance().getRealEmployee(id);
    				Section s = Zoo.getInstance().getSections().get(e.getSection().getId());
    				dataPerform(choice, s.getEmployees().toArray());
    			}
    			else
    				dataPerform(choice, Zoo.getInstance().getEmployees().values().toArray());
    		}
    		if(choice.equals("Visitor")) {
    			dataPerform(choice, Zoo.getInstance().getVisitors().values().toArray());
    		}
    		if(choice.equals("Section")) {
    			dataPerform(choice, Zoo.getInstance().getSections().values().toArray());
    			newSectionBox.setItems(AssistMethods.sections);
    			newSectionBox.setVisible(true);
    		}
    		if(choice.equals("Snack")) {
    			dataPerform(choice, Zoo.getInstance().getSnacks().values().toArray());
    		}
    		if(choice.equals("SnackBar")) {
    			dataPerform(choice, Zoo.getInstance().getBars().values().toArray());
    		}	
    	
    	});
    	
    	removeObjectButton.setOnAction(event -> {
    		if(objectsBox.getValue()!=null) {
    			Object o = objectsBox.getValue();
    			if(o instanceof Mammal) {
    				if(Zoo.getInstance().getMammals().containsKey(((Mammal) o).getId())) {
    					Zoo.getInstance().removeMammal((Mammal)o);
    					AssistMethods.showAlert("Complete", "Mammal removed successfully!");
    				}
    				else {
        				AssistMethods.showAlert("Failed", "This Mammal has already been removed.");
        				AssistMethods.shakeThat(objectDataField, objectTypeBox, newSectionBox, objectsBox, null, null, null, null, null, null, null, null);
    				}
    			}
    			if(o instanceof Bird) {
    				if(Zoo.getInstance().getBirds().containsKey(((Bird) o).getId())) {
    					Zoo.getInstance().removeBird((Bird)o);
    					AssistMethods.showAlert("Complete", "Bird removed successfully!");
    				}
    				else {
        				AssistMethods.showAlert("Failed", "This Bird has already been removed.");
        				AssistMethods.shakeThat(objectDataField, objectTypeBox, newSectionBox, objectsBox, null, null, null, null, null, null, null, null);
    				}
    			}
    			if(o instanceof Reptile) {
    				if(Zoo.getInstance().getReptiles().containsKey(((Reptile) o).getId())) {
    					Zoo.getInstance().removeReptile((Reptile)o);
    					AssistMethods.showAlert("Complete", "Reptile removed successfully!");
    				}
    				else {
        				AssistMethods.showAlert("Failed", "This Reptile has already been removed.");
        				AssistMethods.shakeThat(objectDataField, objectTypeBox, newSectionBox, objectsBox, null, null, null, null, null, null, null, null);
    				}
    			}
    			if(o instanceof ZooEmployee) {
    				if(Zoo.getInstance().getEmployees().containsKey(((ZooEmployee) o).getId())) {
    					Zoo.getInstance().removeEmployee((ZooEmployee)o);
    					AssistMethods.showAlert("Complete", "Employee removed successfully!");
    				}
    				else {
        				AssistMethods.showAlert("Failed", "This Employee has already been removed.");
        				AssistMethods.shakeThat(objectDataField, objectTypeBox, newSectionBox, objectsBox, null, null, null, null, null, null, null, null);
    				}
    			}
    			if(o instanceof Visitor) {
    				if(Zoo.getInstance().getVisitors().containsKey(((Visitor) o).getId())) {
    					Zoo.getInstance().removeVisitor((Visitor)o);
    					AssistMethods.showAlert("Complete", "Visitor removed successfully!");
    				}
    				else {
        				AssistMethods.showAlert("Failed", "This Visitor has already been removed.");	
        				AssistMethods.shakeThat(objectDataField, objectTypeBox, newSectionBox, objectsBox, null, null, null, null, null, null, null, null);
    				}
    			}
    			if(o instanceof Section) {
    				if(Zoo.getInstance().getSections().containsKey(((Section) o).getId())) {
    					Section s = newSectionBox.getValue();
    					if(s!=null) {
    						Zoo.getInstance().removeSection((Section)o, s);
    						AssistMethods.showAlert("Complete", "Section removed successfully!");
    					}
    					else {
    						AssistMethods.shakeThat(objectDataField, objectTypeBox, newSectionBox, objectsBox, null, null, null, null, null, null, null, null);
            				AssistMethods.showAlert("Choose a new section", "Then press a remove button");
    					}
	    			}
    				else {
        				AssistMethods.showAlert("Failed", "This Section has already been removed.");
        				AssistMethods.shakeThat(objectDataField, objectTypeBox, newSectionBox, objectsBox, null, null, null, null, null, null, null, null);
    				}
	    		}
    			if(o instanceof Snack) {
    				if(Zoo.getInstance().getSnacks().containsKey(((Snack) o).getId())) {
    					Zoo.getInstance().removeSnack((Snack)o);
    					AssistMethods.showAlert("Complete", "Snack removed successfully!");
    				}
    				else {
        				AssistMethods.showAlert("Failed", "This Snack has already been removed.");
        				AssistMethods.shakeThat(objectDataField, objectTypeBox, newSectionBox, objectsBox, null, null, null, null, null, null, null, null);
    				}
    			}
    			if(o instanceof SnackBar) {
    				if(Zoo.getInstance().getBars().containsKey(((SnackBar) o).getId())) {
    					Zoo.getInstance().removeSnackBar((SnackBar)o);
    					AssistMethods.showAlert("Complete", "Snack bar removed successfully!");
    				}
    				else {
    					AssistMethods.shakeThat(objectDataField, objectTypeBox, newSectionBox, objectsBox, null, null, null, null, null, null, null, null);
        				AssistMethods.showAlert("Failed", "This Snack bar has already been removed.");
    				}
    			}
    		}
    		else {
    			AssistMethods.shakeThat(objectDataField, objectTypeBox, newSectionBox, objectsBox, null, null, null, null, null, null, null, null);
    			AssistMethods.showAlert("Choose an object", "Then press a remove button");
    		}
    	});
    }
    
    public void dataPerform(Object object, Object[] objects) {
    		objectDataField.clear();
    		objectsBox.getItems().clear();
     		objectsBox.setItems( FXCollections.observableArrayList(objects));
     		objectsBox.setOnAction(event2 -> {
     			Object obj = objectsBox.getSelectionModel().getSelectedItem();
     			if(!objectsBox.getSelectionModel().isEmpty())
     				objectDataField.setText(obj.toString()); 
     		});
    }
    
    public void openScene(String window, Button button) {
  		button.getScene().getWindow().hide();
  		FXMLLoader loader = new FXMLLoader();
  		loader.setLocation(getClass().getResource(window));
  		AssistMethods.loader(loader);
  	}
    
}
