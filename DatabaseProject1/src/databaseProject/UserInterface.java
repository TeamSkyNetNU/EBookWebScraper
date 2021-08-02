package databaseProject;

import java.sql.SQLException;
import java.util.Scanner;

public class UserInterface
{
	DatabaseDriver databaseDriver = new DatabaseDriver();
	static int selection;
	
	
	void beginProgram() throws ClassNotFoundException, SQLException
	{
		Scanner scanner = new Scanner(System.in);
    	System.out.println("Enter the website you want to scrape\n"
    			+ "1.Amazon Books 2.Barnes & Noble 3.Ebay Books:\n");
       	
    	selection = scanner.nextInt();
        databaseDriver.createConnection();
	}
}
