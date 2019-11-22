import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

class CorrectValuesTest {
    static WebDriver driver;    // Драйвер FireFoxа
    static MainPage mainPage;   // Основная страница шаблона Page Object
    int countResult;            // Количество результирующих строк

    @BeforeAll  // Начальные установки
    static void setUpAll() {
        System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "\\Drivers\\geckodriver.exe");
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS );
        driver.manage().window().maximize();
        mainPage = new MainPage(driver);
        driver.get("https://rasp.yandex.ru");
}

    @Test // При заданных условиях должно отобразиться 4-ре результата
    void resultExist() {
        countResult = mainPage.findTrip("Кемерово", "Москва", "22 ноября");
        Assertions.assertTrue(countResult == 4, "При тестовых значениях показывается неправильное количество рейсов.");
    }


    @Test // У каждого рейса должно быть название
    void tripNameExist() {
        countResult = mainPage.findTrip("Кемерово", "Москва", "22 ноября");
        Assertions.assertTrue(countResult > 0);
        for(int i=1; i<=countResult; i++) {
            Assertions.assertTrue(mainPage.checkTripNameField(i), "У " + i + "-го направления отсутствует название рейса.");
        }
    }

    @Test // У каждого направления должно быть время в пути
    void durationExist() {
        countResult = mainPage.findTrip("Кемерово", "Москва", "22 ноября");
        Assertions.assertTrue(countResult > 0);
        for(int i=1; i<=countResult; i++) {
            Assertions.assertTrue(mainPage.checkDurationField(i), "У " + i + "-го направления отсутствует время в пути.");
        }
    }

    @Test // У каждого направления должна быть иконка транспорта
    void pictureExist() {
        countResult = mainPage.findTrip("Кемерово", "Москва", "22 ноября");
        Assertions.assertTrue(countResult > 0);
        for(int i=1; i<=countResult; i++) {
            Assertions.assertTrue(mainPage.checkPictureField(i), "У " + i + "-го направления отсутствует иконка транспорта.");
        }
    }

    // По завершении тестов закрываем браузер
    @AfterAll
    static void tearDownAll() {
        driver.quit();
    }
}
