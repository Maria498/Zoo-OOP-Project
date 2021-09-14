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
import javafx.scene.image.ImageView;

//Controller class of Purchase snack fxml that displays a purchase snack method
public class PurchaseSnackController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button purchaseButton;

    @FXML
    private ComboBox<Visitor> visitorsBox;

    @FXML
    private ImageView image;

    @FXML
    private ComboBox<Snack> snacksBox;

    @FXML
    private TextField snackBarField;

    @FXML
    private Button backButton;

    @FXML
    private Button saveButton; 
    
    @FXML
    private Button homeButton;

    @FXML
    void initialize() {
    	backButton.setOnAction(event -> {
    		openScene("/view/MainMenu.fxml", backButton);
    	});
    	saveButton.setOnAction(event -> {
			Zoo.serialize();
			AssistMethods.showAlert("Complete", "Changes saved");
        });
    
      visitorsBox.setItems(FXCollections.observableArrayList(Zoo.getInstance().getVisitors().values()));
      visitorsBox.setOnAction(event -> {
    	  snacksBox.getItems().clear();
    	  snackBarField.clear();
    	  Visitor v = visitorsBox.getValue();
    	  if(!visitorsBox.getSelectionModel().isEmpty()) {
    		  snackBarField.setText(v.getSection().getBar().toString());
    		  snackBarField.setDisable(true);
    		  snacksBox.setItems(FXCollections.observableArrayList(v.getSection().getBar().getSnacks()));
    		
    	  }
    	  else {
    		  AssistMethods.shakeThat(snackBarField, snacksBox, visitorsBox, null, null, null, null, null, null, null, null, null);
    		  AssistMethods.showAlert("Choose a visitor", "Then choose a snack");
    	  }
      });
      purchaseButton.setOnAction(event -> {
    	  Visitor v = visitorsBox.getValue();
    	  Snack s = snacksBox.getValue();
    	  if(v!=null && s!=null) {
    		  if(v.purchaseSnack(s))
    			  AssistMethods.showAlert("Complete", "Visitor "+v.getFirstName()+" "+v.getLastName()+" purchased a "+s.getSnackName());
    		  else {
    			  AssistMethods.shakeThat(snackBarField, snacksBox, visitorsBox, null, null, null, null, null, null, null, null, null);
    			  AssistMethods.showAlert("Failed!", "Visitor "+v.getFirstName()+" "+v.getLastName()+" could'n purchase a "+s.getSnackName());
    		  }
    	  }
    	  else {
    		  AssistMethods.shakeThat(snackBarField, snacksBox, visitorsBox, null, null, null, null, null, null, null, null, null);
    		  AssistMethods.showAlert("One or more fields are empty or incorrect", "Fill in all required fields correctly");
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
