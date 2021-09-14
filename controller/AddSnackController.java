package controller;

import java.net.URL;
import java.util.Collections;
import java.util.ResourceBundle;

import Model.*;
import Utils.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

//Controller class of Add Snacks fxml where all snack types can be added to Zoo object data.
//Depending on the snack types actual windows appears and disappears
//Assumption - All new added object getting Id number by finding the maximum of the existing objects add 1 (max(Zoo.getInstance().....keySet());
public class AddSnackController {

	ObservableList<String> snackList = FXCollections.observableArrayList("Food", "Drink", "Snack");
	ObservableList<SnackType> snackTypeEnums = FXCollections.observableArrayList(SnackType.values());
	ObservableList<SnackBar> snBars = FXCollections.observableArrayList(Zoo.getInstance().getBars().values());
	double price;
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private ImageView image;

    @FXML
    private URL location;

    @FXML
    private Button addSnackButton;

    @FXML
    private ComboBox<String> foodDrinkBox;

    @FXML
    private ComboBox<SnackType> snackTypeBox;

    @FXML
    private TextField nameField;

    @FXML
    private TextField priceField;

    @FXML
    private ComboBox<Boolean> isFatteringBox;

    @FXML
    private ComboBox<Boolean> icecubeBox;

    @FXML
    private ComboBox<Boolean> strawBox;

    @FXML
    private ComboBox<Boolean> isSprinkleBox;

    @FXML
    private ComboBox<Boolean> glutenFreeBox;

    @FXML
    private ComboBox<Boolean> spicyBox;

    @FXML
    private ComboBox<Boolean> plateBox;

    @FXML
    private ComboBox<SnackBar> snackBarBox;

    @FXML
    private Button backButton;

    @FXML
    private Button saveButton;

    @FXML
    void initialize() {
    	foodDrinkBox.setItems(snackList);
    	snackTypeBox.setItems(snackTypeEnums);
    	isFatteringBox.setItems(AssistMethods.yesNo);
    	icecubeBox.setItems(AssistMethods.yesNo);
    	strawBox.setItems(AssistMethods.yesNo);
    	isSprinkleBox.setItems(AssistMethods.yesNo);
    	glutenFreeBox.setItems(AssistMethods.yesNo);
    	spicyBox.setItems(AssistMethods.yesNo);
    	plateBox.setItems(AssistMethods.yesNo);
    	snackBarBox.setItems(snBars);
    	backButton.setOnAction(event -> {
    		openScene("/view/MainMenu.fxml", backButton);
    	});
    	saveButton.setOnAction(event -> {
			Zoo.serialize();
			AssistMethods.showAlert("Complete", "Changes saved");
        });
    		
    	foodDrinkBox.setOnAction(event -> {
    	String choice = foodDrinkBox.getValue();
    	if(choice.equals("Food")) {
     		addSnackButton.setText("Add food");
     		visibleBox(false, false, false, true, true, true);
     	}
    	if(choice.equals("Drink")) {
     		addSnackButton.setText("Add drink");
     		visibleBox(true, true, true, false, false, false);
    	}
    	if(choice.equals("Snack")) {
     		addSnackButton.setText("Add food");
     		visibleBox(false, false, false, false, false, false);
     	}});
    	
    	priceField.setOnAction(event -> {
    		try {
				price = Double.parseDouble(priceField.getText().trim());
     		} catch (NumberFormatException e) {
     			AssistMethods.shakeThat(foodDrinkBox, glutenFreeBox, icecubeBox, isFatteringBox, isSprinkleBox, nameField, 
						plateBox, priceField, snackBarBox, snackTypeBox, spicyBox, strawBox);
     			AssistMethods.showAlert("Incorrect price input", "Please enter a positive number");
    		}
    	});
  
    	addSnackButton.setOnAction(event -> {
         	SnackType type = snackTypeBox.getValue();
    		String name = nameField.getText().trim();
    		SnackBar bar = snackBarBox.getValue();
    		String str = priceField.getText().trim();
    		if(!str.equals("") && type!=null && !name.equals("") && bar!=null && isFatteringBox.getValue()!=null) {
    			try {	// price validation check
    				price = Double.parseDouble(str);
         		} catch (NumberFormatException e) {
         			AssistMethods.shakeThat(foodDrinkBox, glutenFreeBox, icecubeBox, isFatteringBox, isSprinkleBox, nameField, 
    						plateBox, priceField, snackBarBox, snackTypeBox, spicyBox, strawBox);
         			AssistMethods.showAlert("Incorrect price input", "Please enter a positive number");
        		}
    			if(price>0) {
    				boolean isFat = isFatteringBox.getValue();
    				int id = (int) Collections.max(Zoo.getInstance().getSnacks().keySet());
    				String choice = foodDrinkBox.getValue();
    				if(choice.equals("Snack")) {
    					Snack s = new Snack(type, name, bar, isFat, price);
    					s.setId(id+1);
    					if(Zoo.getInstance().addSnack(s))
    						AssistMethods.showAlert("Complete", "Snack " +s.getSnackName()+" added successfully!");
    				}
    				if(choice.equals("Drink") && isSprinkleBox.getValue()!=null && strawBox.getValue()!=null && icecubeBox.getValue()!=null) {
    					Drink d = new Drink(type, name, bar, isFat, price, isSprinkleBox.getValue(), strawBox.getValue(), icecubeBox.getValue());
    					d.setId(id+1);
    					if(Zoo.getInstance().addSnack(d))
    						AssistMethods.showAlert("Complete", "Snack " +d.getSnackName()+" added successfully!");
    				}
    				if(choice.equals("Food") && plateBox.getValue()!=null && spicyBox.getValue()!=null && glutenFreeBox.getValue()!=null) {
    					Food f = new Food(type, name, bar, isFat, price, plateBox.getValue(), spicyBox.getValue(), glutenFreeBox.getValue());
    					f.setId(id+1);
    					if(Zoo.getInstance().addSnack(f))
    						AssistMethods.showAlert("Complete", "Snack " +f.getSnackName()+" added successfully!");
    				}
    			}
    			else {
    				AssistMethods.shakeThat(foodDrinkBox, glutenFreeBox, icecubeBox, isFatteringBox, isSprinkleBox, nameField, 
    						plateBox, priceField, snackBarBox, snackTypeBox, spicyBox, strawBox);
        			AssistMethods.showAlert("One or more fields are empty", "Fill in all required fields");
    			}
    		}
    		else {
    			AssistMethods.shakeThat(foodDrinkBox, glutenFreeBox, icecubeBox, isFatteringBox, isSprinkleBox, nameField, 
						plateBox, priceField, snackBarBox, snackTypeBox, spicyBox, strawBox);
    			AssistMethods.showAlert("One or more fields are empty or incorrect", "Fill in all required fields");
    		}
    	});
    }
    
    public void openScene(String window, Button button) {
  		button.getScene().getWindow().hide();
  		FXMLLoader loader = new FXMLLoader();
  		loader.setLocation(getClass().getResource(window));
  		AssistMethods.loader(loader);
  	}
  
    public void visibleBox(boolean a, boolean b, boolean c, boolean d, boolean e, boolean f) {
    	icecubeBox.setVisible(a);	
 		strawBox.setVisible(b);
 		isSprinkleBox.setVisible(c);
 		glutenFreeBox.setVisible(d);	
 		spicyBox.setVisible(e);
 		plateBox.setVisible(f);
    }

    
}
