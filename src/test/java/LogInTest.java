import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class LogInTest {
    private WebDriver driver;
    private UserClient userClient;
    private MainPage page;
    private User user;
    private String name;
    private String email;
    private String password;
    private String accessToken;
    private int count = 10;
    private boolean checkNeedSetYandexDriver;

    public LogInTest(boolean checkNeedSetYandexDriver) {
        this.checkNeedSetYandexDriver = checkNeedSetYandexDriver;
    }

    @Parameterized.Parameters
    public static Object[][] testData() {
        return new Object[][]{
                {false},
                {true}
        };
    }

    @Before
    public void startUp() {
        String yandexDriverPath = System.getProperty("yandexDriverPath", "src/main/resources/yandexdriver.exe");
        String chromeDriverPath = System.getProperty("chromeDriverPath", "src/main/resources/chromedriver.exe");

        if (checkNeedSetYandexDriver) {
            System.setProperty("webdriver.chrome.driver", yandexDriverPath);
        } else {
            System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        }

        driver = new ChromeDriver();
        userClient = new UserClient();
        page = new MainPage(driver);

        name = RandomStringUtils.randomAlphabetic(count);
        email = RandomStringUtils.randomAlphabetic(count) + "@mail.ru";
        password = RandomStringUtils.randomAlphabetic(count);
        user = new User(email, password, name);
        ValidatableResponse responseCreate = userClient.createUser(user);
        accessToken = responseCreate.extract().path("accessToken");
    }

    @Test
    public void logInAcrossButtonOnMainTest() {
        boolean flag = page.open()
                .clickGoInAccountButton()
                .logInUser(email, password)
                .expectMainPage()
                .checkButtonCreateOrder();
        assertTrue(flag);
    }

    @Test
    public void logInAcrossPersonalAccountTest() {
        boolean flag = page.open()
                .clickPersonalAccountButton()
                .logInUser(email, password)
                .expectMainPage()
                .checkButtonCreateOrder();
        assertTrue(flag);
    }

    @Test
    public void logInAcrossFormRegistrationTest() {
        boolean flag = page.open()
                .clickPersonalAccountButton()
                .clickRegistrationButton()
                .clickButtonInOnPageRegistration()
                .logInUser(email, password)
                .expectMainPage()
                .checkButtonCreateOrder();
        assertTrue(flag);
    }

    @Test
    public void logInAcrossFormReanimatePasswordTest() {
        boolean flag = page.open()
                .clickPersonalAccountButton()
                .clickReanimatePasswordButton()
                .clickLogInButton()
                .logInUser(email, password)
                .expectMainPage()
                .checkButtonCreateOrder();
        assertTrue(flag);
    }

    @Test
    public void logOutTest() {
        boolean flag = page.open()
                .clickPersonalAccountButton()
                .logInUser(email, password)
                .expectMainPage()
                .clickPersonalAccountButton()
                .expectPersonalAccountButtonLogout()
                .clickLogOutButton()
                .expectPersonalAccount()
                .checkElementIn();
        assertTrue(flag);
    }

    @After
    public void teardown() {
        userClient.deleteUser(accessToken);
        driver.quit();
    }
}


