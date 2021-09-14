package controller;

import java.net.URL;
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


//Controller class of query SnacksBySnackBar fxml where 
//all snacks are displayed from Zoo object data in Tableview.
public class SnacksBySnackBarController {

	ObservableList<SnackBar> bars = FXCollections.observableArrayList(Zoo.getInstance().getBars().values());
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<SnackBar> snackBarBox;

    @FXML
    private TableView<Snack> snacksByworkerTable;

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
    private Button backButton;

    @FXML
    void initialize() {

    	backButton.setOnAction(event -> {
    		openScene("/view/MainMenu.fxml", backButton);
    	});
    	snackBarBox.setItems(bars);
    	snackBarBox.setOnAction(event -> {
    		if(!snackBarBox.getSelectionModel().isEmpty()) {
    			SnackBar sb = snackBarBox.getValue();
    			ObservableList<Snack> snacksByBar = FXCollections.observableArrayList(Zoo.getInstance().findAllSnackByWorker(sb));
    			snackType.setCellValueFactory(new PropertyValueFactory<>("type"));
    			id.setCellValueFactory(new PropertyValueFactory<>("id"));
    			name.setCellValueFactory(new PropertyValueFactory<>("snackName"));
    			price.setCellValueFactory(new PropertyValueFactory<>("price"));
    			snackBar.setCellValueFactory(new PropertyValueFactory<>("bar"));
    			snacksByworkerTable.setItems(snacksByBar);
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
