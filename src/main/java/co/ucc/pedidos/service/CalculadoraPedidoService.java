package co.ucc.pedidos.service;

import co.ucc.pedidos.model.ProductoModel;
import org.springframework.stereotype.Service;

@Service
public class CalculadoraPedidoService {
    
    public double calcularTotal(ProductoModel producto) {
        if (producto == null || producto.getCantidad() <= 0) {
            return 0.0;
        }
        return producto.getCantidad() * obtenerPrecioBase(producto);
    }
    
    private double obtenerPrecioBase(ProductoModel producto) {
        return 100.0;
    }
    
    public double calcularImpuesto(double monto) {
        return monto * 0.19;
    }
    
    public double calcularTotalConImpuesto(double monto) {
        return monto + calcularImpuesto(monto);
    }
}
