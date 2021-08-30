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
	private ArrayList<BigDecimal> bookPrices = new ArrayList<>();
	private ArrayList<String> titlePriceQueriesList = new ArrayList<>();

	/*
	 * This method retrieves all extracted books from WebScraperDriver.
	 */
	void getBookProducts()
	{
		WebScraperDriver.verifySitesToExtract(WebScraperDriver.onlineBookSiteList);

		List<BookProperties> books;
		for (String website : WebScraperDriver.onlineBookSiteList)
		{
			books = webScraper.extractProducts(website);
			createConnection(books);
		}
	}

	/*
	 * This method connects to the SQL database through JDBC then drops all tables,
	 * recreates them, resetting them so they're ready for new entries of Id, Title,
	 * and Price. The reason for resetting them is because the Id(Primary Key)
	 * cannot be overwritten and it causes SQL errors, therefore halting the program
	 */
	private void createConnection(List<BookProperties> books)
	{
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection;
			connection = DriverManager.getConnection("jdbc:mysql://70.183.97.249:3306/db", "student", "student");

			Statement statement = connection.createStatement();

			// TODO: Maybe create a method to enter refreshed data without dropping tables,
			// re-creating tables
			// and entering a new Id. Book amounts can change so this may not be viable as
			// Id amount must stay the same
			statement.execute(DatabaseQueryOperations.SQL_DROP_TABLE);
			statement.executeUpdate(DatabaseQueryOperations.SQL_CREATE_TABLE);

			transferToDatabase(connection, books);
		} catch (SQLException | ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	/*
	 * This method inserts data to the SQL DB
	 */
	private void transferToDatabase(Connection connection, List<BookProperties> books) throws SQLException
	{
		PreparedStatement preparedStatement = connection.prepareStatement(DatabaseQueryOperations.SQL_INSERT);
		for (BookProperties book : books)
		{
			preparedStatement.setInt(1, book.getId());
			preparedStatement.setString(2, book.getTitle());
			preparedStatement.setString(3, book.getFormattedPrice());
			preparedStatement.execute();
		}
		System.out.println("Extraction Complete.");
	}

	void queryBook(String book, boolean lowestPriceRequested) throws SQLException, ClassNotFoundException
	{
		try
		{
			BigDecimal fixedPrice = null;

			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection;
			connection = DriverManager.getConnection(
					"jdbc:mysql://70.183.97.249:3306/db", "student", "student");

			DatabaseQueryOperations.bookSelection = book;

			getQueriesList();

			for (String tableTitle : titlePriceQueriesList)
			{
				displayTableTitle(tableTitle);

				PreparedStatement preparedStatement = connection.prepareStatement(
						"SELECT Title, Price FROM " + tableTitle + " WHERE " + tableTitle + ".Title LIKE ?");
				preparedStatement.setString(1, book);
				ResultSet result = preparedStatement.executeQuery();
				while (result.next())
				{

					System.out.println("Book: " + result.getString(1) + "\nPrice: " + result.getString("Price"));
					String price = result.getString("Price");
					String bookPrice = price.replace("$", ""); // Strip $ symbol to convert into BigDecimal
					fixedPrice = new BigDecimal(bookPrice);

					bookPrices.add(fixedPrice);
				}
			}

			if (lowestPriceRequested == true)
			{
				fixedPrice = itemWithLowestCost(bookPrices, fixedPrice);

				System.out.println("$" + fixedPrice + " is the lowest price for this book!");
			}

		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	private BigDecimal itemWithLowestCost(ArrayList<BigDecimal> bookPrices, BigDecimal fixedPrice)
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

	private void getQueriesList()
	{
		titlePriceQueriesList.add("amazon");
		titlePriceQueriesList.add("barnesnoble");
		titlePriceQueriesList.add("ebay");

	}

	private void displayTableTitle(String tableTitle)
	{
		if (tableTitle == "amazon")
		{
			System.out.println("Amazon Books:");
		}
		if (tableTitle == "barnes")
		{
			System.out.println("Barnes & Noble:");
		}
		if (tableTitle == "ebay")
		{
			System.out.println("Ebay Books:");
		}
	}

	/*
	 * Query DB for viewDB method. Utilizes 1,2,3 for database selection from verifyTableViewed
	 */
	public List<BookProperties> queryDB() {
		List<BookProperties> bookList = new ArrayList<>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection;
			connection = DriverManager.getConnection("jdbc:mysql://70.183.97.249:3306/db", "student", "student");
			
			PreparedStatement preparedStatement = connection.prepareStatement(DatabaseQueryOperations.SQL_SELECT);
			ResultSet result = preparedStatement.executeQuery();
			
			while (result.next()) {
				BookProperties bookListing = new BookProperties();
				
				bookListing.setTitle(result.getString("Title"));
				bookListing.setFormattedPrice(result.getString("Price"));
				bookListing.setId(Integer.parseInt(result.getString("Id")));
				
				bookList.add(bookListing);
				
				/*System.out.println(
						String.format("Product: %s\nPrice: %s\nId: %d", bookListing.getTitle(), bookListing.getFormattedPrice(), bookListing.getId()));
				*/
			}
		}
		catch (ClassNotFoundException | SQLException e){
			e.printStackTrace();
		}
		
		return bookList;
	}
}
