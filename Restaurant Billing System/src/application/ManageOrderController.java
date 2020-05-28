package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.ResourceBundle;



import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * 
 * This class controls the manage order page.
 * 
 * @author femi
 *
 */
public class ManageOrderController implements Initializable  {

	private static Stage window;
	@FXML private TableView<Order> tvOrderTable;
	@FXML private TableColumn<Order, Integer> id;
	@FXML private TableColumn<Order, Integer> tableNumber;
	@FXML private TableColumn<Order, String> date;
	@FXML private TableColumn<Order, String> orderTotal;
	@FXML private TableColumn<Order, String> itemsOrdered;
	@FXML private Button delete;
	@FXML private Button modify;
	@FXML private TextField filterField;
	@FXML private ComboBox<String> month;
	@FXML private ComboBox<String> year;
	
	private enum Months{janurary,february,march,april,may,june,july,august,septemeber,october,november,december};
	
	
	// List of all of the orders that are in the platform 
	public ObservableList<Order> orders = FXCollections.observableArrayList(Restaurant.getRestaurant().getHistory(LocalDate.now().getMonthValue(),LocalDate.now().getYear()));

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		id.setCellValueFactory(new PropertyValueFactory<Order, Integer>("id"));
		tableNumber.setCellValueFactory(new PropertyValueFactory<Order, Integer>("tableNumber"));
		date.setCellValueFactory(new PropertyValueFactory<Order, String>("date"));
		orderTotal.setCellValueFactory(new PropertyValueFactory<Order, String>("total"));
		itemsOrdered.setCellValueFactory(new PropertyValueFactory<Order, String>("items"));
		tvOrderTable.setItems(orders);
		
		ObservableList<String> years = FXCollections.observableArrayList();
		for(int year = 2020 ; year <= LocalDate.now().getYear() ; ++year ) {
			
			years.add(String.valueOf(year));
		}
		year.setItems(years);
		
		ObservableList<String> months = FXCollections.observableArrayList();
		for(int month = 0 ; month < LocalDate.now().getMonthValue() ; ++month) {
		   
			months.add(Month.values()[month].toString().toLowerCase());
		}
		
		month.setItems(months);
		
	}
	
	/**
	 * 
	 * Takes the user to the delete confirmation window.
	 * 
	 * @param event
	 * @throws IOException if the FXML page cannot be loaded 
	 */
	public void filter(ActionEvent event) {
		
		String month = this.month.getSelectionModel().getSelectedItem();
		String year = this.year.getSelectionModel().getSelectedItem();
		int monthValue;
		int yearValue;
		if(month == null) {
			monthValue = LocalDate.now().getMonthValue();
		}
		else {
			monthValue = Months.valueOf(month).ordinal() + 1;
		}
		if(year == null) {
			yearValue = LocalDate.now().getYear();
		}
		else {
			yearValue = Integer.parseInt(year);
		}
		
		List<Order> filteredOrders = Restaurant.getRestaurant()
		                             .getHistory(monthValue,yearValue);
		
		orders = FXCollections.observableArrayList(filteredOrders);
		tvOrderTable.setItems(orders);
		
		
	}
	
	public void searchOrder(ActionEvent event) {
		
		TextField searchField = ((TextField)event.getSource());
		String txtId = searchField.getText();
		if(!txtId.matches("\\d+")) {
			searchField.setText("invalid entry");
		}
		else {
			int id = Integer.parseInt(txtId);
			List<Order> order = Restaurant.getRestaurant().searchOrder(id);
			orders = FXCollections.observableArrayList(order);
			tvOrderTable.setItems(orders);
		}
		
	}

	/**
	 * Gets the window of the delete confirmation box.
	 * @return the window of the delete confirmation box
	 */
	public static Stage getWindow() {
		return window;
	}
	/**
	 * 
	 * Returns the user to the homepage.
	 * 
	 * @param event
	 * @throws IOException if the FXML page cannot be loaded
	 */
	public void Home(ActionEvent event) throws IOException {
		
		// go to homepage 
	   new SceneController().home();
	}

}
