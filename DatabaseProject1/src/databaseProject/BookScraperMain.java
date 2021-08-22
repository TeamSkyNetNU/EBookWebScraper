package databaseProject;

/*
 * 
 * 	This class contains the main method that runs the Ebook Webscraper.
 */
public class BookScraperMain
{
	public static void main(String[] args) 
	{
    	UserInterface userInterface = new UserInterface();
    	DisplayBookData DisplayBookData = new DisplayBookData();
		BookMarketAnalysis bookMarketAnalysis = new BookMarketAnalysis();

    	userInterface.run();
//    	DisplayBookData.beginDisplay();
//		bookMarketAnalysis.beginMarketAnalysis();
	}
}
