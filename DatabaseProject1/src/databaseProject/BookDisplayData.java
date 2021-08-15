package databaseProject;

import java.util.List;

/*
 * 
 *	This class contains the methods that allow the user to view book data.
 */
public class BookDisplayData 
{
	public List<BookProperties> displayBooks()
	{
		String website = "";
		
		WebScraperDriver webScraper = new WebScraperDriver();
		List<BookProperties> products = webScraper.extractProducts(website);
		
		/*
	    for (BookProperties product : products) 
        {
            System.out.println(String.format("Product:\n%s\n%s\n", product.getTitle(), 
                    		product.getFormattedPrice()));
        }
        */
		return products;
	}
}
