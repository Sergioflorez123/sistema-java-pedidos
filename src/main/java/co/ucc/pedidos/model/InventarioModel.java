package co.ucc.pedidos.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "inventario")
public class InventarioModel {
    @Id
    @Column(name = "id_inventario")
    private String idInventario;
    @Column(name = "cantidad")
    private int cantidad;
    @Column(name = "disponibilidad")
    private boolean disponibilidad;
    @Column(name = "categoria", length = 100)
    private String categoria;

    public InventarioModel() {}

    public String getIdInventario() { return idInventario; }
    public void setIdInventario(String idInventario) { this.idInventario = idInventario; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public boolean isDisponibilidad() { return disponibilidad; }
    public void setDisponibilidad(boolean disponibilidad) { this.disponibilidad = disponibilidad; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
}
