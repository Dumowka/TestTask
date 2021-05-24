package pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.*;

public class Poisk223fzPage {
    private final SelenideElement spanSearchSerrings = $(By.cssSelector(".main-search__settings-btn"));
    private final SelenideElement checkBox_615pp_rf = $(By.xpath("//label[text()='615-ПП РФ']"));
    private final SelenideElement labelExcludeJointPurchases = $(By.xpath("//label[text()='Исключить совместные закупки']"));
    private final SelenideElement divFilterByDates = $(By.xpath("//div[text()='Фильтры по датам']"));
    private final SelenideElement datepickerLastTime = $(By.xpath("//div[text()=\"ПОДАЧА ЗАЯВОК\"]/following-sibling::div//div[@class=\"form-group__cell-mdash\"]/following-sibling::div//input"));
    private final SelenideElement divDeliveryRegion = $(By.xpath("//div[text()=\"Регион поставки\"]"));
    private final SelenideElement linkRemoveAll = $(By.xpath("//div[@class='modal-settings-filter__main']/div[7]//a[.='Снять всё']"));
    private final SelenideElement checkBoxAltaiRegion = $(By.xpath("//label[.='Алтайский край']"));
    private final SelenideElement buttonSearch = $(By.xpath("//button[@class='search__btn bottomFilterSearch']"));
    private final SelenideElement buttonConsultation = $(By.cssSelector(".consultation-btn"));
    private final SelenideElement buttonCloseConsultation = $(By.cssSelector(".modal-form-reset"));
    private final SelenideElement buttonLoadMore = $(By.cssSelector("#load-more"));
    private final SelenideElement countNotifications = $(By.xpath("//span[@id='Notifications']/b"));
    private final ElementsCollection divSearchResult = $$x("//div[@id='content']/div//div[@class='card-item']");

    public int getCountNotifications() {
        return Integer.parseInt(countNotifications.getText());
    }


    public ElementsCollection getDivSearchResult() {
        return divSearchResult;
    }

    public SelenideElement getButtonLoadMore() {
        return buttonLoadMore;
    }

    public SelenideElement getButtonConsultation() {
        return buttonConsultation;
    }

    public SelenideElement getButtonCloseConsultation() {
        return buttonCloseConsultation;
    }

    public SelenideElement getButtonSearch() {
        return buttonSearch;
    }

    public SelenideElement getCheckBoxAltaiRegion() {
        return checkBoxAltaiRegion;
    }

    public SelenideElement getLinkRemoveAll() {
        return linkRemoveAll;
    }

    public SelenideElement getDivDeliveryRegion() {
        return divDeliveryRegion;
    }

    public SelenideElement getDatepickerLastTime() {
        return datepickerLastTime;
    }

    public SelenideElement getSpanSearchSerrings() {
        return spanSearchSerrings;
    }

    public SelenideElement getCheckBox_615pp_rf() {
        return checkBox_615pp_rf;
    }

    public SelenideElement getLabelExcludeJointPurchases() {
        return labelExcludeJointPurchases;
    }

    public SelenideElement getDivFilterByDates() {
        return divFilterByDates;
    }

    public void clickSpan(SelenideElement span){
        span.click();
    }

    public void clickCheckBox(SelenideElement checkBox){
        checkBox.click();
    }

    public void clickDiv(SelenideElement div){
        div.click();
    }

    public void clickLink(SelenideElement link){
        link.click();
    }

    public void clickButton(SelenideElement button){
        button.click();
    }

    public String getStartingPrice(SelenideElement card){
        return card.find(By.xpath(".//div[1]/div[@class='card-item__properties-desc']")).getText();
    }

    public SelenideElement getSpanShowMore(SelenideElement card){
        SelenideElement element = card.find(By.xpath(".//span[@class='more-position show-more']"));
        return element.exists()? element : null;
    }

    public double getCountInCardResult(SelenideElement card){
        ElementsCollection countList = card.findAll(By.xpath(".//td[3]"));
        // Переменная для подсчета общего кол-ва товаров у 1 таблицы
        double allCount = 0;

        for (SelenideElement el : countList){
            allCount+= Double.parseDouble(el.getText().replaceAll(" ", "").replaceAll(",", "."));
        }
        return allCount;
    }
}
