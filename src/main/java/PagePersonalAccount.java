import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PagePersonalAccount {
    private final WebDriver driver;

    public PagePersonalAccount(WebDriver driver) {
        this.driver = driver;
    }

    //локатор поля ввода email
    private final By fieldEmail = By.xpath(".//input[@name = 'name']");

    //локатор поля ввода password
    private final By fieldPassword = By.xpath(".//input[@name = 'Пароль']");

    //локатор кнопки зарегистрироваться
    private final By buttonRegistration = By.xpath
            (".//a[@class = 'Auth_link__1fOlj'][text() = 'Зарегистрироваться']");

    //локатор кнопки войти
    private final By buttonIn = By.xpath
            (".//button[@class = 'button_button__33qZ0 button_button_type_primary__1O7Bx " +
                    "button_button_size_medium__3zxIa'][text() = 'Войти']");

    //локатор элемента вход
    private final By elementIn = By.xpath(".//h2[text() = 'Вход']");

    private final By buttonConstructor = By.xpath(".//p[@class = " +
            "'AppHeader_header__linkText__3q_va ml-2'][text() = 'Конструктор']");

    //локатор кнопки восстановления пароля
    private final By buttonReanimatePassword = By.xpath(".//a[@class = " +
            "'Auth_link__1fOlj'][text() = 'Восстановить пароль']");

    //локатор логотипа
    private final By buttonLogoTip = By.xpath("html/body/div[@id='root']/div/header/nav/div/a");

    //локатор выхода из личного кабинета
    private final By buttonLogOut = By.xpath(".//button[@class = " +
            "'Account_button__14Yp3 text text_type_main-medium text_color_inactive'][text() = 'Выход']");

    public PageRegistration clickRegistrationButton() {
        if (driver.findElement(buttonRegistration).isDisplayed()) {
            driver.findElement(buttonRegistration).click();
        }
        return new PageRegistration(driver);
    }

    public PagePersonalAccount clickLogOutButton() {
        if (driver.findElement(buttonLogOut).isDisplayed()) {
            driver.findElement(buttonLogOut).click();
        }
        return this;
    }

    public MainPage clickConstructorButton() {
        if (driver.findElement(buttonConstructor).isDisplayed()) {
            driver.findElement(buttonConstructor).click();
        }
        return new MainPage(driver);
    }

    public PageReanimationPassword clickReanimatePasswordButton() {
        if (driver.findElement(buttonReanimatePassword).isDisplayed()) {
            driver.findElement(buttonReanimatePassword).click();
        }
        return new PageReanimationPassword(driver);
    }

    public PagePersonalAccount clickAndInputEmail(String email) {
        driver.findElement(fieldEmail).click();
        driver.findElement(fieldEmail).sendKeys(email);
        return this;
    }

    public PagePersonalAccount clickAndInputPassword(String password) {
        driver.findElement(fieldPassword).click();
        driver.findElement(fieldPassword).sendKeys(password);
        return this;
    }

    public MainPage clickButtonIn() {
        driver.findElement(buttonIn).click();
        return new MainPage(driver);
    }

    public MainPage clickButtonLogo() {
        driver.findElement(buttonLogoTip).click();
        return new MainPage(driver);
    }

    public boolean checkElementIn() {
        return driver.findElement(elementIn).isDisplayed();
    }

    public PagePersonalAccount expectPersonalAccount() {
        new WebDriverWait(driver, 1000).until(ExpectedConditions.
                textToBePresentInElementLocated(elementIn, "Вход"));
        return this;
    }

    public PagePersonalAccount expectPersonalAccountButtonLogout() {
        new WebDriverWait(driver, 1000).until(ExpectedConditions.
                textToBePresentInElementLocated(buttonLogOut, "Выход"));
        return this;
    }

    public MainPage logInUser(String email, String password) {
        return new PagePersonalAccount(driver)
                .clickAndInputEmail(email)
                .clickAndInputPassword(password)
                .clickButtonIn();
    }
}