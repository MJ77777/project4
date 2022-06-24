import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Instant start = Instant.now();
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\majd1\\Downloads\\chromedriver_win32\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-blink-features=AutomationControlled");
        WebDriver driver = new ChromeDriver(options);

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        driver.get("https://www.orbitz.com/");
        driver.findElement(By.xpath("//button[@aria-label='Going to']")).click();
        driver.findElement(By.id("location-field-destination")).sendKeys("Orlando", Keys.ENTER);
        driver.findElement(By.id("d1-btn")).click();

        Utility.jsClick(driver, driver.findElement(By.xpath("//button[@aria-label='Jul 20, 2022']")));
        Utility.jsClick(driver, driver.findElement(By.xpath("//button[@aria-label='Jul 24, 2022']")));
        Utility.jsClick(driver, driver.findElement(By.xpath("//button[text()='Done']")));
        Utility.jsClick(driver, driver.findElement(By.xpath("//span[text()='Travelers']")));
        driver.findElement(By.xpath("//span[@class='uitk-step-input-button']")).click();
        driver.findElement(By.xpath("(//span[@class='uitk-step-input-button'])[4]")).click();
        driver.findElement(By.xpath("(//span[@class='uitk-step-input-button'])[4]")).click();
        Select child1 = new Select(driver.findElement(By.id("child-age-input-0-0")));
        Select child2 = new Select(driver.findElement(By.id("child-age-input-0-1")));
        child1.selectByValue("4");
        child2.selectByValue("8");
        Utility.jsClick(driver, driver.findElement(By.xpath("//button[text()='Done']")));
        Utility.jsClick(driver, driver.findElement(By.xpath("//button[text()='Search']")));

        Utility.jsClick(driver, driver.findElement(By.id("popularFilter-0-FREE_BREAKFAST")));

        WebElement breakfast = driver.findElement(By.id("playback-filter-pill-mealPlan-FREE_BREAKFAST"));

        Assert.assertEquals(breakfast.getText(), "Breakfast included");

        Utility.jsClick(driver, driver.findElement(By.id("popularFilter-0-FREE_BREAKFAST")));

        Assert.assertFalse(driver.findElement(By.id("popularFilter-0-FREE_BREAKFAST")).isSelected());


        driver.findElement(By.xpath("(//input[@aria-valuemax='300'])[2]")).sendKeys(Keys.ARROW_LEFT);
        Thread.sleep(5000);
        WebElement budgetprice = driver.findElement(By.xpath("//span[@class='uitk-pill-text']"));

        Assert.assertEquals(budgetprice.getText(), "Less than $270");


        List<WebElement> prices = driver.findElements(By.xpath("//div[contains(text(), \"The price is\")]"));
        for (WebElement price : prices) {
            System.out.println(price.getText().replaceAll("[$, Theprics]", ""));
            Assert.assertTrue((Integer.parseInt(price.getText().replaceAll("[$, Theprics]", "")) <= 270));

        }
        Utility.jsClick(driver, driver.findElement(By.xpath("//label[@for='radio-guestRating-45']")));
        Utility.scroll(driver, 0, 1000);
        Thread.sleep(3000);

        List<WebElement> rates = driver.findElements(By.xpath("//span[@class='uitk-text uitk-type-300 uitk-type-bold uitk-text-default-theme']"));
        for (WebElement rate : rates) {
            System.out.println(rate.getText().substring(0, 3).replaceAll("[$, Theprics]", ""));
            Assert.assertTrue((Double.parseDouble(rate.getText().substring(0, 3).replaceAll("[$, Theprics]", "")) >= 4.5));

        }
        List<WebElement> elementsname = driver.findElements(By.xpath("(//h2[@class='uitk-heading-5'])"));
        List<WebElement> elementsrate = driver.findElements(By.xpath("(//span[@class='uitk-text uitk-type-300 uitk-type-bold uitk-text-default-theme'])"));
        String textlast = driver.findElement(By.xpath("(//h2[@class='uitk-heading-5'])[" + elementsname.size() + "]")).getText();
        String ratelast = driver.findElement(By.xpath("(//span[@class='uitk-text uitk-type-300 uitk-type-bold uitk-text-default-theme'])[" + elementsrate.size() + "]")).getText().substring(0, 3).replaceAll("[$, Theprics]", "");
        System.out.println(" the name of the hotel:" + textlast + " the rate for the hotel is:" + ratelast);
        Utility.scroll(driver, 0, 700);
        List<WebElement> elementshotel = driver.findElements(By.xpath("(//a[@data-stid='open-hotel-information'])"));
        Utility.jsClick(driver, driver.findElement(By.xpath("(//a[@data-stid='open-hotel-information'])[" + elementshotel.size() + "]")));
        Set<String> windowHandles = driver.getWindowHandles();
        String windowHandle1 = driver.getWindowHandle();
        for (String windowHandle : windowHandles) {
            driver.switchTo().window(windowHandle);

        }
        Assert.assertEquals(driver.getTitle(), textlast);

        Assert.assertEquals(driver.findElement(By.xpath("//div[@data-stid='content-hotel-title']//h1")).getText(), textlast);
        String compareRate = driver.findElement(By.xpath("(//h3[contains(@class,'uitk-heading-5 uitk-spacing')])[1]")).getText().substring(0, 3).replaceAll("[$, Theprics]", "");
        Assert.assertEquals(compareRate, ratelast);
        driver.close();
        driver.switchTo().window(windowHandle1);
        driver.getTitle();

        Utility.jsClick(driver, driver.findElement(By.xpath("//a//img")));

        WebElement element = driver.findElement(By.xpath("//iframe[starts-with(@src,'https://vac.vap.expedia.com/')]"));

        driver.switchTo().frame(element);
        Utility.jsClick(driver, driver.findElement(By.xpath("//button[@data-test-id='chat-launch-button']")));

        String expectedText = "Hi, I'm your Virtual Agent \uD83D\uDC4B";
        String text = driver.findElement(By.xpath("//div[@class='uitk-text uitk-type-300 uitk-type-regular uitk-layout-flex-item uitk-text-default-theme']")).getText();
        Assert.assertEquals(expectedText, text);

        Utility.jsClick(driver, driver.findElement(By.id("vac-close-button")));
        driver.switchTo().defaultContent();

        Utility.jsClick(driver, driver.findElement(By.xpath("//div[.='More travel']")));

        String[] urlTexts = {"Stays", "Flights", "Packages", "Cars", "Cruises", "Things to do", "Deals", "Groups & meetings", "Travel Blog"};
        Thread.sleep(1000);
        List<WebElement> moreTravelList = driver.findElements(By.xpath("(//div[@class='uitk-list'])[1]//a"));
        for (int i = 0; i < moreTravelList.size(); i++) {

            Assert.assertEquals(moreTravelList.get(i).getText(), urlTexts[i]);

        }
        driver.quit();
        Instant end = Instant.now();
        Duration timeElapsed = Duration.between(start, end);
        System.out.println("Time taken: " + timeElapsed.toMillis() + " milliseconds");

    }
}
