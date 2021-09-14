package controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.Collections;
import java.util.ResourceBundle;

import Model.*;
import Utils.*;
import exceptions.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

//Controller class of Add Animal fxml where all animal types can be added to Zoo object data.
//Depending on the animal types actual windows appears and disappears
//Assumption - All new added object getting Id number by finding the maximum of the existing objects add 1 (max(Zoo.getInstance().....keySet());
public class AddAnimalController {


	ObservableList<AnimalFood> animalFoodEnums = FXCollections.observableArrayList(AnimalFood.values());
	
    @FXML
    private ResourceBundle resources;
    
    @FXML
    private ImageView image;

    @FXML
    private URL location;

    @FXML
    private Button addAnimalButton;

    @FXML
    private ComboBox<String> animalTypeBox;

    @FXML
    private TextField nameField;

    @FXML
    private ComboBox<AnimalFood> animalFoodBox;

    @FXML
    private DatePicker birthdayPicker;

    @FXML
    private ComboBox<Section> sectionBox;

    @FXML
    private ComboBox<Gender> genderBox;

    @FXML
    private ComboBox<Boolean> takePicBox;

    @FXML
    private ComboBox<Boolean> canFlyBox;

    @FXML
    private ComboBox<Boolean> meatEaterBox;

    @FXML
    private ComboBox<Boolean> dangerousBox;

    @FXML
    private ComboBox<Boolean> sleepAtSeasonBox;

    @FXML
    private Button backButton;

    @FXML
    private Button saveButton;

    @FXML
    void initialize() {
    	animalTypeBox.setItems(AssistMethods.animalTypeList);
    	sectionBox.setItems(AssistMethods.sections);
    	takePicBox.setItems(AssistMethods.yesNo);
    	canFlyBox.setItems(AssistMethods.yesNo);
    	meatEaterBox.setItems(AssistMethods.yesNo);
    	dangerousBox.setItems(AssistMethods.yesNo);
    	sleepAtSeasonBox.setItems(AssistMethods.yesNo);
    	genderBox.setItems(AssistMethods.genderEnums);
    	animalFoodBox.setItems(animalFoodEnums);
    	
    	
    	backButton.setOnAction(event -> {
    		openScene("/view/MainMenu.fxml", backButton);
    	});
    	saveButton.setOnAction(event -> {
			Zoo.serialize();
			AssistMethods.showAlert("Complete", "Changes saved");
        });
    	animalTypeBox.setOnAction(e -> {
    	String choice = animalTypeBox.getValue();	
  
    	if(choice.equals("Mammal")) {
     		addAnimalButton.setText("Add mammal");
     		visibleBox(false, true, true, false, false);
     	}
    	if(choice.equals("Bird")) {
    		addAnimalButton.setText("Add bird");
     		visibleBox(true, true, false, false, false);
     	}
    	if(choice.equals("Reptile")) {
    		addAnimalButton.setText("Add reptile");
     		visibleBox(false, false, false, true, true);
    	}});
    	// validation check of date (assumption is that date is before date of submission 10.10.20)
    	birthdayPicker.setOnAction(event -> {
    		LocalDate birthday = birthdayPicker.getValue();
    		try {
				AssistMethods.dateCheck(birthday);
			} catch (BirthDateException e) {
				AssistMethods.shakeThat(animalFoodBox, animalTypeBox, birthdayPicker, canFlyBox, dangerousBox, genderBox, nameField, sectionBox, 
    					takePicBox, sleepAtSeasonBox, meatEaterBox, null);
				e.getMessage();
			}
    	});
    	
    	addAnimalButton.setOnAction(event -> {
    		String name = nameField.getText().trim();
    		LocalDate birthday = birthdayPicker.getValue();
    		Gender gender = genderBox.getValue();
    		AnimalFood food = animalFoodBox.getValue();
    		Section section = sectionBox.getValue();
    		// validation check of input
    		if(name.equals(" ") || birthday==null || gender==null || food==null || section==null || !birthday.isBefore(LocalDate.of(2020, 10, 10))) {
    			AssistMethods.shakeThat(animalFoodBox, animalTypeBox, birthdayPicker, canFlyBox, dangerousBox, genderBox, nameField, sectionBox, 
    					takePicBox, sleepAtSeasonBox, meatEaterBox, null);
    			AssistMethods.showAlert("One or more fields are empty or incorrect", "Fill in all required fields");
    		}
    		else {
    			if(animalTypeBox.getValue() == "Bird") {
    				boolean takePic = false; 
    				boolean canFly = false;
    				if(!canFlyBox.getSelectionModel().isEmpty()) 
    					canFly = canFlyBox.getValue();
    				if(!takePicBox.getSelectionModel().isEmpty())
    					takePic = takePicBox.getValue();
    				int id = Collections.max(Zoo.getInstance().getBirds().keySet());
    				Bird b = new Bird(name, birthday, food, gender, section, canFly, takePic);
    				b.setId(id+1);
    				if(Zoo.getInstance().addBirdById(b))
    					AssistMethods.showAlert("Complete", "Bird " +b.getName()+ " added successfully!");
    			}
    			if(animalTypeBox.getValue() == "Mammal") {
    				boolean takePic = false; 
    				boolean meatEater = false;
    				if(!meatEaterBox.getSelectionModel().isEmpty()) 
    					meatEater = meatEaterBox.getValue();
    				if(!takePicBox.getSelectionModel().isEmpty())
    					takePic = takePicBox.getValue();
    				int id = Collections.max(Zoo.getInstance().getMammals().keySet());
    				Mammal m = new Mammal(name, birthday, food, gender, section, meatEater, takePic);
    				m.setId(id+1);
    				if(Zoo.getInstance().addMammalById(m))
    					AssistMethods.showAlert("Complete", "Mammal " +m.getName()+ " added successfully!");
    			}
    			if(animalTypeBox.getValue() == "Reptile") {
    				boolean danger = false; 
    				boolean sleepAtS = false;
    				if(!dangerousBox.getSelectionModel().isEmpty()) 
    					danger = dangerousBox.getValue();
    				if(!sleepAtSeasonBox.getSelectionModel().isEmpty())
    					sleepAtS = sleepAtSeasonBox.getValue();
    				int id = Collections.max(Zoo.getInstance().getReptiles().keySet());
    				Reptile r = new Reptile(name, birthday, food, gender, section, danger, sleepAtS);
    				r.setId(id+1);
    				if(Zoo.getInstance().addReptileById(r))
    					AssistMethods.showAlert("Complete", "Reptile " +r.getName()+ " added successfully!");
    			}
    		}	
    	});
    }
    
    public void openScene(String window, Button button) {
  		button.getScene().getWindow().hide();
  		FXMLLoader loader = new FXMLLoader();
  		loader.setLocation(getClass().getResource(window));
  		AssistMethods.loader(loader);
  	}
    
    public void visibleBox(boolean a, boolean b, boolean c, boolean d, boolean e) {
    	canFlyBox.setVisible(a);
    	takePicBox.setVisible(b);
    	meatEaterBox.setVisible(c);
    	dangerousBox.setVisible(d);
    	sleepAtSeasonBox.setVisible(e);
    }
 
}
