package controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import Model.*;
import Utils.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

//Controller class of Employees fxml where all employees are displayed from Zoo object data in Tableview 
public class EmployeesController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<ZooEmployee> employeesTable;

    @FXML
    private TableColumn<ZooEmployee, String> firstName;

    @FXML
    private TableColumn<ZooEmployee, String> lastName;

    @FXML
    private TableColumn<ZooEmployee, Integer> id;

    @FXML
    private TableColumn<ZooEmployee, LocalDate> birth;

    @FXML
    private TableColumn<ZooEmployee, Gender> gender;

    @FXML
    private TableColumn<ZooEmployee, Job> job;

    @FXML
    private TableColumn<ZooEmployee, Section> section;
    
    @FXML
    private Button wholeZooButton;

    @FXML
    private ComboBox<Section> sectionsBox;


    @FXML
    private Button backButton;

    @FXML
    void initialize() {
    	backButton.setOnAction(event -> {
    		openScene("/view/MainMenu.fxml", backButton);
    	});
    	wholeZooButton.setOnAction(event -> {
        	setValues();
        	employeesTable.setItems(FXCollections.observableArrayList(Zoo.getInstance().getEmployees().values()));
    	});
    	sectionsBox.setItems(AssistMethods.sections);
    	sectionsBox.setOnAction(event -> {
    		employeesTable.getItems().clear();	
			if(sectionsBox.getValue()!=null) {
				Section s = sectionsBox.getValue();
				setValues();
				employeesTable.setItems(FXCollections.observableArrayList(s.getEmployees()));	
			}
		});
    }
    
    public void setValues() {
		id.setCellValueFactory(new PropertyValueFactory<>("id"));
		firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
		birth.setCellValueFactory(new PropertyValueFactory<>("birthDay"));
		lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
		gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
		job.setCellValueFactory(new PropertyValueFactory<>("job"));
		section.setCellValueFactory(new PropertyValueFactory<>("section"));
    }
    
    public void openScene(String window, Button button) {
  		button.getScene().getWindow().hide();
  		FXMLLoader loader = new FXMLLoader();
  		loader.setLocation(getClass().getResource(window));
  		AssistMethods.loader(loader);
  	}
    
}
