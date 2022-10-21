import io.github.bonigarcia.wdm.WebDriverManager;
        import org.apache.logging.log4j.LogManager;
        import org.apache.logging.log4j.Logger;
        import org.openqa.selenium.PageLoadStrategy;
        import org.openqa.selenium.Platform;
        import org.openqa.selenium.UnexpectedAlertBehaviour;
        import org.openqa.selenium.WebDriver;
        import org.openqa.selenium.chrome.ChromeDriver;
        import org.openqa.selenium.chrome.ChromeOptions;
        import org.openqa.selenium.edge.EdgeDriver;
        import org.openqa.selenium.firefox.FirefoxDriver;
        import org.openqa.selenium.firefox.FirefoxOptions;
        import org.openqa.selenium.firefox.FirefoxProfile;
        import org.openqa.selenium.ie.InternetExplorerDriver;
        import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.remote.Browser;
import org.openqa.selenium.remote.CapabilityType;
        import org.openqa.selenium.remote.DesiredCapabilities;
        import org.openqa.selenium.opera.OperaDriver;

public class WebDriverWithOptionsFactory {
    private static Logger logger = LogManager.getLogger(WebDriverFactory.class);

    // Возврат драйвера для конкретного браузера по его названию
    public static WebDriver getDriver(String browserName, String params) {
        switch (browserName) {
            // Создание драйвера для браузера Google Chrome
            case "chrome":
                WebDriverManager.chromedriver().setup();
                logger.info("Драйвер для браузера Google Chrome");

                // Добавление свойств браузера Google Chrome (настройки сессии)

                // а) с помощью класса DesiredCapabilities и строковых параметров
                DesiredCapabilities capabilities = new DesiredCapabilities();
                capabilities.setCapability("unexpectedAlertBehaviour", "dismiss");
                capabilities.setCapability("unhandledPromptBehavior", "dismiss");

                // б) с помощью класса DesiredCapabilities и констант перечисления CapabilityType
                capabilities.setCapability(CapabilityType.PLATFORM_NAME, Platform.WINDOWS);
                capabilities.setCapability(CapabilityType.BROWSER_NAME, Browser.CHROME);

                // в) с помощью класса ChromeOptions и строковых параметров
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.setCapability("pageLoadStrategy", PageLoadStrategy.NORMAL);
                // options.setPageLoadStrategy(PageLoadStrategy.NORMAL);

                // г) с помощью класса ChromeOptions и констант перечисления CapabilityType
                chromeOptions.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, false);
                // options.setAcceptInsecureCerts(false);

                // Добавление свойств (а) и (б)
                chromeOptions.merge(capabilities);

                // Добавление аргументов запуска Google Chrome
                if (params != "")
                {
                    chromeOptions.addArguments(params);
                }
                else {
                    chromeOptions.addArguments("--start-maximized");
                    chromeOptions.addArguments("--incognito");
                }
                return new ChromeDriver(chromeOptions);

            // Создание драйвера для браузера Mozilla Firefox
            case "firefox" :
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments("-private");
                firefoxOptions.addArguments("--kiosk");
                return new FirefoxDriver(firefoxOptions);
            // Создание драйвера для браузера Microsoft Edge
            case "edge" :
                WebDriverManager.edgedriver().setup();
                logger.info("Драйвер для браузера Microsoft Edge");
                return new EdgeDriver();
            // Создание драйвера для браузера Microsoft Internet Explorer
            case "explorer" :
                WebDriverManager.iedriver().setup();
                logger.info("Драйвер для браузера Microsoft Internet Explorer");
                return new InternetExplorerDriver();
            // Создание драйвера для браузера Opera
            case "opera" :
                WebDriverManager.operadriver().setup();
                logger.info("Драйвер для браузера Opera");
                System.setProperty("webdriver.opera.driver", "C:\\Users\\stasp\\Desktop\\Rep\\Example\\operadriver.exe");
                OperaOptions operaOptions = new OperaOptions();
                // Добавление аргументов запуска Opera
                operaOptions.addArguments("--start-maximized");
                operaOptions.addArguments("--incognito");
                return new OperaDriver(operaOptions);

            // Ответ по умолчанию, если введено некорректное название браузера
            default:
                throw new RuntimeException("Введено некорректное название браузера");
        }
    }
}
