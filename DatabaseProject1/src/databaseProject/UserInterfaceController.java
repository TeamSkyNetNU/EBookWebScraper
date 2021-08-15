package databaseProject;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

public class UserInterfaceController implements Initializable {

    @FXML
    private BorderPane rootPane;

    @FXML
    private ListView<?> dataListView;

    @FXML
    private Button showDataButton;

    @FXML
    private CheckBox amazonCheckBox;

    @FXML
    private CheckBox ebayCheckBox;

    @FXML
    private CheckBox barnesCheckBox;
    
    @FXML
    void showData(ActionEvent event) throws ClassNotFoundException {
    	UserInterface ui = new UserInterface();
    	try {
    		ui.beginProgram();
    	}
    	catch (SQLException ex) {
    		
    	}
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	//rootPane.setOpacity(0);
    	makeFadeInTransition();
    }
    
    private void makeFadeInTransition() {
    	FadeTransition fadeTransition = new FadeTransition();
    	fadeTransition.setDuration(Duration.millis(250));
    	fadeTransition.setNode(rootPane);
    	fadeTransition.setFromValue(0);
    	fadeTransition.setToValue(1);
    	fadeTransition.play();
    }

}
