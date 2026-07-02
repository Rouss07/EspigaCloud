package com.espigapedidos.espigapedidos.form;

import com.espigapedidos.espigapedidos.entity.Tienda;
import lombok.Data;

@Data
public class TiendaForm {
    private Long id;
    private String nombre;
    private String direccion;
    private String telefono;
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
