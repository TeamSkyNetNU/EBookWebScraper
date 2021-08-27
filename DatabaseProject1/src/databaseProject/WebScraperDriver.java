package databaseProject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/*
 * 
 *	This class extracts book data from the top online book retailers.
 */
public class WebScraperDriver
{

	private static final String EBAY_BOOKS = "https://www.ebay.com/t/Books/261186/bn_16566585";
	private static final String BARNES_NOBLE_BOOKS = "https://www.barnesandnoble.com/b/books/_/N-1fZ29Z8q8";
	private static final String AMAZON_BOOKS = "https://www.amazon.com/books-used-books-textbooks/b/"
			+ "?ie=UTF8&node=283155&ref_=nav_cs_books_2ed85a0fb54a4598ba909c22690d166e";
	private static String PRODUCT_CARD_CLASS = "";
	private static String PRODUCT_TITLE_CLASS = "";
	private static String PRODUCT_PRICE_SELECTOR = "";
	static ArrayList<String> onlineBookSiteList = new ArrayList<>();

	/*
	 * This method extracts the books and their information from scraping the site
	 * for data. If option 4 (scrape all sites) is not selected then it will verify
	 * each site selection to ensure the right card class, title class, and price
	 * selector strings are being parsed.
	 * 
	 */
	public List<BookProperties> extractProducts(String website)
	{
		List<BookProperties> books = new ArrayList<>();

		int count = 0;
		Document doc;

		try
		{
			doc = Jsoup.connect(website).get();
		} catch (IOException e)

		{
			throw new RuntimeException(e);
		}


		startMessage(website);

		verifySiteTables(website);
		PRODUCT_CARD_CLASS = verifyProductCard(website);
		Elements productElements = doc.getElementsByClass(PRODUCT_CARD_CLASS);
		for (Element productElement : productElements)
		{
			BookProperties bookListing = new BookProperties();

			PRODUCT_TITLE_CLASS = verifyProductTitle(website);
			Elements titleElements = productElement.getElementsByClass(PRODUCT_TITLE_CLASS);
			if (!titleElements.isEmpty())
			{
				bookListing.setTitle(titleElements.get(0).text());
			}

			PRODUCT_PRICE_SELECTOR = verifyProductPrice(website);
			Elements priceElements = productElement.getElementsByClass(PRODUCT_PRICE_SELECTOR);
			if (!priceElements.isEmpty())
			{
				bookListing.setFormattedPrice(priceElements.get(0).text());
			}

			count++;
			bookListing.setId(count);

			books.add(bookListing);
		}

		return books;
	}

	/*
	 * For scraping selection, uses #'s 1-3, for combinations of 2 sites uses 4,5,6, and 7 for all sites
	 */
	static ArrayList<String> verifySitesToExtract(ArrayList<String> onlineBookSiteList)
	{
		if (UserInterface.selection == 1)
		{
			onlineBookSiteList.add(AMAZON_BOOKS);
		}
		if (UserInterface.selection == 2)
		{
			onlineBookSiteList.add(BARNES_NOBLE_BOOKS);
		}
		if (UserInterface.selection == 3)
		{
			onlineBookSiteList.add(EBAY_BOOKS);
		}
		if (UserInterface.selection == 4)
		{
			onlineBookSiteList.add(AMAZON_BOOKS);
			onlineBookSiteList.add(BARNES_NOBLE_BOOKS);
		}
		if (UserInterface.selection == 5)
		{
			onlineBookSiteList.add(AMAZON_BOOKS);
			onlineBookSiteList.add(EBAY_BOOKS);
		}
		if (UserInterface.selection == 6)
		{
			onlineBookSiteList.add(BARNES_NOBLE_BOOKS);
			onlineBookSiteList.add(EBAY_BOOKS);
		}
		if (UserInterface.selection == 7)
		{
			onlineBookSiteList.add(BARNES_NOBLE_BOOKS);
			onlineBookSiteList.add(EBAY_BOOKS);
			onlineBookSiteList.add(AMAZON_BOOKS);
		}

		return onlineBookSiteList;
	}

	private void startMessage(String website)
	{
		String formattedWebsiteName = "";

		if (website.contentEquals(WebScraperDriver.AMAZON_BOOKS))
		{
			formattedWebsiteName = "Amazon Books";
		}
		if (website.contentEquals(WebScraperDriver.BARNES_NOBLE_BOOKS))
		{
			formattedWebsiteName = "Barnes & Noble";
		}
		if (website.contentEquals(WebScraperDriver.EBAY_BOOKS))
		{
			formattedWebsiteName = "Ebay Books";
		}

		System.out.println("Extracting from " + formattedWebsiteName + ".");
	}

	private void verifySiteTables(String website)
	{
		if (website.contentEquals(AMAZON_BOOKS))
		{
			DatabaseQueryOperations.SQL_DROP_TABLE = DatabaseQueryOperations.SQL_DROP_AMAZON;
			DatabaseQueryOperations.SQL_CREATE_TABLE = DatabaseQueryOperations.SQL_AMAZON_CREATE;
			DatabaseQueryOperations.SQL_INSERT = DatabaseQueryOperations.SQL_INSERT_AMAZON;
		}
		if (website.contentEquals(BARNES_NOBLE_BOOKS))
		{
			DatabaseQueryOperations.SQL_DROP_TABLE = DatabaseQueryOperations.SQL_DROP_BARNES;
			DatabaseQueryOperations.SQL_CREATE_TABLE = DatabaseQueryOperations.SQL_BARNES_CREATE;
			DatabaseQueryOperations.SQL_INSERT = DatabaseQueryOperations.SQL_INSERT_BARNES;
		}
		if (website.contentEquals(EBAY_BOOKS))
		{
			DatabaseQueryOperations.SQL_DROP_TABLE = DatabaseQueryOperations.SQL_DROP_EBAY;
			DatabaseQueryOperations.SQL_CREATE_TABLE = DatabaseQueryOperations.SQL_EBAY_CREATE;
			DatabaseQueryOperations.SQL_INSERT = DatabaseQueryOperations.SQL_INSERT_EBAY;
		}
	}

	private String verifyProductCard(String website)
	{
		if (website.contentEquals(AMAZON_BOOKS))
		{
			PRODUCT_CARD_CLASS = "acs-product-block";
		}
		if (website.contentEquals(BARNES_NOBLE_BOOKS))
		{
			PRODUCT_CARD_CLASS = "product-info-view";
		}
		if (website.contentEquals(EBAY_BOOKS))
		{
			PRODUCT_CARD_CLASS = "details-wrapper";
		}
		return PRODUCT_CARD_CLASS;
	}

	private String verifyProductTitle(String website)
	{
		if (website.contentEquals(AMAZON_BOOKS))
		{
			PRODUCT_TITLE_CLASS = "a-truncate-full";
		}
		if (website.contentEquals(BARNES_NOBLE_BOOKS))
		{
			PRODUCT_TITLE_CLASS = " ";
		}
		if (website.contentEquals(EBAY_BOOKS))
		{
			PRODUCT_TITLE_CLASS = "title";

		}
		return PRODUCT_TITLE_CLASS;
	}

	private String verifyProductPrice(String website)
	{
		if (website.contentEquals(AMAZON_BOOKS))
		{
			PRODUCT_PRICE_SELECTOR = "a-offscreen";
		}
		if (website.contentEquals(BARNES_NOBLE_BOOKS))
		{
			PRODUCT_PRICE_SELECTOR = " current link";
		}
		if (website.contentEquals(EBAY_BOOKS))
		{
			PRODUCT_PRICE_SELECTOR = "cc-ts-BOLD";
		}
		return PRODUCT_PRICE_SELECTOR;
	}
}
