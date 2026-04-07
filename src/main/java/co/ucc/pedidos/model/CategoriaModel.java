package co.ucc.pedidos.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "categoria")
public class CategoriaModel {
    @Id
    @Column(name = "id_categoria")
    private String idCategoria;
    @Column(name = "comida")
    private boolean comida;
    @Column(name = "ropa")
    private boolean ropa;
    @Column(name = "electrodomestico")
    private boolean electrodomestico;
    @Column(name = "etc", length = 100)
    private String etc;

    public CategoriaModel() {}

    public String getIdCategoria() { return idCategoria; }
    public void setIdCategoria(String idCategoria) { this.idCategoria = idCategoria; }

    public boolean isComida() { return comida; }
    public void setComida(boolean comida) { this.comida = comida; }

    public boolean isRopa() { return ropa; }
    public void setRopa(boolean ropa) { this.ropa = ropa; }

    public boolean isElectrodomestico() { return electrodomestico; }
    public void setElectrodomestico(boolean electrodomestico) { this.electrodomestico = electrodomestico; }

    public String getEtc() { return etc; }
    public void setEtc(String etc) { this.etc = etc; }
}
