package com.espigapedidos.espigapedidos.config;

import com.espigapedidos.espigapedidos.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SecurityConfigTest {

    @Test
    void securityFilterChain_envuelveErroresDeSpringSecurity() {
        UsuarioService usuarioService = mock(UsuarioService.class);
        PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
        HttpSecurity http = mock(HttpSecurity.class);

        RuntimeException causa = new RuntimeException("fallo http");
        when(http.authenticationProvider(any())).thenThrow(causa);

        SecurityConfig config = new SecurityConfig(usuarioService, passwordEncoder);

        SecurityConfigException exception = assertThrows(
                SecurityConfigException.class,
                () -> config.securityFilterChain(http)
        );

        assertEquals("Error configurando Spring Security", exception.getMessage());
        assertEquals(causa, exception.getCause());
    }
}
