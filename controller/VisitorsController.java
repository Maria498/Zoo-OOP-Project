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

//Controller class of Visitors fxml where all visitors are displayed from Zoo object data in Tableview
public class VisitorsController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Visitor> visitorsTable;

    @FXML
    private TableColumn<Visitor, String> firstName;

    @FXML
    private TableColumn<Visitor, String> lastName;

    @FXML
    private TableColumn<Visitor, Integer> id;

    @FXML
    private TableColumn<Visitor, LocalDate> birth;

    @FXML
    private TableColumn<Visitor, Gender> gender;

    @FXML
    private TableColumn<Visitor, TicketType> ticketType;

    @FXML
    private TableColumn<Visitor, Discount> discount;

    @FXML
    private TableColumn<Visitor, Section> section;
    
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
        	visitorsTable.setItems(FXCollections.observableArrayList(Zoo.getInstance().getVisitors().values()));
    	});
    	sectionsBox.setItems(AssistMethods.sections);
    	sectionsBox.setOnAction(event -> {
    	visitorsTable.getItems().clear();	
			if(sectionsBox.getValue()!=null) {
				Section s = sectionsBox.getValue();
				setValues();
				visitorsTable.setItems(FXCollections.observableArrayList(s.getVisitors()));	
			}
		});


    }
    
    public void setValues() {
    	discount.setCellValueFactory(new PropertyValueFactory<>("discount"));
		id.setCellValueFactory(new PropertyValueFactory<>("id"));
		firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
		birth.setCellValueFactory(new PropertyValueFactory<>("birthDay"));
		lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
		gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
		ticketType.setCellValueFactory(new PropertyValueFactory<>("ticket"));
		section.setCellValueFactory(new PropertyValueFactory<>("section"));
    }
    
    public void openScene(String window, Button button) {
  		button.getScene().getWindow().hide();
  		FXMLLoader loader = new FXMLLoader();
  		loader.setLocation(getClass().getResource(window));
  		AssistMethods.loader(loader);
  	}
    
}
