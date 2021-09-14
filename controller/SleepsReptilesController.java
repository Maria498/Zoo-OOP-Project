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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

//Controller class of SleepsReptiles fxml that represents all reptiles from reptilesSleepAtSeasson() method 
public class SleepsReptilesController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Reptile> reptileTable;

    @FXML
    private TableColumn<Reptile, Integer> visCount;
    
    @FXML
    private TableColumn<Reptile, Integer> id;

    @FXML
    private TableColumn<Reptile, String> name;

    @FXML
    private TableColumn<Reptile, LocalDate> birth;

    @FXML
    private TableColumn<Reptile, AnimalFood> food;

    @FXML
    private TableColumn<Reptile, Gender> gender;

    @FXML
    private TableColumn<Reptile, String> section;
    
    @FXML
    private Button backButton;

    @FXML
    void initialize() {

    	backButton.setOnAction(event -> {
    		openScene("/view/MainMenu.fxml", backButton);
    	});
    	ObservableList<Reptile> rept = FXCollections.observableArrayList(Zoo.getInstance().reptilesSleepAtSeasson());
    	visCount.setCellValueFactory(new PropertyValueFactory<>("visitCounter"));
		id.setCellValueFactory(new PropertyValueFactory<>("id"));
		name.setCellValueFactory(new PropertyValueFactory<>("name"));
		birth.setCellValueFactory(new PropertyValueFactory<>("birthDay"));
		food.setCellValueFactory(new PropertyValueFactory<>("food"));
		gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
		section.setCellValueFactory(new PropertyValueFactory<>("section"));
	
    	reptileTable.setItems(rept);

    }
    public void openScene(String window, Button button) {
  		button.getScene().getWindow().hide();
  		FXMLLoader loader = new FXMLLoader();
  		loader.setLocation(getClass().getResource(window));
  		AssistMethods.loader(loader);
  	}
    
}
