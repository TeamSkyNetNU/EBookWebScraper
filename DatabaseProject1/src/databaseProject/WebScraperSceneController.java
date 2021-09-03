package databaseProject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class WebScraperSceneController implements Initializable {

    @FXML
    private CheckBox amazonCheckbox;

    @FXML
    private CheckBox ebayCheckbox;

    @FXML
    private CheckBox barnesCheckbox;

    @FXML
    private Button beginScrapingButton;

    @FXML
    private DatabaseDriver databaseDriver;

    @FXML
    private ListView<String> listView;

    private ObservableList<String> systemMessages =
            FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        databaseDriver = new DatabaseDriver();
        listView.setItems(systemMessages);
    }

    @FXML
    void beginScraping(ActionEvent event) {
        if (amazonCheckbox.isSelected() && !ebayCheckbox.isSelected() && !barnesCheckbox.isSelected()) {
            UserInterface.selection = 1;
        } else if (barnesCheckbox.isSelected() && !amazonCheckbox.isSelected() && !ebayCheckbox.isSelected()) {
            UserInterface.selection = 2;
        } else if (ebayCheckbox.isSelected() && !amazonCheckbox.isSelected() && !barnesCheckbox.isSelected()) {
            UserInterface.selection = 3;
        } else if (amazonCheckbox.isSelected() && barnesCheckbox.isSelected() && !ebayCheckbox.isSelected()) {
            UserInterface.selection = 4;
        } else if (amazonCheckbox.isSelected() && !barnesCheckbox.isSelected() && ebayCheckbox.isSelected()) {
            UserInterface.selection = 5;
        } else if (!amazonCheckbox.isSelected() && barnesCheckbox.isSelected() && ebayCheckbox.isSelected()) {
            UserInterface.selection = 6;
        } else if (amazonCheckbox.isSelected() && barnesCheckbox.isSelected() && ebayCheckbox.isSelected()) {
            UserInterface.selection = 7;
        }
        System.out.println("UserInterface.selection = " + UserInterface.selection);

        databaseDriver.getBookProducts();
    }
}
