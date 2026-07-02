package com.espigapedidos.espigapedidos.form;

import com.espigapedidos.espigapedidos.entity.DetallePedido;
import lombok.Data;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Data
public class DetallePedidoForm {
    private Long id;
    @NotNull(message = "La cantidad es obligatoria")
    @Positive(message = "La cantidad debe ser mayor que 0")
    private Integer cantidad;

    public DetallePedido toEntity() {
        DetallePedido detallePedido = new DetallePedido();
        detallePedido.setId(id);
        detallePedido.setCantidad(cantidad);
        return detallePedido;
    }
}
