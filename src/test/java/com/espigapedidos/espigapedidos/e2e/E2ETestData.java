package com.espigapedidos.espigapedidos.e2e;

import com.espigapedidos.espigapedidos.entity.Pedido;
import com.espigapedidos.espigapedidos.entity.Tienda;
import com.espigapedidos.espigapedidos.entity.Usuario;
import com.espigapedidos.espigapedidos.repository.PedidoRepository;
import com.espigapedidos.espigapedidos.repository.TiendaRepository;
import com.espigapedidos.espigapedidos.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

final class E2ETestData {

    static final String ADMIN_USERNAME = "admin";
    static final String ADMIN_PASSWORD = "1234";

    private E2ETestData() {
    }

    static void ensureAdminUser(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        Usuario admin = usuarioRepository.findByUsername(ADMIN_USERNAME)
                .orElseGet(Usuario::new);

        admin.setNombre("Administrador E2E");
        admin.setUsername(ADMIN_USERNAME);
        admin.setPassword(passwordEncoder.encode(ADMIN_PASSWORD));
        admin.setRol("ADMIN");
        admin.setActivo(true);

        usuarioRepository.save(admin);
    }

    static Tienda ensureTienda(TiendaRepository tiendaRepository) {
        return tiendaRepository.findAll().stream()
                .findFirst()
                .orElseGet(() -> {
                    Tienda tienda = new Tienda();
                    tienda.setNombre("Tienda E2E");
                    tienda.setDireccion("Direccion E2E 123");
                    tienda.setTelefono("999888777");
                    tienda.setEstado("Activa");
                    return tiendaRepository.save(tienda);
                });
    }

    static Pedido ensurePedido(TiendaRepository tiendaRepository, PedidoRepository pedidoRepository) {
        Tienda tienda = ensureTienda(tiendaRepository);

        return pedidoRepository.findAll().stream()
                .findFirst()
                .orElseGet(() -> {
                    Pedido pedido = new Pedido();
                    pedido.setFecha(LocalDate.now());
                    pedido.setEstado("Pendiente E2E");
                    pedido.setTienda(tienda);
                    return pedidoRepository.save(pedido);
                });
    }
}
