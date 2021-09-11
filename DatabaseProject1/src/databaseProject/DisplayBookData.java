package databaseProject;

import java.awt.print.Book;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
 * 
 *	This class contains the methods that allow the user to view book data.
 */
public class DisplayBookData 
{
	DatabaseDriver databaseDriver = new DatabaseDriver();

	public List<BookProperties> viewDB()
	{
		verifyTableViewed();
	
		return databaseDriver.queryDB();
	}
	
	private void verifyTableViewed()
	{
		if (UserInterface.selection == 1) {
			DatabaseQueryOperations.SQL_SELECT = DatabaseQueryOperations.SQL_SELECT_AMAZON;
		}
		if (UserInterface.selection == 2) {
			DatabaseQueryOperations.SQL_SELECT = DatabaseQueryOperations.SQL_SELECT_BARNES;
		}
		if (UserInterface.selection == 3) {
			DatabaseQueryOperations.SQL_SELECT = DatabaseQueryOperations.SQL_SELECT_EBAY;
		}
		if (UserInterface.selection == 8) {
			DatabaseQueryOperations.SQL_SELECT = DatabaseQueryOperations.SQL_SELECT_INVENTORY;
		}
	}
	
	public List<BookProperties> searchBook(String bookSpecified)
	{
		List<BookProperties> books = new ArrayList<>();
		boolean lowestPriceRequested = false;

		try
		{
			books = databaseDriver.queryBook(bookSpecified, lowestPriceRequested);
		} 
		catch (ClassNotFoundException | SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return books;
	}
}
