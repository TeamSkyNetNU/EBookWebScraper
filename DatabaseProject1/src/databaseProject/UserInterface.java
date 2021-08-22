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
	DisplayBookData displayBookData = new DisplayBookData();
	DatabaseDriver databaseDriver = new DatabaseDriver();

	static int selection;
	public static final String USER = "root";
	public static final String PASSWORD = "password";

	void run()
	{
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);

		verifyUser();

		System.out.println("Enter the website you want to scrape\n"
				+ "1.Amazon Books 2.Barnes & Noble 3.Ebay Books: 4.A&B 5.A&E 6.B&E 7.All:\n");

		selection = scanner.nextInt();
		if (selection > 7)
		{
			System.out.println("Wrong input. Please try again.");
			exit();
		}
//		if (selection != 4)
//		{
//			// TODO: books only display if a single site is selected, needs more
//			// functionality
//			displayBookData.displayBooks();
//		}

		databaseDriver.getBookProducts();
	}

	private void verifyUser()
	{
		String username = "";
		String password = "";

		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter Username:");

		username = scanner.nextLine();
		if (username.matches(USER))
		{
			System.out.println("Enter password:");

			username = scanner.nextLine();
			if (password.matches(PASSWORD))
			{
				System.out.println("Log In Successful!");
			}
		} else
		{
			System.out.println("Wrong username. Please try again.");
			exit();
		}
	}
	
	private void exit()
	{
		System.out.println("Shutting down EBook WebScraper.");
		System.exit(0);
	}

}
