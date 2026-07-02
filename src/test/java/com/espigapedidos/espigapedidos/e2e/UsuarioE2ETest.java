package com.espigapedidos.espigapedidos.e2e;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Duration;
import java.util.List;

import com.espigapedidos.espigapedidos.repository.UsuarioRepository;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DockerOnlyE2E
public class UsuarioE2ETest {

    @LocalServerPort
    private int port;

    private WebDriver driver;
    private WebDriverWait wait;
    private String appUrl;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final By BOTON_GUARDAR =
            By.cssSelector("form[action*='/guardar'] button[type='submit']");

    private static final By BOTON_LOGIN =
            By.cssSelector("form button[type='submit']");

    @BeforeEach
    void setUp() {
        E2ETestData.ensureAdminUser(usuarioRepository, passwordEncoder);

        driver = E2EWebDriver.create();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        appUrl = E2EWebDriver.appUrl(port);

        login();
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private void login() {
        driver.get(appUrl + "/login");

        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("username")));

        driver.findElement(By.name("username")).sendKeys(E2ETestData.ADMIN_USERNAME);
        driver.findElement(By.name("password")).sendKeys(E2ETestData.ADMIN_PASSWORD);
        driver.findElement(BOTON_LOGIN).click();

        wait.until(ExpectedConditions.urlToBe(appUrl + "/"));
    }

    private void jsClick(WebElement element) {
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block:'center'});", element);

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", element);
    }

    @Test
    @DisplayName("CP-30: Listar usuarios correctamente")
    void listarUsuarios() {

        driver.get(appUrl + "/usuarios");

        wait.until(ExpectedConditions.urlContains("/usuarios"));

        assertTrue(driver.getCurrentUrl().contains("/usuarios"));
        assertTrue(driver.getPageSource().contains("Gestión de Usuarios"));
    }

    @Test
    @DisplayName("CP-31: Crear usuario exitosamente")
    void crearUsuario() {

        driver.get(appUrl + "/usuarios/nuevo");

        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("nombre")));

        String usernameUnico = "user" + System.currentTimeMillis();

        driver.findElement(By.id("nombre"))
                .sendKeys("Usuario Selenium");

        driver.findElement(By.id("username"))
                .sendKeys(usernameUnico);

        driver.findElement(By.id("password"))
                .sendKeys("1234");

        new Select(driver.findElement(By.id("rol")))
                .selectByValue("TIENDA");

        new Select(driver.findElement(By.id("activo")))
                .selectByValue("true");

        driver.findElement(BOTON_GUARDAR).click();

        wait.until(ExpectedConditions.urlContains("/usuarios"));

        wait.until(driver -> driver.getPageSource().contains(usernameUnico));
        assertTrue(driver.getPageSource().contains(usernameUnico));
    }

    @Test
    @DisplayName("CP-32: Editar usuario existente")
    void editarUsuario() {

        driver.get(appUrl + "/usuarios");

        List<WebElement> editarLinks =
                driver.findElements(By.cssSelector("a[href*='/usuarios/editar/']"));

        assertTrue(editarLinks.size() > 0);

        jsClick(editarLinks.get(editarLinks.size() - 1));

        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("nombre")));

        WebElement nombreInput = driver.findElement(By.id("nombre"));

        nombreInput.clear();

        String nuevoNombre = "UsuarioEditado";

        nombreInput.sendKeys(nuevoNombre);

        driver.findElement(BOTON_GUARDAR).click();

        wait.until(ExpectedConditions.urlContains("/usuarios"));

        assertTrue(driver.getCurrentUrl().contains("/usuarios"));
    }

    @Test
    @DisplayName("CP-33: Eliminar usuario existente")
    void eliminarUsuario() {

        driver.get(appUrl + "/usuarios");

        List<WebElement> eliminarLinks =
                driver.findElements(By.cssSelector("a[href*='/usuarios/eliminar/']"));

        assertTrue(eliminarLinks.size() > 0);

        jsClick(eliminarLinks.get(eliminarLinks.size() - 1));

        wait.until(ExpectedConditions.alertIsPresent());

        driver.switchTo().alert().accept();

        wait.until(ExpectedConditions.urlContains("/usuarios"));

        assertTrue(driver.getCurrentUrl().contains("/usuarios"));
    }
}
