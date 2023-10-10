package uniquindio.proyectosoftware.modelo;

import javafx.scene.control.Alert;

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

    public Pasteleria() {
        listaClientes = new ArrayList<>();
        listaProductos = new ArrayList<>();
        listaPedidos = new ArrayList<>();
        listaEmpleados= new ArrayList<>();
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
