package controller;

import java.net.URL;
import java.util.ResourceBundle;

import Model.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

//Control class of Snack bars fxml that displays Snack bars data by chosen SB from combobox
public class SnackBarsController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Snack> snacksBox;

    @FXML
    private TextField snackBarname;

    @FXML
    private TextField profit;

    @FXML
    private TextField sectionName;

    @FXML
    private TextField maxCap;

    @FXML
    private TextField empl;

    @FXML
    private TextField vis;

    @FXML
    private ComboBox<SnackBar> snackBarsBox;

    @FXML
    private TextArea snacksField;

    @FXML
    private TextField zooPerc;

    @FXML
    private Button backButton;

    @FXML
    void initialize() {
    	backButton.setOnAction(event -> {
    		openScene("/view/MainMenu.fxml", backButton);
    	});
    	snackBarsBox.setItems(FXCollections.observableArrayList(Zoo.getInstance().getBars().values()));
    	snackBarsBox.setOnAction(event -> {
    		snacksField.clear();
    		if(snackBarsBox.getValue()!=null) {
    			SnackBar sb = snackBarsBox.getValue();
    			Section s = sb.getSection();
    			snackBarname.setText(sb.getBarName());
    			profit.setText(String.valueOf(sb.getProfit()));
    			sectionName.setText(s.getSectionName());
    			maxCap.setText(String.valueOf(s.getMaxCapacity()));
    			empl.setText(String.valueOf(s.getEmployees().size()));
    			vis.setText(String.valueOf(s.getVisitors().size()));
    			zooPerc.setText(String.valueOf(SnackBar.getZooPercentage()));
    			snacksBox.setItems(FXCollections.observableArrayList(sb.getSnacks()));
    			
    			snacksBox.setOnAction(event2 -> {
    				if(snacksBox.getValue()!=null) {
    					snacksField.setText(snacksBox.getValue().toString());
    				}
    			});
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
