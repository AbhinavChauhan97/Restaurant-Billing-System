package application;
import javafx.scene.paint.Color;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;


/**
 * This class controls the login page of the application.
 * The methods are used to verify user credentials.
 * 
 * @author femi
 *
 */
public class LoginController {

	@FXML private Text lblStatus;
	@FXML private TextField txtUsername, txtPassword;
	//@FXML private TextField txtFirstname, txtLastname, txtNewPassword, txtNewUsername, txtType;
   
	/**
	 * 
	 * Checks to see employee's credentials are correct and alerts the user if they are not.
	 * 
	 * @param event
	 * @throws Exception if user is not found
	 */
	public void Login(ActionEvent event) throws Exception {
		
		String username = txtUsername.getText();
		String password = txtPassword.getText();
		if(username.equals("username") && password.equals("password")) {
			new SceneController().home();
		}
		else {
			
			//lblStatus.setStyle("-fx-color: #FF1234");
			lblStatus.setFill(Color.RED);
			lblStatus.setVisible(true);
			lblStatus.setText("incorrect credentials");
			
		}
		
	}
		

}
