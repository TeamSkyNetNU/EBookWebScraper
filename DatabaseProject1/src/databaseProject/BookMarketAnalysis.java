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

	
	void beginMarketAnalysis() throws ClassNotFoundException, SQLException
	{
		getLowestPrice();
	}
	
	private void getLowestPrice() throws ClassNotFoundException, SQLException
	{
		@SuppressWarnings("resource")
		
		String bookSpecified = "";
		boolean lowestPriceRequested = true;
		
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter book to compare:");
		
		bookSpecified = scanner.nextLine();
		
		dataBaseDriver.queryBook(bookSpecified, lowestPriceRequested);
	}

	
	//TODO Price Recommendation, view Price
}
