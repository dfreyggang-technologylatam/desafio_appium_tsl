package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Pantallas de Checkout (formulario, overview y complete) de Swag Labs.
 */
public class CheckoutPage {

    private final AndroidDriver driver;
    private final WebDriverWait wait;

    private final By firstNameField = AppiumBy.accessibilityId("test-First Name");
    private final By lastNameField = AppiumBy.accessibilityId("test-Last Name");
    private final By zipField = AppiumBy.accessibilityId("test-Zip/Postal Code");
    private final By continueButton = AppiumBy.accessibilityId("test-CONTINUE");
    private final By finishButton = AppiumBy.accessibilityId("test-FINISH");
    private final By thankYouMessage = AppiumBy.androidUIAutomator(
            "new UiSelector().textContains(\"THANK YOU\")");

    public CheckoutPage(AndroidDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void fillCheckoutForm(String firstName, String lastName, String zip) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameField)).sendKeys(firstName);
        driver.findElement(lastNameField).sendKeys(lastName);
        driver.findElement(zipField).sendKeys(zip);
        try {
            driver.hideKeyboard();
        } catch (Exception ignored) {
            // teclado ya oculto
        }
        wait.until(ExpectedConditions.elementToBeClickable(continueButton)).click();
    }

    public String getTotalText() {
        scrollToText("Total");
        // En overview el total suele mostrarse como texto "Total: $XX.XX"
        By totalText = AppiumBy.androidUIAutomator("new UiSelector().textContains(\"Total:\")");
        WebElement total = wait.until(ExpectedConditions.visibilityOfElementLocated(totalText));
        return total.getText();
    }

    public void finishOrder() {
        scrollToText("FINISH");
        wait.until(ExpectedConditions.elementToBeClickable(finishButton)).click();
    }

    public String getThankYouMessage() {
        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(thankYouMessage));
        String text = message.getText();
        if (text == null || text.isBlank()) {
            var texts = message.findElements(AppiumBy.className("android.widget.TextView"));
            StringBuilder sb = new StringBuilder();
            for (WebElement t : texts) {
                String part = t.getText();
                if (part != null && !part.isBlank()) {
                    if (sb.length() > 0) {
                        sb.append(' ');
                    }
                    sb.append(part);
                }
            }
            text = sb.toString();
        }
        return text;
    }

    private void scrollToText(String text) {
        driver.findElement(AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView("
                        + "new UiSelector().textContains(\"" + text + "\"))"));
    }
}
