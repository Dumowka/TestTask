package steps;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.openqa.selenium.By;
import pages.BasicPage;
import pages.Search223fzPage;
import pages.PurchasesParticipants223Page;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

import static com.codeborne.selenide.Selenide.*;

public class BasicPageDef {
    BasicPage basicPage = new BasicPage();
    Search223fzPage search223FzPage = new Search223fzPage();
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
        search223FzPage.clickOnElement(search223FzPage.getSearchSettings());
    }

    @Then("Поставить чекбокс на 615_ПП ФР")
    public void CheckTheBoxNextTo615_PP_FR() {
        search223FzPage.clickOnElement(search223FzPage.getCheckBox_615pp_rf());
    }

    @Then("Поставить чекбокс на исключить совместные покупки")
    public void CheckingTheBoxNextToExcludeJointPurchases() {
        search223FzPage.clickOnElement(search223FzPage.getExcludeJointPurchases());
    }

    @Then("Нажатие на фильтр по датам и установка даты")
    public void ClickingOnTheFilterByDateAndSettingTheDate() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        search223FzPage.clickOnElement(search223FzPage.getFilterByDates());
        search223FzPage.getDatepickerLastTime().click();
        $(By.xpath("//select[@class='react-datepicker__year-select']/option[@value="+ calendar.get(Calendar.YEAR) +"]")).waitUntil(Condition.visible, 500).click();
        $(By.xpath("//select[@class='react-datepicker__month-select']/option[@value="+ calendar.get(Calendar.MONTH) +"]")).waitUntil(Condition.visible, 500).click();
        $(By.cssSelector(".react-datepicker__day--0" + calendar.get(Calendar.DAY_OF_MONTH))).waitUntil(Condition.visible, 500).click();
    }

    @Then("Выбрать регион поставки Алтайский край")
    public void SelectDeliveryRegionAltaiRegion() {
        search223FzPage.clickOnElement(search223FzPage.getDeliveryRegion());
        search223FzPage.clickOnElement(search223FzPage.getLinkRemoveAll());
        search223FzPage.clickOnElement(search223FzPage.getCheckBoxAltaiRegion());
    }

    @Then("Получить результат поиска")
    public void GetSearchResult() {
        try {
            search223FzPage.clickOnElement(search223FzPage.getButtonSearch());
            FileOutputStream fileOutputStream = new FileOutputStream("notes.txt");
            // Вызываем и закрываем окно консультации, чтобы оно в дальнейшем не помешало
            search223FzPage.clickOnElement(search223FzPage.getButtonConsultation());
            search223FzPage.clickOnElement(search223FzPage.getButtonCloseConsultation());

            // Проверяем, если существует кнопка для показания доп результатов и нажимаем на нее, пока она не исчезнет
            SelenideElement loadMore = search223FzPage.getButtonLoadMore();
            int AllowableNumberOfLoopIterations = 50;
            while(loadMore.isDisplayed()){
                search223FzPage.clickOnElement(loadMore);
                sleep(500);
                AllowableNumberOfLoopIterations--;
                if (AllowableNumberOfLoopIterations == 0){
                    throw new Exception("Превышено число итераций для цикла, отвечающего за прогрузку дополнительных результатов поиска");
                }
            }
            // Получение результатов
            for (int i = 0; i < search223FzPage.getCountNotifications(); i++){
                // Получаем div закупкой
                SelenideElement card = search223FzPage.getSearchResult().get(i);
                // Кликаем на него, чтобы подняться или опуститься к нему
                search223FzPage.clickOnElement(card);
                // Получаем начальную цену
                String value = search223FzPage.getStartingPrice(card);
                // Получаем элемент показать еще позиции, если он существует
                SelenideElement spanShowMore = search223FzPage.getSpanShowMore(card);
                if (spanShowMore != null){
                    search223FzPage.clickOnElement(spanShowMore);
                }
                // Строим результат
                StringBuilder string = new StringBuilder();
                string.append(i+1);
                string.append(" : ");
                string.append(value);
                string.append(", ");
                string.append(search223FzPage.getCountInCardResult(card));
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
