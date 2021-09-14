package application;
	
import java.io.File;

import Model.Zoo;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;

// Main class that runs an application
public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("/view/Welcome.fxml"));
			Scene scene = new Scene(root,1050,600);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
	 // checks if file Zoo.ser exists
		if(new File("Zoo.ser").exists()) 
			Zoo.deserialize();
		else {
			System.out.println("File not exists");
			Zoo.zoo = Zoo.getInstance();
		}
		launch(args);
	}
}
