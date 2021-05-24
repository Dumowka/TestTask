package pages;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import static com.codeborne.selenide.Selenide.*;

public class BasicPage {
    private final SelenideElement footerSup223fzLink = $(By.xpath("//a[@href='/zakupki-223/participants-223']"));

    public SelenideElement getFooterSup223fzLink() {
        return footerSup223fzLink;
    }

    public void clickLink(SelenideElement link){
        link.click();
    }
}