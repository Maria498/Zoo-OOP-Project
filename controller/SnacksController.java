package controller;

import java.net.URL;
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

//Controller class of Snacks fxml where all snacks are displayed from Zoo object data in Tableview 
public class SnacksController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<SnackBar> snackBarBox;

    @FXML
    private TableView<Snack> snacksTable;

    @FXML
    private TableColumn<Snack, SnackType> snackType;

    @FXML
    private TableColumn<Snack, Integer> id;

    @FXML
    private TableColumn<Snack, String> name;

    @FXML
    private TableColumn<Snack, Double> price;

    @FXML
    private TableColumn<Snack, SnackBar> snackBar;

    @FXML
    private Button wholeZooButton;

    @FXML
    private Button backButton;

    @FXML
    void initialize() {
    	backButton.setOnAction(event -> {
    		openScene("/view/MainMenu.fxml", backButton);
    	});
    	wholeZooButton.setOnAction(event -> {
        	setValues();
        	snacksTable.setItems(FXCollections.observableArrayList(Zoo.getInstance().getSnacks().values()));
    	});
        snackBarBox.setItems(FXCollections.observableArrayList(Zoo.getInstance().getBars().values()));
    	snackBarBox.setOnAction(event -> {
    		if(snackBarBox.getValue()!=null) {
    			SnackBar sb = snackBarBox.getValue();
    			setValues();
    			snacksTable.setItems(FXCollections.observableArrayList(sb.getSnacks()));
    		}
    	});
    	
    }
    
    public void setValues() {
    	snackType.setCellValueFactory(new PropertyValueFactory<>("type"));
		id.setCellValueFactory(new PropertyValueFactory<>("id"));
		name.setCellValueFactory(new PropertyValueFactory<>("snackName"));
		price.setCellValueFactory(new PropertyValueFactory<>("price"));
		snackBar.setCellValueFactory(new PropertyValueFactory<>("bar"));

    }
    
    public void openScene(String window, Button button) {
  		button.getScene().getWindow().hide();
  		FXMLLoader loader = new FXMLLoader();
  		loader.setLocation(getClass().getResource(window));
  		AssistMethods.loader(loader);
  	}



    
}
