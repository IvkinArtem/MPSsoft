import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class Login {
    static FirefoxDriver driver;
    static Properties prop = new Properties();
    private static InputStream configFile;
    private static String password;

    private  static Long time = 5l;

    @BeforeEach
    void init() throws IOException {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--incognito");
        options.addArguments("start-maximized");
        driver = new FirefoxDriver(options);
        driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
        driver.get("http://91.221.70.79:8042/21");

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        configFile = new FileInputStream("src/main/resources/my.properties");
        prop.load(configFile);

        password = prop.getProperty("password");


    }

    public static String getPassword(){
        return password;
    }



    @AfterEach
    void close(){
        driver.quit();
    }

    @Test
    void login(){

        WebElement password = driver.findElement(By.xpath("//input[@id='pass']"));
        password.sendKeys(getPassword());

        WebElement button = driver.findElement(By.xpath("//button[contains(text(),'Войти')]"));
        button.click();

        WebElement clickTest2 = driver.findElement(By.xpath("//*[@id='69789']"));
        clickTest2.click();

        Assertions.assertNotNull(driver.findElement(By.xpath("//*[@id='69753']")));


    }
}
