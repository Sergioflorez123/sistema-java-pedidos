package co.ucc.pedidos.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.FetchType;

@Entity
@Table(name = "devolucion_pago")
public class DevolucionPago extends Transaccion {
    @Id
    @Column(name = "id_devolucion")
    private String idDevolucion;
    @Column(name = "motivo", length = 500)
    private String motivo;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pago_original")
    private PagoModel pagoOriginal;
    @Column(name = "approved")
    private boolean approved;

    public DevolucionPago() {
        super();
    }

    public DevolucionPago(String idTransaccion, double monto, String motivo, PagoModel pagoOriginal) {
        super(idTransaccion, monto);
        this.motivo = motivo;
        this.pagoOriginal = pagoOriginal;
        this.approved = false;
    }

    @Override
    public boolean validarTransaccion() {
        if (getMonto() <= 0) {
            return false;
        }
        if (pagoOriginal == null) {
            return false;
        }
        if (pagoOriginal.getMonto() < getMonto()) {
            return false;
        }
        return true;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public PagoModel getPagoOriginal() {
        return pagoOriginal;
    }

    public void setPagoOriginal(PagoModel pagoOriginal) {
        this.pagoOriginal = pagoOriginal;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public String getIdDevolucion() {
        return idDevolucion;
    }

    public void setIdDevolucion(String idDevolucion) {
        this.idDevolucion = idDevolucion;
    }
}
