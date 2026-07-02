package com.espigapedidos.espigapedidos.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

class PedidoTest {

    private static final LocalDate FECHA_PEDIDO = LocalDate.of(2026, Month.JULY, 1);

    @Test
    void crearPedidoCompleto() {

        Tienda tienda = new Tienda();
        tienda.setNombre("Tienda Central");

        Pedido pedido = new Pedido();
        pedido.setFecha(FECHA_PEDIDO);
        pedido.setEstado("PENDIENTE");
        pedido.setTienda(tienda);

        assertNotNull(pedido);
        assertNotNull(pedido.getFecha());
        assertEquals("PENDIENTE", pedido.getEstado());
        assertEquals("Tienda Central", pedido.getTienda().getNombre());
    }
}
