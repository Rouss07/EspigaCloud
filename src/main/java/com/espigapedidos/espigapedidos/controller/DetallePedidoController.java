package com.espigapedidos.espigapedidos.controller;

import com.espigapedidos.espigapedidos.entity.DetallePedido;
import com.espigapedidos.espigapedidos.entity.Pedido;
import com.espigapedidos.espigapedidos.entity.Producto;
import com.espigapedidos.espigapedidos.form.DetallePedidoForm;
import com.espigapedidos.espigapedidos.service.DetallePedidoService;
import com.espigapedidos.espigapedidos.service.PedidoService;
import com.espigapedidos.espigapedidos.service.ProductoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/detalle-pedido")
public class DetallePedidoController {

    private final DetallePedidoService detallePedidoService;
    private final PedidoService pedidoService;
    private final ProductoService productoService;

    public DetallePedidoController(DetallePedidoService detallePedidoService,
                                   PedidoService pedidoService,
                                   ProductoService productoService) {
        this.detallePedidoService = detallePedidoService;
        this.pedidoService = pedidoService;
        this.productoService = productoService;
    }

    @GetMapping("/{pedidoId}")
    public String verDetallePedido(@PathVariable Long pedidoId, Model model) {
        model.addAttribute("detalles", detallePedidoService.listarPorPedido(pedidoId));
        model.addAttribute("pedido", pedidoService.obtenerPedidoPorId(pedidoId));
        return "detallepedido/lista";
    }

    @GetMapping("/nuevo/{pedidoId}")
    public String mostrarFormularioNuevo(@PathVariable Long pedidoId, Model model) {
        model.addAttribute("pedido", pedidoService.obtenerPedidoPorId(pedidoId));
        model.addAttribute("productos", productoService.listarProductos());
        model.addAttribute("detallePedido", new DetallePedidoForm());
        return "detallepedido/formulario";
    }

    @PostMapping("/guardar")
    public String guardarDetalleValidado(@Valid @ModelAttribute("detallePedido") DetallePedidoForm detallePedido,
                                 BindingResult resultado,
                                 @RequestParam("pedidoId") Long pedidoId,
                                 @RequestParam(value = "productoId", required = false) Long productoId,
                                 Model model) {
        if (productoId == null) resultado.reject("producto.required", "Seleccione un producto");
        if (resultado.hasErrors()) {
            model.addAttribute("pedido", pedidoService.obtenerPedidoPorId(pedidoId));
            model.addAttribute("productos", productoService.listarProductos());
            return "detallepedido/formulario";
        }
        return guardarDetalle(detallePedido, pedidoId, productoId);
    }

    public String guardarDetalle(DetallePedidoForm detallePedido, Long pedidoId, Long productoId) {

        Pedido pedido = pedidoService.obtenerPedidoPorId(pedidoId);
        Producto producto = productoService.obtenerProductoPorId(productoId);
        DetallePedido detalle = detallePedido.toEntity();

        detalle.setPedido(pedido);
        detalle.setProducto(producto);

        detallePedidoService.guardarDetalle(detalle);

        return "redirect:/detalle-pedido/" + pedidoId;
    }

    @GetMapping("/eliminar/{id}/{pedidoId}")
    public String eliminarDetalle(@PathVariable Long id, @PathVariable Long pedidoId) {
        detallePedidoService.eliminarDetalle(id);
        return "redirect:/detalle-pedido/" + pedidoId;
    }
}
