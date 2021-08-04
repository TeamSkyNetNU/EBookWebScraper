package databaseProject;

import java.sql.SQLException;
import java.util.Scanner;

/*
 * 
 *  This class takes user input selection on how they want to run the program.
 *  This class is a placeholder until a GUI is established
 */
public class UserInterface
{
	BookDisplayData displayBookData = new BookDisplayData();
	DatabaseDriver databaseDriver = new DatabaseDriver();
	
	static int selection;
	
	void beginProgram() throws ClassNotFoundException, SQLException
	{
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
    	System.out.println("Enter the website you want to scrape\n"
    			+ "1.Amazon Books 2.Barnes & Noble 3.Ebay Books: 4.All sites:\n");
       	
    	selection = scanner.nextInt();
    	
		if (selection != 1 &&  selection != 2 && selection != 3 && selection != 4)
		{
			System.out.println("Wrong input. Try Again.");
			System.exit(0);
		}
		if (selection != 4)
		{
			//TODO: books only display if a single site is selected, needs more functionality
			displayBookData.displayBooks();
		}
		
        databaseDriver.getBookProducts();
	}
}
