package databaseProject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/*
 * 
 * 	This class connects to the MySQL Server and inserts the book data in tables.
 */
public class DatabaseDriver
{
	WebScraperDriver webScraper = new WebScraperDriver();
	DatabaseOperations databaseOperations = new DatabaseOperations();
	
	void createConnection() throws ClassNotFoundException, SQLException
	{
		List<BookProperties> products = webScraper.extractProducts();
		
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");

			Connection connection;
			connection = DriverManager.getConnection
					("jdbc:mysql://localhost:3306/mydb", "root", "password");
						
			Statement statement = connection.createStatement();
			
			statement.execute(DatabaseOperations.SQL_DROP_TABLE);
			System.out.println("Table dropped");
						
			statement.executeUpdate(DatabaseOperations.SQL_CREATE_TABLE);
			System.out.println("Table and columns created");
			
			PreparedStatement preparedStatement = connection.prepareStatement(DatabaseOperations.SQL_INSERT);
			for (BookProperties product : products)
			{
				preparedStatement.setInt (1, product.getId());
				preparedStatement.setString (2, product.getTitle());
				preparedStatement.setString (3, product.getFormattedPrice());
				preparedStatement.execute();
				
				System.out.println("Database updated");
			}	
		} 
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
