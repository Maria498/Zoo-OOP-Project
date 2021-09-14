package controller;

import java.net.URL;
import java.util.ResourceBundle;

import Model.*;
import Utils.*;
import exceptions.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

// Controller class of Set discount fxml where user can set a discount to visitor
// method throws DiscountCheckException if the exception is greater than 25%, 
// exception is Hever discount that throws exception but allows to set a 30% discount
public class SetDiscountController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button setDiscountButton;

    @FXML
    private ComboBox<Visitor> visitorsBox;

    @FXML
    private ComboBox<Discount> discountBox;

    @FXML
    private TextField ticketPriceField;

    @FXML
    private ImageView image;
    
    @FXML
    private TextField discountPriceField;

    @FXML
    private Button backButton;

    @FXML
    private Button saveButton;

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
           	 discountBox.getItems().clear();
             ticketPriceField.clear();
             discountPriceField.clear();
        	 Visitor v = visitorsBox.getValue();
           	 if(!visitorsBox.getSelectionModel().isEmpty()) {
           		 ticketPriceField.setText(v.getTicket()+ " " +String.valueOf(v.getTicket().getValue()));
           		 ticketPriceField.setDisable(true);
           		 discountPriceField.setText(v.getDiscount()+" " + String.valueOf(v.getDiscount().getPercentage()));
          		 discountPriceField.setDisable(true);
           		 discountBox.setItems(FXCollections.observableArrayList(Discount.values()));
           	  }
           	  else {
           		  AssistMethods.shakeThat(discountPriceField, visitorsBox, discountBox, ticketPriceField, null, null, null, null, null, null, null, null);
           		  AssistMethods.showAlert("Choose a visitor", "Then choose a discount");
           	  }
             });
    		discountBox.setOnAction(event -> {
    			try {
    				Discount d = discountBox.getSelectionModel().getSelectedItem();
        			if(d.getPercentage()>25) 
        				throw new DiscountCheckException();
    			} catch (DiscountCheckException e) {
    				e.getMessage();
    			}
    		});
    	
             setDiscountButton.setOnAction(event -> {
           	  Visitor v = visitorsBox.getSelectionModel().getSelectedItem();
           	  Discount d = discountBox.getSelectionModel().getSelectedItem();
           	  if(v!=null && d!=null) {
           		  v.setDiscount(d);
           		  if(v.getDiscount().getPercentage() == d.getPercentage())
           			  AssistMethods.showAlert("Complete", "Visitor "+ v.getFirstName()+ " " +v.getLastName()+" got a discount of "+d.getPercentage()+"%.");
           		  else {
           			 AssistMethods.shakeThat(discountPriceField, visitorsBox, discountBox, ticketPriceField, null, null, null, null, null, null, null, null);
           			  AssistMethods.showAlert("Action is not completed", "Visitor "+ v.getFirstName()+ " " +v.getLastName()+" couldn't get discount more than 25%.");
           		  }
           	  	}
           	  else {
           		 AssistMethods.shakeThat(discountPriceField, visitorsBox, discountBox, ticketPriceField, null, null, null, null, null, null, null, null);
           		  AssistMethods.showAlert("Action is not completed", "Try again");
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
