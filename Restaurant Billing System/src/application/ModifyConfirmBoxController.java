package application;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;


/**
 * 
 * This class controls the delete confirmation box page for the modify page
 * 
 * @author femi
 *
 */
public class ModifyConfirmBoxController  {
	
	/**
	 * Gets the selected order and deletes it from the platform.
	 * @param event
	 */
	public void deleteOrderConformation(ActionEvent event) throws IOException  {
				
		 Table selectedTable = Restaurant.getRestaurant()
		.getTable(Restaurant.getRestaurant()
		        .getSelectedTable());
		 selectedTable.setEmpty(true);
		 selectedTable.cancelOrder();
		 new SceneController().home();
		 Button button = ((Button)event.getSource());
		 button.getParent().getScene().getWindow().hide();
		 
	}
	
	/**
	 * 
	 * Closes the deletion confirmation box window
	 * 
	 * @param event
	 */
	public void cancel(ActionEvent event) {
		ModifyOrderController.getWindow().close();
	}
	
}