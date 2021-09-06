package databaseProject;

/*
 * 
 *  This class takes user input selection on how they want to run the program.
 *  This class is a placeholder until a GUI is established
 */
public class UserInterface
{
	DisplayBookData displayBookData = new DisplayBookData();
	DatabaseDriver databaseDriver = new DatabaseDriver();
	static Config cfg = new Config();

	static int selection;
	public static final String USER = cfg.getProperty("mDbUser");
	public static final String PASSWORD = cfg.getProperty("mDbPwd");

	void run()
	{
		databaseDriver.getBookProducts(); // we don't need run() anymore it seems
	}

	public static boolean verifyUser(String username, String password)
	{
		return username.matches(USER) && password.matches(PASSWORD);
	}

	public static void exit()
	{
		System.out.println("Shutting down EBook WebScraper.");
		System.exit(0);
	}
}
