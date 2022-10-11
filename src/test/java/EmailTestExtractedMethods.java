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

public class EmailTestExtractedMethods {

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

        clickElementXPath("//span[text()='Rozumím a přijímám']");

        // LOGIN

        sendKeysXPath(By.xpath("//input[@placeholder='Jméno']"), "autocont131022");

        sendKeysXPath(By.xpath("//input[@placeholder='Heslo']"), "Autocont131022");

        clickElementXPath("//button[contains(text(),'Přihlásit')]");

        // SENDING EMAIL

        watingForElementXPath("//strong[@id='compose_button']");

        clickElementXPath("//strong[@id='compose_button']");

        watingForElementXPath("//input[@id='smart_input_to']");

        sendKeysXPath(By.xpath("//input[@id='smart_input_to']"), "autocont131022@centrum.cz");

        sendKeysXPath(By.xpath("//input[@id='subject_input']"), "předmět");

        driver.findElement(By.id("mail_composer_body_ifr"))
                .sendKeys("obsah emailu");

        clickElementXPath("//a[@id='qa_email_send_bottom']//strong[@class='b-content'][normalize-space()='Odeslat']");

        // WAITING FOR RECIEVE + REFRESH

        threadSleep();

        refresh();


        // RESPONSE TO THE RECEIVED EMAIL

        watingForElementXPath("//div[@class='list-sender' and text()='autocont']");

        clickElementXPath("//div[@class='list-sender' and text()='autocont']");

        watingForElementXPath("//span[@class='b-listing-green b-outer']//strong[@class='b-content'][contains(text(),'Odpovědět')]");

        clickElementXPath("//span[@class='b-listing-green b-outer']//strong[@class='b-content'][contains(text(),'Odpovědět')]");

        // ATTACHING IMG TO RESPONSE

        sendKeysXPath(By.xpath("//input[@name='attach[]']"), "C:\\Users\\42077\\Desktop\\IMG20220724155906.jpg");

        // SENDING A RESPONSE

        clickElementXPath("//a[@id='qa_email_send_bottom']//strong[@class='b-content'][normalize-space()='Odeslat']");

        // WAITING FOR RECEIVING A RESPONSE

        threadSleep();

        refresh();

        watingForElementXPath("//div[@class='list-sender' and text()='autocont']");

        // ASSERTING ATTACHMENT PRESENCE

        clickElementXPath("//div[@class='list-sender' and text()='autocont']");

        watingForElementXPath("//ul[@id='md_attachments_td']//a[contains(text(),'IMG20220724155906.jpg')]");

        Assert.assertTrue
                (driver.findElement(By.xpath
                        ("//ul[@id='md_attachments_td']//a[contains(text(),'IMG20220724155906.jpg')]"))
                        .isDisplayed());

    }


    @After
    public void tearDown(){
         driver.close();
         driver.quit();

    }

    private void refresh() {
        driver.navigate().refresh();
    }

    private static void threadSleep() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void watingForElementXPath(String xpathExpression) {
        new WebDriverWait(driver,Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathExpression)));
    }

    private void sendKeysXPath(By xpath, String inicials) {
        driver.findElement(xpath)
                .sendKeys(inicials);
    }

    private void clickElementXPath(String xpathExpression) {
        driver.findElement(By.xpath(xpathExpression))
                .click();
    }
}

