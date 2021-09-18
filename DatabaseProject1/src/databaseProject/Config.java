package databaseProject;

import java.util.Properties;

/*
 * This class retrieves sensitive values from the config.cfg file
 */

public class Config
{
	Properties configFile;

	public Config()
	{
		configFile = new Properties();

		try
		{
			configFile.load(BookScraperMain.class.getResourceAsStream("/config.properties"));
		} catch (Exception ex)
		{
			System.out.println("Config file cannot be found");
		}
	}

	public String getProperty(String key)
	{
		String value = this.configFile.getProperty(key);
		return value;
	}
}
