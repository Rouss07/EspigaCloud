package com.espigapedidos.espigapedidos.controller;

import com.espigapedidos.espigapedidos.entity.Pedido;
import com.espigapedidos.espigapedidos.entity.Tienda;
import com.espigapedidos.espigapedidos.form.PedidoForm;
import com.espigapedidos.espigapedidos.service.PedidoService;
import com.espigapedidos.espigapedidos.service.TiendaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.time.LocalDate;
import java.time.ZoneId;

@Controller
@RequestMapping("/pedidos")
public class PedidoController {

    private static final ZoneId APP_ZONE = ZoneId.of("America/Lima");

    private final PedidoService pedidoService;
    private final TiendaService tiendaService;

    public PedidoController(PedidoService pedidoService, TiendaService tiendaService) {
        this.pedidoService = pedidoService;
        this.tiendaService = tiendaService;
    }

    @GetMapping
    public String listarPedidos(Model model) {
        model.addAttribute("pedidos", pedidoService.listarPedidos());
        return "pedidos/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        PedidoForm pedido = new PedidoForm();
        pedido.setFecha(LocalDate.now(APP_ZONE));
        model.addAttribute("pedido", pedido);
        model.addAttribute("tiendas", tiendaService.listarTiendas());
        return "pedidos/formulario";
    }

    @PostMapping("/guardar")
    public String guardarPedidoValidado(@Valid @ModelAttribute("pedido") PedidoForm pedido,
                                         BindingResult resultado,
                                         @RequestParam(value = "tienda", required = false) Long tiendaId,
                                         Model model) {
        if (tiendaId == null) resultado.reject("tienda.required", "Seleccione una tienda");
        if (resultado.hasErrors()) {
            model.addAttribute("tiendas", tiendaService.listarTiendas());
            return "pedidos/formulario";
        }
        return guardarPedido(pedido, tiendaId);
    }

    public String guardarPedido(PedidoForm pedido, Long tiendaId) {
        Tienda tienda = tiendaService.obtenerTiendaPorId(tiendaId);
        Pedido pedidoEntidad = pedido.toEntity();
        pedidoEntidad.setTienda(tienda);
        pedidoService.guardarPedido(pedidoEntidad);
        return "redirect:/pedidos";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Pedido pedido = pedidoService.obtenerPedidoPorId(id);
        model.addAttribute("pedido", PedidoForm.fromEntity(pedido));
        model.addAttribute("tiendas", tiendaService.listarTiendas());
        return "pedidos/formulario";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarPedido(@PathVariable Long id) {
        pedidoService.eliminarPedido(id);
        return "redirect:/pedidos";
    }
}
