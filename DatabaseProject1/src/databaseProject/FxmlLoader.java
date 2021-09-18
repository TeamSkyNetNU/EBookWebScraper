package databaseProject;

import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

/* This class allows the Main Scene Controller to switch between BorderPane
 * for the GUI
 */

public class FxmlLoader
{
	private BorderPane view;

	public BorderPane getPage(String fileName)
	{
		try
		{
			URL fileUrl = BookScraperMain.class.getResource("/" + fileName + ".fxml");
			if (fileUrl == null)
			{
				throw new java.io.FileNotFoundException("FXML file can't be found");
			}

			new FXMLLoader();
			view = FXMLLoader.load(fileUrl);
		} catch (Exception e)
		{
			System.out.println("No page " + fileName + " please check FxmlLoader.");
		}

		return view;
	}
}
