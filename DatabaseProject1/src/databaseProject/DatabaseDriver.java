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
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/*
 * 
 * 	This class connects to the MySQL Server and inserts the book data in tables.
 */
public class DatabaseDriver
{
	static WebScraperDriver webScraper = new WebScraperDriver();
	DatabaseQueryOperations databaseOperations = new DatabaseQueryOperations();
	private ArrayList<BigDecimal> bookPrices = new ArrayList<>();
	private ArrayList<String> titlePriceQueriesList = new ArrayList<>();
	private static Config cfg = new Config();
	private static String dbName = cfg.getProperty("mDbName");
	private static String dbUser = cfg.getProperty("mDbUser");
	private static String dbPwd = cfg.getProperty("mDbPwd");
	private static String dbHost = cfg.getProperty("mDbHost");

	public static long hours;
	public static long days;
	private final static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

	/*
	 * This method sets the interval the user wants to do webscraping at if they
	 * wish to do so, starting with a scraping session in 1 minute delay , then
	 * taking in the parameter 'hours' for how many hours interval they want to
	 * thew program to continue scraping, and 'days' for how many days they want the
	 * interval scraping to continue. This happens in a seperate thread from the main
	 * thread.
	 */
	public static void setUserIntervalForExtraction()
	{
		Runnable scrapeAtInterval = new Runnable()
		{
			public void run()
			{
				getBookProducts();
			}
		};

		ScheduledFuture<?> bookGetter = scheduler.scheduleAtFixedRate(scrapeAtInterval, 1, hours, TimeUnit.HOURS);
		scheduler.schedule(new Runnable()
		{
			public void run()
			{
				bookGetter.cancel(true);
			}
		}, days, TimeUnit.DAYS);
	}

	/*
	 * This is called when the user wants to stop scraping via interval.
	 */
	public static void stopInterval()
	{
		scheduler.shutdown();
	}

	/*
	 * This method retrieves all extracted books from WebScraperDriver.
	 */
	static void getBookProducts()
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
	private static void createConnection(List<BookProperties> books)
	{
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection;
			String connectionStr = String.format("jdbc:mysql://%s/%s", dbHost, dbName);
			connection = DriverManager.getConnection(connectionStr, dbUser, dbPwd);

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
	private static void transferToDatabase(Connection connection, List<BookProperties> books) throws SQLException
	{
		PreparedStatement preparedStatement = connection.prepareStatement(DatabaseQueryOperations.SQL_INSERT);
		for (BookProperties book : books)
		{
			preparedStatement.setInt(1, book.getId());
			preparedStatement.setString(2, book.getTitle());
			preparedStatement.setString(3, book.getFormattedPrice());
			preparedStatement.execute();
		}
		//System.out.println("Extraction Complete.");
	}

	public List<BookProperties> queryBook(String book, boolean lowestPriceRequested) throws SQLException, ClassNotFoundException {
		List<BookProperties> books = new ArrayList<>();
		try {
			BigDecimal fixedPrice = null;

			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection;
			String connectionStr = String.format("jdbc:mysql://%s/%s", dbHost, dbName);
			connection = DriverManager.getConnection(connectionStr, dbUser, dbPwd);

			DatabaseQueryOperations.bookSelection = book;

			getQueriesList();

			for (String tableTitle : titlePriceQueriesList) {
				BookProperties bookListing = new BookProperties();
				displayTableTitle(tableTitle);
				bookListing.setSite(tableTitle);

				PreparedStatement preparedStatement = connection.prepareStatement(
						"SELECT Title, Price FROM " + tableTitle + " WHERE " + tableTitle + ".Title LIKE ?");
				preparedStatement.setString(1, book);
				ResultSet result = preparedStatement.executeQuery();
				while (result.next())
				{

					System.out.println("Book: " + result.getString(1) + "\nPrice: " + result.getString("Price"));
					String price = result.getString("Price");
					bookListing.setTitle(result.getString(1));
					bookListing.setFormattedPrice(price);
					String bookPrice = price.replace("$", ""); // Strip $ symbol to convert into BigDecimal
					fixedPrice = new BigDecimal(bookPrice);

					bookPrices.add(fixedPrice);
				}

				books.add(bookListing);
			}

			if (lowestPriceRequested == true) {
				fixedPrice = itemWithLowestCost(bookPrices, fixedPrice);

				System.out.println("$" + fixedPrice + " is the lowest price for this book!");
			}

		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return books;
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
		titlePriceQueriesList.add("inventory");
	}

	private void displayTableTitle(String tableTitle)
	{
		if (tableTitle == "amazon")
		{
			System.out.println("Amazon Books:");
		}
		if (tableTitle == "barnesnoble")
		{
			System.out.println("Barnes & Noble:");
		}
		if (tableTitle == "ebay")
		{
			System.out.println("Ebay Books:");
		}
		if (tableTitle == "inventory")
		{
			System.out.println("Your Inventory:");
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
			String connectionStr = String.format("jdbc:mysql://%s/%s", dbHost, dbName);
			connection = DriverManager.getConnection(connectionStr, dbUser, dbPwd);
			
			PreparedStatement preparedStatement = connection.prepareStatement(DatabaseQueryOperations.SQL_SELECT);
			ResultSet result = preparedStatement.executeQuery();
			
			while (result.next()) {
				BookProperties bookListing = new BookProperties();
				
				bookListing.setTitle(result.getString("Title"));
				bookListing.setFormattedPrice(result.getString("Price"));
				bookListing.setId(Integer.parseInt(result.getString("Id")));
				
				bookList.add(bookListing);

			}
		}
		catch (ClassNotFoundException | SQLException e){
			e.printStackTrace();
		}
		
		return bookList;
	}
}
