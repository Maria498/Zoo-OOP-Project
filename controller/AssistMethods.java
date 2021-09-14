package controller;

import java.io.IOException;
import java.time.LocalDate;

import Model.*;
import Utils.*;
import exceptions.*;
import animation.Shake;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

// AssistMethods Class of helper methods for reducing code iterations
public class AssistMethods {
	
	// variety of lists for combobox reuse
	static ObservableList<Section> sections = FXCollections.observableArrayList(Zoo.getInstance().getSections().values());
	static 	ObservableList<Gender> genderEnums = FXCollections.observableArrayList(Gender.values());
	static ObservableList<Boolean> yesNo = FXCollections.observableArrayList(Boolean.FALSE, Boolean.TRUE);
	static ObservableList<String> animalTypeList = FXCollections.observableArrayList("Mammal", "Reptile", "Bird");
	
	// method for showing information alerts to user
	public static void showAlert(String headerTxt, String contentTxt) {
    	Alert at = new Alert(Alert.AlertType.INFORMATION);
		at.setTitle("InformationDialogBox");
		at.setHeaderText(headerTxt);
		at.setContentText(contentTxt);
		at.show();
    }
	// method for showing warning alerts to user
	public static void showWarning(String headerTxt, String contentTxt) {
    	Alert at = new Alert(Alert.AlertType.ERROR);
		at.setTitle("InformationDialogBox");
		at.setHeaderText(headerTxt);
		at.setContentText(contentTxt);
		at.show();
    }
	// validation check of date (assumption is that date is before date of submission 10.10.20)
	 public static void dateCheck(LocalDate d) throws BirthDateException {
	    	if(d.isAfter(LocalDate.of(2020, 10, 10)))
	     		throw new BirthDateException();
	    }
	// Animation - shaking while incorrect data is entered
	 public static void shakeThat(Node a, Node b, Node c, Node d, Node e, Node f, Node g, Node h, Node i, Node j, Node k, Node l) {
	    	Shake a1 = new Shake(a);
			Shake b1 = new Shake(b);
			Shake c1 = new Shake(c);
			Shake d1 = new Shake(d);
			Shake e1 = new Shake(e);
			Shake f1 = new Shake(f);
			Shake g1 = new Shake(g);
			Shake h1 = new Shake(h);
			Shake i1 = new Shake(i);
			Shake j1 = new Shake(j);
			Shake k1 = new Shake(k);
			Shake l1 = new Shake(l);
			k1.playAnim();
			l1.playAnim();
			a1.playAnim();
			b1.playAnim();
			c1.playAnim();
			d1.playAnim();
			e1.playAnim();
			f1.playAnim();
			g1.playAnim();
			h1.playAnim();
			i1.playAnim();
			j1.playAnim();
	    }
	// helper method for setting a new scene 
	 public static void loader(FXMLLoader loader) {
		 try {
	 			loader.load();
	 		} catch (IOException e) {
	 			e.printStackTrace();
	 		}
	 		Parent root = loader.getRoot();
	 		Stage stage = new Stage();
	 		stage.setScene(new Scene(root));
	 		stage.show();
	 }

	  
	  
	
}
