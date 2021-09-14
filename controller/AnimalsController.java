package controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
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

//Controller class of Animals fxml where all animals are displayed from Zoo object data in Tableview by chosen categories

public class AnimalsController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Section> sectionsBox;

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
    private ComboBox<String> animalsBox;
    
    @FXML
    private Button wholeZooButton;

    @FXML
    private Button backButton;

    @FXML
    void initialize() {
    	backButton.setOnAction(event -> {
    		openScene("/view/MainMenu.fxml", backButton);
    	});
    	sectionsBox.setItems(AssistMethods.sections);
    	sectionsBox.setOnAction(event -> {
    	aniBySectionTable.getItems().clear();	
			if(sectionsBox.getValue()!=null) 
				animalsBox.setItems(AssistMethods.animalTypeList);	
			});
    	animalsBox.setOnAction(event -> {
    		String choice = animalsBox.getValue();
    		Section s = sectionsBox.getValue();
    		if(s!=null) {
    			if(choice.equals("Mammal")) {
    				aniBySectionTable.getItems().clear();
    	 			ObservableList<Animal> animals = FXCollections.observableArrayList(s.getMammals());
    	 			setValues();
    	 			aniBySectionTable.setItems(animals);
    			}
    			if(choice.equals("Bird")) {
    				aniBySectionTable.getItems().clear();
    				ObservableList<Animal> animals = FXCollections.observableArrayList(s.getBirds());
    				setValues();
    				aniBySectionTable.setItems(animals);
    			}
    			if(choice.equals("Reptile")) {
    				aniBySectionTable.getItems().clear();
        	 		ObservableList<Animal> animals = FXCollections.observableArrayList(s.getReptiles());
        	 	    setValues();
        	 	    aniBySectionTable.setItems(animals);
    			}	
    		}
    	});		//animals in whole Zoo
    	wholeZooButton.setOnAction(event -> {
	 		ArrayList<Animal> anim = new ArrayList<>();
	 		anim.addAll(Zoo.getInstance().getMammals().values());
	 		anim.addAll(Zoo.getInstance().getBirds().values());
	 		anim.addAll(Zoo.getInstance().getReptiles().values());
    	 	setValues();
    	 	aniBySectionTable.setItems(FXCollections.observableArrayList(anim));		
    	});	
    } 
   	
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
