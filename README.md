# Welcome to the Ebook WebScraper!

**Team SkyNet Developers:**
- Juan Guzman
- Jordan Walker
- Jonathan Franche
- Thomas Ainesworth

**Below are the requirements to run the program on your Java IDE:**

The "/resources" folder must be added to the Java Build Path.
config.properties file in the resources folder contains four variables that must be set to the MySQL database
information you are using.

For example:

mDbUser = student

mDbHost = localhost:3306

mDbPwd = password123

mDbName = myDb


**Must have at minimum:**
- Java version installed: Java jdk-16
- JavaFX version installed: Javafx-sdk-11.0
- Java Compiler compliance level: Java 11

**The following jars must also be added to the Classpath:**
- mysql-connector-java-8.0.25.jar
- jsoup-1.14.1.jar

**Run as Java application with VM arguments below.**
Java run configuration VM arguments must be:

--module-path="...location of your Javafx sdk lib folder here..."
--add-modules=javafx.controls,javafx.fxml

Example below:

--module-path="C:\Program Files\openjfx-11.0.2_windows-x64_bin-sdk\javafx-sdk-11.0.2\lib"
--add-modules=javafx.controls,javafx.fxml
