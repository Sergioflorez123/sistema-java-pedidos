package co.ucc.pedidos.service;

import co.ucc.pedidos.data.DatosPrueba;
import co.ucc.pedidos.exception.MontoInvalidoException;
import co.ucc.pedidos.exception.PagoNoEncontradoException;
import co.ucc.pedidos.model.DevolucionPago;
import co.ucc.pedidos.model.PagoModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PagoServiceTest {

    @InjectMocks
    private PagoService pagoService;

    private PagoModel pago;
    private PagoModel pagoInvalido;

    @BeforeEach
    void setUp() {
        pago = DatosPrueba.crearPago();
        pagoInvalido = DatosPrueba.crearPagoConMonto(0);
    }

    @Test
    void testRegistrarPago_DeberiaRegistrarExitosamente() {
        var resultado = pagoService.registrarPago(pago);
        
        assertTrue(resultado.isPresent());
        assertTrue(resultado.get().isProcesado());
        assertEquals("COMPLETADO", resultado.get().getEstado());
    }

    @Test
    void testRegistrarPago_Nulo_DeberiaLanzarExcepcion() {
        assertThrows(MontoInvalidoException.class, () -> {
            pagoService.registrarPago(null);
        });
    }

    @Test
    void testRegistrarPago_MontoCero_DeberiaLanzarExcepcion() {
        assertThrows(MontoInvalidoException.class, () -> {
            pagoService.registrarPago(pagoInvalido);
        });
    }

    @Test
    void testRegistrarPago_MontoNegativo_DeberiaLanzarExcepcion() {
        PagoModel pagoNegativo = DatosPrueba.crearPagoConMonto(-100);
        
        assertThrows(MontoInvalidoException.class, () -> {
            pagoService.registrarPago(pagoNegativo);
        });
    }

    @Test
    void testListarPagos_DeberiaRetornarListaVaciaInicialmente() {
        List<PagoModel> resultado = pagoService.listarPagos();
        
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    @Test
    void testListarPagos_DespuesDeRegistrar() {
        pagoService.registrarPago(pago);
        
        List<PagoModel> resultado = pagoService.listarPagos();
        
        assertEquals(1, resultado.size());
    }

    @Test
    void testBuscarPorId_Existente() {
        pagoService.registrarPago(pago);
        
        var resultado = pagoService.buscarPorId("PAG001");
        
        assertTrue(resultado.isPresent());
        assertEquals("PAG001", resultado.get().getIdPago());
    }

    @Test
    void testBuscarPorId_NoExistente_DeberiaLanzarExcepcion() {
        assertThrows(PagoNoEncontradoException.class, () -> {
            pagoService.buscarPorId("NOEXISTE");
        });
    }

    @Test
    void testValidarMetodoPago_Valido() {
        boolean resultado = pagoService.validarMetodoPago(pago);
        
        assertTrue(resultado);
    }

    @Test
    void testValidarMetodoPago_Nulo() {
        pago.setMetodoPago(null);
        
        boolean resultado = pagoService.validarMetodoPago(pago);
        
        assertFalse(resultado);
    }

    @Test
    void testValidarMetodoPago_Vacio() {
        pago.setMetodoPago("");
        
        boolean resultado = pagoService.validarMetodoPago(pago);
        
        assertFalse(resultado);
    }

    @Test
    void testProcesarPago_TransaccionValida() {
        pago.setMetodoPago("tarjeta_credito");
        
        pagoService.procesarPago(pago);
        
        assertTrue(pago.isProcesado());
        assertEquals("COMPLETADO", pago.getEstado());
    }

    @Test
    void testProcesarPago_TransaccionInvalida() {
        pago.setMetodoPago(null);
        
        pagoService.procesarPago(pago);
        
        assertFalse(pago.isProcesado());
    }

    @Test
    void testCancelarPago() {
        pagoService.registrarPago(pago);
        
        var resultado = pagoService.cancelarPago(pago.getIdPago());
        
        assertTrue(resultado.isPresent());
        assertFalse(resultado.get().isProcesado());
        assertEquals("CANCELADO", resultado.get().getEstado());
    }

    @Test
    void testGenerarComprobante() {
        String resultado = pagoService.generarComprobante(pago);
        
        assertNotNull(resultado);
        assertTrue(resultado.contains("PAG001"));
        assertTrue(resultado.contains("tarjeta_credito"));
    }

    @Test
    void testProcesarDevolucion_Valida() {
        PagoModel pagoOriginal = DatosPrueba.crearPago();
        DevolucionPago devolucion = DatosPrueba.crearDevolucionPago(pagoOriginal);
        
        boolean resultado = pagoService.procesarDevolucion(devolucion);
        
        assertTrue(resultado);
        assertTrue(devolucion.isApproved());
        assertEquals("COMPLETADA", devolucion.getEstado());
    }

    @Test
    void testProcesarDevolucion_MontoMayorQueOriginal() {
        PagoModel pagoOriginal = DatosPrueba.crearPago();
        pagoOriginal.setPrecio(50);
        
        DevolucionPago devolucion = new DevolucionPago();
        devolucion.setIdTransaccion("DEV001");
        devolucion.setMonto(100);
        devolucion.setPagoOriginal(pagoOriginal);
        
        boolean resultado = pagoService.procesarDevolucion(devolucion);
        
        assertFalse(resultado);
        assertEquals("RECHAZADA", devolucion.getEstado());
    }

    @Test
    void testProcesarDevolucion_SinPagoOriginal() {
        DevolucionPago devolucion = new DevolucionPago();
        devolucion.setIdTransaccion("DEV001");
        devolucion.setMonto(100);
        devolucion.setPagoOriginal(null);
        
        boolean resultado = pagoService.procesarDevolucion(devolucion);
        
        assertFalse(resultado);
    }
}
