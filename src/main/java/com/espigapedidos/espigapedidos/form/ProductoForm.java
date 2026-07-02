package com.espigapedidos.espigapedidos.form;

import com.espigapedidos.espigapedidos.entity.Producto;
import lombok.Data;
import jakarta.validation.constraints.*;

@Data
public class ProductoForm {
    private Long id;
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede superar 100 caracteres")
    @Pattern(regexp = "^[\\p{L}0-9 .,'-]+$", message = "El nombre contiene caracteres no permitidos")
    private String nombre;
    @NotBlank(message = "La categoría es obligatoria")
    @Size(max = 60, message = "La categoría no puede superar 60 caracteres")
    private String categoria;
    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor que 0")
    @Digits(integer = 8, fraction = 2, message = "Ingrese un precio válido con máximo 2 decimales")
    private Double precio;
    @NotNull(message = "El stock es obligatorio")
    @Positive(message = "El stock debe ser mayor que 0")
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
