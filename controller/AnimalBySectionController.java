package controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import Model.*;
import Utils.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

// Controller class of query Animals by section and Animal by worker fxml where 
// all objects displays from Zoo object data in Tableview.

public class AnimalBySectionController {

	ObservableList<Object> objectTypeList = FXCollections.observableArrayList("Zoo employee", "Section", "Visitor");
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Object> objectsBox;

    @FXML
    private ComboBox<Object> objectTypeBox;
    
    @FXML
    private TableView<Animal> aniBySectionTable;

    @FXML
    private TableColumn<Animal, Integer> visCount;
    
    @FXML
    private TableColumn<Animal, Integer> id;

    @FXML
    private TableColumn<Animal, String> name;

    @FXML
    private TableColumn<Animal, LocalDate> birth;

    @FXML
    private TableColumn<Animal, AnimalFood> food;

    @FXML
    private TableColumn<Animal, Gender> gender;

    @FXML
    private TableColumn<Animal, String> section;


    @FXML
    private Button backButton;

    @FXML
    void initialize() {

    	backButton.setOnAction(event -> {
    		openScene("/view/MainMenu.fxml", backButton);
    	});
    	objectTypeBox.setItems(objectTypeList);
    	objectTypeBox.setOnAction(event -> {
    		
    		Object choice = objectTypeBox.getValue();
    		if(choice.equals("Section")) {
         		dataPerform(choice, Zoo.getInstance().getSections().values().toArray());
    		}
    		if(choice.equals("Zoo employee")) {
    			dataPerform(choice, Zoo.getInstance().getAnimalTreatedByZooEmployee().keySet().toArray());
    		}
    		if(choice.equals("Visitor")) {
    			dataPerform(choice, Zoo.getInstance().getAnimalVisitsByPeople().keySet().toArray());
    		}
    	});
    
    }
    // performing data and cleans the fields between events
    public void dataPerform(Object object, Object[] objects) {
		aniBySectionTable.getItems().clear();
		objectsBox.getItems().clear();
 		objectsBox.setItems( FXCollections.observableArrayList(objects));
 		objectsBox.setOnAction(event2 -> {
 			Object obj = objectsBox.getSelectionModel().getSelectedItem();
 			if(!objectsBox.getSelectionModel().isEmpty()) {
 				if(obj instanceof ZooEmployee) {
 					ZooEmployee e = (ZooEmployee)obj;
 					ObservableList<Animal> aniWork = FXCollections.observableArrayList(Zoo.getInstance().allAnimalsByWorker(e));
 	    			setValues();
 	    			aniBySectionTable.setItems(aniWork);
 				}
 				if(obj instanceof Section) {
 					ObservableList<Animal> aniSec = FXCollections.observableArrayList(Zoo.getInstance().getAllAnimalsBySectionMaxVisits((Section)obj));
 	    			setValues();
 	    			aniBySectionTable.setItems(aniSec);
 				}
 				if(obj instanceof Visitor) {
 					ObservableList<Animal> aniVis = FXCollections.observableArrayList(Zoo.getInstance().getAnimalVisitsByPeople().get((Visitor)obj));
 	    			setValues();
 	    			aniBySectionTable.setItems(aniVis);
 				}
 			}
 			});
    }
 	// set values in tableview	
 	public void setValues() {
    	visCount.setCellValueFactory(new PropertyValueFactory<>("visitCounter"));
		id.setCellValueFactory(new PropertyValueFactory<>("id"));
		name.setCellValueFactory(new PropertyValueFactory<>("name"));
		birth.setCellValueFactory(new PropertyValueFactory<>("birthDay"));
		food.setCellValueFactory(new PropertyValueFactory<>("food"));
		gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
		section.setCellValueFactory(new PropertyValueFactory<>("section"));
    }
    
    public void openScene(String window, Button button) {
  		button.getScene().getWindow().hide();
  		FXMLLoader loader = new FXMLLoader();
  		loader.setLocation(getClass().getResource(window));
  		AssistMethods.loader(loader);
  	}
    
}
