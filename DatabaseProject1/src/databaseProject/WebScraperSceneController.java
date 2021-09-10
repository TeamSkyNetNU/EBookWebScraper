package databaseProject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.ScheduledService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    private Button scrapeIntervalButton;

    @FXML
    private TextField setHoursTextField;

    @FXML
    private TextField setDaysTextField;

    @FXML
    private ListView<String> listView;

    private static ObservableList<String> systemMessages =
            FXCollections.observableArrayList();

    private boolean intervalToggle = true;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listView.setItems(systemMessages);
        //scrapeIntervalButton.setDisable(true);
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

        WebScraperTask webScraperTask = new WebScraperTask();

        webScraperTask.valueProperty().addListener((observable, oldMessage, message) -> {
            systemMessages.add(message);
            listView.scrollTo(listView.getItems().size());
        });

        webScraperTask.setOnRunning((succeededEvent) -> {
            beginScrapingButton.setDisable(true);
        });

        webScraperTask.setOnSucceeded((succeededEvent) -> {
            beginScrapingButton.setDisable(false);
        });

        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.execute(webScraperTask);
        executorService.shutdown();
    }

    @FXML
    void toggleScrapeInterval(ActionEvent event) {
        if (intervalToggle) {
            UserInterface.selection = 7;
            try {
                DatabaseDriver.hours = Long.parseLong(setHoursTextField.getText());
                DatabaseDriver.days = Long.parseLong(setDaysTextField.getText());
                systemMessages.add("Now Scraping websites every "
                        + DatabaseDriver.hours + " hour(s) for "
                        + DatabaseDriver.days + " days");
                scrapeIntervalButton.setText("Cancel Interval Scraping");
                scrapeIntervalButton.getStyleClass().clear();
                scrapeIntervalButton.getStyleClass().add("intervalToggleFalse");
                DatabaseDriver.setUserIntervalForExtraction();
                intervalToggle = !intervalToggle;
            }
            catch (NumberFormatException ex) {
                systemMessages.add("Only numbers are accepted for Scrape Interval");
            }
        }
        else {
            scrapeIntervalButton.setText("Scrape at an Interval");
            scrapeIntervalButton.getStyleClass().clear();
            scrapeIntervalButton.getStyleClass().add("intervalToggleTrue");
            DatabaseDriver.stopInterval();
            systemMessages.add("Stopped hourly scraping");
            intervalToggle = !intervalToggle;
        }

        listView.scrollTo(listView.getItems().size());
    }
}
