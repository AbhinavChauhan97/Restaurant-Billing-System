package application;

import java.io.IOException;
import java.util.Random;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;


public class Main extends Application {
	
	private static Stage stage;
   
	@Override
	public void start(Stage primaryStage) throws IOException {
		
       new Thread(() -> {
			
			Database.establishConnection();
			Restaurant.getRestaurant();
			     
		}).start();
       
		Main.stage = primaryStage;
		Parent root = FXMLLoader.load(getClass().getResource("/application/Login.fxml"));
		Scene scene = new Scene(root, 900, 500);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
	}
   
	public static Stage getStage() {
		return stage;
	}

	public static void main(String[] args) throws IOException {
	
		Font.loadFont(Main.class.getResource("resources/Inconsolata.otf").toExternalForm(), 10);
		launch(args);
	}

}
