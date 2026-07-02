package com.espigapedidos.espigapedidos.form;

import com.espigapedidos.espigapedidos.entity.DetallePedido;
import lombok.Data;

@Data
public class DetallePedidoForm {
    private Long id;
    private Integer cantidad;

    public DetallePedido toEntity() {
        DetallePedido detallePedido = new DetallePedido();
        detallePedido.setId(id);
        detallePedido.setCantidad(cantidad);
        return detallePedido;
    }
}
