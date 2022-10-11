import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class EmailTest {

    private WebDriver driver;
    private final String BASE_URL = "http://centrum.cz";

    @Before
    public void setUP(){
        System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver.exe");
        driver = new ChromeDriver();
        driver.get(BASE_URL);
        driver.manage().window().maximize();

    }
    @Test
    public void test(){

        // COOKIES AGREEMENT

        driver.findElement(By.xpath("//span[text()='Rozumím a přijímám']"))
                .click();

        // LOGIN

        driver.findElement(By.xpath("//input[@placeholder='Jméno']"))
                .sendKeys("autocont131022");
        driver.findElement(By.xpath("//input[@placeholder='Heslo']"))
                .sendKeys("Autocont131022");
        driver.findElement(By.xpath("//button[contains(text(),'Přihlásit')]"))
                .click();

        // SENDING EMAIL

        new WebDriverWait(driver,Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//strong[@id='compose_button']")));
        driver.findElement(By.xpath("//strong[@id='compose_button']"))
                .click();
        new WebDriverWait(driver,Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='smart_input_to']")));
        driver.findElement(By.xpath("//input[@id='smart_input_to']"))
                .sendKeys("autocont131022@centrum.cz");
        driver.findElement(By.xpath("//input[@id='subject_input']"))
                .sendKeys("předmět");
        driver.findElement(By.id("mail_composer_body_ifr"))
                .sendKeys("obsah emailu");
        driver.findElement(By.xpath
                        ("//a[@id='qa_email_send_bottom']//strong[@class='b-content'][normalize-space()='Odeslat']"))
                .click();

        // WAITING FOR RECIEVE + REFRESH

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        driver.navigate().refresh();


        // RESPONSE TO THE RECEIVED EMAIL

        new WebDriverWait(driver,Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated
                        (By.xpath("//div[@class='list-sender' and text()='autocont']")));
        driver.findElement(By.xpath("//div[@class='list-sender' and text()='autocont']"))
                .click();
        new WebDriverWait(driver,Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(By
                        .xpath("//span[@class='b-listing-green b-outer']//strong[@class='b-content'][contains(text(),'Odpovědět')]")));
        driver.findElement(By
                        .xpath("//span[@class='b-listing-green b-outer']//strong[@class='b-content'][contains(text(),'Odpovědět')]"))
                .click();

        // ATTACHING IMG TO RESPONSE

        driver.findElement(By.xpath("//input[@name='attach[]']"))
                .sendKeys("C:\\Users\\42077\\Desktop\\IMG20220724155906.jpg");

        // SENDING A RESPONSE

        driver.findElement(By.xpath
                        ("//a[@id='qa_email_send_bottom']//strong[@class='b-content'][normalize-space()='Odeslat']"))
                .click();

        // WAITING FOR RECEIVING A RESPONSE

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        driver.navigate().refresh();
        new WebDriverWait(driver,Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(By
                        .xpath("//div[@class='list-sender' and text()='autocont']")));

        // ASSERTING ATTACHMENT PRESENCE

        driver.findElement(By.xpath("//div[@class='list-sender' and text()='autocont']"))
                .click();

        new WebDriverWait(driver,Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(By
                        .xpath("//ul[@id='md_attachments_td']//a[contains(text(),'IMG20220724155906.jpg')]")));

        Assert.assertTrue
                (driver.findElement(By.xpath
                        ("//ul[@id='md_attachments_td']//a[contains(text(),'IMG20220724155906.jpg')]")).isDisplayed());

    }
    @After
    public void tearDown(){
         driver.close();
         driver.quit();

    }
}
