package uniquindio.proyectosoftware.modelo;

import java.io.Serial;
import java.io.Serializable;
import java.util.PropertyResourceBundle;

public class DetallePedido implements Serializable {
    @Serial
    private static final long serialVersionUID = 1l;
    private String id;
    private int cantidad;
    private Producto producto;

    public DetallePedido() {
    }

    public DetallePedido(String id, int cantidad, Producto producto) {
        this.id = id;
        this.cantidad = cantidad;
        this.producto = producto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }
}
