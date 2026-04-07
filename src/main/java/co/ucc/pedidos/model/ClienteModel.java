package co.ucc.pedidos.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;

@Entity
@Table(name = "cliente")
public class ClienteModel {
    @Id
    @Column(name = "id_cliente", length = 50)
    private String idCliente;
    @Column(name = "genero", length = 20)
    private String genero;
    @Column(name = "nombre", length = 100)
    private String nombre;
    @Column(name = "correo_electronico", length = 100)
    private String correoElectronico;
    @Column(name = "direccion", length = 255)
    private String direccion;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pago")
    private PagoModel pago;
    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
    private List<PedidoModel> pedidos;

    public ClienteModel() {}

    public ClienteModel(String idCliente, String genero, String nombre, String correoElectronico, String direccion) {
        this.idCliente = idCliente;
        this.genero = genero;
        this.nombre = nombre;
        this.correoElectronico = correoElectronico;
        this.direccion = direccion;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public PagoModel getPago() {
        return pago;
    }

    public void setPago(PagoModel pago) {
        this.pago = pago;
    }

    public List<PedidoModel> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<PedidoModel> pedidos) {
        this.pedidos = pedidos;
    }
}
