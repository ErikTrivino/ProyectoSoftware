package uniquindio.proyectosoftware.modelo;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

public class Cliente extends Persona implements Serializable {
    @Serial
    private static final long serialVersionUID = 1l;
    private String id;
    private String fecahRegistro;
    private ArrayList<Producto> carrito;
    private ArrayList<Producto> favoritos;
    private ArrayList<Pedido> pedidos;


    public Cliente() {
        this.carrito = new ArrayList<>();
        this.favoritos = new ArrayList<>();
        this.pedidos = new ArrayList<>();
    }

    public Cliente(String cedula, String nombre, String telefono, String email, Usuario usuario, String imageUrl, String id, String fecahRegistro) {
        super(cedula, nombre, telefono, email, usuario, imageUrl);
        this.id = id;
        this.fecahRegistro = fecahRegistro;
        this.carrito = new ArrayList<>();
        this.favoritos = new ArrayList<>();
        this.pedidos = new ArrayList<>();
    }

    public ArrayList<Producto> getCarrito() {
        return carrito;
    }

    public void setCarrito(ArrayList<Producto> carrito) {
        this.carrito = carrito;
    }

    public ArrayList<Producto> getFavoritos() {
        return favoritos;
    }

    public void setFavoritos(ArrayList<Producto> favoritos) {
        this.favoritos = favoritos;
    }

    public ArrayList<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(ArrayList<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFecahRegistro() {
        return fecahRegistro;
    }

    public void setFecahRegistro(String fecahRegistro) {
        this.fecahRegistro = fecahRegistro;
    }
}
