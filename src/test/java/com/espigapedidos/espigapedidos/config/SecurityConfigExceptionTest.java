package com.espigapedidos.espigapedidos.config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class SecurityConfigExceptionTest {

    @Test
    void constructorConservaMensajeYCausa() {
        RuntimeException causa = new RuntimeException("causa");

        SecurityConfigException exception =
                new SecurityConfigException("Error configurando Spring Security", causa);

        assertEquals("Error configurando Spring Security", exception.getMessage());
        assertSame(causa, exception.getCause());
    }
}
