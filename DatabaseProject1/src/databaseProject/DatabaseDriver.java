package databaseProject;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/*
 * 
 * 	This class connects to the MySQL Server and inserts the book data in tables.
 */
public class DatabaseDriver
{
	WebScraperDriver webScraper = new WebScraperDriver();
	DatabaseQueryOperations databaseOperations = new DatabaseQueryOperations();
	ArrayList<BigDecimal> bookPrices = new ArrayList<>();
	ArrayList<String> titlePriceQueriesList = new ArrayList<>();

	/*
	 * This method retrieves books from WebScraperDriver, if 4 is selected by the
	 * user on program launch then it retrieves books for all 3 sites and updates
	 * their tables otherwise it retrieves the site selected by the user.
	 */
	void getBookProducts() throws ClassNotFoundException, SQLException
	{
		List<BookProperties> products = null;

		ArrayList<String> onlineBookSiteList = new ArrayList<>();
		onlineBookSiteList.add(WebScraperDriver.BARNES_NOBLE_BOOKS);
		onlineBookSiteList.add(WebScraperDriver.EBAY_BOOKS);
		onlineBookSiteList.add(WebScraperDriver.AMAZON_BOOKS);

		if (scrapeSpecificSite())
		{
			String website = "";
			products = webScraper.extractProducts(website);
			createConnection(products);
		} else if (scrapeAllSites())
		{
			for (String website : onlineBookSiteList)
			{
				products = webScraper.extractProducts(website);
				createConnection(products);
			}
		} else
		{
			System.out.println("We're sorry. A invalid selection was made somehow.\n" + "Please try again.");
			System.exit(0);
		}
	}

	boolean scrapeSpecificSite()
	{
		if (UserInterface.selection != 4)
		{

			return true;
		}
		return false;
	}

	boolean scrapeAllSites()
	{
		if (UserInterface.selection == 4)
		{

			return true;
		}
		return false;
	}

	/*
	 * This method connects to the SQL database through JDBC then drops all tables,
	 * recreates them, resetting them so they're ready for new entries of Id, Title,
	 * and Price. The reason for resetting them is because the Id(Primary Key)
	 * cannot be overwritten and it causes SQL errors, therefore halting the program
	 */
	private void createConnection(List<BookProperties> products) throws ClassNotFoundException, SQLException
	{
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection;
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "password");

			Statement statement = connection.createStatement();

			// TODO: Maybe create a method to enter refreshed data without dropping tables,
			// re-creating tables
			// and entering a new Id. Book amounts can change so this may not be viable as
			// Id amount must stay the same
			statement.execute(DatabaseQueryOperations.SQL_DROP_TABLE);
			System.out.println("Table dropped");

			statement.executeUpdate(DatabaseQueryOperations.SQL_CREATE_TABLE);
			System.out.println("Table and columns created");

			transferToDatabase(connection, products);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	/*
	 * This method inserts data to the SQL DB
	 */
	private void transferToDatabase(Connection connection, List<BookProperties> products) throws SQLException
	{
		PreparedStatement preparedStatement = connection.prepareStatement(DatabaseQueryOperations.SQL_INSERT);
		for (BookProperties product : products)
		{
			preparedStatement.setInt(1, product.getId());
			preparedStatement.setString(2, product.getTitle());
			preparedStatement.setString(3, product.getFormattedPrice());
			preparedStatement.execute();

			System.out.println("Database updated");
		}
	}

	void queryBook(String book) throws SQLException, ClassNotFoundException
	{
		try
		{
			BigDecimal fixedPrice = null;
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection;
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "password");

			DatabaseQueryOperations.bookSelection = book;

			GetQueriesList();

			for (String tableTitle : titlePriceQueriesList)
			{
				PreparedStatement preparedStatement = connection.prepareStatement(
						"SELECT Title, Price FROM " + tableTitle + " WHERE " + tableTitle + ".Title LIKE ?");
				preparedStatement.setString(1, book);
				ResultSet result = preparedStatement.executeQuery();
				while (result.next())
				{

					System.out.println("Book: " + result.getString(1) + "\nPrice: " + result.getString("Price"));
					String price = result.getString("Price");
					String bookPrice = price.replace("$", ""); //Strip $ symbol to convert into BigDecimal
					fixedPrice = new BigDecimal(bookPrice);
					
					bookPrices.add(fixedPrice);	
				}
			}
			fixedPrice = itemWithLowestCost(bookPrices, fixedPrice);
			
			System.out.println("$" +fixedPrice + " is the lowest price for this book!");


		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	
	public BigDecimal itemWithLowestCost(ArrayList<BigDecimal> bookPrices, BigDecimal fixedPrice)
	{
		BigDecimal smallest = bookPrices.get(0);

		for (int i = 1; i < bookPrices.size(); i++)
		{
			if (bookPrices.get(i).intValue() < smallest.intValue())
			{
				smallest = bookPrices.get(i);
			}
		}
	
		return smallest;
	}
	
	
	private void GetQueriesList()
	{
		titlePriceQueriesList.add("amazon");
		titlePriceQueriesList.add("barnesnoble");
		titlePriceQueriesList.add("ebay");

	}
}
