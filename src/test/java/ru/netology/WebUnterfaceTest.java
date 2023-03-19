package ru.netology;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WebUnterfaceTest {

    private WebDriver driver;

    @BeforeAll
   // static void setUpAll() {
   //     System.setProperty("webdriver.chrome.driver", "./driver/chromedriver.exe"); }

    static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    //  void setUp() {
      //  driver = new ChromeDriver();

    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldTestThePositiveForm() {       // Positive
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Науруз Каппушев-Габараев-Ахмет-Саддин");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79167776655");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("[type=button]")).click();

        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText().trim();
        assertEquals(expected, actual);
    }

    /*
    @Test
    void shouldTestInvalidPhone() {       // Nagative - invalid phone
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Марат Габараев");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("9991234567");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("[type=button]")).click();

        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = driver.findElements(By.cssSelector("[data-test-id=phone]")).input_invalid .checkbox_size_m .checkbox__text;



        assertEquals(expected, actual);
    }

    @Test
    void shouldTestUnchecking() {   // Оставляем неотмеченным чек-бокс
        driver.get("http://localhost:9999/");
        driver.findElements(By.tagName("input")).get(0).sendKeys("Марат Габараев");
        driver.findElements(By.className("input__control")).get(1).sendKeys("+77968645676");
        driver.findElement(By.className("button__text")).click();

        String expected = "Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй";

        String actual = driver.findElement(By.cssSelector("[data-test-id=agreement]")).getText().trim();
        assertEquals(expected, actual);
    }
    
     */
}