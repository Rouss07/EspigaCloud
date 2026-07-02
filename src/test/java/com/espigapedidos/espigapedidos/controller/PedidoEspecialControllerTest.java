package com.espigapedidos.espigapedidos.controller;

import com.espigapedidos.espigapedidos.entity.PedidoEspecial;
import com.espigapedidos.espigapedidos.form.PedidoEspecialForm;
import com.espigapedidos.espigapedidos.service.PedidoEspecialService;
import com.espigapedidos.espigapedidos.service.TiendaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.ui.Model;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PedidoEspecialControllerTest {

    @Test
    void listarPedidosEspeciales() {

        PedidoEspecialService pedidoEspecialService = mock(PedidoEspecialService.class);
        TiendaService tiendaService = mock(TiendaService.class);
        Model model = mock(Model.class);

        PedidoEspecial pedido = new PedidoEspecial();

        when(pedidoEspecialService.listarPedidosEspeciales())
                .thenReturn(List.of(pedido));

        PedidoEspecialController controller =
                new PedidoEspecialController(pedidoEspecialService, tiendaService);

        String vista = controller.listarPedidosEspeciales(model);

        assertEquals("pedidosespeciales/lista", vista);

        verify(model).addAttribute(
                "pedidosEspeciales",
                List.of(pedido)
        );
    }

    @Test
    void mostrarFormularioNuevo() {

        PedidoEspecialService pedidoEspecialService = mock(PedidoEspecialService.class);
        TiendaService tiendaService = mock(TiendaService.class);
        Model model = mock(Model.class);

        when(tiendaService.listarTiendas())
                .thenReturn(List.of());

        PedidoEspecialController controller =
                new PedidoEspecialController(pedidoEspecialService, tiendaService);

        String vista = controller.mostrarFormularioNuevo(model);

        assertEquals("pedidosespeciales/formulario", vista);

        verify(model).addAttribute(eq("pedidoEspecial"), any(PedidoEspecialForm.class));
        verify(model).addAttribute(eq("tiendas"), any());
    }

    @Test
    void eliminarPedidoEspecial() {

        PedidoEspecialService pedidoEspecialService = mock(PedidoEspecialService.class);
        TiendaService tiendaService = mock(TiendaService.class);

        PedidoEspecialController controller =
                new PedidoEspecialController(pedidoEspecialService, tiendaService);

        String vista = controller.eliminarPedidoEspecial(1L);

        assertEquals("redirect:/pedidos-especiales", vista);

        verify(pedidoEspecialService).eliminarPedidoEspecial(1L);
    }

    @Test
    void guardarPedidoEspecialSinImagen() throws Exception {

        PedidoEspecialService pedidoEspecialService = mock(PedidoEspecialService.class);
        TiendaService tiendaService = mock(TiendaService.class);

        PedidoEspecialForm pedidoEspecial = new PedidoEspecialForm();
        pedidoEspecial.setCliente("Cliente sin imagen");

        when(tiendaService.obtenerTiendaPorId(1L))
                .thenReturn(null);

        org.springframework.web.multipart.MultipartFile archivo =
                mock(org.springframework.web.multipart.MultipartFile.class);

        when(archivo.isEmpty()).thenReturn(true);

        PedidoEspecialController controller =
                new PedidoEspecialController(
                        pedidoEspecialService,
                        tiendaService
                );

        String vista = controller.guardarPedidoEspecial(
                pedidoEspecial,
                1L,
                archivo
        );

        assertEquals("redirect:/pedidos-especiales", vista);

        verify(tiendaService).obtenerTiendaPorId(1L);
        verify(pedidoEspecialService).guardarPedidoEspecial(argThat(guardado ->
                "Cliente sin imagen".equals(guardado.getCliente())));
    }

    @Test
    void guardarPedidoEspecialConImagen(@TempDir Path tempDir) throws Exception {

        PedidoEspecialService pedidoEspecialService = mock(PedidoEspecialService.class);
        TiendaService tiendaService = mock(TiendaService.class);

        PedidoEspecialForm pedidoEspecial = new PedidoEspecialForm();
        pedidoEspecial.setCliente("Cliente con imagen");

        when(tiendaService.obtenerTiendaPorId(1L))
                .thenReturn(new com.espigapedidos.espigapedidos.entity.Tienda());

        org.springframework.web.multipart.MultipartFile archivo =
                mock(org.springframework.web.multipart.MultipartFile.class);

        when(archivo.isEmpty()).thenReturn(false);
        when(archivo.getOriginalFilename()).thenReturn("foto.jpg");

        PedidoEspecialController controller =
                new PedidoEspecialController(
                        pedidoEspecialService,
                        tiendaService
                );

        String userDirAnterior = System.getProperty("user.dir");
        System.setProperty("user.dir", tempDir.toString());
        String vista;
        try {
            vista = controller.guardarPedidoEspecial(
                    pedidoEspecial,
                    1L,
                    archivo
            );
        } finally {
            System.setProperty("user.dir", userDirAnterior);
        }

        assertEquals("redirect:/pedidos-especiales", vista);

        verify(archivo).transferTo(any(java.io.File.class));
        verify(pedidoEspecialService).guardarPedidoEspecial(argThat(guardado ->
                "Cliente con imagen".equals(guardado.getCliente())
                        && guardado.getImagen() != null
                        && guardado.getImagen().endsWith("_foto.jpg")));
    }

    @Test
    void guardarPedidoEspecialConImagenYUploadsExistente(@TempDir Path tempDir) throws Exception {

        PedidoEspecialService pedidoEspecialService = mock(PedidoEspecialService.class);
        TiendaService tiendaService = mock(TiendaService.class);

        PedidoEspecialForm pedidoEspecial = new PedidoEspecialForm();
        pedidoEspecial.setCliente("Cliente con uploads existente");

        when(tiendaService.obtenerTiendaPorId(1L))
                .thenReturn(new com.espigapedidos.espigapedidos.entity.Tienda());

        org.springframework.web.multipart.MultipartFile archivo =
                mock(org.springframework.web.multipart.MultipartFile.class);

        when(archivo.isEmpty()).thenReturn(false);
        when(archivo.getOriginalFilename()).thenReturn("foto.jpg");

        PedidoEspecialController controller =
                new PedidoEspecialController(
                        pedidoEspecialService,
                        tiendaService
                );

        Files.createDirectories(tempDir.resolve("uploads"));

        String userDirAnterior = System.getProperty("user.dir");
        System.setProperty("user.dir", tempDir.toString());
        try {
            String vista = controller.guardarPedidoEspecial(
                    pedidoEspecial,
                    1L,
                    archivo
            );
            assertEquals("redirect:/pedidos-especiales", vista);
        } finally {
            System.setProperty("user.dir", userDirAnterior);
        }

        verify(archivo).transferTo(any(java.io.File.class));
        verify(pedidoEspecialService).guardarPedidoEspecial(argThat(guardado ->
                "Cliente con uploads existente".equals(guardado.getCliente())));
    }

    @Test
    void crearFormularioDesdeEntidadPedidoEspecial() {

        PedidoEspecial pedidoEspecial = new PedidoEspecial();
        pedidoEspecial.setId(7L);
        pedidoEspecial.setCliente("Cliente form");
        pedidoEspecial.setTelefono("999888777");
        pedidoEspecial.setDescripcion("Descripcion form");
        pedidoEspecial.setSabor("Chocolate");
        pedidoEspecial.setTamano("Grande");
        pedidoEspecial.setFechaEntrega(LocalDate.of(2026, Month.JULY, 10));
        pedidoEspecial.setEstado("Pendiente");

        PedidoEspecialForm formulario = PedidoEspecialForm.fromEntity(pedidoEspecial);

        assertEquals(7L, formulario.getId());
        assertEquals("Cliente form", formulario.getCliente());
        assertEquals("999888777", formulario.getTelefono());
        assertEquals("Descripcion form", formulario.getDescripcion());
        assertEquals("Chocolate", formulario.getSabor());
        assertEquals("Grande", formulario.getTamano());
        assertEquals(LocalDate.of(2026, Month.JULY, 10), formulario.getFechaEntrega());
        assertEquals("Pendiente", formulario.getEstado());
    }
}
