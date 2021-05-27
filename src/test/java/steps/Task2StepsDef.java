package steps;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import pages.BasicPage;
import pages.PurchasesParticipants223Page;
import pages.Search223fzPage;
import java.util.ArrayList;
import java.util.logging.Logger;

import static com.codeborne.selenide.Selenide.*;

public class Task2StepsDef {
    BasicPage basicPage = new BasicPage();
    Search223fzPage search223FzPage = new Search223fzPage();
    PurchasesParticipants223Page zakupkiParticipants223Page = new PurchasesParticipants223Page();
    private static final Logger log = Logger.getLogger(String.valueOf(BasicPageDef.class));

    @Then("Получение всех номеров доступных закупок со страницы и проверить, что закупка каждая закупка встречается только раз")
    public void gettingAllAvailablePurchasesFromThePage() {
        search223FzPage.clickOnElement(search223FzPage.getButtonConsultation());
        search223FzPage.clickOnElement(search223FzPage.getButtonCloseConsultation());
        try{
            SelenideElement loadMore = search223FzPage.getButtonLoadMore();
            int allowableNumberOfLoopIterations = 1000;
            while(loadMore.isDisplayed()){
                search223FzPage.clickOnElement(loadMore);
                sleep(1000);
                allowableNumberOfLoopIterations--;
                if (allowableNumberOfLoopIterations == 0){
                    throw new Exception("Превышено число итераций для цикла, отвечающего за прогрузку дополнительных результатов поиска");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        final ElementsCollection purchaseNumbers = search223FzPage.getPurchaseNumbers();
        ArrayList<String> numbers = new ArrayList<>();
        // Получаем номера поставщиков
        for (SelenideElement element : purchaseNumbers){
            numbers.add(element.getText().split(" ")[1]);
        }
        try {
            for (String number : numbers) {
                search223FzPage.getInputSearch().waitUntil(Condition.visible, 500).sendKeys(number);
                search223FzPage.clickOnElement(search223FzPage.getButtonMainSearch());
                sleep(1000);
                uniquenessCheck();
                equalsNumbers(number);
                SelenideElement searchClear = $(".search-tab__clear");
                searchClear.waitUntil(Condition.visible, 500).click();
            }
        }catch (Error error){
            log.info("Найдено более одной закупки");
        }
    }

    public void uniquenessCheck(){
        int count = search223FzPage.getCountNotifications();
        Assert.assertEquals(1, count);
    }

    public void equalsNumbers(String text){
        String number = $x("//div[@class='card-item__about']//a").getText().split(" ")[1];
        Assert.assertEquals(number, text);
    }
}
