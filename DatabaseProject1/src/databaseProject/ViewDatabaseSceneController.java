package databaseProject;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

public class ViewDatabaseSceneController implements Initializable {
	
	private enum WebsiteChoice {
		AMAZON(1),
		BARNES(2),
		EBAY(3),
		INVENTORY(8);
		
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
    private RadioButton inventoryRadioButton;
    
    @FXML
    private ToggleGroup websiteToggleGroup;

    @FXML
    private TextField searchBookTextField;

    @FXML
    private Label searchResultLabel;

    @FXML
    private Label amazonSearchLabel;

    @FXML
    private Label ebaySearchLabel;

    @FXML
    private Label barnesSearchLabel;

    @FXML
    private Label inventorySearchLabel;

    @FXML
    private Label amazonSearchPriceLabel;

    @FXML
    private Label ebaySearchPriceLabel;

    @FXML
    private Label barnesSearchPriceLabel;

    @FXML
    private Label inventorySearchPriceLabel;

    ObservableList<BookProperties> bookData = FXCollections.observableArrayList();
    DisplayBookData displayBook;


    @FXML
    void showData(ActionEvent event) throws ClassNotFoundException, SQLException {
        bookData.clear();
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
    	inventoryRadioButton.setUserData(WebsiteChoice.INVENTORY);

        displayBook = new DisplayBookData();
    }
    
    @FXML
    private void radioButtonSelected(ActionEvent e) {
    	WebsiteChoice choice = (WebsiteChoice) websiteToggleGroup.getSelectedToggle().getUserData();
    	UserInterface.selection = choice.getSiteNum();
    }
    
    private ObservableList<BookProperties> getBookData() {
		
		List<BookProperties> products = displayBook.viewDB();
		
	    for (BookProperties product : products) {
	    	bookData.add(product);
        }
	    
    	return bookData;
    }

    @FXML
    void searchBook(ActionEvent event) {
        clearSearchLabelText();
        List<BookProperties> searchResults = new ArrayList<>();

        boolean atLeastOneResult = false;

        searchResultLabel.setText(searchBookTextField.getText());
        searchResults = displayBook.searchBook(searchBookTextField.getText());

        for (BookProperties searchResult : searchResults) {
            if (searchResult.getTitle() != null) {
                atLeastOneResult = true;
                changeSearchLabelText(searchResult);
            }
        }

        if (!atLeastOneResult) {
            searchResultLabel.setText(searchBookTextField.getText() + " was not found");
        }
    }

    void changeSearchLabelText(BookProperties searchResult) {
        switch (searchResult.getSite()) {
            case ("amazon"):
                amazonSearchLabel.setText("Amazon's Price:");
                amazonSearchPriceLabel.setText("$" + searchResult.getFormattedPrice());
                break;
            case ("ebay"):
                ebaySearchLabel.setText("Ebay's Price:");
                ebaySearchPriceLabel.setText("$" + searchResult.getFormattedPrice());
                break;
            case ("barnesnoble"):
                barnesSearchLabel.setText("Barnes & Noble's Price:");
                barnesSearchPriceLabel.setText("$" + searchResult.getFormattedPrice());
                break;
            case ("inventory"):
                inventorySearchLabel.setText("Your Price:");
                inventorySearchPriceLabel.setText("$" + searchResult.getFormattedPrice());
                break;
            default:
                break;
        }
    }

    void clearSearchLabelText() {
        amazonSearchLabel.setText("");
        amazonSearchPriceLabel.setText("");
        ebaySearchLabel.setText("");
        ebaySearchPriceLabel.setText("");
        barnesSearchLabel.setText("");
        barnesSearchPriceLabel.setText("");
        inventorySearchLabel.setText("");
        inventorySearchPriceLabel.setText("");
    }
}
