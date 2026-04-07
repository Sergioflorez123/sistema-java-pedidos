package co.ucc.pedidos.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.ucc.pedidos.model.ProductoModel;

@Repository
public interface ProductoRepository extends JpaRepository<ProductoModel, String> {

    Optional<ProductoModel> findByIdProducto(String idProducto);

    List<ProductoModel> findByCategoriaIdCategoria(String idCategoria);

    List<ProductoModel> findByInventarioIdInventario(String idInventario);

    boolean existsByIdProducto(String idProducto);

    void deleteByIdProducto(String idProducto);
}