package steps;

import com.codeborne.selenide.SelenideElement;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.openqa.selenium.By;
import pages.BasicPage;
import pages.Poisk223fzPage;
import pages.ZakupkiParticipants223Page;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

import static com.codeborne.selenide.Selenide.*;

public class BasicPageDef {
    BasicPage basicPage = new BasicPage();
    Poisk223fzPage poisk223fzPage = new Poisk223fzPage();
    ZakupkiParticipants223Page zakupkiParticipants223Page = new ZakupkiParticipants223Page();
    private static final Logger log = Logger.getLogger(String.valueOf(BasicPageDef.class));

    @Given("Сайт https:\\/\\/www.rts-tender.ru\\/")
    public void сайтHttpsWwwRtsTenderRu() {
        open("https://www.rts-tender.ru/");
    }

    @Then("Нажатие в футере в разделе поставщиков на ссылку 223_ФЗ")
    public void нажатиеВФутереВРазделеПоставщиковНаСсылку_223ФЗ() {
        basicPage.clickLink(basicPage.getFooterSup223fzLink());
    }

    @Then("Нажатие на расширенный поиск")
    public void нажатиеНаРасширенныйПоиск() {
        zakupkiParticipants223Page.clickLink(zakupkiParticipants223Page.getAdvancedSearchLink());
    }

    @Then("Нажатие на настройки поиска")
    public void нажатиеНаНастройкиПоиска() {
        try{
            poisk223fzPage.clickSpan(poisk223fzPage.getSpanSearchSerrings());
            Thread.sleep(500);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Then("Поставить чекбокс на 615_ПП ФР")
    public void поставитьЧекбоксНа_ППФР() {
        try{
            poisk223fzPage.clickCheckBox(poisk223fzPage.getCheckBox_615pp_rf());
            Thread.sleep(500);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Then("Поставить чекбокс на исключить совместные покупки")
    public void поставитьЧекбоксНаИсключитьСовместныеПокупки() {
        try{
            poisk223fzPage.clickCheckBox(poisk223fzPage.getLabelExcludeJointPurchases());
            Thread.sleep(500);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Then("Нажатие на фильтр по датам и установка даты")
    public void нажатиеНаФильтрПоДатамИУстановкаДаты() {
        try{
            Date date = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            poisk223fzPage.clickDiv(poisk223fzPage.getDivFilterByDates());
            Thread.sleep(500);
            poisk223fzPage.getDatepickerLastTime().click();
            $(By.xpath("//select[@class='react-datepicker__year-select']/option[@value="+ calendar.get(Calendar.YEAR) +"]")).click();
            Thread.sleep(500);
           // SelenideElement month = $(By.xpath("//select[@class='react-datepicker__month-select']/option[@value="+ month1 +"]")).click();
            $(By.xpath("//select[@class='react-datepicker__month-select']/option[@value="+ calendar.get(Calendar.MONTH) +"]")).click();
            Thread.sleep(500);
            $(By.cssSelector(".react-datepicker__day--0" + calendar.get(Calendar.DAY_OF_MONTH))).click();
            Thread.sleep(500);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Then("Выбрать регион поставки Алтайский край")
    public void выбратьРегионПоставкиАлтайскийКрай() {
        try{
            poisk223fzPage.clickDiv(poisk223fzPage.getDivDeliveryRegion());
            Thread.sleep(500);
            poisk223fzPage.clickLink(poisk223fzPage.getLinkRemoveAll());
            Thread.sleep(500);
            poisk223fzPage.clickCheckBox(poisk223fzPage.getCheckBoxAltaiRegion());
            Thread.sleep(500);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Then("Получить результат поиска")
    public void получитьРезультатПоиска() {
        try {
            poisk223fzPage.clickButton(poisk223fzPage.getButtonSearch());
            FileOutputStream fileOutputStream = new FileOutputStream("D://notes.txt");
            Thread.sleep(500);
            // Вызываем и закрываем окно консультации, чтобы оно в дальнейшем не помешало
            poisk223fzPage.clickButton(poisk223fzPage.getButtonConsultation());
            Thread.sleep(500);
            poisk223fzPage.clickButton(poisk223fzPage.getButtonCloseConsultation());
            Thread.sleep(500);

            // Проверяем, если существует кнопка для показания доп результатов и нажимаем на нее, пока она не исчезнет
            SelenideElement loadMore = poisk223fzPage.getButtonLoadMore();
            while(loadMore.isDisplayed()){
                poisk223fzPage.clickButton(loadMore);
                Thread.sleep(500);
            }
            // Получение результатов
            for (int i = 0; i < poisk223fzPage.getCountNotifications(); i++){
                // Получаем div закупкой
                SelenideElement card = poisk223fzPage.getDivSearchResult().get(i);
                // Кликаем на него, чтобы подняться или опуститься к нему
                poisk223fzPage.clickDiv(card);
                Thread.sleep(500);
                // Получаем начальную цену
                String value = poisk223fzPage.getStartingPrice(card);
                // Получаем элемент показать еще позиции, если он существует
                SelenideElement spanShowMore = poisk223fzPage.getSpanShowMore(card);
                if (spanShowMore != null){
                    poisk223fzPage.clickSpan(spanShowMore);
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
                Thread.sleep(500);
            }
            Thread.sleep(500);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
