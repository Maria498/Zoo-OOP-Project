package controller;

import java.net.URL;
import java.util.ResourceBundle;

import Model.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

//Control class of Sections fxml that displays section data by chosen section from combobox
public class SectionsController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    
    @FXML
    private ComboBox<Section> sectionBox;

    @FXML
    private ComboBox<ZooEmployee> employeesBox;

    @FXML
    private ComboBox<Visitor> visitorsBox;

    @FXML
    private ComboBox<Reptile> reptilesBox;

    @FXML
    private ComboBox<Bird> birdsBox;

    @FXML
    private ComboBox<Mammal> mammalsBox;

    @FXML
    private TextField id;

    @FXML
    private TextField name;

    @FXML
    private TextField snBar;

    @FXML
    private TextField maxCap;

    @FXML
    private TextField totRev;

    @FXML
    private TextField empl;

    @FXML
    private TextField vis;

    @FXML
    private TextField mam;

    @FXML
    private TextField bir;

    @FXML
    private TextField rept;

    @FXML
    private Button backButton;

    @FXML
    void initialize() {
    	sectionBox.setItems(AssistMethods.sections);
    	sectionBox.setOnAction(event -> {
    		if(sectionBox.getValue()!=null) {
    			Section s = sectionBox.getValue();
                employeesBox.setItems(FXCollections.observableArrayList(s.getEmployees()));
                visitorsBox.setItems(FXCollections.observableArrayList(s.getVisitors()));
                reptilesBox.setItems(FXCollections.observableArrayList(s.getReptiles()));
                birdsBox.setItems(FXCollections.observableArrayList(s.getBirds()));
                mammalsBox.setItems(FXCollections.observableArrayList(s.getMammals()));
                name.setText(s.getSectionName());
                if(s.getBar()!=null)
               	 snBar.setText(s.getBar().getBarName());
                else
               	 snBar.setText("No snack bar");
                maxCap.setText(String.valueOf(s.getMaxCapacity()));
                totRev.setText(String.valueOf(s.getTodayRevenue()));
                empl.setText(String.valueOf(s.getEmployees().size()));
                vis.setText(String.valueOf(s.getVisitors().size()));
                mam.setText(String.valueOf(s.getMammals().size()));
                bir.setText(String.valueOf(s.getBirds().size()));
                rept.setText(String.valueOf(s.getReptiles().size()));
    		}
    	});
        
         backButton.setOnAction(event -> {
     		openScene("/view/MainMenu.fxml", backButton);
     	}); 
    }
    public void openScene(String window, Button button) {
  		button.getScene().getWindow().hide();
  		FXMLLoader loader = new FXMLLoader();
  		loader.setLocation(getClass().getResource(window));
  		AssistMethods.loader(loader);
  	}
}
