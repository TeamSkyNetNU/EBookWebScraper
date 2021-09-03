package databaseProject;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

public class ViewDatabaseSceneController implements Initializable {
	
	private enum WebsiteChoice {
		AMAZON(1),
		BARNES(2),
		EBAY(3),
		INVENTORY(4);
		
		private final int siteNum;

		WebsiteChoice(int siteNum) {
			this.siteNum = siteNum;
		}
		
		public int getSiteNum() {
			return siteNum;
		}
	}

    @FXML
    private BorderPane rootPane;

    @FXML
    private TableView<BookProperties> tableView;
    
    @FXML
    private TableColumn<BookProperties, Integer> idColumn;
    
    @FXML
    private TableColumn<BookProperties, String> nameColumn;
    
    @FXML
    private TableColumn<BookProperties, String> priceColumn;

    @FXML
    private Button showDataButton;
    
    @FXML
    private RadioButton amazonRadioButton;

    @FXML
    private RadioButton ebayRadioButton;

    @FXML
    private RadioButton barnesRadioButton;
    
    @FXML
    private ToggleGroup websiteToggleGroup;
    
    @FXML
    void showData(ActionEvent event) throws ClassNotFoundException, SQLException {
    	tableView.setItems(getBookData());
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	
    	idColumn.setCellValueFactory(new PropertyValueFactory<BookProperties, Integer>("id"));
    	nameColumn.setCellValueFactory(new PropertyValueFactory<BookProperties, String>("title"));
    	priceColumn.setCellValueFactory(new PropertyValueFactory<BookProperties, String>("formattedPrice"));
    	
    	amazonRadioButton.setUserData(WebsiteChoice.AMAZON);
    	ebayRadioButton.setUserData(WebsiteChoice.EBAY);
    	barnesRadioButton.setUserData(WebsiteChoice.BARNES);
    }
    
    @FXML
    private void radioButtonSelected(ActionEvent e) {
    	WebsiteChoice choice = (WebsiteChoice) websiteToggleGroup.getSelectedToggle().getUserData();
    	UserInterface.selection = choice.getSiteNum();
    }
    
    private ObservableList<BookProperties> getBookData() {
    	
    	ObservableList<BookProperties> bookData = FXCollections.observableArrayList();
    	
    	DisplayBookData displayBook = new DisplayBookData();
		
		List<BookProperties> products = displayBook.viewDB();
		
	    for (BookProperties product : products) {
	    	bookData.add(product);
        }
	    
    	return bookData;
    }
}
