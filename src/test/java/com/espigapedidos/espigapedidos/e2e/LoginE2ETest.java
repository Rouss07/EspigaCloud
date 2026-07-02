package com.espigapedidos.espigapedidos.e2e;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Duration;

import com.espigapedidos.espigapedidos.repository.UsuarioRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DockerOnlyE2E
class LoginE2ETest {

    @LocalServerPort
    private int port;

    private WebDriver driver;
    private WebDriverWait wait;
    private String appUrl;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        E2ETestData.ensureAdminUser(usuarioRepository, passwordEncoder);

        driver = E2EWebDriver.create();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        appUrl = E2EWebDriver.appUrl(port);
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @DisplayName("CP-01: Login exitoso con credenciales validas (admin)")
    void loginExitoso() {
        driver.get(appUrl + "/login");

        driver.findElement(By.name("username")).sendKeys(E2ETestData.ADMIN_USERNAME);
        driver.findElement(By.name("password")).sendKeys(E2ETestData.ADMIN_PASSWORD);
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        wait.until(ExpectedConditions.urlToBe(appUrl + "/"));

        // Tras login correcto, redirige a la pagina principal (dashboard)
        String url = driver.getCurrentUrl();
        assertEquals(appUrl + "/", url,
                "Se esperaba redireccion a la pagina principal, pero la URL fue: " + url);

        assertTrue(driver.getPageSource().contains("Dashboard"),
                "Se esperaba ver el Dashboard tras iniciar sesion");
    }

    @Test
    @DisplayName("CP-02: Login fallido con credenciales invalidas")
    void loginFallido() {
        driver.get(appUrl + "/login");

        driver.findElement(By.name("username")).sendKeys("usuario_invalido");
        driver.findElement(By.name("password")).sendKeys("clave_mala");
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        wait.until(ExpectedConditions.urlContains("error"));

        // Tras login fallido, Spring Security redirige a /login?error
        assertTrue(driver.getCurrentUrl().contains("error"),
                "Se esperaba la URL con parametro 'error' tras login fallido");

        assertTrue(driver.getPageSource().contains("incorrectos"),
                "Se esperaba ver el mensaje de error de credenciales incorrectas");
    }

    @Test
    @DisplayName("CP-03: Acceso a pagina protegida sin login redirige a /login")
    void accesoSinLoginRedirigeALogin() {
        driver.get(appUrl + "/productos");
        wait.until(ExpectedConditions.urlContains("/login"));

        assertTrue(driver.getCurrentUrl().contains("/login"),
                "Se esperaba redireccion a /login al acceder sin autenticacion");
    }
}
