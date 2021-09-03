package databaseProject;

import java.sql.SQLException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/*
 * 
 * 	This class contains the main method that runs the Ebook Webscraper.
 */

public class BookScraperMain extends Application {
	
	@Override
	public void start(Stage primaryStage) throws Exception{
		Parent root = FXMLLoader.load(getClass().getResource("/LoginScene.fxml"));
		primaryStage.setTitle("Ebook WebScraper");
		primaryStage.setScene(new Scene(root, 600, 400));
		primaryStage.show();
	}
	
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
    	launch(args);
    }
}

/*
public class BookScraperMain
{
	public static void main(String[] args) 
	{
    	UserInterface userInterface = new UserInterface();
    	DisplayBookData DisplayBookData = new DisplayBookData();
		BookMarketAnalysis bookMarketAnalysis = new BookMarketAnalysis();

    	userInterface.run();
    	DisplayBookData.beginDisplay();
		bookMarketAnalysis.beginMarketAnalysis();
	}
}
*/