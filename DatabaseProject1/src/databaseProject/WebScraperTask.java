package databaseProject;

import javafx.concurrent.Task;

public class WebScraperTask extends Task<String> {
    @Override
    protected String call() throws Exception {
        System.out.println("Task Started");
        websiteSystemMessage();
        DatabaseDriver.getBookProducts();
        System.out.println("Task Completed");
        return "Extraction Complete";
    }

    void websiteSystemMessage() {
        switch (UserInterface.selection) {
            case 1:
                updateValue("Extracting Data from Amazon");
                break;
            case 2:
                updateValue("Extracting Data from Barnes & Noble");
                break;
            case 3:
                updateValue("Extracting Data from Ebay");
                break;
            case 4:
                updateValue("Extracting Data from Amazon and Barnes & Noble");
                break;
            case 5:
                updateValue("Extracting Data from Amazon and Ebay");
                break;
            case 6:
                updateValue("Extracting Data from Barnes & Noble and Ebay");
                break;
            case 7:
                updateValue("Extracting Data from all three sites");
                break;
            default:
                break;
        }
    }
}
