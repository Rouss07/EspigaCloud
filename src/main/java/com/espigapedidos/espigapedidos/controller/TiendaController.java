package com.espigapedidos.espigapedidos.controller;

import com.espigapedidos.espigapedidos.entity.Tienda;
import com.espigapedidos.espigapedidos.form.TiendaForm;
import com.espigapedidos.espigapedidos.service.TiendaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/tiendas")
public class TiendaController {

    private static final String TIENDA_ATTR = "tienda";
    private static final String FORM_VIEW = "tiendas/formulario";

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
        model.addAttribute(TIENDA_ATTR, new TiendaForm());
        return FORM_VIEW;
    }

    @PostMapping("/guardar")
    public String guardarTiendaValidada(@Valid @ModelAttribute(TIENDA_ATTR) TiendaForm tienda,
                                         BindingResult resultado) {
        if (resultado.hasErrors()) return FORM_VIEW;
        return guardarTienda(tienda);
    }

    public String guardarTienda(TiendaForm tienda) {
        tiendaService.guardarTienda(tienda.toEntity());
        return "redirect:/tiendas";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Tienda tienda = tiendaService.obtenerTiendaPorId(id);
        model.addAttribute(TIENDA_ATTR, TiendaForm.fromEntity(tienda));
        return FORM_VIEW;
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarTienda(@PathVariable Long id) {
        tiendaService.eliminarTienda(id);
        return "redirect:/tiendas";
    }
}
