package pages;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class ZakupkiParticipants223Page {
    private final SelenideElement advancedSearchLink = $(By.xpath("//a[@href='/poisk/poisk-223-fz/']"));

    public SelenideElement getAdvancedSearchLink() {
        return advancedSearchLink;
    }

    public void clickLink(SelenideElement link){
        link.click();
    }
}
