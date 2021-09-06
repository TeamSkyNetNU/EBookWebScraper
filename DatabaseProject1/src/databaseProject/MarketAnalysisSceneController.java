package databaseProject;

import java.net.URL;
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

public class MarketAnalysisSceneController implements Initializable
{
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
	private BarChart<String, Number> barChart;

	@FXML
	private CategoryAxis xAxis;

	@FXML
	private NumberAxis yAxis;

	@FXML
	void compareBook(ActionEvent event)
	{
		String bookSpecified = bookSearchTextField.getText();
		bookTitleLabel.setText("Market Analysis for: " + bookSpecified);

		DisplayBookData bookDisplay = new DisplayBookData();
		bookDisplay.searchBook(bookSpecified);
	}

	@Override
	public void initialize(URL url, ResourceBundle rb)
	{
		/*
		 * xAxis = new CategoryAxis(); yAxis = new NumberAxis(); barChart = new
		 * BarChart<String,Number>(xAxis,yAxis);
		 */

		double amazonPrice = 0;
		double ebayPrice = 0;
		double barnesPrice = 0;
		double yourPrice = 0;

		barChart.setTitle("Price Analysis");
		amazonPriceLabel.setText("$" + amazonPrice);
		amazonPriceLabel.getStyleClass().clear();
		// amazonPriceLabel.getStyleClass().add("greenLabel");
		ebayPriceLabel.setText("$" + ebayPrice);
		ebayPriceLabel.getStyleClass().clear();
		// ebayPriceLabel.getStyleClass().add("greenLabel");
		barnesPriceLabel.setText("$" + barnesPrice);
		barnesPriceLabel.getStyleClass().clear();
		// barnesPriceLabel.getStyleClass().add("redLabel");
		yourPriceLabel.setText("$" + yourPrice);

		XYChart.Series series1 = new XYChart.Series<>();
		series1.getData().add(new XYChart.Data("Amazon", amazonPrice));
		series1.getData().add(new XYChart.Data("Ebay", ebayPrice));
		series1.getData().add(new XYChart.Data("Barnes & Noble", barnesPrice));
		series1.getData().add(new XYChart.Data("Our Price", yourPrice));

		barChart.getData().add(series1);
	}

	// TODO: complete function to update Chart
	void updateChart()
	{

	}

	// TODO: complete function to update Prices
	void updatePrices()
	{

	}
}
