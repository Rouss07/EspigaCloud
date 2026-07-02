package com.espigapedidos.espigapedidos.controller;

import com.espigapedidos.espigapedidos.entity.Usuario;
import com.espigapedidos.espigapedidos.form.UsuarioForm;
import com.espigapedidos.espigapedidos.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public String listarUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioService.listarUsuarios());
        return "usuarios/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("usuario", new UsuarioForm());
        return "usuarios/formulario";
    }

    @PostMapping("/guardar")
    public String guardarUsuarioValidado(@Valid @ModelAttribute("usuario") UsuarioForm usuario,
                                          BindingResult resultado) {
        if (resultado.hasErrors()) return "usuarios/formulario";
        return guardarUsuario(usuario);
    }

    public String guardarUsuario(UsuarioForm usuario) {
        usuarioService.guardarUsuario(usuario.toEntity());
        return "redirect:/usuarios";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Usuario usuario = usuarioService.obtenerUsuarioPorId(id);
        model.addAttribute("usuario", UsuarioForm.fromEntity(usuario));
        return "usuarios/formulario";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return "redirect:/usuarios";
    }
}
