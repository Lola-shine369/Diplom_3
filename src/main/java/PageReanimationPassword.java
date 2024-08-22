import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PageReanimationPassword {
    private final WebDriver driver;

    public PageReanimationPassword(WebDriver driver) {
        this.driver = driver;
    }

    private final By buttonLogIn = By.xpath
            (".//a[@class = 'Auth_link__1fOlj'][text() = 'Войти']");

    public PagePersonalAccount clickLogInButton() {
        if (driver.findElement(buttonLogIn).isDisplayed()) {
            driver.findElement(buttonLogIn).click();
        }
        return new PagePersonalAccount(driver);
    }
}