package databaseProject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
	DatabaseOperations databaseOperations = new DatabaseOperations();
	
	/*
	 * 	This method retrieves books from WebScraperDriver, if 4 is selected by the
	 * 	user on program launch then it retrieves books for all 3 sites and updates
	 *  their tables otherwise it retrieves the site selected by the user.
	 */
	void getBookProducts() throws ClassNotFoundException, SQLException
	{
		List<BookProperties> products = null;
		
		ArrayList<String> onlineBookSiteList = new ArrayList<>();
		onlineBookSiteList.add(WebScraperDriver.BARNES_NOBLE_BOOKS);
		onlineBookSiteList.add(WebScraperDriver.EBAY_BOOKS);
		onlineBookSiteList.add(WebScraperDriver.AMAZON_BOOKS);
		
		if (UserInterface.selection == 4)
		{
			for (String website : onlineBookSiteList)
			{
				products = webScraper.extractProducts(website);
				createConnection(products);
			}
		}
		else
		{
			String website = "";
			products = webScraper.extractProducts(website);
			createConnection(products);
		}
	}
	
	/*
	 *  This method connects to the SQL database through JDBC then drops all tables, recreates them, resetting 
	 *  them so they're ready for new entries of Id, Title, and Price. The reason for resetting them is because
	 *  the Id(Primary Key) cannot be overwritten and it causes SQL errors, therefore halting the program
	 */
	private void createConnection(List<BookProperties> products) throws ClassNotFoundException, SQLException
	{
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");

			Connection connection;
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/books", "student", "student");

			Statement statement = connection.createStatement();
			
			//TODO: Maybe create a method to enter refreshed data without dropping tables, re-creating tables 
			// and entering a new Id. Book amounts can change so this may not be viable as Id amount must stay the same
			statement.execute(DatabaseOperations.SQL_DROP_TABLE);
			System.out.println("Table dropped");

			statement.executeUpdate(DatabaseOperations.SQL_CREATE_TABLE);
			System.out.println("Table and columns created");

			transferToDatabase(connection, products);
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	/*
	 *  This method inserts data to the SQL DB
	 */
	private void transferToDatabase(Connection connection, List<BookProperties> products) throws SQLException 
	{
		PreparedStatement preparedStatement = connection.prepareStatement(DatabaseOperations.SQL_INSERT);
		for (BookProperties product : products)
		{
			preparedStatement.setInt(1, product.getId());
			preparedStatement.setString(2, product.getTitle());
			preparedStatement.setString(3, product.getFormattedPrice());
			preparedStatement.execute();

			System.out.println("Database updated");
		}
	}
}
