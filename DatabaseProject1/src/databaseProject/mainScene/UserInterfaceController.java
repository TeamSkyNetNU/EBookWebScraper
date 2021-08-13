package databaseProject.mainScene;

import databaseProject.BookDisplayData;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;

public class UserInterfaceController {

    @FXML
    private ListView<BookDisplayData> dataListView;

    @FXML
    private Button showDataButton;

    @FXML
    private CheckBox amazonCheckBox;

    @FXML
    private CheckBox ebayCheckBox;

    @FXML
    private CheckBox barnesCheckBox;

}
