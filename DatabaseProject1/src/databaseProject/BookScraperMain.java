package databaseProject;

import java.sql.SQLException;

/*
 * 
 * 	This class contains the main method that runs the Ebook Webscraper.
 */
public class BookScraperMain 
{
    public static void main(String[] args) throws ClassNotFoundException, SQLException
    {
    	UserInterface userInterface = new UserInterface();
    	BookDisplayData displayBookData = new BookDisplayData();

    	userInterface.beginProgram();
    	displayBookData.displayBooks();
    }
}
