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
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

public class MainSceneController implements Initializable
{

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
	private MenuItem loadScraperStageButton;

	@FXML
	private MenuItem exitProgramButton;

	@FXML
	private Label messageLabel;

	private BorderPane viewDatabaseScene;
	private BorderPane marketAnalysisScene;
	private BorderPane webScraperScene;

	@Override
	public void initialize(URL url, ResourceBundle rb)
	{
		rootPane.setOpacity(0);
		makeFadeInTransition();

		FxmlLoader object = new FxmlLoader();
		viewDatabaseScene = object.getPage("ViewDatabaseScene");
		viewDatabaseScene.setVisible(false);

		marketAnalysisScene = object.getPage("MarketAnalysisScene");
		marketAnalysisScene.setVisible(false);

		webScraperScene = object.getPage("WebScraperScene");
		webScraperScene.setVisible(false);
	}

	private void makeFadeInTransition()
	{
		FadeTransition fadeTransition = new FadeTransition();
		fadeTransition.setDuration(Duration.millis(500));
		fadeTransition.setNode(rootPane);
		fadeTransition.setFromValue(0);
		fadeTransition.setToValue(1);
		fadeTransition.play();
	}

	@FXML
	private void loadMarketStage(ActionEvent event)
	{
		if (!marketAnalysisScene.isVisible())
		{
			webScraperScene.setVisible(false);
			viewDatabaseScene.setVisible(false);
			rootPane.setCenter(marketAnalysisScene);
			marketAnalysisScene.setVisible(true);
		}
	}

	@FXML
	private void loadDataStage(ActionEvent event)
	{
		if (!viewDatabaseScene.isVisible())
		{
			webScraperScene.setVisible(false);
			marketAnalysisScene.setVisible(false);
			rootPane.setCenter(viewDatabaseScene);
			viewDatabaseScene.setVisible(true);
		}
	}

	@FXML
	private void loadScraperStage(ActionEvent event)
	{
		if (!webScraperScene.isVisible())
		{
			marketAnalysisScene.setVisible(false);
			viewDatabaseScene.setVisible(false);
			rootPane.setCenter(webScraperScene);
			webScraperScene.setVisible(true);
		}
	}

	@FXML
	private void exitProgram(ActionEvent event)
	{
		UserInterface.exit();
	}
}