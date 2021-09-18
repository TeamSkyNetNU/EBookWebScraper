package databaseProject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.*;

public class WebScraperSceneController implements Initializable
{

	@FXML
	private CheckBox amazonCheckbox;

	@FXML
	private CheckBox ebayCheckbox;

	@FXML
	private CheckBox barnesCheckbox;

	@FXML
	private Button beginScrapingButton;

	@FXML
	private Button scrapeIntervalButton;

	@FXML
	private Button cancelScrapeIntervalButton;

	@FXML
	private TextField setHoursTextField;

	@FXML
	private TextField setDaysTextField;

	@FXML
	private ListView<String> listView;

	private static ObservableList<String> systemMessages = FXCollections.observableArrayList();

	@SuppressWarnings("unused")
	private boolean intervalToggle = true;

	ScraperScheduledService service1;

	@Override
	public void initialize(URL url, ResourceBundle rb)
	{
		listView.setItems(systemMessages);
		cancelScrapeIntervalButton.setDisable(true);
	}

	@FXML
	void beginScraping(ActionEvent event)
	{

		setUserSelection();

		WebScraperTask webScraperTask = new WebScraperTask();

		webScraperTask.valueProperty().addListener((observable, oldMessage, message) ->
		{
			systemMessages.add(message);
			listView.scrollTo(listView.getItems().size());
		});

		webScraperTask.setOnRunning((succeededEvent) ->
		{
			beginScrapingButton.setDisable(true);
		});

		webScraperTask.setOnSucceeded((succeededEvent) ->
		{
			beginScrapingButton.setDisable(false);
		});

		ExecutorService executorService = Executors.newFixedThreadPool(1);
		executorService.execute(webScraperTask);
		executorService.shutdown();
	}

	@FXML
	void startScrapeInterval(ActionEvent event)
	{

		setUserSelection();

		try
		{
			long hours = Long.parseLong(setHoursTextField.getText());
			long days = Long.parseLong(setDaysTextField.getText());

			systemMessages.add("Now Scraping websites every " + hours + " hour(s) for " + days + " days");

			scrapeIntervalButton.setDisable(true);
			cancelScrapeIntervalButton.setDisable(false);

			service1 = new ScraperScheduledService();

			service1.setPeriod(Duration.hours(hours));

			service1.start();

			service1.valueProperty().addListener((observable, oldMessage, message) ->
			{
				if (message != null)
				{
					systemMessages.add(message);
					listView.scrollTo(listView.getItems().size());
				}
			});
		} catch (NumberFormatException ex)
		{
			systemMessages.add("Only numbers are accepted for Scrape Interval");
		}

		listView.scrollTo(listView.getItems().size());
	}

	@FXML
	void cancelScrapeInterval(ActionEvent event)
	{
		if (service1.isRunning())
		{
			scrapeIntervalButton.setDisable(false);
			cancelScrapeIntervalButton.setDisable(true);
			service1.cancel();
			systemMessages.add("Stopped hourly scraping");
		}
	}

	void setUserSelection()
	{
		if (amazonCheckbox.isSelected() && !ebayCheckbox.isSelected() && !barnesCheckbox.isSelected())
		{
			UserInterface.selection = 1;
		} else if (barnesCheckbox.isSelected() && !amazonCheckbox.isSelected() && !ebayCheckbox.isSelected())
		{
			UserInterface.selection = 2;
		} else if (ebayCheckbox.isSelected() && !amazonCheckbox.isSelected() && !barnesCheckbox.isSelected())
		{
			UserInterface.selection = 3;
		} else if (amazonCheckbox.isSelected() && barnesCheckbox.isSelected() && !ebayCheckbox.isSelected())
		{
			UserInterface.selection = 4;
		} else if (amazonCheckbox.isSelected() && !barnesCheckbox.isSelected() && ebayCheckbox.isSelected())
		{
			UserInterface.selection = 5;
		} else if (!amazonCheckbox.isSelected() && barnesCheckbox.isSelected() && ebayCheckbox.isSelected())
		{
			UserInterface.selection = 6;
		} else if (amazonCheckbox.isSelected() && barnesCheckbox.isSelected() && ebayCheckbox.isSelected())
		{
			UserInterface.selection = 7;
		}
	}

	private static class ScraperScheduledService extends ScheduledService<String>
	{

		@Override
		protected Task<String> createTask()
		{
			return new WebScraperTask();
		}
	}
}
