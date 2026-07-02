package com.espigapedidos.espigapedidos.controller;

import com.espigapedidos.espigapedidos.entity.Producto;
import com.espigapedidos.espigapedidos.form.ProductoForm;
import com.espigapedidos.espigapedidos.service.ProductoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    private static final String PRODUCTO_ATTR = "producto";
    private static final String FORM_VIEW = "productos/formulario";

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public String listarProductos(Model model) {
        model.addAttribute("productos", productoService.listarProductos());
        return "productos/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute(PRODUCTO_ATTR, new ProductoForm());
        return FORM_VIEW;
    }

    @PostMapping("/guardar")
    public String guardarProductoValidado(@Valid @ModelAttribute(PRODUCTO_ATTR) ProductoForm producto,
                                           BindingResult resultado) {
        if (resultado.hasErrors()) return FORM_VIEW;
        return guardarProducto(producto);
    }

    public String guardarProducto(ProductoForm producto) {
        productoService.guardarProducto(producto.toEntity());
        return "redirect:/productos";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Producto producto = productoService.obtenerProductoPorId(id);
        model.addAttribute(PRODUCTO_ATTR, ProductoForm.fromEntity(producto));
        return FORM_VIEW;
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable Long id) {
        productoService.eliminarProducto(id);
        return "redirect:/productos";
    }
}
