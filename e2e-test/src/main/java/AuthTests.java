import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.html5.WebStorage;

import java.time.Duration;

public class AuthTests {
    private static final String HOME_URL = "http://localhost:8000";
    private static final String PROFILE_URL = "/profile";
    private static final String SIGN_IN_TEXT = "Sign In";
    private static final String ADMIN_EMAIL = "admin@adminov.com";
    private static final String ADMIN_NAME = "Roma Makeev";
    private static final String ADMIN_PASSWORD = "Qwerty123!";
    private static final String TEST_EMAIL = "new@email.com";
    private static final String TEST_PASSWORD = "Qwerty123!";
    private static final String TEST_FIRSTNAME = "Test";
    private static final String TEST_LASTNAME = "Testovich";
    private static WebDriver driver;
    private static StartPage startPage;
    private static SignInPage signInPage;
    private static SignUpPage signUpPage;
    private static ProfilePage profilePage;

    @BeforeAll
    static void setup() {
        driver = WebDriverManager.chromedriver().create();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get(HOME_URL);
        startPage = new StartPage(driver);
        signInPage = new SignInPage(driver);
        signUpPage = new SignUpPage(driver);
        profilePage = new ProfilePage(driver);
    }

    @AfterEach
    void clear() {
        driver.manage().deleteAllCookies();
        ((WebStorage)driver).getLocalStorage().clear();
        ((WebStorage)driver).getSessionStorage().clear();
        driver.get(HOME_URL);
    }

    @Test
    void loginTest() {
        login(ADMIN_EMAIL, ADMIN_PASSWORD);
        Assertions.assertEquals(ADMIN_NAME, startPage.getProfileButtonText());
        startPage.profileButtonClick();
        Assertions.assertEquals(HOME_URL + PROFILE_URL, driver.getCurrentUrl());
    }

    @Test
    void loginWithPageRefreshTest() {
        login(ADMIN_EMAIL, ADMIN_PASSWORD);
        driver.navigate().refresh();
        Assertions.assertEquals(ADMIN_NAME, startPage.getProfileButtonText());
        startPage.profileButtonClick();
        Assertions.assertEquals(HOME_URL + PROFILE_URL, driver.getCurrentUrl());
    }

    public void login(String login, String password) {
        startPage.profileButtonClick();
        signInPage.inputEmail(login);
        signInPage.inputPassword(password);
        signInPage.signIn();
        waitAuth();
    }

    @Test
    public void validSignUpTest() {
        signUp(TEST_EMAIL, TEST_PASSWORD, TEST_FIRSTNAME, TEST_LASTNAME);
        waitAuth();
        driver.navigate().refresh();
        Assertions.assertEquals(TEST_FIRSTNAME + " " + TEST_LASTNAME, startPage.getProfileButtonText());
        startPage.profileButtonClick();
        Assertions.assertEquals(TEST_EMAIL, profilePage.getEmail());
        Assertions.assertEquals(TEST_FIRSTNAME, profilePage.getFirstname());
        Assertions.assertEquals(TEST_LASTNAME, profilePage.getLastname());
        profilePage.logout();
        signInPage.goStart();
        Assertions.assertEquals(SIGN_IN_TEXT, startPage.getProfileButtonText());
        login(TEST_EMAIL, TEST_PASSWORD);
        startPage.profileButtonClick();
        profilePage.deleteAccount();
    }

    public void signUp(String login, String password, String firstname, String lastname) {
        startPage.profileButtonClick();
        signInPage.goSignUp();
        signUpPage.signUp(login, password, firstname, lastname);
    }

    public static void waitUntilURL(Duration duration, String URL) {
        if (!URL.endsWith("/")) URL = URL + "/";
        long waitTime;
        long startTime = System.currentTimeMillis();
        while (!driver.getCurrentUrl().equals(URL)) {
            waitTime = System.currentTimeMillis() - startTime;
            if (waitTime > duration.toMillis()) break;
        }

        if (!driver.getCurrentUrl().equals(URL))
            throw new RuntimeException("Time's up!");
    }

    public static void waitAuth() {
        waitUntilURL(Duration.ofSeconds(10), HOME_URL);
    }
}
