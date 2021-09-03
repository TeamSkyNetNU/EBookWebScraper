package databaseProject;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.util.Duration;
import javafx.scene.layout.BorderPane;


public class MainSceneController implements Initializable {
	
	@FXML
	private BorderPane rootPane;
	
	@FXML
	private Menu dataMenuButton;
	
	@FXML
	private Menu marketMenuButton;
	
	@FXML
	private MenuItem loadDataStageButton;
	
	@FXML
	private MenuItem loadMarketStageButton;
	
	@FXML
	private MenuItem exitProgramButton;

	@FXML
	private MenuItem scrapeAmazonButton;

	@FXML
	private MenuItem scrapeEbayButton;

	@FXML
	private MenuItem scrapeBarnesButton;

	@FXML
	private MenuItem scrapeAmazonEbayButton;

	@FXML
	private MenuItem scrapeAmazonBarnesButton;

	@FXML
	private MenuItem scrapeEbayBarnesButton;

	@FXML
	private MenuItem scrapeAllButton;

	@FXML
	private Label messageLabel;
	
	@Override
    public void initialize(URL url, ResourceBundle rb) {
    	rootPane.setOpacity(0);
    	makeFadeInTransition();
    }
	
	private void makeFadeInTransition() {
    	FadeTransition fadeTransition = new FadeTransition();
    	fadeTransition.setDuration(Duration.millis(500));
    	fadeTransition.setNode(rootPane);
    	fadeTransition.setFromValue(0);
    	fadeTransition.setToValue(1);
    	fadeTransition.play();
    }

	
    @FXML
    private void loadMarketStage(ActionEvent event) {
    	FxmlLoader object = new FxmlLoader();
    	BorderPane view = object.getPage("MarketAnalysisScene");
    	rootPane.setCenter(view);
    }
	
    @FXML
    private void loadDataStage(ActionEvent event) {
    	FxmlLoader object = new FxmlLoader();
    	BorderPane view = object.getPage("ViewDatabaseScene");
    	rootPane.setCenter(view);
    }
    
    @FXML
    private void exitProgram(ActionEvent event) {
    	UserInterface.exit();
    }

	@FXML
	void scrapeAll(ActionEvent event) {
		runScraper();
	}

	@FXML
	void scrapeAmazon(ActionEvent event) {
		runScraper();
	}

	@FXML
	void scrapeAmazonBarnes(ActionEvent event) {
		runScraper();
	}

	@FXML
	void scrapeAmazonEbay(ActionEvent event) {
		runScraper();
	}

	@FXML
	void scrapeBarnes(ActionEvent event) {
		runScraper();
	}

	@FXML
	void scrapeEbay(ActionEvent event) {
		runScraper();
	}

	@FXML
	void scrapeEbayBarnes(ActionEvent event) {
		runScraper();
	}

	void runScraper() {
		messageLabel.setText("Scraping");
	}
}