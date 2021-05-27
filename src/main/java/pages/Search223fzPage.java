package pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.*;

public class Search223fzPage extends BasicPage{
    private final SelenideElement searchSettings = $(".main-search__settings-btn");
    private final SelenideElement checkBox_615pp_rf = $x("//label[text()='615-ПП РФ']");
    private final SelenideElement excludeJointPurchases = $x("//label[text()='Исключить совместные закупки']");
    private final SelenideElement filterByDates = $x("//div[text()='Фильтры по датам']");
    private final SelenideElement datepickerLastTime = $x("//div[text()=\"ПОДАЧА ЗАЯВОК\"]/following-sibling::div//div[@class=\"form-group__cell-mdash\"]/following-sibling::div//input");
    private final SelenideElement deliveryRegion = $x("//div[text()=\"Регион поставки\"]");
    private final SelenideElement linkRemoveAll = $x("//div[@class='modal-settings-filter__main']/div[7]//a[.='Снять всё']");
    private final SelenideElement checkBoxAltaiRegion = $x("//label[.='Алтайский край']");
    private final SelenideElement buttonSearch = $x("//button[@class='search__btn bottomFilterSearch']");
    private final SelenideElement buttonConsultation = $(".consultation-btn");
    private final SelenideElement buttonCloseConsultation = $(".modal-form-reset");
    private final SelenideElement buttonLoadMore = $("#load-more");
    private final SelenideElement countNotifications = $x("//span[@id='Notifications']/b");
    private final ElementsCollection searchResult = $$x("//div[@id='content']/div//div[@class='card-item']");
    private final SelenideElement inputSearch = $x("//div[@class=\"main-search__controls\"]/input");
    private final SelenideElement buttonMainSearch = $(".search__btn_def");

    public int getCountNotifications() {
        return Integer.parseInt(countNotifications.getText());
    }

    public ElementsCollection getPurchaseNumbers(){
        return $$x("//div[@class='card-item__about']//a");
    }

    public SelenideElement getButtonMainSearch() {
        return buttonMainSearch;
    }

    public SelenideElement getInputSearch() {
        return inputSearch;
    }

    public ElementsCollection getSearchResult() {
        return searchResult;
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

    public SelenideElement getDeliveryRegion() {
        return deliveryRegion;
    }

    public SelenideElement getDatepickerLastTime() {
        return datepickerLastTime;
    }

    public SelenideElement getSearchSettings() {
        return searchSettings;
    }

    public SelenideElement getCheckBox_615pp_rf() {
        return checkBox_615pp_rf;
    }

    public SelenideElement getExcludeJointPurchases() {
        return excludeJointPurchases;
    }

    public SelenideElement getFilterByDates() {
        return filterByDates;
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
