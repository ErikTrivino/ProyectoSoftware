package uniquindio.proyectosoftware.modelo;

import java.io.Serial;
import java.io.Serializable;

public class Producto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1l;
    private String id;
    private String nombre;
    private double precio;
    private String descripcion;
    private int porciones;
    private String imagen;

    public Producto() {
    }

    public Producto(String id, String nombre, double precio, String descripcion, int porciones, String imagen) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.porciones = porciones;
        this.imagen = imagen;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getPorciones() {
        return porciones;
    }

    public void setPorciones(int porciones) {
        this.porciones = porciones;
    }
}
