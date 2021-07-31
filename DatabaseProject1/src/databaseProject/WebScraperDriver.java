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
 *	This class scrapes the online book retailers for book data.
 */
public class WebScraperDriver
{
	private static final String EBAY_BOOKS = 
			"https://www.ebay.com/b/Books/261186/bn_16566585";
	private static final String BARNES_NOBLE_BOOKS = 
			"https://www.barnesandnoble.com/b/books/_/N-1fZ29Z8q8";
	private static final String AMAZON_BOOKS = "https://www.amazon.com/books-used-books-textbooks/b/" +
			"?ie=UTF8&node=283155&ref_=nav_cs_books_2ed85a0fb54a4598ba909c22690d166e";
	private static String PRODUCT_CARD_CLASS = ""; 
	private static String PRODUCT_TITLE_CLASS = "";
	private static String PRODUCT_PRICE_SELECTOR = "";
	private ArrayList<String> onlineBookVendorList = new ArrayList<>();

	void onlineBookVendorFactory()
	{
		onlineBookVendorList.add(BARNES_NOBLE_BOOKS);
		onlineBookVendorList.add(EBAY_BOOKS);
		onlineBookVendorList.add(AMAZON_BOOKS);
	}

	public List<BookProperties> extractProducts()
	{
		List<BookProperties> books = new ArrayList<>();

		onlineBookVendorFactory();

		int count = 0;
		Document doc;

		for (String website : onlineBookVendorList)
		{
			try
			{
				doc = Jsoup.connect(website).get();
			} catch (IOException e)
			{
				throw new RuntimeException(e);
			}

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
		}
		return books;
	}

	private String verifyProductCard(String website)
	{
		if (website.matches(EBAY_BOOKS))
		{
			PRODUCT_CARD_CLASS = "b-info";
		}
		if (website.matches(BARNES_NOBLE_BOOKS))
		{
			PRODUCT_CARD_CLASS = "product-info-view";
		}
		if (website.matches(AMAZON_BOOKS))
		{
			PRODUCT_CARD_CLASS = "acs-product-block";
		}
		return PRODUCT_CARD_CLASS;
	}
	
	private String verifyProductTitle(String website)
	{
		if (website.matches(EBAY_BOOKS))
		{
			PRODUCT_TITLE_CLASS = "b-info__title ";
		}
		if (website.matches(BARNES_NOBLE_BOOKS))
		{
			PRODUCT_TITLE_CLASS = " ";
		}
		if (website.matches(AMAZON_BOOKS))
		{
			PRODUCT_TITLE_CLASS = "a-truncate-full";
		}
		return PRODUCT_TITLE_CLASS;
	}
	
	private String verifyProductPrice(String website)
	{
		if (website.contains(EBAY_BOOKS))
		{
			PRODUCT_PRICE_SELECTOR = "b-info__trendprice";
		}
		if (website.contains(BARNES_NOBLE_BOOKS))
		{
			PRODUCT_PRICE_SELECTOR = " current link";
		}
		if (website.contains(AMAZON_BOOKS))
		{
			PRODUCT_PRICE_SELECTOR = "a-offscreen"; 
		}
		return PRODUCT_PRICE_SELECTOR;
	}
}
