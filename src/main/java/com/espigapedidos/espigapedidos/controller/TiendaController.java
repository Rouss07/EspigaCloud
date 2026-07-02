package com.espigapedidos.espigapedidos.controller;

import com.espigapedidos.espigapedidos.entity.Tienda;
import com.espigapedidos.espigapedidos.form.TiendaForm;
import com.espigapedidos.espigapedidos.service.TiendaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tiendas")
public class TiendaController {

    private final TiendaService tiendaService;

    public TiendaController(TiendaService tiendaService) {
        this.tiendaService = tiendaService;
    }

    @GetMapping
    public String listarTiendas(Model model) {
        model.addAttribute("tiendas", tiendaService.listarTiendas());
        return "tiendas/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("tienda", new TiendaForm());
        return "tiendas/formulario";
    }

    @PostMapping("/guardar")
    public String guardarTienda(@ModelAttribute TiendaForm tienda) {
        tiendaService.guardarTienda(tienda.toEntity());
        return "redirect:/tiendas";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Tienda tienda = tiendaService.obtenerTiendaPorId(id);
        model.addAttribute("tienda", TiendaForm.fromEntity(tienda));
        return "tiendas/formulario";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarTienda(@PathVariable Long id) {
        tiendaService.eliminarTienda(id);
        return "redirect:/tiendas";
    }
}
