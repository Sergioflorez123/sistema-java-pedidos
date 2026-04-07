package co.ucc.pedidos.service;

import co.ucc.pedidos.data.DatosPrueba;
import co.ucc.pedidos.model.InventarioModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class InventarioServiceTest {

    @InjectMocks
    private InventarioService inventarioService;

    private InventarioModel inventario;

    @BeforeEach
    void setUp() {
        inventario = DatosPrueba.crearInventario();
    }

    @Test
    void testVerificarDisponibilidad_Disponible() {
        boolean resultado = inventarioService.verificarDisponibilidad(inventario);
        
        assertTrue(resultado);
    }

    @Test
    void testVerificarDisponibilidad_NoDisponible() {
        inventario.setDisponibilidad(false);
        
        boolean resultado = inventarioService.verificarDisponibilidad(inventario);
        
        assertFalse(resultado);
    }

    @Test
    void testVerificarDisponibilidad_CantidadCero() {
        inventario.setCantidad(0);
        
        boolean resultado = inventarioService.verificarDisponibilidad(inventario);
        
        assertFalse(resultado);
    }

    @Test
    void testVerificarDisponibilidad_Nulo() {
        boolean resultado = inventarioService.verificarDisponibilidad(null);
        
        assertFalse(resultado);
    }

    @Test
    void testActualizarCantidad() {
        int nuevaCantidad = 75;
        
        inventarioService.actualizarCantidad(inventario, nuevaCantidad);
        
        assertEquals(nuevaCantidad, inventario.getCantidad());
    }

    @Test
    void testActualizarCantidad_Nulo() {
        assertDoesNotThrow(() -> {
            inventarioService.actualizarCantidad(null, 50);
        });
    }
}
