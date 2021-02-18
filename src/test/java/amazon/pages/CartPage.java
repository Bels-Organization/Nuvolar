package amazon.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CartPage {
    private By totalPrice = By.id("sc-subtotal-amount-activecart");
    private By item = By.className("sc-list-item-content");
    private By itemPrice = By.className("sc-product-price");
    private By itemQuantity = By.name("quantity");

    public WebElement getTotalPrice (WebDriver driver) {
        return driver.findElement(totalPrice);
    }

    public List<WebElement> getItem (WebDriver driver) {
        return driver.findElements(item);
    }

    public WebElement getItemPrice (WebDriver driver, int index) {
        List<WebElement> selectedItem = getItem(driver);
        return selectedItem.get(index).findElement(itemPrice);
    }

    public WebElement getItemQuantity (WebDriver driver, int index) {
        List<WebElement> selectedItem = getItem(driver);
        try {
            return selectedItem.get(index).findElement(itemQuantity);
        } catch (NoSuchElementException e) {
            return null;
        }
    }
}
