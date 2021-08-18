package databaseProject;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * 
 *	This class generates price recommendation analysis based off the book entries pricing data. 
 */
public class BookMarketAnalysis
{
	DatabaseDriver dataBaseDriver = new DatabaseDriver();
	private String bookSpecified = "";
	
	void getLowestPrice() throws ClassNotFoundException, SQLException
	{
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter book to compare:");
		
		bookSpecified = scanner.nextLine();
		
		dataBaseDriver.queryBook(bookSpecified);
	}

	
	//TODO Price Recommendation, view Price
}
