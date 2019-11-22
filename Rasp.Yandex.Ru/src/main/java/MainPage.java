import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class MainPage {
    private WebDriver driver;
    private WebElement field;

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    // Данные для ввода
    private By fromField = By.xpath("//input[@id='from']");      // Поле "Откуда"
    private By toField = By.xpath("//input[@id='to']");          // Поле "Куда"
    private By whenField = By.xpath("//input[@id='when']");        // Поле "Когда"
    private By findButton = By.xpath("//button[@class='Button SearchForm__submit']");       // Кнопка "Найти"

    // Данные результата
    private By tripRecord = By.xpath("//section[@class='SearchSegments']//article");       // Все записи результата
    private By pictureField;        // Иконка
    private String pictureFieldStr = "//section[@class='SearchSegments']//article[%s]//figure";
    private By tripNameField;       // Название рейса
    private String tripNameFieldStr = "//section[@class='SearchSegments']//article[%s]//span[@class='SegmentTitle__number']";
    private By durationField;       // Продолжительность рейса
    private String durationFieldStr = "//section[@class='SearchSegments']//article[%s]//div[@class='SearchSegment__duration']";

    // Обработка ошибок
    private By errorField = By.xpath("//div[@class='ErrorPageSearchForm__title']");       // Поле с ошибками

    static final String wrongFrom =
            "Пункт прибытия не найден. Проверьте правильность написания или выберите другой город.";
    static final String wrongTo =
            "Пункт отправления не найден. Проверьте правильность написания или выберите другой город.";
    static final String wrongWhen =
            "Введите дату отправления в формате «25 февраля».";

    // Ввести значение в поле "Откуда", если оно новое
    private boolean typeFromField(String from) {
        field = driver.findElement(fromField);
        if (!field.getAttribute("value") .equals(from)){
            field.clear();
            field.sendKeys(from);
            return true;
        } else {
            return false;
        }
    }

    // Ввести значение в поле "Куда", если оно новое
    private boolean typeToField(String to) {
        field = driver.findElement(toField);
        if (!field.getAttribute("value").equals(to)){
            field.clear();
            field.sendKeys(to);
            return true;
        } else {
            return false;
        }
    }

    // Ввести значение в поле "Когда", если оно новое
    private boolean typeWhenField(String when) {
        field = driver.findElement(whenField);
        if (!field.getAttribute("value").equals(when)){
            field.clear();
            field.sendKeys(when);
            return true;
        } else {
            return false;
        }
    }

    // Нажать кнопку "Найти"
    private void clickFindButton() {
        driver.findElement(findButton).click();
    }

    // Поиск рейса по заданным значениям, если было обновлено хотя бы одно поле ввода
    int findTrip(String from, String to, String when) {
        boolean changedFrom = this.typeFromField(from);
        boolean changedTo = this.typeToField(to);
        boolean changedWhen = this.typeWhenField(when);
        if (changedFrom || changedTo || changedWhen) this.clickFindButton();
        return this.checkResults();
    }

    // Проверка наличия результата
    private int checkResults() {
        return driver.findElements(tripRecord).size();
    }

    // Проверка наличия иконки транспорта в рейсе
    boolean checkPictureField(int num) {
        int count = driver.findElements(setPictureField(num)).size();
        return count != 0;
    }

    // Регистрация XPath селектора для иконки транспорта
    private By setPictureField (int num) {
        this.pictureField = By.xpath(String.format(pictureFieldStr, num));
        return pictureField;
    }

    // Проверка наличия названия рейса
    boolean checkTripNameField(int num) {
        int count = driver.findElements(setTripNameField(num)).size();
        return count != 0;
    }

    // Регистрация XPath селектора для названия рейса
    private By setTripNameField (int num) {
        tripNameField = By.xpath(String.format(tripNameFieldStr, num));
        return tripNameField;
    }

    // Проверка наличия продолжительности рейса
    boolean checkDurationField(int num) {
        int count = driver.findElements(setDurationField(num)).size();
        return count != 0;
    }

    // Регистрация XPath селектора для продолжительности рейса
    private By setDurationField (int num) {
        durationField = By.xpath(String.format(durationFieldStr, num));
        return durationField;
    }

    // Получение списка ошибок
    List<String> getErrors() {
        List<WebElement> errors = driver.findElements(errorField);
        List<String> lErrors = new ArrayList<String>();
        for (WebElement error : errors) {
            lErrors.add(error.getText());
        }
        return lErrors;
    }
}