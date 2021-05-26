package steps;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.openqa.selenium.By;
import pages.BasicPage;
import pages.Poisk223fzPage;
import pages.PurchasesParticipants223Page;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

import static com.codeborne.selenide.Selenide.*;

public class BasicPageDef {
    BasicPage basicPage = new BasicPage();
    Poisk223fzPage poisk223fzPage = new Poisk223fzPage();
    PurchasesParticipants223Page zakupkiParticipants223Page = new PurchasesParticipants223Page();
    private static final Logger log = Logger.getLogger(String.valueOf(BasicPageDef.class));

    @Given("Сайт https:\\/\\/www.rts-tender.ru\\/")
    public void WwwRtsTenderRu() {
        open("https://www.rts-tender.ru/");
    }

    @Then("Нажатие в футере в разделе поставщиков на ссылку 223_ФЗ")
    public void ClickInTheFooterLink() {
        basicPage.clickOnElement(basicPage.getFooterSup223fzLink());
    }

    @Then("Нажатие на расширенный поиск")
    public void ClickingOnAdvencedSearch() {
        zakupkiParticipants223Page.clickOnElement(zakupkiParticipants223Page.getAdvancedSearchLink());
    }

    @Then("Нажатие на настройки поиска")
    public void ClickingOnSearchSettings() {
        poisk223fzPage.clickOnElement(poisk223fzPage.getSearchSettings());
    }

    @Then("Поставить чекбокс на 615_ПП ФР")
    public void CheckTheBoxNextTo615_PP_FR() {
        poisk223fzPage.clickOnElement(poisk223fzPage.getCheckBox_615pp_rf());
    }

    @Then("Поставить чекбокс на исключить совместные покупки")
    public void CheckingTheBoxNextToExcludeJointPurchases() {
        poisk223fzPage.clickOnElement(poisk223fzPage.getExcludeJointPurchases());
    }

    @Then("Нажатие на фильтр по датам и установка даты")
    public void ClickingOnTheFilterByDateAndSettingTheDate() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        poisk223fzPage.clickOnElement(poisk223fzPage.getFilterByDates());
        poisk223fzPage.getDatepickerLastTime().click();
        $(By.xpath("//select[@class='react-datepicker__year-select']/option[@value="+ calendar.get(Calendar.YEAR) +"]")).waitUntil(Condition.visible, 500).click();
        $(By.xpath("//select[@class='react-datepicker__month-select']/option[@value="+ calendar.get(Calendar.MONTH) +"]")).waitUntil(Condition.visible, 500).click();
        $(By.cssSelector(".react-datepicker__day--0" + calendar.get(Calendar.DAY_OF_MONTH))).waitUntil(Condition.visible, 500).click();
    }

    @Then("Выбрать регион поставки Алтайский край")
    public void SelectDeliveryRegionAltaiRegion() {
        poisk223fzPage.clickOnElement(poisk223fzPage.getDeliveryRegion());
        poisk223fzPage.clickOnElement(poisk223fzPage.getLinkRemoveAll());
        poisk223fzPage.clickOnElement(poisk223fzPage.getCheckBoxAltaiRegion());
    }

    @Then("Получить результат поиска")
    public void GetSearchResult() {
        try {
            poisk223fzPage.clickOnElement(poisk223fzPage.getButtonSearch());
            FileOutputStream fileOutputStream = new FileOutputStream("notes.txt");
            // Вызываем и закрываем окно консультации, чтобы оно в дальнейшем не помешало
            poisk223fzPage.clickOnElement(poisk223fzPage.getButtonConsultation());
            poisk223fzPage.clickOnElement(poisk223fzPage.getButtonCloseConsultation());

            // Проверяем, если существует кнопка для показания доп результатов и нажимаем на нее, пока она не исчезнет
            SelenideElement loadMore = poisk223fzPage.getButtonLoadMore();
            int AllowableNumberOfLoopIterations = 50;
            while(loadMore.isDisplayed()){
                poisk223fzPage.clickOnElement(loadMore);
                sleep(500);
                AllowableNumberOfLoopIterations--;
                if (AllowableNumberOfLoopIterations == 0){
                    throw new Exception("Превышено число итераций для цикла, отвечающего за прогрузку дополнительных результатов поиска");
                }
            }
            // Получение результатов
            for (int i = 0; i < poisk223fzPage.getCountNotifications(); i++){
                // Получаем div закупкой
                SelenideElement card = poisk223fzPage.getSearchResult().get(i);
                // Кликаем на него, чтобы подняться или опуститься к нему
                poisk223fzPage.clickOnElement(card);
                // Получаем начальную цену
                String value = poisk223fzPage.getStartingPrice(card);
                // Получаем элемент показать еще позиции, если он существует
                SelenideElement spanShowMore = poisk223fzPage.getSpanShowMore(card);
                if (spanShowMore != null){
                    poisk223fzPage.clickOnElement(spanShowMore);
                }
                // Строим результат
                StringBuilder string = new StringBuilder();
                string.append(i+1);
                string.append(" : ");
                string.append(value);
                string.append(", ");
                string.append(poisk223fzPage.getCountInCardResult(card));
                string.append(" штук");
                string.append("\n");
                byte[] text = string.toString().getBytes();
                fileOutputStream.write(text, 0, text.length);
                log.info(value);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
