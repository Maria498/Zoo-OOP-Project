package controller;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

import Model.*;
import exceptions.PossibleBunkruptcyException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

// Controller class of Daily profit calculation fxml that displays a profit 

public class CalculationQueriesController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView image;

    @FXML
    private TextField profitField;

    @FXML
    private Button calculateButton;
    
    @FXML
    private Button backButton;

    @FXML
    private TextField newPercField;

    @FXML
    private Button setButton;

    @FXML
    private Slider newPercSlider;

    @FXML
    private TextField currPercField;

    @FXML
    void initialize() {
    	backButton.setOnAction(event -> {
    		openScene("/view/MainMenu.fxml", backButton);
    	});
    	profitField.setVisible(false);
    	calculateButton.setOnAction(event -> {
    		profitField.setVisible(true);
    		profitField.setText(String.valueOf(Zoo.getInstance().checkTotalRevenue()));
    	});
    	newPercSlider.setMin(0.0);
    	newPercSlider.setMax(1.0);
    	DecimalFormat df = new DecimalFormat("#.##");
    	newPercSlider.valueProperty().addListener(event ->{
    		currPercField.setText(String.valueOf(SnackBar.getZooPercentage()));
    		newPercField.setText(String.valueOf(df.format(newPercSlider.getValue())));
    		newPercField.setViewOrder(newPercSlider.getValue());
    	});
    	setButton.setOnAction(event -> {
    		boolean valid = true;
    		if(newPercSlider!=null) {
    			try {
        			if( (double)Math.round(newPercSlider.getValue()*100)/100 > 0.5) {
        				valid = false;
        				throw new PossibleBunkruptcyException();
        			}
         		} catch (PossibleBunkruptcyException e) {
         			e.getMessage();
        		} 
    			
    			if(valid) {
    			SnackBar.setZooPercentage((double)Math.round(newPercSlider.getValue()*100)/100);
    			currPercField.setText(String.valueOf(SnackBar.getZooPercentage()));
    			AssistMethods.showAlert("Complete", "Zoo percentage has been changed to "+SnackBar.getZooPercentage());
    			profitField.setText(String.valueOf(Zoo.getInstance().checkTotalRevenue()));
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
}
