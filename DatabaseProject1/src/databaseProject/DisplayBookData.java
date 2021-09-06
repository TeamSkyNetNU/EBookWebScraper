package databaseProject;

import java.sql.SQLException;
import java.util.List;

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
		if (UserInterface.selection == 1)
		{
			DatabaseQueryOperations.SQL_SELECT = DatabaseQueryOperations.SQL_SELECT_AMAZON;
		}
		if (UserInterface.selection == 2)
		{
			DatabaseQueryOperations.SQL_SELECT = DatabaseQueryOperations.SQL_SELECT_BARNES;
		}
		if (UserInterface.selection == 3)
		{
			DatabaseQueryOperations.SQL_SELECT = DatabaseQueryOperations.SQL_SELECT_EBAY;
		}
		if (UserInterface.selection == 8)
		{
			// Query will be for user's inventory
		}
	}

	public void searchBook(String bookSpecified)
	{
		boolean lowestPriceRequested = false;

		try
		{
			databaseDriver.queryBook(bookSpecified, lowestPriceRequested);
		} catch (ClassNotFoundException | SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
