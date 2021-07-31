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
	private String sqlInsert = "INSERT INTO inventory (Id, Title, Price) VALUES (?,?,?)";
	private String sqlDrop = "DROP TABLE IF EXISTS inventory";
	private String sqlCreate = "CREATE TABLE inventory "
			+ "(Id INT NOT NULL, " 
			+ "Title VARCHAR(1000) NULL," 
			+ "Price VARCHAR(45) NULL, "
			+ "PRIMARY KEY ( Id ))";
	
	void createConnection() throws ClassNotFoundException, SQLException
	{
		WebScraperDriver webScraper = new WebScraperDriver();
		List<BookProperties> products = webScraper.extractProducts();
		
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");

			Connection connection;
			connection = DriverManager.getConnection
					("jdbc:mysql://localhost:3306/mydb", "root", "password");
						
			Statement statement = connection.createStatement();
			
			statement.execute(sqlDrop);
			System.out.println("Table dropped");
						
			statement.executeUpdate(sqlCreate);
			System.out.println("Table and columns created");
			
			PreparedStatement preparedStatement = connection.prepareStatement(sqlInsert);
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
