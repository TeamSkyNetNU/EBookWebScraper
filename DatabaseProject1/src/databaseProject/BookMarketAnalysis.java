package databaseProject;

import java.sql.SQLException;
import java.util.Scanner;

/*
 * 
 *	This class generates price recommendation analysis based off the book entries pricing data. 
 */
public class BookMarketAnalysis
{
	DatabaseDriver dataBaseDriver = new DatabaseDriver();

	void beginMarketAnalysis()
	{
		getLowestPrice();
	}

	private void getLowestPrice()
	{
		String bookSpecified = "";
		boolean lowestPriceRequested = true;
		
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter book to compare:");

		bookSpecified = scanner.nextLine();

		try
		{
			dataBaseDriver.queryBook(bookSpecified, lowestPriceRequested);
		} catch (ClassNotFoundException | SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// TODO Price Recommendation, view Price
}
