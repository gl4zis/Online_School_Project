import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class StartPage extends Page {
    public StartPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "profile-button")
    private WebElement profileButton;

    public void profileButtonClick() {
        profileButton.click();
    }

    public String getProfileButtonText() {
        return profileButton.getText();
    }
}
