package uniquindio.proyectosoftware.controllers;

import javafx.scene.control.Alert;
import uniquindio.proyectosoftware.hilos.HiloCorreo;
import uniquindio.proyectosoftware.exceptions.ClienteNoExiste;
import uniquindio.proyectosoftware.modelo.*;
import uniquindio.proyectosoftware.persistence.Persistencia;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ModelFactoryController  {
    Pasteleria pasteleria;
    Cliente clienteActual;
    Empleado empleadoActual;
    boolean empleadoLogin;

    String mensaje;
    int nivel;
    String accion;


    public Pasteleria getPasteleria() {
        return pasteleria;
    }

    public void setPasteleria(Pasteleria pasteleria) {
        this.pasteleria = pasteleria;
    }

    public void verificarLoginEmpleado(String usuario, String contrasenia) {
        Optional<Empleado> cliente = pasteleria.getListaEmpleados().stream().filter(x->x.getUsuario().getNombreUsuario().equals(usuario)).findFirst();
        if(cliente.isPresent()){
            if(cliente.get().getUsuario().getContrasenia().equals(contrasenia)){
                empleadoActual = cliente.get();
                empleadoLogin = true;
            }else{
                if(cliente.get().getUsuario().getContraseniaTemp().equals(contrasenia)){
                    empleadoActual = cliente.get();
                    empleadoLogin = true;
                    cliente.get().getUsuario().setContraseniaTemp("");                }
            }

        }
    }

    public boolean isLoginEmpleado() {
        return empleadoLogin;
    }

    public void agregarEmpleado(Empleado empleado) {
        pasteleria.getListaEmpleados().add(empleado);
    }

    public void mandarCorreoNotificacionCliente(String cedulaCliente, String direccion, Producto producto) {
        Cliente cliente = pasteleria.getListaClientes().stream().filter(x-> x.getCedula().equals(cedulaCliente)).findFirst().get();

        String contenido = "El cliente con cedula: "+cliente.getCedula()+" y "+cliente.getNombre()+"compro el producto "+producto.getNombre()+" y esta es la direccin donde le llegara" +
                ": "+direccion;

        HiloCorreo hiloCorreo = new HiloCorreo(cliente.getUsuario().getNombreUsuario(), contenido);
        hiloCorreo.start();

    }

    public void agregarContraseniaTemp(String correo, String contraseniaTemp) {

        pasteleria.getListaEmpleados().stream().filter(x -> x.getUsuario().getNombreUsuario().equals(correo)).findFirst().get().getUsuario().setContraseniaTemp(contraseniaTemp);
    }

    public List<Producto> obtenerListaDeProductos() {
        return pasteleria.getListaProductos();
    }

    public int obtenerCantUsuarios() {
        return pasteleria.getListaClientes().size()+pasteleria.getListaEmpleados().size();
    }

    private static class SingletonHolder {
        // El constructor de Singleton puede ser llamado desde aquí al ser protected
        private final static ModelFactoryController eINSTANCE = new ModelFactoryController();
    }
    public static ModelFactoryController getInstance() {
        return SingletonHolder.eINSTANCE;
    }
    public ModelFactoryController() {

        inicializarDatos();
        //4. Guardar y Cargar el recurso serializable XML
	     guardarResourceXML();
        //cargarResourceXML();

        //Siempre se debe verificar si la raiz del recurso es null

        if(pasteleria == null){
            inicializarDatos();
            guardarResourceXML();
        }
        //registrarAccionesSistema("Datos cargados", 1, "Cargar datos");
        //inicializarDatos();
    }
    public void guardarRespaldosArchivos(){
        Persistencia.copiarArchivoRespaldoXml();

    }
    public void guardarDatosArchivos() throws IOException {


        Persistencia.guardarRecursoMarketplaceXML(pasteleria);


    }
    public void inicializarDatos() {

        pasteleria = new Pasteleria();
        Empleado empleado1 = new Empleado();
        empleado1.setCedula("1234567890");
        empleado1.setNombre("Juan Pérez");
        empleado1.setTelefono("123-456-7890");
        empleado1.setEmail("juan@example.com");
        empleado1.setSalario("50000");
        empleado1.setFechaContratacion("2023-01-15");
        empleado1.setUsuario(new Usuario("erikpablot@gmail.com", "1111"));

        Empleado empleado2 = new Empleado();
        empleado2.setCedula("9876543210");
        empleado2.setNombre("María González");
        empleado2.setTelefono("987-654-3210");
        empleado2.setEmail("maria@example.com");
        empleado2.setSalario("55000");
        empleado2.setFechaContratacion("2023-02-20");
        empleado2.setUsuario(new Usuario("erikpablot@gmail.com", "2222"));

        // Agregar los empleados a la pastelería
        pasteleria.getListaEmpleados().add(empleado1);
        pasteleria.getListaEmpleados().add(empleado2);

        // Crear algunos clientes de prueba
        Cliente cliente1 = new Cliente();
        cliente1.setCedula("1111111111");
        cliente1.setNombre("Carlos Rodríguez");
        cliente1.setTelefono("111-111-1111");
        cliente1.setEmail("carlos@example.com");
        cliente1.setUsuario(new Usuario("erikpablot@gmail.com", "3333"));


        Cliente cliente2 = new Cliente();
        cliente2.setCedula("2222222222");
        cliente2.setNombre("Ana López");
        cliente2.setTelefono("222-222-2222");
        cliente2.setEmail("ana@example.com");
        cliente2.setUsuario(new Usuario("erikpablot@gmail.com", "4444"));


        // Agregar los clientes a la pastelería
        pasteleria.getListaClientes().add(cliente1);
        pasteleria.getListaClientes().add(cliente2);
        ArrayList<Producto> productos = new ArrayList<>();

        productos.add(new Producto("1", "Pastel de Chocolate", 15.99, "Delicioso pastel de chocolate", 8, "/uniquindio/proyectosoftware/image/imagen-prodcuto7.jpg"));
        productos.add(new Producto("2", "Tarta de Fresa", 12.49, "Tarta fresca de fresas", 6, "/uniquindio/proyectosoftware/image/imagen-prodcuto1.jpg"));
        productos.add(new Producto("3", "Cupcakes de Vainilla", 9.99, "Cupcakes de vainilla decorados", 12, "/uniquindio/proyectosoftware/image/imagen-prodcuto3.jpg"));
        productos.add(new Producto("4", "Galletas de Chocolate", 7.99, "Galletas crujientes de chocolate", 20, "/uniquindio/proyectosoftware/image/imagen-prodcuto4.jpg"));
        productos.add(new Producto("5", "Galletas de Vainilla", 7.99, "Galletas crujientes de chocolate", 20, "/uniquindio/proyectosoftware/image/imagen-prodcuto4.jpg"));
        productos.add(new Producto("6", "Pastel de Chocolate Grande", 25.99, "Galletas crujientes de chocolate", 20, "/uniquindio/proyectosoftware/image/imagen-prodcuto7.jpg"));
        productos.add(new Producto("7", "Pastel de Vainilla", 7.99, "Galletas crujientes de chocolate", 20, "/uniquindio/proyectosoftware/image/imagen-prodcuto5.jpg"));
        productos.add(new Producto("8", "Pastel de Coco", 7.99, "Galletas crujientes de chocolate", 20, "/uniquindio/proyectosoftware/image/imagen-prodcuto5.jpg"));
        pasteleria.setListaProductos(productos);



        registrarAccionesSistema("incio sesion", 1, "inicializar Datos");



    }

    private void cargarResourceXML(){
        pasteleria = Persistencia.cargarRecursoMarketplaceXML();
    }
    private void guardarResourceXML() {
        Persistencia.guardarRecursoMarketplaceXML(pasteleria);

    }

    public void registrarAccionesSistema(String mensaje, int nivel, String accion) {
        Persistencia.guardaRegistroLog(mensaje, nivel, accion);
    }

    public boolean verificarLogin(String usuario, String contrasenia) {
        return this.getPasteleria().verificarLogin(usuario, contrasenia);
    }
    public Cliente loginCliente(String nombre) throws ClienteNoExiste {
        return this.getPasteleria().obtenerClienteLogin(nombre);
    }


    void mostrarMensaje(String titulo, String header, String contenido, Alert.AlertType alertType) {

        Alert alerta = new Alert(alertType);
        alerta.setTitle(titulo);
        alerta.setHeaderText(header);
        alerta.setContentText(contenido);
        alerta.showAndWait();

    }


}
