package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * 
 * 
 * This class controls the manage menu page.
 * 
 * @author femi
 *
 */
public class ManageMenuController implements Initializable {
	
	@FXML private TextField txtItem, txtPrice;
	@FXML private TableView<Item> tvItems;
	@FXML private TableColumn<Item,String> itemName;
	@FXML private TableColumn<Item,Integer> itemPrice;
	@FXML private Button btnAdd;
	@FXML private Button btnDelete;
	
	// list containing all of the current items in the menu
	public ObservableList<Item> items = FXCollections.observableArrayList(Restaurant.getRestaurant().getAvailableItems());
	
	public void initialize(URL location, ResourceBundle resources) {
		
		
		tvItems.setItems(items);
		
		// assign values to the table columns
		itemName.setCellValueFactory(new PropertyValueFactory<Item, String>("name"));
		itemPrice.setCellValueFactory(new PropertyValueFactory<Item, Integer>("price"));
		
	}

	/**
	 * 
	 * Allows user to add a new item to the restaurant menu
	 * 
	 * @param event
	 * @throws Exception
	 */
	public void addNewItem(ActionEvent event) throws Exception {
		
		String txtPrice = this.txtPrice.getText();
		String txtItem  = this.txtItem.getText();
		if(txtPrice.matches("\\d+") && txtItem.matches("([a-zA-Z]+\\s?)+")) {
			int price = Integer.parseInt(txtPrice);
			Item item = new Item(txtItem,price);
			items.add(item);
			Restaurant.getRestaurant()
			          .addItem(item);
			
		}
		else {
			System.out.println("invalid input");
		}
	}
	
	
	/**
	 * 
	 * Allows user to delete item from restaurant menu
	 * 
	 * @param event
	 */
	public void deleteItem(ActionEvent event) {
		
		Item itemSelected = tvItems.getSelectionModel().getSelectedItem();
		items.remove(itemSelected);
		Restaurant.getRestaurant().deleteItem(itemSelected);
	}
	
	public void home(ActionEvent event) throws IOException {
		
		// go back to home screen 
		new SceneController().home();
		
	}

}
