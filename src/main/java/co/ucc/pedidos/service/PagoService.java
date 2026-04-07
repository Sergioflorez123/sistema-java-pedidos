package co.ucc.pedidos.service;

import co.ucc.pedidos.model.DevolucionPago;
import co.ucc.pedidos.model.PagoModel;
import co.ucc.pedidos.repository.PagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PagoService {
    
    @Autowired
    private PagoRepository pagoRepository;

    @Transactional
    public Optional<PagoModel> registrarPago(PagoModel pago) {
        if (pago == null) {
            return Optional.empty();
        }
        if (pago.getMonto() <= 0) {
            return Optional.empty();
        }
        procesarPago(pago);
        return Optional.of(pagoRepository.save(pago));
    }

    public List<PagoModel> listarPagos() {
        return pagoRepository.findAll();
    }

    public Optional<PagoModel> buscarPorId(String id) {
        return pagoRepository.findByIdPago(id);
    }

    public boolean validarMetodoPago(PagoModel pago) {
        return pago.getMetodoPago() != null && !pago.getMetodoPago().isEmpty();
    }

    public void procesarPago(PagoModel pago) {
        if (pago.validarTransaccion()) {
            pago.setProcesado(true);
            pago.setEstado("COMPLETADO");
        }
    }

    @Transactional
    public Optional<PagoModel> cancelarPago(String idPago) {
        Optional<PagoModel> pagoOpt = pagoRepository.findByIdPago(idPago);
        if (pagoOpt.isPresent()) {
            PagoModel pago = pagoOpt.get();
            pago.setProcesado(false);
            pago.setEstado("CANCELADO");
            return Optional.of(pagoRepository.save(pago));
        }
        return Optional.empty();
    }

    public String generarComprobante(PagoModel pago) {
        return "Comprobante - Pago: " + pago.getIdPago() + ", Monto: " + pago.getPrecio() + ", Metodo: " + pago.getMetodoPago();
    }

    @Transactional
    public boolean procesarDevolucion(DevolucionPago devolucion) {
        if (devolucion.validarTransaccion()) {
            devolucion.setApproved(true);
            devolucion.setEstado("COMPLETADA");
            return true;
        }
        devolucion.setEstado("RECHAZADA");
        return false;
    }
    
    public List<PagoModel> findByClienteId(String idCliente) {
        return pagoRepository.findByClienteIdCliente(idCliente);
    }
}