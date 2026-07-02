package com.espigapedidos.espigapedidos.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

class PedidoEspecialTest {

    private static final LocalDate FECHA_ENTREGA = LocalDate.of(2026, Month.JULY, 4);

    @Test
    void validarPedidoEspecial() {
        PedidoEspecial pedidoEspecial = new PedidoEspecial();

        pedidoEspecial.setCliente("Rosa María");
        pedidoEspecial.setDescripcion("Torta personalizada de cumpleaños");
        pedidoEspecial.setFechaEntrega(FECHA_ENTREGA);
        pedidoEspecial.setEstado("PENDIENTE");

        assertEquals("Rosa María", pedidoEspecial.getCliente());
        assertEquals("Torta personalizada de cumpleaños", pedidoEspecial.getDescripcion());
        assertNotNull(pedidoEspecial.getFechaEntrega());
        assertEquals("PENDIENTE", pedidoEspecial.getEstado());
    }
}
