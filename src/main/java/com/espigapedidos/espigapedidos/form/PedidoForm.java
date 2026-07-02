package com.espigapedidos.espigapedidos.form;

import com.espigapedidos.espigapedidos.entity.Pedido;
import com.espigapedidos.espigapedidos.entity.Tienda;
import lombok.Data;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

@Data
public class PedidoForm {
    private Long id;
    @NotNull(message = "La fecha es obligatoria")
    private LocalDate fecha;
    @NotBlank(message = "El estado es obligatorio")
    @Size(max = 40, message = "El estado no puede superar 40 caracteres")
    private String estado;
    private Tienda tienda;

    public static PedidoForm fromEntity(Pedido pedido) {
        PedidoForm form = new PedidoForm();
        form.setId(pedido.getId());
        form.setFecha(pedido.getFecha());
        form.setEstado(pedido.getEstado());
        form.setTienda(pedido.getTienda());
        return form;
    }

    public Pedido toEntity() {
        Pedido pedido = new Pedido();
        pedido.setId(id);
        pedido.setFecha(fecha);
        pedido.setEstado(estado);
        pedido.setTienda(tienda);
        return pedido;
    }
}
