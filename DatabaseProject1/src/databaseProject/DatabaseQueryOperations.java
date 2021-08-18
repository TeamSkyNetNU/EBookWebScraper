package databaseProject;

/*
 * 
 * 	This class stores the SQL query operations
 */
public class DatabaseQueryOperations
{	
	public static String tableName = "";
	public static String bookSelection = "";
	
	public static final String SQL_INSERT_AMAZON = "INSERT INTO amazon (Id, Title, Price) VALUES (?,?,?)";
	public static final String SQL_INSERT_BARNES = "INSERT INTO barnesnoble (Id, Title, Price) VALUES (?,?,?)";
	public static final String SQL_INSERT_EBAY = "INSERT INTO ebay (Id, Title, Price) VALUES (?,?,?)";
	public static String SQL_INSERT = "INSERT INTO inventory (Id, Title, Price) VALUES (?,?,?)";

	public static final String SQL_DROP_AMAZON = "DROP TABLE IF EXISTS amazon";
	public static final String SQL_DROP_BARNES = "DROP TABLE IF EXISTS barnesnoble";
	public static final String SQL_DROP_EBAY= "DROP TABLE IF EXISTS ebay";
	public static String SQL_DROP_TABLE = "DROP TABLE IF EXISTS inventory";
	
	public static final String SQL_AMAZON_CREATE = "CREATE TABLE amazon"
			+ "(Id INT NOT NULL, " 
			+ "Title VARCHAR(1000) NULL," 
			+ "Price VARCHAR(45) NULL, "
			+ "PRIMARY KEY ( Id ))";
	public static final String SQL_BARNES_CREATE = "CREATE TABLE barnesnoble"
			+ "(Id INT NOT NULL, " 
			+ "Title VARCHAR(1000) NULL," 
			+ "Price VARCHAR(45) NULL, "
			+ "PRIMARY KEY ( Id ))";
	public static final String SQL_EBAY_CREATE = "CREATE TABLE ebay"
			+ "(Id INT NOT NULL, " 
			+ "Title VARCHAR(1000) NULL," 
			+ "Price VARCHAR(45) NULL, "
			+ "PRIMARY KEY ( Id ))";
	public static String SQL_CREATE_TABLE = "CREATE TABLE inventory "
			+ "(Id INT NOT NULL, " 
			+ "Title VARCHAR(1000) NULL," 
			+ "Price VARCHAR(45) NULL, "
			+ "PRIMARY KEY ( Id ))";
	
	public static String SQL_AMAZON_SELECT_LIKE = "SELECT Title, Price FROM amazon "
			+ "WHERE amazon.Title LIKE '%" + bookSelection + "%';";
	public static String SQL_BARNES_SELECT_LIKE = "SELECT Title, Price FROM barnesnoble"
			+ " WHERE barnesnoble.Title LIKE '%" + bookSelection + "%';";
	public static String SQL_EBAY_SELECT_LIKE = "SELECT Title, Price FROM ebay "
			+ "WHERE ebay.Title LIKE '%" + bookSelection + "%';";

}
