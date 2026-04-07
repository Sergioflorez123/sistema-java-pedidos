package co.ucc.pedidos.data;

import co.ucc.pedidos.model.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class DatosPrueba {

    public static ClienteModel crearCliente() {
        ClienteModel cliente = new ClienteModel();
        cliente.setIdCliente("CLI001");
        cliente.setNombre("Juan Perez");
        cliente.setGenero("M");
        cliente.setCorreoElectronico("juan@example.com");
        cliente.setDireccion("Calle 123, Bogota");
        return cliente;
    }

    public static ProductoModel crearProducto() {
        ProductoModel producto = new ProductoModel();
        producto.setIdProducto("PROD001");
        producto.setCantidad(5);
        producto.setResena("Excelente producto");
        
        InventarioModel inventario = new InventarioModel();
        inventario.setCantidad(50);
        inventario.setDisponibilidad(true);
        inventario.setCategoria("electronica");
        producto.setInventario(inventario);
        
        CategoriaModel categoria = new CategoriaModel();
        categoria.setComida(false);
        categoria.setRopa(false);
        categoria.setElectrodomestico(true);
        categoria.setEtc("electronica");
        producto.setCategoria(categoria);
        
        return producto;
    }

    public static PedidoModel crearPedido() {
        PedidoModel pedido = new PedidoModel();
        pedido.setIdPedido("PED001");
        pedido.setPrecio(500.00);
        pedido.setCategoria("electronica");
        pedido.setLugarEntrega("Calle 123, Bogota");
        pedido.setCliente(crearCliente());
        pedido.setProducto(crearProducto());
        return pedido;
    }

    public static PagoModel crearPago() {
        PagoModel pago = new PagoModel();
        pago.setIdPago("PAG001");
        pago.setPrecio(500.00);
        pago.setMetodoPago("tarjeta_credito");
        pago.setCliente(crearCliente());
        return pago;
    }

    public static PagoModel crearPagoConMonto(double monto) {
        PagoModel pago = new PagoModel();
        pago.setIdPago("PAG002");
        pago.setPrecio(monto);
        pago.setMetodoPago("tarjeta_credito");
        return pago;
    }

    public static DevolucionPago crearDevolucionPago(PagoModel pagoOriginal) {
        DevolucionPago devolucion = new DevolucionPago();
        devolucion.setIdTransaccion("DEV001");
        devolucion.setMonto(100.00);
        devolucion.setMotivo("Producto defectuoso");
        devolucion.setPagoOriginal(pagoOriginal);
        return devolucion;
    }

    public static InventarioModel crearInventario() {
        InventarioModel inventario = new InventarioModel();
        inventario.setCantidad(100);
        inventario.setDisponibilidad(true);
        inventario.setCategoria("ropa");
        return inventario;
    }

    public static CategoriaModel crearCategoria() {
        CategoriaModel categoria = new CategoriaModel();
        categoria.setComida(true);
        categoria.setRopa(false);
        categoria.setElectrodomestico(false);
        categoria.setEtc("comida");
        return categoria;
    }

    public static FechaPedidoModel crearFechaPedido() {
        FechaPedidoModel fecha = new FechaPedidoModel();
        fecha.setFechaPedir(LocalDateTime.now());
        fecha.setFechaEstimada(LocalDateTime.now().plusDays(1));
        return fecha;
    }

    public static List<PedidoModel> crearListaPedidos() {
        return Arrays.asList(crearPedido(), crearPedido());
    }

    public static List<PagoModel> crearListaPagos() {
        return Arrays.asList(crearPago(), crearPago());
    }
}
