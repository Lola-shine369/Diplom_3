import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class TransitionTest {
    private WebDriver driver;
    private MainPage page;
    private int count = 10;
    private boolean checkNeedSetYandexDriver;

    public TransitionTest(boolean checkNeedSetYandexDriver) {
        this.checkNeedSetYandexDriver = checkNeedSetYandexDriver;
    }

    @Parameterized.Parameters
    public static Object[][] testData() {
        return new Object[][] {
                {false},
                {true}
        };
    }

    @Before
    public void startUp() {
        if (checkNeedSetYandexDriver) {
            System.setProperty("webdriver.chrome.driver",
                    "C:\\Users\\Olya\\Diplom\\Diplom_3\\src\\main\\resources\\yandexdriver.exe");
        }

        System.setProperty("webdriver.chrome.driver",
                "C:\\Users\\Olya\\Diplom\\Diplom_3\\src\\main\\resources\\chromedriver.exe");

        driver = new ChromeDriver();
        page = new MainPage(driver);
    }

    @Test
    public void logInTransitionInPersonalAccountTest() {
        boolean flag = page.open()
                .clickPersonalAccountButton()
                .expectPersonalAccount()
                .checkElementIn();
        assertTrue(flag);
    }

    @Test
    public void logInTransitionInMainPageAcrossConstructorTest() {
        boolean flag = page.open()
                .clickPersonalAccountButton()
                .expectPersonalAccount()
                .clickConstructorButton()
                .expectMainPageLogInAccount()
                .checkButtonLogInAccount();
        assertTrue(flag);
    }

    @Test
    public void logInTransitionInMainPageAcrossLogoTipTest() {
        boolean flag = page.open()
                .clickPersonalAccountButton()
                .expectPersonalAccount()
                .clickButtonLogo()
                .expectMainPageLogInAccount()
                .checkButtonLogInAccount();
        assertTrue(flag);
    }

    @Test
    public void transitionInSousTest() {
        boolean flag = page.open()
                .clickSousButton()
                .checkButtonSousLight();
        assertTrue(flag);
    }

    @Test
    public void transitionInBulkTest() {
        boolean flag = page.open()
                .clickSousButton()
                .clickBulksButton()
                .checkButtonBulksLight();
        assertTrue(flag);
    }

    @Test
    public void transitionInFillingsTest() {
        boolean flag = page.open()
                .clickFillingsButton()
                .checkButtonFillingsLight();
        assertTrue(flag);
    }

    @After
    public void teardown() {
        // Закрытие браузера
        driver.quit();
    }
}