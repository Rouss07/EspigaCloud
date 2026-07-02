package com.espigapedidos.espigapedidos.form;

import com.espigapedidos.espigapedidos.entity.Usuario;
import lombok.Data;
import jakarta.validation.constraints.*;

@Data
public class UsuarioForm {
    private Long id;
    @NotBlank(message = "El nombre es obligatorio")
    @Pattern(regexp = "^[\\p{L} .'-]{2,100}$", message = "El nombre solo debe contener letras")
    private String nombre;
    @NotBlank(message = "El usuario es obligatorio")
    @Pattern(regexp = "^[A-Za-z0-9._-]{4,40}$", message = "Use de 4 a 40 letras, números, punto, guion o guion bajo")
    private String username;
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, max = 100, message = "La contraseña debe tener entre 8 y 100 caracteres")
    private String password;
    @NotBlank(message = "Seleccione un rol")
    @Pattern(regexp = "^(ADMIN|TIENDA)$", message = "Seleccione un rol válido")
    private String rol;
    @NotNull(message = "Seleccione si el usuario está activo")
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
