import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SignInPage extends Page {
    public SignInPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(className = "confirm-button")
    private WebElement confirmButton;
    @FindBy(xpath = "//*[@id=\"email-input\"]/div/input")
    private WebElement emailInput;
    @FindBy(xpath = "//*[@id=\"psswd-input\"]/div/div/input")
    private WebElement psswdInput;
    @FindBy(id = "sign-up-link")
    private WebElement signUpLink;
    @FindBy(className = "return")
    private WebElement backButton;

    public void inputEmail(String email) {
        emailInput.sendKeys(email);
    }

    public void inputPassword(String password) {
        psswdInput.sendKeys(password);
    }

    public void signIn() {
        confirmButton.click();
    }

    public void goSignUp() {
        signUpLink.click();
    }

    public void goStart() {
        backButton.click();
    }
}
