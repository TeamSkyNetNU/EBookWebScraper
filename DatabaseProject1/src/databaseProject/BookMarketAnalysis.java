package databaseProject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*
 * 
 *	This class generates price recommendation analysis based off the book entries pricing data. 
 */
public class BookMarketAnalysis
{
	DatabaseDriver dataBaseDriver = new DatabaseDriver();

	public List<BookProperties> getLowestPrice(String bookSpecified)
	{
		List<BookProperties> books = new ArrayList<>();

		try
		{
			books = dataBaseDriver.queryBook(bookSpecified);
		} catch (ClassNotFoundException | SQLException e)
		{
			e.printStackTrace();
		}

		return books;
	}
}
