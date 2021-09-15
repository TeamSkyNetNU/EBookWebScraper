package databaseProject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class MarketAnalysisSceneController implements Initializable {

    double amazonPrice;
    double ebayPrice;
    double barnesPrice ;
    double yourPrice;
    XYChart.Series series1 = new XYChart.Series<>();

    @FXML
    private BorderPane rootPane;

    @FXML
    private TextField bookSearchTextField;

    @FXML
    private Button bookSearchSubmitButton;
    
    @FXML
    private Label bookTitleLabel;

    @FXML
    private Label amazonPriceLabel;

    @FXML
    private Label ebayPriceLabel;

    @FXML
    private Label barnesPriceLabel;

    @FXML
    private Label yourPriceLabel;
    
    @FXML
    private BarChart<String,Number> barChart;
    
    @FXML
    private CategoryAxis xAxis;
    
    @FXML
    private NumberAxis yAxis;

    @FXML
    void compareBook(ActionEvent event) {
    	String bookSpecified = bookSearchTextField.getText();
    	bookTitleLabel.setText("Market Analysis for: " + bookSpecified);

        List<BookProperties> books;
    	BookMarketAnalysis bookDisplay = new BookMarketAnalysis();

    	try {
            books = bookDisplay.getLowestPrice(bookSpecified);
            updatePrices(books);
            updateChart();
        }
    	catch (Exception ex) {
    	    bookTitleLabel.setText(bookSpecified + " was not found on any website");
    	    clearPrices();
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	
    	amazonPrice = 0;
    	ebayPrice = 0;
    	barnesPrice = 0;
    	yourPrice = 0;
    	
    	barChart.setTitle("Price Analysis");
    	amazonPriceLabel.setText("$" + amazonPrice);
    	amazonPriceLabel.getStyleClass().clear();

    	ebayPriceLabel.setText("$" + ebayPrice);
    	ebayPriceLabel.getStyleClass().clear();

    	barnesPriceLabel.setText("$" + barnesPrice);
    	barnesPriceLabel.getStyleClass().clear();

    	yourPriceLabel.setText("$" + yourPrice);
    	series1.getData().add(new XYChart.Data("Amazon", amazonPrice));
    	series1.getData().add(new XYChart.Data("Ebay", ebayPrice));
    	series1.getData().add(new XYChart.Data("Barnes & Noble", barnesPrice));
    	series1.getData().add(new XYChart.Data("Your Price", yourPrice));
    	
    	barChart.getData().add(series1);
    }

    void updateChart() {
        series1.getData().clear();
        barChart.getData().clear();
        series1.getData().add(new XYChart.Data("Amazon", amazonPrice));
        series1.getData().add(new XYChart.Data("Ebay", ebayPrice));
        series1.getData().add(new XYChart.Data("Barnes & Noble", barnesPrice));
        series1.getData().add(new XYChart.Data("Our Price", yourPrice));

        barChart.getData().add(series1);
    }

    void updatePrices(List<BookProperties> books) {
        clearPrices();
    	for (BookProperties book : books) {
            if (book.getFormattedPrice() > 0) {
                switch (book.getSite()) {
                    case "amazon":
                        amazonPrice = book.getFormattedPrice();
                        amazonPriceLabel.setText("$ " + UserInterface.formatPrice(amazonPrice));
                        changeLabelColor(amazonPriceLabel, amazonPrice);
                        break;
                    case "barnesnoble":
                        barnesPrice = book.getFormattedPrice();
                        barnesPriceLabel.setText("$ " + UserInterface.formatPrice(barnesPrice));
                        changeLabelColor(barnesPriceLabel, barnesPrice);
                        break;
                    case "ebay":
                        ebayPrice = book.getFormattedPrice();
                        ebayPriceLabel.setText("$ " + UserInterface.formatPrice(ebayPrice));
                        changeLabelColor(ebayPriceLabel, ebayPrice);
                        break;
                    case "inventory":
                        yourPrice = book.getFormattedPrice();
                        yourPriceLabel.setText("$ " + UserInterface.formatPrice(yourPrice));
                        break;
                    default:
                        break;
                }
            }
        }
        changeLabelColor(amazonPriceLabel, amazonPrice);
        changeLabelColor(barnesPriceLabel, barnesPrice);
        changeLabelColor(ebayPriceLabel, ebayPrice);
    }

    void clearPrices() {
        amazonPrice = 0;
        ebayPrice = 0;
        barnesPrice = 0;
        yourPrice = 0;
        amazonPriceLabel.setText("");
        ebayPriceLabel.setText("");
        barnesPriceLabel.setText("");
        yourPriceLabel.setText("");
        amazonPriceLabel.getStyleClass().clear();
        ebayPriceLabel.getStyleClass().clear();
        barnesPriceLabel.getStyleClass().clear();
    }

    void changeLabelColor(Label label, double price) {
        if (price > yourPrice) {
            label.getStyleClass().add("greenLabel");
        } else if (price < yourPrice) {
            label.getStyleClass().add("redLabel");
        } else if (yourPrice == 0) {
            label.getStyleClass().clear();
        }
    }
}
