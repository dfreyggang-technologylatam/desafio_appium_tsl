package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class CartPage {

    private final AndroidDriver driver;
    private final WebDriverWait wait;

    private final By cartScreen = AppiumBy.accessibilityId("test-Cart Content");
    private final By cartItem = AppiumBy.accessibilityId("test-Item");
    private final By removeButton = AppiumBy.accessibilityId("test-REMOVE");
    private final By checkoutButton = AppiumBy.accessibilityId("test-CHECKOUT");

    public CartPage(AndroidDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public boolean isProductInCart() {
        wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(cartItem),
                ExpectedConditions.visibilityOfElementLocated(removeButton),
                ExpectedConditions.visibilityOfElementLocated(cartScreen)));
        return !driver.findElements(cartItem).isEmpty()
                || !driver.findElements(removeButton).isEmpty();
    }

    public void proceedToCheckout() {
        scrollToCheckout();
        wait.until(ExpectedConditions.elementToBeClickable(checkoutButton)).click();
    }

    private void scrollToCheckout() {
        try {
            driver.findElement(AppiumBy.androidUIAutomator(
                    "new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView("
                            + "new UiSelector().description(\"test-CHECKOUT\"))"));
        } catch (Exception ignored) {
            
        }
    }
}
