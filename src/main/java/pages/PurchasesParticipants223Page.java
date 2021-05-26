package pages;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class PurchasesParticipants223Page extends BasicPage{
    private final SelenideElement advancedSearchLink = $(By.xpath("//a[@href='/poisk/poisk-223-fz/']"));

    public SelenideElement getAdvancedSearchLink() {
        return advancedSearchLink;
    }
}
