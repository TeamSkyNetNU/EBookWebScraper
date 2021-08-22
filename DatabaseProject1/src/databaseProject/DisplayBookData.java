package databaseProject;

import java.sql.SQLException;
import java.util.Scanner;

/*
 * 
 *	This class contains the methods that allow the user to view book data.
 */
public class DisplayBookData 
{
	DatabaseDriver databaseDriver = new DatabaseDriver();
	
	void beginDisplay()
	{
		viewDb();
		
		searchBook();
	}
	
	private void viewDb()
	{
		verifyTableViewed();
		databaseDriver.queryDB();
		
//		String website = null;
//		WebScraperDriver webScraper = new WebScraperDriver();
//		List<BookProperties> products = webScraper.extractProducts(website);
//		
//	    for (BookProperties product : products) 
//        {
//            System.out.println(String.format("Product:\n%s\n%s\n", product.getTitle(), 
//                    		product.getFormattedPrice()));
//        }
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
	}
	
	private void searchBook() 
	{
		String bookSpecified = "";
		boolean lowestPriceRequested = false;
		
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter book to search:");
		
		bookSpecified = scanner.nextLine();
		
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
