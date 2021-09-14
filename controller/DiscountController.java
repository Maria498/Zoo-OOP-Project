package controller;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;

import Model.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

//Controller class of Discount fxml displays a treeMap from Zoo object query geAllDiscountAmount() in Tableview
public class DiscountController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<String> discountTable;

    @FXML
    private TableColumn<String,String> keyColumn;

    @FXML
    private TableColumn<String,String> valueColumn;

    @FXML
    private Button backButton;

    @SuppressWarnings("unchecked")
	@FXML
    void initialize() {
    	backButton.setOnAction(event -> {
    		openScene("/view/MainMenu.fxml", backButton);
    	});
    	Map<Visitor, Double> map = Zoo.getInstance().geAllDiscountAmount();
    	Map<String,String> m = new TreeMap<String,String>();
    	for(Visitor v: map.keySet()) {
    		m.put(v.toString(), String.valueOf(map.get(v)));
    	}
    	
    	discountTable.getItems().addAll(m.keySet());
    	keyColumn.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue()));
    	valueColumn.setCellValueFactory(cd -> new SimpleStringProperty(m.get(cd.getValue())));
    	discountTable.getColumns().clear();
    	discountTable.getColumns().addAll(keyColumn, valueColumn);
    }
    
    public void openScene(String window, Button button) {
  		button.getScene().getWindow().hide();
  		FXMLLoader loader = new FXMLLoader();
  		loader.setLocation(getClass().getResource(window));
  		AssistMethods.loader(loader);
  	}
    
}
