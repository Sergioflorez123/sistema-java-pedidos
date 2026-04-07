package co.ucc.pedidos.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.OneToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.FetchType;

@Entity
@Table(name = "estado")
public class EstadoModel {
    @Id
    @Column(name = "id_estado")
    private String idEstado;
    @Column(name = "creado")
    private boolean creado;
    @Column(name = "enviado")
    private boolean enviado;
    @Column(name = "entregado")
    private boolean entregado;
    @Column(name = "cancelado")
    private boolean cancelado;
    @OneToOne(mappedBy = "estado", fetch = FetchType.LAZY)
    private PedidoModel pedido;

    public EstadoModel() {
        this.creado = true;
        this.enviado = false;
        this.entregado = false;
        this.cancelado = false;
    }

    public boolean isCreado() { return creado; }
    public void setCreado(boolean creado) { this.creado = creado; }

    public boolean isEnviado() { return enviado; }
    public void setEnviado(boolean enviado) { this.enviado = enviado; }

    public boolean isEntregado() { return entregado; }
    public void setEntregado(boolean entregado) { this.entregado = entregado; }

    public boolean isCancelado() { return cancelado; }
    public void setCancelado(boolean cancelado) { this.cancelado = cancelado; }

    public PedidoModel getPedido() { return pedido; }
    public void setPedido(PedidoModel pedido) { this.pedido = pedido; }

    public String getIdEstado() { return idEstado; }
    public void setIdEstado(String idEstado) { this.idEstado = idEstado; }
}
