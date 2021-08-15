package databaseProject;

import java.io.IOException;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LoginSceneController {
	
    @FXML
    private BorderPane rootPane;

    @FXML
    private TextField usernameTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private Button loginButton;

    @FXML
    void switchScenes(ActionEvent event) {
    	makeFadeOut();
    }
    
    private void makeFadeOut() {
    	FadeTransition fadeTransition = new FadeTransition();
    	fadeTransition.setDuration(Duration.millis(1000));
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
	    	secondView = (BorderPane) FXMLLoader.load(getClass().getResource("/UserInterface.fxml"));
	    	Scene newScene = new Scene(secondView);
	    	Stage curStage = (Stage) rootPane.getScene().getWindow();
	    	curStage.setScene(newScene);
    	}
    	catch (IOException ex) {
    		System.out.println(ex);
    	}
    }

}
