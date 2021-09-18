package databaseProject;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
	private ArrayList<String> titlePriceQueriesList = new ArrayList<>();
	private static Config cfg = new Config();
	private static String dbName = cfg.getProperty("mDbName");
	private static String dbUser = cfg.getProperty("mDbUser");
	private static String dbPwd = cfg.getProperty("mDbPwd");
	private static String dbHost = cfg.getProperty("mDbHost");

	public static long hours;
	public static long days;
	private static ScheduledFuture<?> bookGetter;

	/*
	 * This method sets the interval the user wants to do webscraping at if they
	 * wish to do so, starting with a scraping session in 1 minute delay , then
	 * taking in the parameter 'hours' for how many hours interval they want to thew
	 * program to continue scraping, and 'days' for how many days they want the
	 * interval scraping to continue. This happens in a seperate thread from the
	 * main thread.
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

		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

		ScheduledFuture<?> bookGetter = scheduler.scheduleAtFixedRate(scrapeAtInterval, 0, hours, TimeUnit.SECONDS);
		scheduler.schedule(new Runnable()
		{
			public void run()
			{
				bookGetter.cancel(true);
			}
		}, days, TimeUnit.MINUTES);
	}

	/*
	 * This is called when the user wants to stop scraping via interval.
	 */
	public static void stopInterval()
	{
		bookGetter.cancel(true);
		System.out.println("Scheduler stopped");
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
			preparedStatement.setDouble(3, book.getBookPrice().doubleValue());
			preparedStatement.execute();
		}
	}

	public List<BookProperties> queryBook(String book) throws SQLException, ClassNotFoundException
	{
		List<BookProperties> books = new ArrayList<>();
		try
		{
			BigDecimal fixedPrice = null;

			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection;
			String connectionStr = String.format("jdbc:mysql://%s/%s", dbHost, dbName);
			connection = DriverManager.getConnection(connectionStr, dbUser, dbPwd);

			DatabaseQueryOperations.bookSelection = book;
			getQueriesList();

			for (String tableTitle : titlePriceQueriesList)
			{
				BookProperties bookListing = new BookProperties();
				displayTableTitle(tableTitle);
				bookListing.setSite(tableTitle);

				PreparedStatement preparedStatement = connection.prepareStatement(
						"SELECT Title, Price FROM " + tableTitle + " WHERE " + tableTitle + ".Title LIKE ?");
				preparedStatement.setString(1, book);
				ResultSet result = preparedStatement.executeQuery();
				while (result.next())
				{
					double price = Double.parseDouble(result.getString("Price"));
					fixedPrice = new BigDecimal(price).setScale(2, RoundingMode.HALF_DOWN);
					bookListing.setTitle(result.getString(1));
					bookListing.setBookPrice(fixedPrice);
				}

				books.add(bookListing);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return books;
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
	 * Query DB for viewDB method. Utilizes 1,2,3 for database selection from
	 * verifyTableViewed
	 */
	public List<BookProperties> queryDB()
	{
		List<BookProperties> bookList = new ArrayList<>();
		BigDecimal bdPrice;
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection;
			String connectionStr = String.format("jdbc:mysql://%s/%s", dbHost, dbName);
			connection = DriverManager.getConnection(connectionStr, dbUser, dbPwd);

			PreparedStatement preparedStatement = connection.prepareStatement(DatabaseQueryOperations.SQL_SELECT);
			ResultSet result = preparedStatement.executeQuery();

			while (result.next())
			{
				BookProperties bookListing = new BookProperties();

				double price = 0.0;

				if (!result.getString("Price").equals(""))
				{
					price = Double.parseDouble(result.getString("Price"));
				}

				bdPrice = new BigDecimal(price).setScale(2, RoundingMode.HALF_DOWN);

				bookListing.setTitle(result.getString("Title"));
				bookListing.setBookPrice(bdPrice);
				bookListing.setId(Integer.parseInt(result.getString("Id")));

				bookList.add(bookListing);

			}
		} catch (ClassNotFoundException | SQLException e)
		{
			e.printStackTrace();
		}

		return bookList;
	}
}
