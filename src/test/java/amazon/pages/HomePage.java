package amazon.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage {
    private By searchField = By.id("twotabsearchtextbox");
    private By searchButton = By.id("nav-search-submit-button");
    public WebElement getSearchField (WebDriver driver) {
        return driver.findElement(searchField);
    }
    public WebElement getSearchButton (WebDriver driver) {
        return driver.findElement(searchButton);
    }
}
