package co.ucc.pedidos.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.ucc.pedidos.model.InventarioModel;

@Repository
public interface InventarioRepository extends JpaRepository<InventarioModel, String> {

    Optional<InventarioModel> findByIdInventario(String idInventario);

    List<InventarioModel> findByCategoria(String categoria);

    List<InventarioModel> findByDisponibilidad(boolean disponibilidad);

    boolean existsByIdInventario(String idInventario);

    void deleteByIdInventario(String idInventario);
}