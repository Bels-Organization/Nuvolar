package amazon.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ResultsPage {
    private By resultItem = By.className("template=SEARCH_RESULTS");
    public List<WebElement> getResultItem (WebDriver driver) {
        return driver.findElements(resultItem);
    }
}
