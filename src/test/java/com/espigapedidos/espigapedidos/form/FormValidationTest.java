package com.espigapedidos.espigapedidos.form;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FormValidationTest {

    private static Validator validator;

    @BeforeAll
    static void configurarValidador() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void productoRechazaPrecioCeroNegativoYStockNoPositivo() {
        ProductoForm form = productoValido();

        form.setPrecio(0.0);
        assertTieneError(form, "precio");

        form.setPrecio(-0.01);
        assertTieneError(form, "precio");

        form.setPrecio(5.50);
        form.setStock(0);
        assertTieneError(form, "stock");

        form.setStock(-1);
        assertTieneError(form, "stock");
    }

    @Test
    void productoValidoNoGeneraErrores() {
        assertTrue(validator.validate(productoValido()).isEmpty());
    }

    @Test
    void cantidadDebeSerMayorQueCero() {
        DetallePedidoForm form = new DetallePedidoForm();
        form.setCantidad(0);
        assertTieneError(form, "cantidad");

        form.setCantidad(-2);
        assertTieneError(form, "cantidad");

        form.setCantidad(1);
        assertTrue(validator.validate(form).isEmpty());
    }

    @Test
    void telefonoRechazaLetras() {
        TiendaForm form = new TiendaForm();
        form.setNombre("Tienda Centro");
        form.setDireccion("Av. Principal 123");
        form.setTelefono("987ABC321");
        form.setEstado("Activo");

        assertTieneError(form, "telefono");
    }

    private static ProductoForm productoValido() {
        ProductoForm form = new ProductoForm();
        form.setNombre("Pan integral");
        form.setCategoria("Panes");
        form.setPrecio(5.50);
        form.setStock(10);
        return form;
    }

    private static void assertTieneError(Object form, String campo) {
        Set<ConstraintViolation<Object>> errores = validator.validate(form);
        assertFalse(errores.isEmpty());
        assertTrue(errores.stream().anyMatch(error ->
                error.getPropertyPath().toString().equals(campo)));
    }
}
