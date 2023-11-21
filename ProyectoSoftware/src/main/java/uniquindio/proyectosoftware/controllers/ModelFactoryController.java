package uniquindio.proyectosoftware.controllers;

import javafx.scene.control.Alert;
import uniquindio.proyectosoftware.hilos.HiloCorreo;
import uniquindio.proyectosoftware.exceptions.ClienteNoExiste;
import uniquindio.proyectosoftware.modelo.*;
import uniquindio.proyectosoftware.persistence.Persistencia;


import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

public class ModelFactoryController  {
    Pasteleria pasteleria;
    Cliente clienteActual;
    Empleado empleadoActual;
    boolean empleadoLogin;

    String mensaje;
    int nivel;
    String accion;

    ArrayList<Producto> listaProductosCarrito = new ArrayList<>();
    ArrayList<Producto> listaProductosFavoritos = new ArrayList<>();


    public Cliente getClienteActual() {
        return clienteActual;
    }

    public void setClienteActual(Cliente clienteActual) {
        this.clienteActual = clienteActual;
    }

    public ArrayList<Producto> getListaProductosFavoritos() {
        return listaProductosFavoritos;
    }

    public void setListaProductosFavoritos(ArrayList<Producto> listaProductosFavoritos) {
        this.listaProductosFavoritos = listaProductosFavoritos;
    }

    public double generarValorProdcutosCarrito() {
        double valor = 0;
    for (Producto p:listaProductosCarrito){
        valor+=p.getPrecio();
    }
        return valor;
    }
    private static String generarHTML(ArrayList<DetallePedido> detallePedidos, Pedido pedido) {
        StringBuilder htmlBuilder = new StringBuilder();

        // Encabezado del HTML
        htmlBuilder.append("<html><head><style>")
                .append("body {font-family: Arial, sans-serif; margin: 20px;}")
                .append("h2, h3 {color: #333;}")
                .append("p, li {color: #666;}")
                .append("</style></head><body>");

        htmlBuilder.append("<html><head><title>Detalles del Pedido</title></head><body>");

        // Detalles del pedido
        htmlBuilder.append("<h2>Resumen del Pedido</h2>");
        htmlBuilder.append("<p>Nombre cliente: ").append(pedido.getCliente().getNombre()).append("</p>");
        htmlBuilder.append("<p>Cedula cliente: ").append(pedido.getCliente().getCedula()).append("</p>");
        htmlBuilder.append("<p>Direccion: ").append(pedido.getDireccion()).append("</p>");
        htmlBuilder.append("<p>NIT: ").append(pedido.getNit()).append("</p>");
        htmlBuilder.append("<p>Fecha: ").append(pedido.getFechaPedido()).append("</p>");
        htmlBuilder.append("<p>Tipo Pago: ").append(pedido.getTipoPago()).append("</p>");
        htmlBuilder.append("<p>Valor Total: ").append(pedido.getTotal()).append("</p>");

        // Detalles de los productos en el carrito
        htmlBuilder.append("<h3>Detalles del pedido productos:</h3>");
        htmlBuilder.append("<ul>");
        for (DetallePedido detalle : detallePedidos) {
            htmlBuilder.append("<li>").append(detalle.getProducto().getNombre()).append(" - Cantidad: ").append(detalle.getCantidad()).append("</li>");
        }
        htmlBuilder.append("</ul>");

        // Fin del HTML
        htmlBuilder.append("</body></html>");
        return htmlBuilder.toString();
    }
    public void procederPago(String tipoPago, String direccion) {
        int cont=1;
        ArrayList<DetallePedido> detallePedidos = new ArrayList<>();
        for (Producto p:listaProductosCarrito){
            DetallePedido detallePedido = new DetallePedido(String.valueOf(cont), 1, p);
            detallePedidos.add(detallePedido);
        }

        Pedido pedido = new Pedido(generarNIT(), String.valueOf(LocalDate.now()),generarValorProdcutosCarrito(), 0,getPasteleria().getAdmin(), clienteActual);
        pedido.setDireccion(direccion);
        pedido.setTipoPago(tipoPago);
        pasteleria.getListaPedidos().add(pedido);
        System.out.println("Correo pedido se esta generando");
        HiloCorreo hiloCorreo = new HiloCorreo(clienteActual.getEmail(), generarHTML(detallePedidos, pedido));
        hiloCorreo.start();


    }
    public void procederPagoPrioducto(){

    }
    public static String generarNIT() {
        // El formato de NIT puede variar, ajusta según sea necesario
        StringBuilder nit = new StringBuilder("1"); // Primer dígito puede variar según país

        // Generar 9 dígitos aleatorios
        Random random = new Random();
        for (int i = 0; i < 9; i++) {
            nit.append(random.nextInt(10));
        }

        // Calcular el dígito verificador (puede variar según país)
        int suma = 0;
        for (int i = 0; i < nit.length(); i++) {
            suma += Character.getNumericValue(nit.charAt(i)) * (i + 2);
        }
        int digitoVerificador = (11 - (suma % 11)) % 11;

        nit.append(digitoVerificador);

        return nit.toString();
    }

    public boolean verificarAccesoLogin(String text) {
        if(clienteActual.getUsuario().getContraseniaTemp().equals(text)){
            clienteActual.getUsuario().setContraseniaTemp("");
            return true;
        }else {
            return false;
        }

    }

    public List<Producto> obtenerListaProductosFav() {
        return listaProductosFavoritos;
    }

    public List<Pedido> obtenerListaPedidosCliente() {
        return clienteActual.getPedidos();
    }

    public void crearPqrs(String tipoPQRS, String mensaje) {
        Pqrs pqrs = new Pqrs(clienteActual,tipoPQRS, mensaje );
        pasteleria.getListaPqrs().add(pqrs);
    }

    public List<Pedido> obtenerListaProductosClietes() {
        return clienteActual.getPedidos();
    }

    public void crearDevolucionProducto(Producto producto) {
        for (Pedido p:clienteActual.getPedidos()
             ) {
            int cont = 0;
            int contF = 0;
            for (DetallePedido detallePedido: p.getDetallePedido()
                 ) {

                if(detallePedido.getProducto().getNombre().equals(producto.getNombre())){

                    contF = cont;
                    //logica nose
                }
                cont++;
            }
            p.getDetallePedido().remove(contF);

            int totalNuevo = 0;
            for (DetallePedido detallePedido: p.getDetallePedido()
            ) {
                totalNuevo+= detallePedido.getProducto().getPrecio()*detallePedido.getCantidad();
            }
            p.setTotal(totalNuevo);

        }
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>");
        html.append("<html>");
        html.append("<head>");
        html.append("<title>Producto Quitado del Pedido</title>");
        html.append("<style>");
        html.append("body { font-family: Arial, sans-serif; }");
        html.append("h1 { color: #FF5733; }");
        html.append("ul { list-style-type: none; padding-left: 0; }");
        html.append("li { margin-bottom: 10px; }");
        html.append("</style>");
        html.append("</head>");
        html.append("<body>");

        // Contenido principal
        html.append("<div style='max-width: 600px; margin: 0 auto;'>");
        html.append("<h1>");
        html.append("Hola ");
        html.append(clienteActual.getNombre());
        html.append("</h1>");
        html.append("<h1>¡Producto quitado del pedido!</h1>");
        html.append("<p>El siguiente producto ha sido quitado de su pedido:</p>");

        // Detalles del producto quitado
        html.append("<ul>");
        html.append("<li><strong>Nombre:</strong> ").append(producto.getNombre()).append("</li>");
        html.append("<li><strong>Precio:</strong> ").append(producto.getPrecio()).append("</li>");
        // Agregar más detalles si es necesario
        html.append("</ul>");

        html.append("</div>");
        html.append("</body>");
        html.append("</html>");
        HiloCorreo hiloCorreo = new HiloCorreo(clienteActual.getUsuario().getNombreUsuario(), html.toString());
        hiloCorreo.start();
    }

    public void generarCambio(Producto producto, Producto productoCambio) {
        for (Pedido p : clienteActual.getPedidos()
        ) {

            for (DetallePedido detallePedido : p.getDetallePedido()
            ) {

                if (detallePedido.getProducto().getNombre().equals(productoCambio.getNombre())) {

//                    System.out.println(producto.getNombre());
//                    System.out.println(productoCambio.getNombre());
                    detallePedido.setProducto(producto);
                }

            }


        }
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>");
        html.append("<html>");
        html.append("<head>");
        html.append("<title>Modificación del Pedido</title>");
        html.append("</head>");
        html.append("<body>");

        // Mensaje de anuncio al usuario sobre la modificación del pedido
        html.append("<h1>¡Su pedido ha sido modificado!</h1>");
        html.append("<p>Se ha realizado un cambio en su pedido. Detalles a continuación:</p>");

        // Detalles de los productos modificados
        html.append("<p>Producto original:</p>");
        html.append("<ul>");
        html.append("<li><strong>Nombre:</strong> ").append(producto.getNombre()).append("</li>");
        html.append("<li><strong>Precio:</strong> ").append(producto.getPrecio()).append("</li>");
        // Agregar más detalles si es necesario
        html.append("</ul>");

        html.append("<p>Producto modificado:</p>");
        html.append("<ul>");
        html.append("<li><strong>Nombre:</strong> ").append(productoCambio.getNombre()).append("</li>");
        html.append("<li><strong>Precio:</strong> ").append(productoCambio.getPrecio()).append("</li>");
        // Agregar más detalles si es necesario
        html.append("</ul>");

        html.append("</body>");
        html.append("</html>");
        HiloCorreo hiloCorreo = new HiloCorreo(clienteActual.getUsuario().getNombreUsuario(), html.toString());
        hiloCorreo.start();

        mostrarMensaje("Confirmacion cambio", "Confirmacion cambio", "Se ha hecho el cambio de  su producto", Alert.AlertType.INFORMATION);
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
        empleado1.setUsuario(new Usuario("erikpablot1@gmail.com", "1111"));

        Empleado empleado2 = new Empleado();
        empleado2.setCedula("9876543210");
        empleado2.setNombre("María González");
        empleado2.setTelefono("987-654-3210");
        empleado2.setEmail("maria@example.com");
        empleado2.setSalario("55000");
        empleado2.setFechaContratacion("2023-02-20");
        empleado2.setUsuario(new Usuario("erikpablot@gmail2.com", "2222"));

        // Agregar los empleados a la pastelería
        pasteleria.getListaEmpleados().add(empleado1);
        pasteleria.getListaEmpleados().add(empleado2);

        // Crear algunos clientes de prueba
        Cliente cliente1 = new Cliente();
        cliente1.setCedula("1111111111");
        cliente1.setNombre("Carlos Rodríguez");
        cliente1.setTelefono("111-111-1111");
        cliente1.setEmail("erikpablot@gmail.com");
        cliente1.setUsuario(new Usuario("erikpablot@gmail.com", "3333"));
        clienteActual = cliente1;


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
        Pedido pedido = new Pedido("1231", "20/2508", 123.0, 0, empleado1, cliente1);
        DetallePedido detallePedido = new DetallePedido("1", 1, productos.get(0));
        pedido.getDetallePedido().add(detallePedido);
        clienteActual.getPedidos().add(pedido);
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
    public ArrayList<Producto> obtenerListaProductosCarrito(){
        return listaProductosCarrito;
    }
    public void agregarProductoCarrito(Producto producto){
        if(!listaProductosCarrito.contains(producto)){
            listaProductosCarrito.add(producto);
            clienteActual.setCarrito(listaProductosCarrito);
        }else{
            mostrarMensaje("Informacion", "Informacion", "El producto ya se encuentra en carrito", Alert.AlertType.INFORMATION);
        }
    }
    public void eliminarProductoCarrito(Producto producto){
        listaProductosCarrito.remove(producto);
        clienteActual.setCarrito(listaProductosCarrito);
    }
    public boolean agregarProductoFav(Producto producto){
        if(!listaProductosFavoritos.contains(producto)){
            listaProductosFavoritos.add(producto);
            clienteActual.setFavoritos(listaProductosFavoritos);
            return true;
        }else{
            mostrarMensaje("Informacion", "Informacion", "El producto ya se encuentra en favoritos", Alert.AlertType.INFORMATION);
            return false;
        }
    }
    public void eliminarProductoFav(Producto producto){
        listaProductosFavoritos.remove(producto);

        clienteActual.setFavoritos(listaProductosFavoritos);
    }


    void mostrarMensaje(String titulo, String header, String contenido, Alert.AlertType alertType) {

        Alert alerta = new Alert(alertType);
        alerta.setTitle(titulo);
        alerta.setHeaderText(header);
        alerta.setContentText(contenido);
        alerta.showAndWait();

    }
    public Pasteleria getPasteleria() {
        return pasteleria;
    }

    public void setPasteleria(Pasteleria pasteleria) {
        this.pasteleria = pasteleria;
    }

    public void verificarLoginEmpleado(String usuario, String contrasenia) {
        Optional<Cliente> cliente = pasteleria.getListaClientes().stream().filter(x->x.getUsuario().getNombreUsuario().equals(usuario)).findFirst();
        if(cliente.isPresent()){
            if(cliente.get().getUsuario().getContrasenia().equals(contrasenia)){
                clienteActual = cliente.get();
                listaProductosCarrito = clienteActual.getCarrito();
                empleadoLogin = true;
            }else{
                if(cliente.get().getUsuario().getContraseniaTemp().equals(contrasenia)){
                    clienteActual = cliente.get();
                    listaProductosCarrito = clienteActual.getCarrito();
                    empleadoLogin = true;
                    cliente.get().getUsuario().setContraseniaTemp("");                }
            }

        }
    }

    public boolean isLoginEmpleado() {
        return empleadoLogin;
    }

    public void agregarEmpleado(Cliente empleado) {
        pasteleria.getListaClientes().add(empleado);
    }

    public void mandarCorreoNotificacionCliente(String cedulaCliente, String direccion, Producto producto, String tipoPago) {
        Cliente cliente = pasteleria.getListaClientes().stream().filter(x-> x.getCedula().equals(cedulaCliente)).findFirst().get();

        Pedido pedido= new Pedido(generarNIT(), String.valueOf(LocalDate.now()), producto.getPrecio(), 0, pasteleria.getAdmin(), clienteActual);
        DetallePedido detallePedido = new DetallePedido(producto.getId(), 1, producto);
        pedido.getDetallePedido().add(detallePedido);
        pasteleria.getListaPedidos().add(pedido);
        clienteActual.getPedidos().add(pedido);
        String contenido = "<html>" +
                "<head>" +
                "<style>" +
                "body { font-family: Arial, sans-serif; }" +
                ".seccion { border: 1px solid #ccc; padding: 10px; margin: 10px 0; }" +
                ".titulo-seccion { font-size: 18px; font-weight: bold; }" +
                ".datos-cliente { font-style: italic; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<div class='seccion'>" +
                "<div class='titulo-seccion'>DIRECCIÓN</div>" +
                "<p>Direccion: " + direccion + "</p>" +
                "</div>" +
//                "<div class='seccion'>" +
//                "<div class='titulo-seccion'>ENVÍO</div>" +
//                "</div>" +
                "<div class='seccion'>" +
                "<div class='titulo-seccion'>PAGO</div>" +
                "<p>Tipo pago: " + tipoPago + "</p>" +
                "<p>Valor pago: " + producto.getPrecio() + "</p>" +
                "</div>" +
                "<div class='seccion'>" +
                "<div class='titulo-seccion'>RESUMEN</div>" +
                "<p>Resumen: " + producto.getNombre() + "</p>" +
                "<p>" + producto.getDescripcion() + "</p>" +
                "<p>Porciones:" + producto.getPorciones() + "</p>" +
                "<img src="+producto.getImagen() +" alt=  >" +
                "</div>" +
                "<div class='seccion datos-cliente'>" +
                "<div class='titulo-seccion'>DATOS DEL CLIENTE</div>" +
                "<p>Nombre: " + cliente.getNombre() + "</p>" +
                "<p>Número de Cédula: " + cliente.getCedula() + "</p>" +
                //                "<img src= uniquindio/proyectosoftware/image/imagen-prodcuto1.jpg "+"alt=  >" +
                "</div>" +
                "</body>" +
                "</html>";

//        String contenido = "El cliente con cedula: "+cliente.getCedula()+" y "+cliente.getNombre()+"compro el producto "+producto.getNombre()+" y esta es la direccin donde le llegara" +
//                ": "+direccion;

        HiloCorreo hiloCorreo = new HiloCorreo(cliente.getUsuario().getNombreUsuario(), contenido);
        hiloCorreo.start();

    }

    public void agregarContraseniaTemp(String correo, String contraseniaTemp) {

        pasteleria.getListaClientes().stream().filter(x -> x.getUsuario().getNombreUsuario().equals(correo)).findFirst().get().getUsuario().setContraseniaTemp(contraseniaTemp);
    }

    public List<Producto> obtenerListaDeProductos() {
        return pasteleria.getListaProductos();
    }

    public int obtenerCantUsuarios() {
        return pasteleria.getListaClientes().size()+pasteleria.getListaEmpleados().size();
    }

    public ArrayList<Producto> obtenerListaDeProductosByBuscador(String txtBuscar) {
        return pasteleria.getListaProductos().stream()
                .filter(x -> x.getNombre().contains(txtBuscar))
                .collect(Collectors.toCollection(ArrayList::new));
    }


}
