import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SignUpPage extends Page {
    public SignUpPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//*[@id=\"email-input\"]/div/input")
    private WebElement emailInput;
    @FindBy(xpath = "//*[@id=\"psswd-input\"]/div/div/input")
    private WebElement psswdInput;
    @FindBy(xpath = "//*[@id=\"firstname\"]/div/input")
    private WebElement firstnameInput;
    @FindBy(xpath = "//*[@id=\"lastname\"]/div/input")
    private WebElement lastnameInput;
    @FindBy(className = "return")
    private WebElement backButton;
    @FindBy(id = "sign-up-button")
    private WebElement confirmButton;

    public void signUp(String email, String password, String firstname, String lastname) {
        emailInput.sendKeys(email);
        psswdInput.sendKeys(password);
        firstnameInput.sendKeys(firstname);
        lastnameInput.sendKeys(lastname);
        confirmButton.click();
    }

    public void goSignIn() {
        backButton.click();
    }
}
