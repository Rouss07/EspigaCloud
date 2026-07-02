package com.espigapedidos.espigapedidos;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import org.junit.jupiter.api.Test;

class EspigapedidosApplicationMainTest {

    @Test
    void ejecutarMain() {
        assertDoesNotThrow(() -> EspigapedidosApplication.main(new String[]{
                "--spring.main.web-application-type=none"
        }));
    }
}
