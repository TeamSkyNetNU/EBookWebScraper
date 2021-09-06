package databaseProject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
 * 
 *	This class generates price recommendation analysis based off the book entries pricing data. 
 */
public class BookMarketAnalysis
{
	DatabaseDriver dataBaseDriver = new DatabaseDriver();

	/*void beginMarketAnalysis(){
		getLowestPrice();
	}
	*/

	public List<BookProperties> getLowestPrice(String bookSpecified) {
		boolean lowestPriceRequested = true;

		List<BookProperties> books = new ArrayList<>();

		try {
			books = dataBaseDriver.queryBook(bookSpecified, lowestPriceRequested);
		}
		catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return books;
	}

	// TODO Price Recommendation, view Price
}
