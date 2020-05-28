package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Pair;


/**
 * 
 * This class controls the new order page.
 * @author femi
 *
 */
public class NewOrderController implements Initializable {
	
	@FXML private Label total;
	@FXML private ComboBox<String> cbItems, cbTables;
	@FXML private TextField txtQuantity;
	@FXML private Button btnOrder;
	@FXML private Button btnAdd;
	@FXML private TableView<Item> orderTable;
	@FXML private TableColumn<Item, String> quantityColumn;
	@FXML private TableColumn<Item, String> itemColumn;
	@FXML private TableColumn<Item, Integer> priceColumn;
	@FXML private TextArea txtComments;
  
	
	private Order order;
	public ObservableList<Item> itemList = FXCollections.observableArrayList();
	public int tableNumber;
	public int subTotal = 0;
	ObservableList<String> dropdownList;
	ObservableList<String> dropdownTables;
	List<Item> orderedItems;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		dropdownTables = FXCollections.observableArrayList(Restaurant.getRestaurant().getTableNumbers());
		dropdownList = FXCollections.observableArrayList(Restaurant.getRestaurant().getAvailableItemNames());
		orderedItems = new LinkedList<>();
		txtQuantity.setText("1");
		quantityColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("servings"));
		priceColumn.setCellValueFactory(new PropertyValueFactory<Item, Integer>("price"));
		itemColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("name"));
		cbItems.setItems(dropdownList);
		cbTables.setItems(dropdownTables);
		cbTables.getSelectionModel().selectFirst();
		cbItems.getSelectionModel().selectFirst();
	}
	
	
	public void makeOrder(ActionEvent event) throws IOException {
		
		
	    order = new Order(Integer.parseInt(cbTables.getSelectionModel().getSelectedItem()));
	    System.out.println("ordered item" + orderedItems);
	    order.addItems(orderedItems);
	    order.setTotal(subTotal);
	    
	    Restaurant.getRestaurant()
	    .getAvailableTables()
	    .get(order.getTableNumber() - 1)
	    .setEmpty(false);
	    
	    Restaurant.getRestaurant()
	    .getAvailableTables()
	    .get(order.getTableNumber() - 1)
	    .setOrder(order);
		
	    new SceneController().home();
	
	}
	
	public void changeCombo(ActionEvent event) {
		//btnOrder.setDisable(false);
		//order = new Order(Integer.parseInt(cbTables.getSelectionModel().getSelectedItem()));
	}
	
	public void addItem(ActionEvent event) {
		
		String quantityText = txtQuantity.getText();
		int quantity = 1;
		if(quantityText.matches("\\d+")) {
		 quantity = Integer.parseInt(quantityText);
		}
		else {
			txtQuantity.setText("Invalid Quantity");
			return;
		}
		
		Item item = Restaurant.getRestaurant()
				.getAvailableItems()
				.get(cbItems.getSelectionModel()
				.getSelectedIndex());
		
		item.setServings(quantity);
		
		orderedItems.add(item);
		subTotal += item.getPrice() * quantity;
		total.setText("" + subTotal + ".00");
		orderTable.getItems().add(item);
		
		btnOrder.setDisable(false);
	}
	
public void Home(ActionEvent event) throws IOException {
		
	    new SceneController().home();
		
	}
}