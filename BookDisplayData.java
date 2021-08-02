package databaseProject;

import java.util.List;

/*
 * 
 *	This class contains the methods that allow the user to view book data.
 */
public class BookDisplayData 
{
	void displayBooks()
	{
		WebScraperDriver webScraper = new WebScraperDriver();
		List<BookProperties> products = webScraper.extractProducts();
		
	    for (BookProperties product : products) 
        {
            System.out.println(String.format("Product:\n%s\n%s\n", product.getTitle(), 
                    		product.getFormattedPrice()));
        }
	}
}
