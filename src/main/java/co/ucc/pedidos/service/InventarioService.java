package co.ucc.pedidos.service;

import co.ucc.pedidos.model.InventarioModel;
import org.springframework.stereotype.Service;

@Service
public class InventarioService {

    public boolean verificarDisponibilidad(InventarioModel inventario) {
        return inventario != null && inventario.isDisponibilidad() && inventario.getCantidad() > 0;
    }

    public void actualizarCantidad(InventarioModel inventario, int nuevaCantidad) {
        if (inventario != null) {
            inventario.setCantidad(nuevaCantidad);
        }
    }
}
