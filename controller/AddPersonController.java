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

//Controller class of Add Person Class where new visitors and employees can be added to Zoo object data.
//Depending on the person types actual windows appears and disappears
//Assumption - All new added object getting Id number by finding the maximum of the existing objects add 1 (max(Zoo.getInstance().....keySet());
public class AddPersonController {

	ObservableList<String> pesronTypeList = FXCollections.observableArrayList("New visitor", "Zoo employee");
	ObservableList<Job> jobEnums = FXCollections.observableArrayList(Job.values());
	ObservableList<TicketType> ticketTypeEnums = FXCollections.observableArrayList(TicketType.values());
	ObservableList<Discount> discountEnums = FXCollections.observableArrayList(Discount.values());
	

    @FXML
    private ResourceBundle resources;

    @FXML
    private ImageView image;

    @FXML
    private URL location;

    @FXML
    private Button addPersonnButton;

    @FXML
    private ComboBox<String> personTypeBox;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private DatePicker birthdayPicker;

    @FXML
    private ComboBox<TicketType> ticketTypeBox;

    @FXML
    private ComboBox<Gender> genderBox;

    @FXML
    private ComboBox<Section> sectionBox;

    @FXML
    private ComboBox<Discount> discountBox;

    @FXML
    private ComboBox<Job> jobBox;

    @FXML
    private Button backButton;

    @FXML
    private Button saveButton;

    @FXML
    void initialize() {
    	personTypeBox.setItems(pesronTypeList);
    	sectionBox.setItems(AssistMethods.sections);
    	genderBox.setItems(AssistMethods.genderEnums);
    	jobBox.setItems(jobEnums);
    	ticketTypeBox.setItems(ticketTypeEnums);
    	discountBox.setItems(discountEnums);
    	visibleBox(false, false, false);
    	backButton.setOnAction(event -> {
    		openScene("/view/MainMenu.fxml", backButton);
    	});
    	saveButton.setOnAction(event -> {
			Zoo.serialize();
			AssistMethods.showAlert("Complete", "Changes saved");
        });
    	personTypeBox.setOnAction(event -> {
    		
    	String choice = personTypeBox.getValue();
    	if(choice.equals("New visitor")) {
     		addPersonnButton.setText("Add new visitor");
     		sectionBox.setDisable(false);
     		visibleBox(false, true, true);
     	}
    	if(choice.equals("Zoo employee")) {
     		addPersonnButton.setText("Add employee");
     		visibleBox(true, false, false);
     		if(!WelcomeController.getUser().equals("admin")) {
    			sectionBox.setDisable(true);
    			int id = Integer.parseInt(WelcomeController.getUser());
				ZooEmployee e = Zoo.getInstance().getRealEmployee(id);
				sectionBox.setValue(e.getSection());
    		}
     	}});
    	birthdayPicker.setOnAction(event -> {
    		LocalDate birthday = birthdayPicker.getValue();
    		try { // validation check of date (assumption is that date is before date of submission 10.10.20)
				AssistMethods.dateCheck(birthday);
			} catch (BirthDateException e) {
				e.getMessage();
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
    	addPersonnButton.setOnAction(event -> {
    		boolean validForVisitor = true;
    		boolean validForEmployee = true;
         	String firstName = firstNameField.getText().trim();
         	String lastName = lastNameField.getText().trim();
         	LocalDate birthday = birthdayPicker.getValue();
         	Gender gender = genderBox.getValue();
         	
    			if(personTypeBox.getValue()==null) {
             		AssistMethods.shakeThat(birthdayPicker, discountBox, firstNameField, genderBox, jobBox, lastNameField, personTypeBox,
             				sectionBox, ticketTypeBox, null, null, null);
    				AssistMethods.showAlert("One or more fields are empty or incorrect", "Fill in all required fields");
    			}
    			// adding new visitor to the Zoo
    			if(personTypeBox.getValue() == "New visitor") {
    				if(!firstName.equals(" ") && !lastName.equals(" ") && birthday!=null && gender!=null) {
    				try {
    					//checks if visitor is not existing employee
             			for(ZooEmployee e: Zoo.getInstance().getEmployees().values()) {
             				if(firstName.equals(e.getFirstName()) && lastName.equals(e.getLastName()) && birthday.equals(e.getBirthDay())) {
             					validForVisitor = false;
             					throw new DuplicatedValuesException();
             				}
             			} 	//checks if visitor is already been added
             			for(Visitor v: Zoo.getInstance().getVisitors().values()) {
             				if(firstName.equals(v.getFirstName()) && lastName.equals(v.getLastName()) && birthday.equals(v.getBirthDay())) {
             					validForVisitor = false;
             					throw new ExistingObjectException();
             				}
             			}
             		} 
    				catch (DuplicatedValuesException e) {
        			e.getMessage();
             		}
    				catch (ExistingObjectException e) {
        			e.getMessage();
             		}
    				TicketType ticket = ticketTypeBox.getValue();
    				Discount discount = discountBox.getValue();
    				Section s = sectionBox.getValue();
 
    				if(discount!=null && ticket!=null && s!=null && birthday.isBefore(LocalDate.of(2020, 10, 10))) {
    					try { 	//checks if section is not overcrowded (assumption + additional exception)
    						if(s.getVisitors().size() +1 >= s.getMaxCapacity()) {
    							validForVisitor = false;
    							throw new OverflowCapacityException();
    						}
    	    			
    					} catch (OverflowCapacityException e) {
    						e.getMessage();
    					}
    				
    					if(validForVisitor) {
             				int id = Collections.max(Zoo.getInstance().getVisitors().keySet());
             				Visitor v = new Visitor(firstName, lastName, birthday, gender, ticket, discount);
             				v.setId(id+1);
             				if(Zoo.getInstance().newVisitorInZoo(v, s))
             					AssistMethods.showAlert("Complete", "New visitor " +v.getFirstName()+" "+v.getLastName()+ " added successfully!");
             				}
             				else if(!firstName.equals(" ") && !lastName.equals(" ") && birthday!=null && gender!=null && validForVisitor) {
             	         		AssistMethods.shakeThat(birthdayPicker, discountBox, firstNameField, genderBox, jobBox, lastNameField, personTypeBox,
             	         				sectionBox, ticketTypeBox, null, null, null);
             					AssistMethods.showAlert("One or more fields are empty or incorrect", "Fill in all required fields correctly");
             				}
             			}
    				}	
    				else {
    	         		AssistMethods.shakeThat(birthdayPicker, discountBox, firstNameField, genderBox, jobBox, lastNameField, personTypeBox,
    	         				sectionBox, ticketTypeBox, null, null, null);
    					AssistMethods.showAlert("One or more fields are empty or incorrect", "Fill in all required fields correctly");
    				}
    			}
    			if(personTypeBox.getValue() == "Zoo employee") {
    				if(!firstName.equals(" ") && !lastName.equals(" ") && birthday!=null && gender!=null) {
    	    			try { //checks if visitor is already been added
    	         			for(ZooEmployee e: Zoo.getInstance().getEmployees().values()) {
    	         				if(firstName.equals(e.getFirstName()) && lastName.equals(e.getLastName()) && birthday.equals(e.getBirthDay())) {
    	         					validForEmployee = false;
    	         					throw new ExistingObjectException();
    	         				}
    	         			} 	//checks if employee is not existing visitor
    	         			for(Visitor v: Zoo.getInstance().getVisitors().values()) {
    	         				if(firstName.equals(v.getFirstName()) && lastName.equals(v.getLastName()) && birthday.equals(v.getBirthDay())) {
    	         					validForEmployee = false;
    	         					throw new ExistingObjectException();
    	         				}
    	         			}
    	         		} catch (ExistingObjectException e) {
    	    			e.getMessage();
    	         		}
    				Section s = null;
    				// employee can add another employees only to his section
    				if(!WelcomeController.getUser().equals("admin")) {
    					int id = Integer.parseInt(WelcomeController.getUser());
    					ZooEmployee e = Zoo.getInstance().getRealEmployee(id);
    					s = e.getSection();
    	    		}
    				else {
    					s = sectionBox.getValue();
    				}
    				Job job = jobBox.getValue();
    				if(s!=null && job!=null && birthday.isBefore(LocalDate.of(2020, 10, 10)) && validForEmployee) {
    					int id = Collections.max(Zoo.getInstance().getEmployees().keySet());
        				ZooEmployee e = new ZooEmployee(firstName, lastName, birthday, gender, s, job);
        				e.setId(id+1);
        				if(Zoo.getInstance().addEmployee(e)) {
        					AssistMethods.showAlert("Complete", "Employee " +e.getFirstName()+" "+e.getLastName()+ " added successfully!");
        				}
        				
    				}
    				else if(!firstName.equals(" ") && !lastName.equals(" ") && birthday!=null && gender!=null && validForEmployee) {
    	         		AssistMethods.shakeThat(birthdayPicker, discountBox, firstNameField, genderBox, jobBox, lastNameField, personTypeBox,
    	         				sectionBox, ticketTypeBox, null, null, null);
    					AssistMethods.showAlert("One or more fields are empty or incorrect", "Fill in all required fields correctly");
    				}
    			}
    		
         	else {
         		AssistMethods.shakeThat(birthdayPicker, discountBox, firstNameField, genderBox, jobBox, lastNameField, personTypeBox,
         				sectionBox, ticketTypeBox, null, null, null);
				AssistMethods.showAlert("One or more fields are empty or incorrect", "Fill in all required fields correctly");
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
    
    public void visibleBox(boolean a, boolean b, boolean c) {
    	jobBox.setVisible(a);	
 		ticketTypeBox.setVisible(b);
 		discountBox.setVisible(c);
    }
}
    
 

