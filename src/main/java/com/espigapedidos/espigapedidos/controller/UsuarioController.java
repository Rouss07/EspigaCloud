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

    private static final String USUARIO_ATTR = "usuario";
    private static final String FORM_VIEW = "usuarios/formulario";

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
        model.addAttribute(USUARIO_ATTR, new UsuarioForm());
        return FORM_VIEW;
    }

    @PostMapping("/guardar")
    public String guardarUsuarioValidado(@Valid @ModelAttribute(USUARIO_ATTR) UsuarioForm usuario,
                                          BindingResult resultado) {
        if (resultado.hasErrors()) return FORM_VIEW;
        return guardarUsuario(usuario);
    }

    public String guardarUsuario(UsuarioForm usuario) {
        usuarioService.guardarUsuario(usuario.toEntity());
        return "redirect:/usuarios";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Usuario usuario = usuarioService.obtenerUsuarioPorId(id);
        model.addAttribute(USUARIO_ATTR, UsuarioForm.fromEntity(usuario));
        return FORM_VIEW;
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return "redirect:/usuarios";
    }
}
