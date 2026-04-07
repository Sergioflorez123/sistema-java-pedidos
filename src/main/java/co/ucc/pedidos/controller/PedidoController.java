package co.ucc.pedidos.controller;

import co.ucc.pedidos.model.PedidoModel;
import co.ucc.pedidos.service.PedidoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping
    public List<PedidoModel> listarPedidos() {
        return pedidoService.findAll();
    }

    @GetMapping("/")
    public ResponseEntity<String> raiz() {
        return ResponseEntity.ok("API de pedidos: use /api/pedidos para listar pedidos");
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoModel> obtenerPedido(@PathVariable String id) {
        return pedidoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PedidoModel> crearPedido(@RequestBody PedidoModel pedido) {
        return pedidoService.save(pedido)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPedido(@PathVariable String id) {
        return pedidoService.delete(id)
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }
}