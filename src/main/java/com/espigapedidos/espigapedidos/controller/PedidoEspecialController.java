package com.espigapedidos.espigapedidos.controller;

import com.espigapedidos.espigapedidos.entity.PedidoEspecial;
import com.espigapedidos.espigapedidos.entity.Tienda;
import com.espigapedidos.espigapedidos.form.PedidoEspecialForm;
import com.espigapedidos.espigapedidos.service.PedidoEspecialService;
import com.espigapedidos.espigapedidos.service.TiendaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import jakarta.validation.Valid;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
@RequestMapping("/pedidos-especiales")
public class PedidoEspecialController {

    private final PedidoEspecialService pedidoEspecialService;
    private final TiendaService tiendaService;

    public PedidoEspecialController(PedidoEspecialService pedidoEspecialService, TiendaService tiendaService) {
        this.pedidoEspecialService = pedidoEspecialService;
        this.tiendaService = tiendaService;
    }

    @GetMapping
    public String listarPedidosEspeciales(Model model) {
        model.addAttribute("pedidosEspeciales", pedidoEspecialService.listarPedidosEspeciales());
        return "pedidosespeciales/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("pedidoEspecial", new PedidoEspecialForm());
        model.addAttribute("tiendas", tiendaService.listarTiendas());
        return "pedidosespeciales/formulario";
    }

    @PostMapping("/guardar")
    public String guardarPedidoEspecialValidado(@Valid @ModelAttribute("pedidoEspecial") PedidoEspecialForm pedidoEspecial,
                                        BindingResult resultado,
                                        @RequestParam(value = "tiendaId", required = false) Long tiendaId,
                                        @RequestParam("archivoImagen") MultipartFile archivoImagen,
                                        Model model) throws IOException {
        if (tiendaId == null) resultado.reject("tienda.required", "Seleccione una tienda");
        String contentType = archivoImagen.getContentType();
        if (!archivoImagen.isEmpty() && (contentType == null || !contentType.startsWith("image/"))) {
            resultado.reject("imagen.invalid", "El archivo debe ser una imagen");
        }
        if (resultado.hasErrors()) {
            model.addAttribute("tiendas", tiendaService.listarTiendas());
            return "pedidosespeciales/formulario";
        }
        return guardarPedidoEspecial(pedidoEspecial, tiendaId, archivoImagen);
    }

    public String guardarPedidoEspecial(PedidoEspecialForm pedidoEspecial,
                                         Long tiendaId,
                                         MultipartFile archivoImagen) throws IOException {

        Tienda tienda = tiendaService.obtenerTiendaPorId(tiendaId);
        PedidoEspecial pedidoEspecialEntidad = pedidoEspecial.toEntity();
        pedidoEspecialEntidad.setTienda(tienda);

        if (!archivoImagen.isEmpty()) {
            String carpetaUploads = System.getProperty("user.dir") + "/uploads/";
            File directorio = new File(carpetaUploads);
            if (!directorio.exists()) {
                directorio.mkdirs();
            }

            String nombreArchivo = UUID.randomUUID() + "_" + archivoImagen.getOriginalFilename();
            File destino = new File(carpetaUploads + nombreArchivo);
            archivoImagen.transferTo(destino);

            pedidoEspecialEntidad.setImagen(nombreArchivo);
        }

        pedidoEspecialService.guardarPedidoEspecial(pedidoEspecialEntidad);
        return "redirect:/pedidos-especiales";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarPedidoEspecial(@PathVariable Long id) {
        pedidoEspecialService.eliminarPedidoEspecial(id);
        return "redirect:/pedidos-especiales";
    }
}
