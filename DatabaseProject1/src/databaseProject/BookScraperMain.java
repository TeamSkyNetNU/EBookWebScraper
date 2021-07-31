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
    	BookDisplayData displayBookData = new BookDisplayData();
    	DatabaseDriver databaseDriver = new DatabaseDriver();

    	displayBookData.displayBooks();
        databaseDriver.createConnection();
    }
}
