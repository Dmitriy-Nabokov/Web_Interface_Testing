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

    static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach

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
    void shouldTestTheValidForm() {   // Valid
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Науруз Каппушев-Габараев-Ахмет-Саддин");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79167776655");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("[type=button]")).click();

        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    void shouldTestInvalidName() {       // Invalid name
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Johnny");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+71111111111");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("[type=button]")).click();

        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText().trim();
        assertEquals(expected, actual);
    }
    @Test
    void shouldTestInvalidPhone() {      // Invalid phone
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Марат Габараев");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("9991234567");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("[type=button]")).click();

        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    void shouldTestUnchecking() {   // Invalid check-box
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Марат Габараев");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+77968645676");
        driver.findElement(By.cssSelector("[type=button]")).click();

        String expected = "Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй";

        String actual = driver.findElement(By.cssSelector("[data-test-id=agreement].input_invalid")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    void shouldTestEmptyName() {

        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79167776655");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("[type=button]")).click();

        String expected = "Поле обязательно для заполнения";
        String actual = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    void shouldTestEmptyPhone() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Науруз Каппушев-Габараев-Ахмет-Саддин");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("[type=button]")).click();

        String expected = "Поле обязательно для заполнения"; //////!!!
        String actual = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText().trim();
        assertEquals(expected, actual);
    }
}