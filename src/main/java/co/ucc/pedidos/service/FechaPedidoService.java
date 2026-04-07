package co.ucc.pedidos.service;

import co.ucc.pedidos.model.FechaPedidoModel;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class FechaPedidoService {

    public LocalDateTime calcularFechaEstimada(FechaPedidoModel fechaPedido) {
        return fechaPedido != null ? fechaPedido.getFechaEstimada() : null;
    }

    public void actualizarFechaRecibo(FechaPedidoModel fechaPedido, LocalDateTime fechaRecibir) {
        if (fechaPedido != null) {
            fechaPedido.setFechaRecibir(fechaRecibir);
        }
    }

    public boolean verificarRetrasoPedido(FechaPedidoModel fechaPedido) {
        if (fechaPedido == null) {
            return false;
        }
        LocalDateTime fechaRecibir = fechaPedido.getFechaRecibir();
        LocalDateTime fechaEstimada = fechaPedido.getFechaEstimada();
        return fechaRecibir != null && fechaEstimada != null && fechaRecibir.isAfter(fechaEstimada);
    }
}
