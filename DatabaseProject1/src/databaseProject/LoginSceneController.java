package databaseProject;

import java.io.IOException;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LoginSceneController {
	
    @FXML
    private BorderPane rootPane;

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private Button loginButton;
    
    @FXML
    private Button exitButton;
    
    @FXML
    private Label systemMessageLabel;
    
    private void makeFadeOut() {
    	FadeTransition fadeTransition = new FadeTransition();
    	fadeTransition.setDuration(Duration.millis(500));
    	fadeTransition.setNode(rootPane);
    	fadeTransition.setFromValue(1);
    	fadeTransition.setToValue(0);
    	
    	// ActionEvent to load the next Scene after the fade animation is finished
    	fadeTransition.setOnFinished((ActionEvent event) -> {
    		loadNextScene();
    	});
    	fadeTransition.play();
    }
    
    private void loadNextScene() {
    	try {
	    	Parent secondView;
	    	secondView = (BorderPane) FXMLLoader.load(getClass().getResource("/MainScene.fxml"));
	    	Scene newScene = new Scene(secondView);
	    	Stage curStage = (Stage) rootPane.getScene().getWindow();
	    	curStage.setScene(newScene);
    	}
    	catch (IOException ex) {
    		System.out.println(ex);
    	}
    }
    
    @FXML
    private void exitApplication(ActionEvent event) {
    	UserInterface.exit();
    }
    
    @FXML
    void verifyLogin(ActionEvent event) {
    	if (UserInterface.verifyUser(usernameTextField.getText(), passwordTextField.getText())) {
    		// Change message text color to black
    		systemMessageLabel.setTextFill(Color.color(0, 0, 0));
    		systemMessageLabel.setText("Login Successful");
    		makeFadeOut();
    	} else {
    		// Change message text color to red
    		systemMessageLabel.setTextFill(Color.color(1, 0.2, 0.2));
    		systemMessageLabel.setText("Incorrect credentials. Please try again.");
    	}
    }
}
