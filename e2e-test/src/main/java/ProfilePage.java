import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProfilePage extends Page{
    private WebDriverWait wait;
    public ProfilePage(WebDriver driver) {
        super(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @FindBy(className = "return")
    private WebElement backButton;
    @FindBy(id = "logout-button")
    private WebElement logoutButton;
    @FindBy(id = "delete-button")
    private WebElement deleteButton;
    @FindBy(xpath = "/html/body/div[3]/div/div[3]/button[2]")
    private WebElement dialogConfirmButton;
    @FindBy(xpath = "//*[@id=\"email-input\"]/div/input")
    private WebElement emailInput;
    @FindBy(xpath = "//*[@id=\"firstname\"]/div/input")
    private WebElement firstnameInput;
    @FindBy(xpath = "//*[@id=\"lastname\"]/div/input")
    private WebElement lastnameInput;
    @FindBy(xpath = "//*[@id=\"old-password\"]/div/div")
    private WebElement oldPasswordInput;
    @FindBy(xpath = "//*[@id=\"new-password\"]/div/div")
    private WebElement newPasswordInput;
    @FindBy(xpath = "//*[@id=\"edit-password\"]/*[@id=\"edit\"]")
    private WebElement editPasswordButton;
    @FindBy(xpath = "//*[@id=\"edit-password\"]/*[@id=\"confirm\"]")
    private WebElement confirmPasswordButton;

    public void goStartPage() {
        backButton.click();
    }

    public void logout() {
        logoutButton.click();
        dialogConfirmButton.click();
    }

    public void deleteAccount() {
        deleteButton.click();
        dialogConfirmButton.click();
    }

    public String getFirstname() {
        return firstnameInput.getAttribute("value");
    }

    public String getLastname() {
        return lastnameInput.getAttribute("value");
    }

    public String getEmail() {
        return emailInput.getAttribute("value");
    }

    public void changePassword(String oldP, String newP) {
        editPasswordButton.click();
        oldPasswordInput.sendKeys(oldP);
        newPasswordInput.sendKeys(newP);
        confirmPasswordButton.click();
    }
}
