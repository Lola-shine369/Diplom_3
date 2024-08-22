import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PageRegistration {
    private final WebDriver driver;

    public PageRegistration(WebDriver driver) {
        this.driver = driver;
    }

    //локатор поля ввода имени
    private final By fieldName = By.xpath
            (".//fieldset[1]/div/div/input");

    //локатор поля ввода email
    private final By fieldEmail = By.xpath
            (".//fieldset[2]/div/div/input");

    //локатор поля ввода password
    private final By fieldPassword = By.xpath
            (".//fieldset[3]/div/div/input");

    //локатор кнопки зарегистрироваться
    private final By buttonRegistration = By.xpath(".//button[@class = " +
            "'button_button__33qZ0 button_button_type_primary__1O7Bx button_button_size_medium__3zxIa']");

    //локатор кнопки Войти
    private final By buttonLogIn = By.xpath(".//a[@class = 'Auth_link__1fOlj'][text() = 'Войти']");

    //локатор элемента существующего пользователя
    private final By errorElementExistUser = By.xpath
            (".//p[@class = 'input__error text_type_main-default']" +
                    "[text() = 'Такой пользователь уже существует']");

    //локатор элемента некорректного пароля
    private final By errorElementIncorrectPassword = By.xpath
            (".//p[@class = 'input__error text_type_main-default']" +
                    "[text() = 'Некорректный пароль']");


    public PageRegistration clickAndInputName(String name) {
        driver.findElement(fieldName).click();
        driver.findElement(fieldName).sendKeys(name);
        return this;
    }

    public PageRegistration clickAndInputEmail(String email) {
        driver.findElement(fieldEmail).click();
        driver.findElement(fieldEmail).sendKeys(email);
        return this;
    }

    public PageRegistration clickAndInputPassword(String password) {
        driver.findElement(fieldPassword).click();
        driver.findElement(fieldPassword).sendKeys(password);
        return this;
    }

    public PagePersonalAccount clickButtonRegistration() {
        driver.findElement(buttonRegistration).click();
        new WebDriverWait(driver, 100);
        return new PagePersonalAccount(driver);
    }

    public PageRegistration clickButtonRegistrationError() {
        driver.findElement(buttonRegistration).click();
        return this;
    }

    public PagePersonalAccount clickButtonInOnPageRegistration() {
        driver.findElement(buttonLogIn).click();
        return new PagePersonalAccount(driver);
    }

    public boolean checkErrorRegistrationExistUser() {
        return driver.findElement(errorElementExistUser).isDisplayed();
    }

    public boolean checkErrorRegistrationIncorrectPassword() {
        return driver.findElement(errorElementIncorrectPassword).isDisplayed();
    }

    public PageRegistration scroll() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, Math.max" +
                "(document.documentElement.scrollHeight, document.body.scrollHeight, " +
                "document.documentElement.clientHeight));");
        new WebDriverWait(driver, 1000).until(ExpectedConditions.
                textToBePresentInElementLocated(errorElementExistUser, "Такой пользователь уже существует"));
        return this;
    }

    public PagePersonalAccount registrationUser(String name, String email, String password) {
        return new PageRegistration(driver)
                .clickAndInputName(name)
                .clickAndInputEmail(email)
                .clickAndInputPassword(password)
                .clickButtonRegistration();
    }

    public PageRegistration registrationUserError(String name, String email, String password) {
        return new PageRegistration(driver)
                .clickAndInputName(name)
                .clickAndInputEmail(email)
                .clickAndInputPassword(password)
                .clickButtonRegistrationError();
    }
}