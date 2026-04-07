package co.ucc.pedidos.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.FetchType;

@Entity
@Table(name = "producto")
public class ProductoModel {
    @Id
    @Column(name = "id_producto")
    private String idProducto;
    @Column(name = "cantidad")
    private int cantidad;
    @Column(name = "resena", length = 500)
    private String resena;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_inventario")
    private InventarioModel inventario;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categoria")
    private CategoriaModel categoria;

    public ProductoModel() {}

    public String getIdProducto() { return idProducto; }
    public void setIdProducto(String idProducto) { this.idProducto = idProducto; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public String getResena() { return resena; }
    public void setResena(String resena) { this.resena = resena; }

    public InventarioModel getInventario() { return inventario; }
    public void setInventario(InventarioModel inventario) { this.inventario = inventario; }

    public CategoriaModel getCategoria() { return categoria; }
    public void setCategoria(CategoriaModel categoria) { this.categoria = categoria; }
}
