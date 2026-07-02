package com.espigapedidos.espigapedidos.repository;

import com.espigapedidos.espigapedidos.entity.Pedido;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

class PedidoRepositoryTest {

    private static final LocalDate FECHA_PEDIDO = LocalDate.of(2026, Month.JULY, 1);

    @Test
    void crearPedidoConFecha() {

        Pedido pedido = new Pedido();
        pedido.setFecha(FECHA_PEDIDO);
        pedido.setEstado("ENTREGADO");

        assertNotNull(pedido.getFecha());
        assertEquals("ENTREGADO", pedido.getEstado());
    }
}
