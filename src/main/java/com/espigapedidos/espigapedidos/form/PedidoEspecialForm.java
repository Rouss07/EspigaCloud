package com.espigapedidos.espigapedidos.form;

import com.espigapedidos.espigapedidos.entity.PedidoEspecial;
import lombok.Data;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

@Data
public class PedidoEspecialForm {
    private Long id;
    @NotBlank(message = "El cliente es obligatorio")
    @Pattern(regexp = "^[\\p{L} .'-]{2,100}$", message = "El nombre del cliente solo debe contener letras")
    private String cliente;
    @NotBlank(message = "El teléfono es obligatorio")
    @Pattern(regexp = "^\\d{7,15}$", message = "Ingrese entre 7 y 15 números, sin letras")
    private String telefono;
    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 500, message = "La descripción no puede superar 500 caracteres")
    private String descripcion;
    @NotBlank(message = "El sabor es obligatorio")
    @Size(max = 80, message = "El sabor no puede superar 80 caracteres")
    private String sabor;
    @NotBlank(message = "El tamaño es obligatorio")
    @Size(max = 50, message = "El tamaño no puede superar 50 caracteres")
    private String tamano;
    @NotNull(message = "La fecha de entrega es obligatoria")
    @FutureOrPresent(message = "La fecha de entrega no puede estar en el pasado")
    private LocalDate fechaEntrega;
    @NotBlank(message = "El estado es obligatorio")
    @Size(max = 40, message = "El estado no puede superar 40 caracteres")
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
