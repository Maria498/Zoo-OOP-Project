package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Model.*;
import exceptions.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;


//Controller class of Animal visits fxml with reference to animalVisits method 
//Assumption - visitor can visit an animal once
//Assumption - Reptile class also throws AnimalsVisitsException if it's visitCount greater than 29
public class AnimalVisitsController {
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private ImageView image;

    @FXML
    private URL location;

    @FXML
    private Button visitAnimalButton;

    @FXML
    private ComboBox<Person> personBox;

    @FXML
    private ComboBox<Animal> animalBox;

    @FXML
    private Button backButton;

    @FXML
    void initialize() {
    	backButton.setOnAction(event -> {
    		openScene("/view/MainMenu.fxml", backButton);
    	});
    	// list of employees and visitors
    	ArrayList<Person> person = new ArrayList<Person>();
    	person.addAll(Zoo.getInstance().getEmployees().values());
    	person.addAll(Zoo.getInstance().getVisitors().values());

    	personBox.setItems(FXCollections.observableArrayList(person));
    	personBox.setOnAction(event -> {
    		// validation data check
    		if(personBox.getValue()!=null) {
    			Section s = personBox.getValue().getSection();
    			ArrayList<Animal> ani = new ArrayList<>();
    	    	ani.addAll(s.getBirds());
    	    	ani.addAll(s.getMammals());
    	    	ani.addAll(s.getReptiles());
    	    	animalBox.setItems(FXCollections.observableArrayList(ani));
    		}
    	});
    
    	visitAnimalButton.setOnAction(event -> {
    		boolean valid = true;
    		if(animalBox.getValue()!=null && personBox.getValue()!=null) {
    			Person p = personBox.getValue();
    			Animal a = animalBox.getValue();
    			int count = a.getVisitCounter();
    			if(a instanceof Mammal || a instanceof Bird) {
    			try {	//Assumption - visitor can visit an animal once
    				if(p instanceof Visitor && Zoo.getInstance().getAnimalVisitsByPeople().containsKey(p) &&
    						Zoo.getInstance().getAnimalVisitsByPeople().get(p).contains(a)) {
    						valid = false;
    						AssistMethods.shakeThat(animalBox, personBox, null, null, null, null, null, null, null, null, null, null);
        					AssistMethods.showAlert(a.getName()+" already has visited by "+p.getFirstName()+" "+p.getLastName(), "Choose another bird or person");
    				}
					if(valid) {
						if(a instanceof Bird) {
							Bird b = (Bird)a;
							b.visitcount(p);
						}
						if(a instanceof Mammal) {
							Mammal m = (Mammal)a;
							m.visitcount(p);
						}		
					}
				} catch (AnimalsVisitsException e) {
					e.getMessage();
				}
    		}
    		if(a instanceof Reptile) {
    			try {
    				Reptile r = (Reptile)a;
    				if(r.getVisitCounter()>29 && p instanceof Visitor)
    					throw new AnimalsVisitsException();
    				else
    					r.setVisitCounter(r.getVisitCounter()+1);
    			} catch (AnimalsVisitsException e) {
					e.getMessage();
				}
    		}
    		if(a.getVisitCounter()>count)	// if counter changed
    			AssistMethods.showAlert("Complete", "Person "+p.getFirstName()+ " " +p.getLastName()+" has visited "+a.getName());
    		}
    		else {
    			AssistMethods.shakeThat(animalBox, personBox, null, null, null, null, null, null, null, null, null, null);
    			AssistMethods.showAlert("One or more fields are empty", "Choose a bird and a person");
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
