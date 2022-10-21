import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.Browser;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

public class BrowserOptionsTest {
    protected static WebDriver driver;
    private Logger logger = LogManager.getLogger(BrowserOptionsTest.class);

    // Чтение передаваемого параметра browser (-Dbrowser)
    String env = System.getProperty("browser", "chrome" );
    String params = System.getProperty("params", "" );

    @BeforeEach
    public void setUp() {
        logger.info("env = " + env);
        driver = WebDriverWithOptionsFactory.getDriver(env.toLowerCase(), params);
        logger.info("Драйвер стартовал!");
    }

    @Test
    public void browserOptionsTest() {
        driver.get("https://www.dns-shop.ru/");
        logger.info("Открыта страница DNS - " + "https://www.dns-shop.ru/");

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Ввод текста для поиска
        String searchInputXpath = "(//*[@placeholder=\"Поиск по сайту\"])[2]";
        WebElement searchInput = driver.findElement(By.xpath(searchInputXpath));
        String searchText = "Samsung";
        searchInput.sendKeys(searchText);

        // Нажатие кнопка "Найти"
        String searchButtonXpath = "(//span[@class=\"ui-input-search__icon ui-input-search__icon_search ui-input-search__icon_presearch\"])[2]";
        WebElement searchButton = driver.findElement(By.xpath(searchButtonXpath));
        searchButton.click();

        // Добавление задержки Thread.sleep, чтобы увидеть результат
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    public void setDown() {
        if(driver != null) {
            driver.quit();
            logger.info("Драйвер остановлен!");
        }
    }
}