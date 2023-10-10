package uniquindio.proyectosoftware.modelo;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

public class Pedido implements Serializable {
    @Serial
    private static final long serialVersionUID = 1l;
    private String nit;
    private String fechaPedido;
    private double total;
    private double subTotal;
    private Empleado empleado;
    private Cliente cliente;
    private ArrayList<DetallePedido> detallePedido;

    public Pedido() {
    }

    public Pedido(String nit, String fechaPedido, double total, double subTotal, Empleado empleado, Cliente cliente) {
        this.nit = nit;
        this.fechaPedido = fechaPedido;
        this.total = total;
        this.subTotal = subTotal;
        this.empleado = empleado;
        this.cliente = cliente;
        detallePedido = new ArrayList<>();
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(String fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public ArrayList<DetallePedido> getDetallePedido() {
        return detallePedido;
    }

    public void setDetallePedido(ArrayList<DetallePedido> detallePedido) {
        this.detallePedido = detallePedido;
    }
}
