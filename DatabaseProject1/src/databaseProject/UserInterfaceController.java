package databaseProject;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
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
import javafx.util.Duration;

public class UserInterfaceController implements Initializable {

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
    	//bookData.clear();
    	/*
		bookDisplayData = new BookDisplayData();
		bookList = (List<BookProperties>) bookDisplayData.displayBooks();
		for (BookProperties product : bookList) {
			bookData.add(String.format("Product:\n%s\n%s\n", product.getTitle(), 
		        		product.getFormattedPrice()));
		}
		*/
    	
    	tableView.setItems(getBookData());
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	//dataListView.setItems(bookData);
    	//rootPane.setOpacity(0);
    	//makeFadeInTransition();
    	
    	idColumn.setCellValueFactory(new PropertyValueFactory<BookProperties, Integer>("id"));
    	nameColumn.setCellValueFactory(new PropertyValueFactory<BookProperties, String>("title"));
    	priceColumn.setCellValueFactory(new PropertyValueFactory<BookProperties, String>("formattedPrice"));
    	
    	amazonRadioButton.setUserData(1);
    	ebayRadioButton.setUserData(2);
    	barnesRadioButton.setUserData(3);
    }
    
    /*
    private void makeFadeInTransition() {
    	FadeTransition fadeTransition = new FadeTransition();
    	fadeTransition.setDuration(Duration.millis(250));
    	fadeTransition.setNode(rootPane);
    	fadeTransition.setFromValue(0);
    	fadeTransition.setToValue(1);
    	fadeTransition.play();
    }
    */
    
    @FXML
    private void radioButtonSelected(ActionEvent e) {
    	UserInterface.selection = (int) websiteToggleGroup.getSelectedToggle().getUserData();
    }
    
    private ObservableList<BookProperties> getBookData() {
    	ObservableList<BookProperties> bookData = FXCollections.observableArrayList();
    	
    	String website = "";
		
		WebScraperDriver webScraper = new WebScraperDriver();
		List<BookProperties> products = webScraper.extractProducts(website);
	
	    for (BookProperties product : products) {
	    	bookData.add(product);
        }
    	
    	return bookData;
    }

}
