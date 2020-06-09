package application;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.print.PrinterJob;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;


/**
 * 
 * This class controls the delete confirmation box page for the modify page
 * 
 * @author Abhinav Chauhan
 *
 */
public class FinishOrderConfirmationController {
	
	/**
	 * Gets the selected order and deletes it from the platform.
	 * @param event
	 */
	
	public void finishOrderConformation(ActionEvent event) throws IOException  {
		
		Restaurant.getRestaurant().finishOrder();
		 new SceneController().home();
		 Button button = ((Button)event.getSource());
		 button.getParent().getScene().getWindow().hide();
		/*PrinterJob printerJob = PrinterJob.createPrinterJob();
		Button button = (Button)event.getSource();
		if(printerJob.showPrintDialog(button.getParent().getScene().getWindow())) {
		
		}*/
		 
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
