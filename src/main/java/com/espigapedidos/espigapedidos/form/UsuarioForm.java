package com.espigapedidos.espigapedidos.form;

import com.espigapedidos.espigapedidos.entity.Usuario;
import lombok.Data;

@Data
public class UsuarioForm {
    private Long id;
    private String nombre;
    private String username;
    private String password;
    private String rol;
    private Boolean activo;

    public static UsuarioForm fromEntity(Usuario usuario) {
        UsuarioForm form = new UsuarioForm();
        form.setId(usuario.getId());
        form.setNombre(usuario.getNombre());
        form.setUsername(usuario.getUsername());
        form.setPassword(usuario.getPassword());
        form.setRol(usuario.getRol());
        form.setActivo(usuario.getActivo());
        return form;
    }

    public Usuario toEntity() {
        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setNombre(nombre);
        usuario.setUsername(username);
        usuario.setPassword(password);
        usuario.setRol(rol);
        usuario.setActivo(activo);
        return usuario;
    }
}
