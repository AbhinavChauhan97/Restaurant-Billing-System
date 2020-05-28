package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;

/**
 * 
 * This class controls the modify order page.
 * @author femi
 *
 */
public class ModifyOrderController implements Initializable {
	
	private static Stage window;
	private Order order;

	@FXML private Label lblOrderNumber, lblTotal;
	@FXML private TableView<Item> tvItemTable;
	@FXML private TableColumn<Item, String> item;
	@FXML private TableColumn<Item, Integer> price;
	@FXML private TableColumn<Item, String> quantity;
	
	@FXML private ComboBox<String> cbItems;
	@FXML private TextField txtQuantity;
	
	public HashMap<String, Integer> orderList2 = new HashMap<String, Integer>();
	
	public ObservableList<Item> itemList;
	
	ObservableList<String> dropdownList ;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		order = Restaurant.getRestaurant()
				.getTable(Restaurant.getRestaurant().getSelectedTable())
				.getOrder();
		
		List<Item> items = new LinkedList<>();
		for(Item item : order.getItemsList()) {
			items.add(item);
		}
		
		itemList = FXCollections.observableArrayList(items);
		dropdownList = FXCollections.observableArrayList(Restaurant.getRestaurant().getAvailableItemNames());
		
		//lblOrderNumber.setVisible(true);
	
		lblTotal.setText("Rs " + order.getTotal() + ".00" );
		
		tvItemTable.setItems(itemList);
		
		
		
		item.setCellValueFactory(new PropertyValueFactory<Item, String>("name"));
		price.setCellValueFactory(new PropertyValueFactory<Item, Integer>("price"));
		quantity.setCellValueFactory(new PropertyValueFactory<Item, String>("servings"));
		
		cbItems.setItems(dropdownList);
		txtQuantity.setText("1");
		cbItems.getSelectionModel().selectFirst();
		
		System.out.println("initialized modifiy order");
	}
	
	public void deleteItem(ActionEvent event) {
		
		ObservableList<Item> allItems = tvItemTable.getItems(); 
		Item itemSelected = tvItemTable.getSelectionModel().getSelectedItem();
		allItems.remove(itemSelected);
		order.removeItem(itemSelected);
		lblTotal.setText("Rs" + order.getTotal() + ".00" );
	}
	
	public void Home(ActionEvent event) throws IOException {
		
		// Apply changes to comments and special messages
		
		
		// go to homepage 
		new SceneController().home();
	
	}
	
	/**
	 * 
	 * Takes the user to the delete confirmation modal window
	 * 
	 * @param event
	 * @throws IOException if the FXML page cannot be loaded 
	 */
	public void deleteConformation(ActionEvent event) throws IOException {

		window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		Parent root = FXMLLoader.load(getClass().getResource("/application/ModifyConfirmBox.fxml"));
		Scene scene = new Scene(root, 300, 200);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		window.setScene(scene);
		window.show();
	}
	
	/**
	 * 
	 * Gets the window for the delete confirmation box.
	 * 
	 * @return the delete confirmation window 
	 */
	public static Stage getWindow() {
		return window;	
	}
	
	
	/**
	 * 
	 * Allows user to add an item to their existing order.
	 * 
	 * @param event
	 */
	public void addItem(ActionEvent event) {
		
		int quantity = Integer.parseInt(txtQuantity.getText()); 
		Item item = Restaurant.getRestaurant()
				.getAvailableItems()
				.get(cbItems.getSelectionModel().getSelectedIndex());
		if(isItemAlreadyExist(item)) {
			order.removeItem(item);
			tvItemTable.getItems().remove(item);
			int serving = item.getServings();
			item.setServings(quantity + serving);
		}
		else {
		item.setServings(quantity);
		}
		order.addItem(item);
		order.setTotal(order.getTotal() + item.getPrice() * quantity);
		lblTotal.setText("Rs " + order.getTotal() + ".00");
		tvItemTable.getItems().add(item);
	}
	
	public void showFinishOrderDialog(ActionEvent event) throws IOException {
		window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		Parent root = FXMLLoader.load(getClass().getResource("/application/FinishOrderDialog.fxml"));
		Scene scene = new Scene(root, 300, 200);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		window.setScene(scene);
		window.show();
	}
	private boolean isItemAlreadyExist(Item item) {
		for(Item i : itemList) {
			if(i.getName().equals(item.getName())) {
				return true;
			}
		}
		return false;
	}
	
	
}
