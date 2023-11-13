package uniquindio.proyectosoftware.modelo;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

public class Pasteleria implements Serializable {
    @Serial
    private static final long serialVersionUID = 1l;
    private ArrayList<Empleado> listaEmpleados;
    private ArrayList<Cliente> listaClientes;
    private ArrayList<Producto> listaProductos;
    private ArrayList<Pedido> listaPedidos;
    private Empleado admin;
    public Pasteleria() {
        listaClientes = new ArrayList<>();
        listaProductos = new ArrayList<>();
        listaPedidos = new ArrayList<>();
        listaEmpleados= new ArrayList<>();
        admin = new Empleado("1111", "Erik", "312543", "erikpablot@gmail.com", new Usuario("erikpablot@gmail.com", "2222"), "", "2000", "15/10/10");

    }

    public Empleado getAdmin() {
        return admin;
    }

    public void setAdmin(Empleado admin) {
        this.admin = admin;
    }

    public ArrayList<Empleado> getListaEmpleados() {
        return listaEmpleados;
    }

    public void setListaEmpleados(ArrayList<Empleado> listaEmpleados) {
        this.listaEmpleados = listaEmpleados;
    }

    public ArrayList<Cliente> getListaClientes() {
        return listaClientes;
    }

    public void setListaClientes(ArrayList<Cliente> listaClientes) {
        this.listaClientes = listaClientes;
    }

    public ArrayList<Producto> getListaProductos() {
        return listaProductos;
    }

    public void setListaProductos(ArrayList<Producto> listaProductos) {
        this.listaProductos = listaProductos;
    }

    public ArrayList<Pedido> getListaPedidos() {
        return listaPedidos;
    }

    public void setListaPedidos(ArrayList<Pedido> listaPedidos) {
        this.listaPedidos = listaPedidos;
    }

    public boolean verificarLogin(String usuario, String contrasenia) {
        return true;
    }

    public Cliente obtenerClienteLogin(String nombre) {
        return null;
    }
}
