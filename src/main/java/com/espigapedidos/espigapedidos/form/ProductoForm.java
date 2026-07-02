package com.espigapedidos.espigapedidos.form;

import com.espigapedidos.espigapedidos.entity.Producto;
import lombok.Data;

@Data
public class ProductoForm {
    private Long id;
    private String nombre;
    private String categoria;
    private Double precio;
    private Integer stock;

    public static ProductoForm fromEntity(Producto producto) {
        ProductoForm form = new ProductoForm();
        form.setId(producto.getId());
        form.setNombre(producto.getNombre());
        form.setCategoria(producto.getCategoria());
        form.setPrecio(producto.getPrecio());
        form.setStock(producto.getStock());
        return form;
    }

    public Producto toEntity() {
        Producto producto = new Producto();
        producto.setId(id);
        producto.setNombre(nombre);
        producto.setCategoria(categoria);
        producto.setPrecio(precio);
        producto.setStock(stock);
        return producto;
    }
}
