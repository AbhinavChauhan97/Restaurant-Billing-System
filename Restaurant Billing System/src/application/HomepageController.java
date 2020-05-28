package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;


/**
 * 
 * This controller class controls the homepage of the application. 
 * 
 * @author femi
 *
 */
public class HomepageController implements Initializable {

	@FXML private Label lblPlatformTotal;
	
	// Table buttons 
	@FXML private Button btn1 = new Button("1");
	@FXML private Button btn2 = new Button("2");
	@FXML private Button btn3 = new Button("3");
	@FXML private Button btn4 = new Button("4");
	@FXML private Button btn5 = new Button("5");
	@FXML private Button btn6 = new Button("6");
	@FXML private Button btn7 = new Button("7");
	@FXML private Button btn8 = new Button("8");
	@FXML private Button btn9 = new Button("9");
	
	@FXML private Button menuManager = new Button();
	@FXML private Button staffManager = new Button();
	
	
	public static List<Button> allButtons; 
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		allButtons = new ArrayList<Button>();
		allButtons.add(btn1);
		allButtons.add(btn2);
		allButtons.add(btn3);
		allButtons.add(btn4);
		allButtons.add(btn5);
		allButtons.add(btn6);
		allButtons.add(btn7);
		allButtons.add(btn8);
		allButtons.add(btn9);
		setButtonsColour();
		System.out.println("initialized");
		
	}
	

	/**
	 * 
	 * Sets the colour of the buttons to reflect the status of the order.
	 * 
	 * @param allButtons contains buttons used in restaurant map
	 */
	
	
	public void modifyOrder(ActionEvent event) throws Exception {
	   
		int tableNumber = Integer.parseInt(((Button)event.getSource()).getText());
		if(Restaurant.getRestaurant().getTable(tableNumber).isEmpty()) {
			return;
		}
		Restaurant.getRestaurant().setSelectedTable(tableNumber);
		goToModifyPage();
	}
	private void setButtonsColour() {
		
		List<Table> availableTables = Restaurant.getRestaurant().getAvailableTables();
		
		availableTables.forEach(table -> {
			Button button = allButtons.get(table.getNumber() - 1);
			//System.out.println("button " + button.getText());
			if(table.isEmpty()) {
				//System.out.println(table.getNumber() + "in if");
				button.setOnMouseDragEntered(e -> button.setStyle("-fx-color: #F06666"));
				button.setOnMouseExited(e -> button.setStyle("-fx-color: #F06666") );
				button.setStyle("-fx-color: #F06666");
			}
			else {
	           // System.out.println(table.getNumber() + "in else");
				String HOVERED_BUTTON_STYLE = "-fx-background-color: -fx-outer-border, -fx-inner-border;";
				button.setOnMouseEntered(e -> button.setStyle(HOVERED_BUTTON_STYLE));
				button.setOnMouseExited(e -> button.setStyle("-fx-color: #00FF7F"));
				button.setStyle("-fx-color: #00FF7F");
			}
		});
	
	}
	
	/**
	 * 
	 * Creates a new scene that points to the modify order page 
	 * when a button is clicked.
	 * 
	 * @throws IOException if the required FXML page cannot be loaded 
	 */
	public void goToModifyPage() throws IOException {
	
		Stage primaryStage = Main.getStage();
		Parent root = FXMLLoader.load(getClass().getResource("/application/ModifyOrder.fxml"));
		Scene scene = new Scene(root, 900, 500);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		
	}
	
	/**
	 * 
	 * Creates a new scene that points to the new order page 
	 * when a button is clicked.
	 * 
	 * @throws IOException if the required FXML page cannot be loaded 
	 */
	public void goToOrder() throws IOException {

		Stage primaryStage = Main.getStage();
		Parent root = FXMLLoader.load(getClass().getResource("/application/NewOrder.fxml"));
		Scene scene = new Scene(root, 900, 500);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();

	}
	
	// ----------------------------------------------------------------------------------------------------------

	
	/**
	 * 
	 * Creates a new scene that points to the menu page 
	 * when a button is clicked.
	 * 
	 * @param event
	 * @throws IOException if the required FXML page cannot be loaded 
	 */
	public void newOrder(ActionEvent event) throws IOException {

		Stage primaryStage = Main.getStage();
		Parent root = FXMLLoader.load(getClass().getResource("/application/NewMenuItem.fxml"));
		Scene scene = new Scene(root, 900, 500);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();

	}
	
	/**
	 * 
	 * Creates a new scene that points to the new order page 
	 * when a button is clicked.
	 * 
	 * @throws IOException if the required FXML page cannot be loaded 
	 */
	public void goToOrder(ActionEvent event) throws IOException {

		Stage primaryStage = Main.getStage();
		Parent root = FXMLLoader.load(getClass().getResource("/application/NewOrder.fxml"));
		Scene scene = new Scene(root, 900, 500);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	/**
	 * 
	 * Creates a new scene that points to the manage orders page  
	 * when a button is clicked.
	 * 
	 * @param event
	 * @throws IOException if the required FXML page cannot be loaded 
	 */
	public void goToOrderManager(ActionEvent event) throws IOException {

		Stage primaryStage = Main.getStage();
		Parent root = FXMLLoader.load(getClass().getResource("/application/ManageOrder.fxml"));
		Scene scene = new Scene(root, 900, 500);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	
	
	
	/**
	 * 
	 * Creates a new scene that points to the main login page
	 * when a button is clicked.
	 * 
	 * @param event
	 * @throws IOException if the required FXML page cannot be loaded 
	 */
	public void logout(ActionEvent event) throws IOException {

		Stage primaryStage = Main.getStage();
		Parent root = FXMLLoader.load(getClass().getResource("/application/Login.fxml"));
		Scene scene = new Scene(root, 900, 500);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();

	}

}
