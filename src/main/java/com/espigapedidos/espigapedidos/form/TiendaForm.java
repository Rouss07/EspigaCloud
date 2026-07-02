package com.espigapedidos.espigapedidos.form;

import com.espigapedidos.espigapedidos.entity.Tienda;
import lombok.Data;
import jakarta.validation.constraints.*;

@Data
public class TiendaForm {
    private Long id;
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede superar 100 caracteres")
    private String nombre;
    @NotBlank(message = "La dirección es obligatoria")
    @Size(max = 180, message = "La dirección no puede superar 180 caracteres")
    private String direccion;
    @NotBlank(message = "El teléfono es obligatorio")
    @Pattern(regexp = "^[0-9]{7,15}$", message = "Ingrese entre 7 y 15 números, sin letras")
    private String telefono;
    @NotBlank(message = "El estado es obligatorio")
    @Pattern(regexp = "(?i)^(activo|inactivo)$", message = "El estado debe ser Activo o Inactivo")
    private String estado;

    public static TiendaForm fromEntity(Tienda tienda) {
        TiendaForm form = new TiendaForm();
        form.setId(tienda.getId());
        form.setNombre(tienda.getNombre());
        form.setDireccion(tienda.getDireccion());
        form.setTelefono(tienda.getTelefono());
        form.setEstado(tienda.getEstado());
        return form;
    }

    public Tienda toEntity() {
        Tienda tienda = new Tienda();
        tienda.setId(id);
        tienda.setNombre(nombre);
        tienda.setDireccion(direccion);
        tienda.setTelefono(telefono);
        tienda.setEstado(estado);
        return tienda;
    }
}
