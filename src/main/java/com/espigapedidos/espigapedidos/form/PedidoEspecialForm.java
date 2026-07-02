package com.espigapedidos.espigapedidos.form;

import com.espigapedidos.espigapedidos.entity.PedidoEspecial;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PedidoEspecialForm {
    private Long id;
    private String cliente;
    private String telefono;
    private String descripcion;
    private String sabor;
    private String tamano;
    private LocalDate fechaEntrega;
    private String estado;

    public static PedidoEspecialForm fromEntity(PedidoEspecial pedidoEspecial) {
        PedidoEspecialForm form = new PedidoEspecialForm();
        form.setId(pedidoEspecial.getId());
        form.setCliente(pedidoEspecial.getCliente());
        form.setTelefono(pedidoEspecial.getTelefono());
        form.setDescripcion(pedidoEspecial.getDescripcion());
        form.setSabor(pedidoEspecial.getSabor());
        form.setTamano(pedidoEspecial.getTamano());
        form.setFechaEntrega(pedidoEspecial.getFechaEntrega());
        form.setEstado(pedidoEspecial.getEstado());
        return form;
    }

    public PedidoEspecial toEntity() {
        PedidoEspecial pedidoEspecial = new PedidoEspecial();
        pedidoEspecial.setId(id);
        pedidoEspecial.setCliente(cliente);
        pedidoEspecial.setTelefono(telefono);
        pedidoEspecial.setDescripcion(descripcion);
        pedidoEspecial.setSabor(sabor);
        pedidoEspecial.setTamano(tamano);
        pedidoEspecial.setFechaEntrega(fechaEntrega);
        pedidoEspecial.setEstado(estado);
        return pedidoEspecial;
    }
}
