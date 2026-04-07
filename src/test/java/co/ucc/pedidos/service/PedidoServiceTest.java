package co.ucc.pedidos.service;

import co.ucc.pedidos.data.DatosPrueba;
import co.ucc.pedidos.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PedidoServiceTest {

    @Mock
    private CalculadoraPedidoService calculadora;

    @InjectMocks
    private PedidoService pedidoService;

    private PedidoModel pedido;
    private ProductoModel producto;
    private CategoriaModel categoria;

    @BeforeEach
    void setUp() {
        pedido = DatosPrueba.crearPedido();
        producto = DatosPrueba.crearProducto();
        categoria = DatosPrueba.crearCategoria();
    }

    @Test
    void testFindAll_DeberiaRetornarListaVacia() {
        List<PedidoModel> resultado = pedidoService.findAll();
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    @Test
    void testFindById_DeberiaRetornarNull() {
        var resultado = pedidoService.findById("PED001");
        assertTrue(resultado.isEmpty());
    }

    @Test
    void testSave_ConProducto_DeberiaCalcularTotal() {
        when(calculadora.calcularTotal(any(ProductoModel.class))).thenReturn(500.0);
        
        var resultado = pedidoService.save(pedido);
        
        assertTrue(resultado.isPresent());
        assertEquals(500.0, resultado.get().getPrecio());
        verify(calculadora).calcularTotal(any(ProductoModel.class));
    }

    @Test
    void testSave_SinProducto_NoDebeCalcularTotal() {
        pedido.setProducto(null);
        
        var resultado = pedidoService.save(pedido);
        
        assertTrue(resultado.isPresent());
        verify(calculadora, never()).calcularTotal(any());
    }

    @Test
    void testCalcularTotalPedido_ConProducto() {
        when(calculadora.calcularTotal(any(ProductoModel.class))).thenReturn(500.0);
        
        double resultado = pedidoService.calcularTotalPedido(pedido);
        
        assertEquals(500.0, resultado);
    }

    @Test
    void testCalcularTotalPedido_SinProducto() {
        pedido.setProducto(null);
        
        double resultado = pedidoService.calcularTotalPedido(pedido);
        
        assertEquals(0, resultado);
    }

    @Test
    void testCrearPedido_DeberiaSetearEstadoCreado() {
        pedidoService.crearPedido(pedido);
        
        assertTrue(pedido.getEstado().isCreado());
    }

    @Test
    void testCancelarPedido_DeberiaSetearEstadoCancelado() {
        var resultado = pedidoService.cancelarPedido(pedido.getIdPedido());
        
        assertTrue(resultado.isPresent());
        assertTrue(resultado.get().getEstado().isCancelado());
    }

    @Test
    void testCambiarEstado_DeberiaCambiarEstado() {
        EstadoModel nuevoEstado = new EstadoModel();
        nuevoEstado.setEnviado(true);
        
        var resultado = pedidoService.cambiarEstado(pedido.getIdPedido(), nuevoEstado);
        
        assertTrue(resultado.isPresent());
        assertTrue(resultado.get().getEstado().isEnviado());
    }

    @Test
    void testValidarCategoria_CategoriaValida() {
        boolean resultado = pedidoService.validarCategoria(categoria);
        
        assertTrue(resultado);
    }

    @Test
    void testValidarCategoria_CategoriaInvalida() {
        CategoriaModel categoriaInvalida = new CategoriaModel();
        
        boolean resultado = pedidoService.validarCategoria(categoriaInvalida);
        
        assertFalse(resultado);
    }

    @Test
    void testListarCategorias_DeberiaRetornarArray() {
        String[] resultado = pedidoService.listarCategorias(categoria);
        
        assertNotNull(resultado);
        assertEquals(4, resultado.length);
    }
}
