import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.html5.LocalStorage;
import org.openqa.selenium.html5.WebStorage;
import org.openqa.selenium.remote.Augmenter;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class RegistrationTest {
    private WebDriver driver;
    private UserClient userClient;
    private MainPage page;
    private String name;
    private String email;
    private String password;
    private String passwordIncorrect;
    private int count = 10;
    private int countForPasswordIncorrect = 1;
    private boolean checkNeedSetYandexDriver;

    public RegistrationTest(boolean checkNeedSetYandexDriver) {
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
        passwordIncorrect = RandomStringUtils.randomAlphabetic(countForPasswordIncorrect);
    }

    @Test
    public void registrationWithCorrectPasswordTest() {
        boolean flag = page.open()
                .clickPersonalAccountButton()
                .clickRegistrationButton()
                .registrationUser(name, email, password)
                .expectPersonalAccount()
                .checkElementIn();
        assertTrue(flag);
    }

    @Test
    public void registrationWithExistUserTest() {
        boolean flag = page.open()
                .clickPersonalAccountButton()
                .clickRegistrationButton()
                .registrationUser(name, email, password)
                .expectPersonalAccount()
                .clickRegistrationButton()
                .registrationUserError(name, email, password)
                .scroll()
                .checkErrorRegistrationExistUser();
        assertTrue(flag);
    }

    @Test
    public void registrationWithIncorrectPasswordTest() {
        boolean flag = page.open()
                .clickPersonalAccountButton()
                .clickRegistrationButton()
                .registrationUserError(name, email, passwordIncorrect)
                .checkErrorRegistrationIncorrectPassword();
        assertTrue(flag);
    }

    @After
    public void teardown() {
        page.open()
                .clickPersonalAccountButton()
                .logInUser(email, password);
        WebStorage webStorage = (WebStorage) new Augmenter().augment(driver);
        LocalStorage localStorage = webStorage.getLocalStorage();
        String accessToken = localStorage.getItem("accessToken");
        userClient.deleteUser(accessToken);
        driver.quit();
    }
}
