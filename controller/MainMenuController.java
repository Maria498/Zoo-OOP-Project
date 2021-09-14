package controller;

import java.net.URL;
import java.util.ResourceBundle;

import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import javafx.scene.media.MediaView;


// MainWindowController Class represents a Main Window in which user can execute all the options 
// in the system including all the queries, adding and removing objects and view all the data
public class MainMenuController implements Initializable {


	ObservableList<String> dataEntryList = FXCollections.observableArrayList("Add new visitor/employee", "Add animal",
			"Add section", "Add snack", "Add snack bar", "Remove objects");
	
	// List of data entry options for Employee
	ObservableList<String> dataEntryEmployeeList = FXCollections.observableArrayList("Add new visitor/employee", 
			"Add snack", "Remove objects");
	
	// List of queries - same for employee and admin
	ObservableList<String> queriesList = FXCollections.observableArrayList("Animals by section/worker/visitor",
			"Snacks by snack bar", "Sleeps at season reptiles", "Discount amount", "Profitable section", "Animal care", "Animal visits",
			"Calculation queries", "Purchase snack by visitor", "Move visitor to section", "Set a ticket discount");
	
	// List of data display tableviews
	ObservableList<String> displayList = FXCollections.observableArrayList("Employees", 
	    		"Visitors", "Animals", "Sections", "Snacks", "Snack bars");

	
    @FXML
    private ResourceBundle resources;  

    @FXML
    private URL location;
    
    @FXML
    private MediaView media;

    @FXML
    private Button submitBtnData;

    @FXML
    private Button submitBtnObj;
    
    @FXML
    private Button submitBtnQry;
    
    @FXML
    private Button loginButton;
    
    @FXML
    private Button saveChangesButton;

    @FXML
    private ComboBox<String> dataDisplayBox;
   
    @FXML
    private ComboBox<String> runQueriesBox;
   
    @FXML
    private ComboBox<String> dataEntryBox;
    
    
    public void initialize(URL arg0, ResourceBundle arg1) {

    	// exit button (logout) returns to login window 
    	loginButton.setOnAction(event -> {
    		openScene("/view/Welcome.fxml", loginButton);
    	});
    	// if user is employee - displaying List of data entry options for employee
    	if(!WelcomeController.getUser().equals("admin")) 
    		dataEntryBox.setItems(dataEntryEmployeeList);
    	else //admin
    		dataEntryBox.setItems(dataEntryList);
    	dataDisplayBox.setItems(displayList);
    	runQueriesBox.setItems(queriesList);
    	submitBtnObj.setOnAction(event -> {
        	String choice = null;
        	if(dataDisplayBox.getValue()!=null && runQueriesBox.getValue()==null && dataEntryBox.getValue()==null) {
        		choice = dataDisplayBox.getValue();
        		if(choice.equals("Animals"))
        			openScene("/view/Animals.fxml", submitBtnObj);
        		if(choice.equals("Visitors"))
        			openScene("/view/Visitors.fxml", submitBtnObj);
        		if(choice.equals("Employees"))
        			openScene("/view/Employees.fxml", submitBtnObj);
        		if(choice.equals("Sections"))
        			openScene("/view/Sections.fxml", submitBtnObj);
        		if(choice.equals("Snack bars"))
        			openScene("/view/SnackBars.fxml", submitBtnObj);
        		if(choice.equals("Snacks"))
        			openScene("/view/Snacks.fxml", submitBtnObj);
        	}    
        });
        	
    	submitBtnQry.setOnAction(event -> {
    		String choice = null;
        	if(dataDisplayBox.getValue()==null && runQueriesBox.getValue()!=null && dataEntryBox.getValue()==null) {
       		 	choice = runQueriesBox.getValue();
       		 if(choice.equals("Animals by section/worker/visitor"))
      			openScene("/view/AnimalsBySection.fxml", submitBtnQry);
       		 if(choice.equals("Snacks by snack bar"))
       			openScene("/view/SnacksBySnackBar.fxml", submitBtnQry);
       		 if(choice.equals("Sleeps at season reptiles"))
        		openScene("/view/SleepsReptiles.fxml", submitBtnQry);
       		if(choice.equals("Discount amount"))
        		openScene("/view/Discount.fxml", submitBtnQry);
       		if(choice.equals("Profitable section"))
        		openScene("/view/ProfitableSection.fxml", submitBtnQry);
       		if(choice.equals("Animal care"))
        		openScene("/view/AnimalCare.fxml", submitBtnQry);
       		if(choice.equals("Animal visits"))
        		openScene("/view/AnimalVisits.fxml", submitBtnQry);
       		if(choice.equals("Calculation queries"))
        		openScene("/view/CalculationQueries.fxml", submitBtnQry);
       		if(choice.equals("Purchase snack by visitor"))
        		openScene("/view/PurchaseSnack.fxml", submitBtnQry);
       		if(choice.equals("Move visitor to section"))
        		openScene("/view/MoveVisitor.fxml", submitBtnQry);
       		if(choice.equals("Set a ticket discount"))
        		openScene("/view/SetDiscount.fxml", submitBtnQry);
       		if(choice.equals("Set a Zoo percentage"))
        		openScene("/view/ZooPercentage.fxml", submitBtnQry);
       			
        	}
    	 });
    	submitBtnData.setOnAction(event -> {
    		String choice = null;
        	if(dataDisplayBox.getValue()==null && runQueriesBox.getValue()==null && dataEntryBox.getValue()!=null) {
        		choice = dataEntryBox.getValue();
        		if(choice.equals("Add new visitor/employee"))
        			openScene("/view/AddPerson.fxml", submitBtnData);
        		if(choice.equals("Add section"))
        			openScene("/view/AddSection.fxml", submitBtnData);
        		if(choice.equals("Add animal"))
        			openScene("/view/AddAnimal.fxml", submitBtnData);
        		if(choice.equals("Add snack"))
        			openScene("/view/AddSnack.fxml", submitBtnData);
        		if(choice.equals("Add snack bar"))
        			openScene("/view/AddSnackBar.fxml", submitBtnData);
        		if(choice.equals("Remove objects"))
        			openScene("/view/RemoveObject.fxml", submitBtnData);

        	}
        	// Animation - shaking while incorrect data is entered
        	if(choice == null) {
        		AssistMethods.shakeThat(dataDisplayBox, dataEntryBox, runQueriesBox, null, null, null, null, null, null, null, null, null);
        		AssistMethods.showAlert("Choose one option", "Than press a Proceed button");
        		dataDisplayBox.getSelectionModel().clearSelection();
        		dataEntryBox.getSelectionModel().clearSelection();
        		runQueriesBox.getSelectionModel().clearSelection();
        	}
         });
        // Save changes button before serialization
        saveChangesButton.setOnAction(event -> {
			Zoo.serialize();
			AssistMethods.showAlert("Complete", "Changes saved");
        });

    }
    // new scene
    public void openScene(String window, Button button) {
  		button.getScene().getWindow().hide();
  		FXMLLoader loader = new FXMLLoader();
  		loader.setLocation(getClass().getResource(window));
  		AssistMethods.loader(loader);
  	}
    
 	

}
