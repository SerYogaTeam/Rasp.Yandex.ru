import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

class IncorrectValuesTest {
    static WebDriver driver;    // Драйвер FireFoxа
    static MainPage mainPage;   // Основная страница шаблона Page Object
    int countResult;            // Количество результирующих строк
    int countError;             // Количество нужных сообщений об ошибке

    @BeforeAll  // Начальные установки
    static void setUpAll() {
        System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "\\Drivers\\geckodriver.exe");
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS );
        driver.manage().window().maximize();
        mainPage = new MainPage(driver);
        driver.get("https://rasp.yandex.ru");
    }

    @Test // При неверном выборе поля "Откуда" должно выдаваться соответствующее сообщение
    void wrongFrom() {
        countResult = mainPage.findTrip("Кемерово проспект Ленина", "Кемерово Бакинский переулок", "45");
        Assertions.assertTrue(countResult == 0, "При неправильном вводе полей выдался верный результат");
        List<String> errors = mainPage.getErrors();
        countError = 0;     // Количество "правильных" ошибок
        for(String error : errors) {
            if (error.equals(mainPage.wrongFrom)) ++countError;
        }
        Assertions.assertTrue(countError >0, "Не найдено сообщений о неверном вводе поля \"Откуда\"");
    }

    @Test // При неверном выборе поля "Куда" должно выдаваться соответствующее сообщение
    void wrongTo() {
        countResult = mainPage.findTrip("Кемерово проспект Ленина", "Кемерово Бакинский переулок", "45");
        Assertions.assertTrue(countResult == 0, "При неправильном вводе полей выдался верный результат");
        List<String> errors = mainPage.getErrors();
        countError = 0;     // Количество "правильных" ошибок
        for(String error : errors) {
            if (error.equals(mainPage.wrongTo)) ++countError;
        }
        Assertions.assertTrue(countError >0, "Не найдено сообщений о неверном вводе поля \"Куда\"");
    }

    @Test // При неверном выборе поля "Когда" должно выдаваться соответствующее сообщение
    void wrongWhen() {
        countResult = mainPage.findTrip("Кемерово проспект Ленина", "Кемерово Бакинский переулок", "45");
        Assertions.assertTrue(countResult == 0, "При неправильном вводе полей выдался верный результат");
        List<String> errors = mainPage.getErrors();
        countError = 0;     // Количество "правильных" ошибок
        for(String error : errors) {
            if (error.equals(mainPage.wrongWhen)) ++countError;
        }
        Assertions.assertTrue(countError >0, "Не найдено сообщений о неверном вводе поля \"Когда\"");
    }

    // По завершении тестов закрываем браузер
    @AfterAll
    static void tearDownAll() {
        driver.quit();
    }
}
