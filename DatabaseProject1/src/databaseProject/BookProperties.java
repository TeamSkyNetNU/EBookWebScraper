package databaseProject;

import java.math.BigDecimal;

/*
 * 
 *	This is the book product listing class that defines the attributes of each book.
 */
public class BookProperties 
{
	private int id;
	private String site;
  	private String title;
  	private BigDecimal bookPrice;
	
	public BookProperties() 
	{
	}
	
	public int getId()
	{
		return id;
	}

	public void setId(int id) 
	{
		this.id = id;
	}
	
	public String getTitle() 
	{
		return title;
	}

	public void setTitle(String title) 
	{
		this.title = title;
	}

	public BigDecimal getBookPrice()
	{
    	return bookPrice;
	}

	public void setBookPrice(BigDecimal bookPrice)
	{
		this.bookPrice = bookPrice;
	}

	public String getSite() { return site; }

	public void setSite(String site) { this.site = site; }
}
