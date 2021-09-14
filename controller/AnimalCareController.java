package controller;

import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;

import Model.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;

//Controller class of Animal care fxml without reference to animalVisits method (there is another class for it)
//Assumption - admin can add/remove every animal to treatment, employee only those from the same section
public class AnimalCareController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private ImageView image;

    @FXML
    private URL location;

    @FXML
    private Button addAnimalButton;

    @FXML
    private ComboBox<Object> animalObjBox;

    @FXML
    private ComboBox<ZooEmployee> employeeBox;

    @FXML
    private ComboBox<String> animalTypeBox;
    
    @FXML
    private Button removeAnimalButton;

    @FXML
    private ComboBox<ZooEmployee> employeeBox2;

    @FXML
    private ComboBox<Animal> animalObjBox2;

    @FXML
    private Button backButton;

    @FXML
    private Button homeButton;

    @FXML
    void initialize() {
    	animalTypeBox.setItems(AssistMethods.animalTypeList);
    	backButton.setOnAction(event -> {
    		openScene("/view/MainMenu.fxml", backButton);
    	});

		animalTypeBox.setOnAction(event -> {
    	String choice = animalTypeBox.getValue();	
  
    	if(choice.equals("Mammal")) {
    		if(!WelcomeController.getUser().equals("admin")) {
    			ZooEmployee e = Zoo.getInstance().getRealEmployee(Integer.parseInt(WelcomeController.getUser()));
    			dataPerform(e.getSection().getMammals().toArray());
    		}
    		else
    			dataPerform(Zoo.getInstance().getMammals().values().toArray());	
     	}
    	if(choice.equals("Bird")) {
    		if(!WelcomeController.getUser().equals("admin")) {
    			ZooEmployee e = Zoo.getInstance().getRealEmployee(Integer.parseInt(WelcomeController.getUser()));
    			dataPerform(e.getSection().getBirds().toArray());
    		}
    		else
    			dataPerform(Zoo.getInstance().getBirds().values().toArray());	
     	}
    	if(choice.equals("Reptile")) {
    		if(!WelcomeController.getUser().equals("admin")) {
    			ZooEmployee e = Zoo.getInstance().getRealEmployee(Integer.parseInt(WelcomeController.getUser()));
    			dataPerform(e.getSection().getReptiles().toArray());
    		}
    		else
    			dataPerform(Zoo.getInstance().getReptiles().values().toArray());	
    	}
   
		});
    	addAnimalButton.setOnAction(event -> {
    		if(!animalObjBox.getSelectionModel().isEmpty() && !employeeBox.getSelectionModel().isEmpty()) {
    			Animal a = (Animal) animalObjBox.getValue();
    			ZooEmployee e = employeeBox.getValue();
    			Zoo.getInstance().getAnimalTreatedByZooEmployee().putIfAbsent(e, new HashSet<Animal>());
    			if(Zoo.getInstance().getAnimalTreatedByZooEmployee().get(e).add(a))
    				AssistMethods.showAlert("Complete", "Animal " +a.getName()+ " added to treatment successfully!");
    			else {
    				AssistMethods.shakeThat(animalObjBox, animalObjBox2, animalTypeBox, employeeBox, employeeBox2, null, null, null, null, null, null, null);
    			AssistMethods.showAlert("Something went wrong", "Check if "+a.getName()+" is already in treatment");
    			}
    		}
    		else {
    			AssistMethods.shakeThat(animalObjBox, animalObjBox2, animalTypeBox, employeeBox, employeeBox2, null, null, null, null, null, null, null);
				AssistMethods.showAlert("One or more fields are empty or incorrect", "Fill in all required fields correctly");
    		}
 				
    	});
    	
    		if(!WelcomeController.getUser().equals("admin")) {
    			ZooEmployee e = Zoo.getInstance().getRealEmployee(Integer.parseInt(WelcomeController.getUser()));
    			animalObjBox2.getItems().clear();

    			employeeBox2.getItems().clear();
    			employeeBox2.setItems(FXCollections.observableArrayList(e.getSection().getEmployees()));
    		}
    		else {
    			animalObjBox2.getItems().clear();

    			employeeBox2.getItems().clear();
    			employeeBox2.setItems(FXCollections.observableArrayList(Zoo.getInstance().getEmployees().values()));
    		}
    		employeeBox2.setOnAction(event -> {
    		animalObjBox2.getItems().clear();

    		ZooEmployee e = employeeBox2.getValue();
    		if(Zoo.getInstance().getAnimalTreatedByZooEmployee().containsKey(e)) {
    			animalObjBox2.setItems(FXCollections.observableArrayList(Zoo.getInstance().getAnimalTreatedByZooEmployee().get(e)));
    			animalObjBox2.setPromptText("Choose an animals to remove");
    		}
    		else {
    			AssistMethods.shakeThat(animalObjBox, animalObjBox2, animalTypeBox, employeeBox, employeeBox2, null, null, null, null, null, null, null);
    			animalObjBox2.setPromptText("No animals in treatment to " +e.getFirstName());
    		}
    	});
    	removeAnimalButton.setOnAction(event -> {
    		if(!animalObjBox2.getSelectionModel().isEmpty() && !employeeBox2.getSelectionModel().isEmpty()) {
    			Animal a = animalObjBox2.getValue();
    			ZooEmployee e = employeeBox2.getValue();
    			if(Zoo.getInstance().getAnimalTreatedByZooEmployee().get(e).contains(a)) {
    				Zoo.getInstance().getAnimalTreatedByZooEmployee().get(e).remove(a);
    				AssistMethods.showAlert("Complete", "Animal " +a.getName()+ " removed from treatment successfully!");
    			}
    			else {
    				AssistMethods.shakeThat(animalObjBox, animalObjBox2, animalTypeBox, employeeBox, employeeBox2, null, null, null, null, null, null, null);
    				AssistMethods.showAlert("Something went wrong", "Check if "+a.getName()+" is in treatment");
    			}
    		}
    		else {
    			AssistMethods.shakeThat(animalObjBox, animalObjBox2, animalTypeBox, employeeBox, employeeBox2, null, null, null, null, null, null, null);
				AssistMethods.showAlert("One or more fields are empty or incorrect", "Fill in all required fields correctly");
    		}
 				
    	});
    }

	public void dataPerform( Object[] objects) {
		employeeBox.getItems().clear();
    	animalObjBox.getItems().clear();
		animalObjBox.setItems( FXCollections.observableArrayList(objects));
		if(!WelcomeController.getUser().equals("admin")) {
    		ZooEmployee e = Zoo.getInstance().getRealEmployee(Integer.parseInt(WelcomeController.getUser()));
    		employeeBox.setItems(FXCollections.observableArrayList(e.getSection().getEmployees()));
    	}
    	else 
    		employeeBox.setItems(FXCollections.observableArrayList(Zoo.getInstance().getEmployees().values()));
    }
    
	   public void openScene(String window, Button button) {
	  		button.getScene().getWindow().hide();
	  		FXMLLoader loader = new FXMLLoader();
	  		loader.setLocation(getClass().getResource(window));
	  		AssistMethods.loader(loader);
	  	}
}
