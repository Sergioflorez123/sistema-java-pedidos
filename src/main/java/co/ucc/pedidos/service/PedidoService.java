package co.ucc.pedidos.service;

import co.ucc.pedidos.model.CategoriaModel;
import co.ucc.pedidos.model.EstadoModel;
import co.ucc.pedidos.model.PedidoModel;
import co.ucc.pedidos.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {
    
    private final CalculadoraPedidoService calculadora;
    
    @Autowired
    private PedidoRepository pedidoRepository;

    public PedidoService(CalculadoraPedidoService calculadora) {
        this.calculadora = calculadora;
    }

    public List<PedidoModel> findAll() {
        return pedidoRepository.findAll();
    }

    public Optional<PedidoModel> findById(String id) {
        return pedidoRepository.findByIdPedido(id);
    }

    @Transactional
    public Optional<PedidoModel> save(PedidoModel pedido) {
        if (pedido == null || pedido.getIdPedido() == null || pedido.getIdPedido().isEmpty()) {
            return Optional.empty();
        }
        if (pedido.getProducto() != null) {
            double total = calculadora.calcularTotal(pedido.getProducto());
            pedido.setPrecio(total);
        }
        return Optional.of(pedidoRepository.save(pedido));
    }

    @Transactional
    public boolean delete(String id) {
        if (pedidoRepository.existsByIdPedido(id)) {
            pedidoRepository.deleteByIdPedido(id);
            return true;
        }
        return false;
    }

    @Transactional
    public Optional<PedidoModel> actualizarEstado(String id, EstadoModel nuevoEstado) {
        Optional<PedidoModel> pedidoOpt = pedidoRepository.findByIdPedido(id);
        if (pedidoOpt.isPresent()) {
            PedidoModel pedido = pedidoOpt.get();
            pedido.setEstado(nuevoEstado);
            return Optional.of(pedidoRepository.save(pedido));
        }
        return Optional.empty();
    }

    public double calcularTotalPedido(PedidoModel pedido) {
        if (pedido.getProducto() != null) {
            return calculadora.calcularTotal(pedido.getProducto());
        }
        return 0;
    }

    public Optional<PedidoModel> crearPedido(PedidoModel pedido) {
        if (pedido == null || pedido.getIdPedido() == null || pedido.getIdPedido().isEmpty()) {
            return Optional.empty();
        }
        if (pedido.getEstado() == null) {
            pedido.setEstado(new EstadoModel());
        }
        pedido.getEstado().setCreado(true);
        return Optional.of(pedido);
    }

    public Optional<PedidoModel> cancelarPedido(String idPedido) {
        Optional<PedidoModel> pedidoOpt = pedidoRepository.findByIdPedido(idPedido);
        if (pedidoOpt.isPresent()) {
            PedidoModel pedido = pedidoOpt.get();
            if (pedido.getEstado() == null) {
                pedido.setEstado(new EstadoModel());
            }
            pedido.getEstado().setCancelado(true);
            return Optional.of(pedidoRepository.save(pedido));
        }
        return Optional.empty();
    }

    public Optional<PedidoModel> cambiarEstado(String idPedido, EstadoModel nuevoEstado) {
        Optional<PedidoModel> pedidoOpt = pedidoRepository.findByIdPedido(idPedido);
        if (pedidoOpt.isPresent()) {
            PedidoModel pedido = pedidoOpt.get();
            pedido.setEstado(nuevoEstado);
            return Optional.of(pedidoRepository.save(pedido));
        }
        return Optional.empty();
    }

    public boolean validarCategoria(CategoriaModel categoria) {
        return categoria != null && (categoria.isComida() || categoria.isRopa() || categoria.isElectrodomestico() || (categoria.getEtc() != null && !categoria.getEtc().isEmpty()));
    }

    public String[] listarCategorias(CategoriaModel categoria) {
        return new String[]{"comida", "ropa", "electrodomestico", categoria.getEtc()};
    }
    
    public List<PedidoModel> findByClienteId(String idCliente) {
        return pedidoRepository.findByClienteIdCliente(idCliente);
    }
}