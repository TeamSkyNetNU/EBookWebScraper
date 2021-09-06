package databaseProject;

/*
 * 
 *	This is the book product listing class that defines the attributes of each book.
 */
public class BookProperties 
{
	private int id;
	private String site;
  	private String title;
  	private String formattedPrice;
	
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

	public String getFormattedPrice() 
	{
    	return formattedPrice;
	}

	public void setFormattedPrice(String formattedPrice) 
	{
		this.formattedPrice = formattedPrice;
	}

	public String getSite() { return site; }

	public void setSite(String site) { this.site = site; }
}
