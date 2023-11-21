package uniquindio.proyectosoftware.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import uniquindio.proyectosoftware.modelo.DetallePedido;
import uniquindio.proyectosoftware.modelo.Pedido;
import uniquindio.proyectosoftware.modelo.Producto;

public class ProductoView {
    public TextField txtBuscarProducto;
    public VBox pedidosVBox;
    public VBox productosClienteVBoxVBox;
    public TextField txtBuscarProductoCliente1;
    ModelFactoryController modelFactoryController = ModelFactoryController.getInstance();


    @FXML
    private VBox productosVBox;

    public void initialize() {
        // Aquí puedes configurar tus productos usando una lista de productos
        List<Producto> listaProductos = modelFactoryController.obtenerListaDeProductos();
        List<Pedido> listaPedidos = modelFactoryController.obtenerListaPedidosCliente();
        List<Pedido> listaPedidosClientes = modelFactoryController.obtenerListaProductosClietes();


        for (Pedido p : listaPedidos) {
            GridPane productoHBox = crearCuadroDePedido(p);
            pedidosVBox.getChildren().add(productoHBox);
        }
        for (Producto producto : listaProductos) {
            GridPane productoHBox = crearCuadroDeProducto(producto);
            productosVBox.getChildren().add(productoHBox);
        }
        for (Pedido pedido : listaPedidosClientes) {

            for (DetallePedido detallePedido:pedido.getDetallePedido()
                 ) {
                GridPane productoHBox = crearCuadroDeProductosCliente(detallePedido.getProducto(), detallePedido.getCantidad());
                productosClienteVBoxVBox.getChildren().add(productoHBox);
            }

        }



    }

    private GridPane crearCuadroDeProductosCliente(Producto producto, int cantidadComprada) {
        // Crear el GridPane para los productos comprados por el cliente
        GridPane cuadroProductoCliente = new GridPane();
        cuadroProductoCliente.setPrefSize(800, 80);

        // Crear etiquetas para nombre, valor y cantidad del producto
        Label productName = new Label(producto.getNombre());
        Label productPrice = new Label("$" + producto.getPrecio());
        Label productQuantity = new Label("Cantidad: " + cantidadComprada);

        // Crear botones de "Cambio" y "Devoluciones"
        Button btnCambio = new Button("Cambio");
        Button btnDevoluciones = new Button("Devoluciones");

        // Configurar acciones de los botones
        btnCambio.setOnAction(event -> realizarCambio(producto));
        btnDevoluciones.setOnAction(event -> solicitarDevolucion(producto));

        // Agregar elementos al GridPane
        cuadroProductoCliente.add(productName, 0, 0);         // Columna 0, Fila 0
        cuadroProductoCliente.add(productPrice, 1, 0);        // Columna 1, Fila 0
        cuadroProductoCliente.add(productQuantity, 2, 0);     // Columna 2, Fila 0
        cuadroProductoCliente.add(btnCambio, 3, 0);           // Columna 3, Fila 0
        cuadroProductoCliente.add(btnDevoluciones, 4, 0);     // Columna 4, Fila 0

        // Configurar la distribución de columnas en el GridPane
        // Aquí puedes ajustar el ancho de las columnas según tus necesidades

        // Estilo para el GridPane
        cuadroProductoCliente.setStyle("-fx-border-color: #CCCCCC; -fx-border-width: 1px; -fx-padding: 10px;");

        return cuadroProductoCliente;
    }
    private void modificarCambiosCuadrosDevo(){
        productosClienteVBoxVBox.getChildren().clear();
        List<Pedido> listaPedidosClientes = modelFactoryController.obtenerListaProductosClietes();
        for (Pedido pedido : listaPedidosClientes) {

            for (DetallePedido detallePedido:pedido.getDetallePedido()
            ) {
                GridPane productoHBox = crearCuadroDeProductosCliente(detallePedido.getProducto(), detallePedido.getCantidad());
                productosClienteVBoxVBox.getChildren().add(productoHBox);
            }

        }
    }

    // Función para realizar el cambio de un producto
    private void realizarCambio(Producto producto) {
        // Aquí implementa la lógica para el cambio del producto
        // Puedes agregar el código necesario para manejar el cambio del producto comprado
        ArrayList<Producto> listaProdcutos = (ArrayList<Producto>) modelFactoryController.obtenerListaDeProductos();
        mostrarTodosLosProductos(listaProdcutos , producto);

       modificarCambiosCuadrosDevo();
    }

    private void mostrarTodosLosProductos(List<Producto> listaProductos, Producto productoCambio) {
        Stage ventanaProductos = new Stage();
        ventanaProductos.initModality(Modality.APPLICATION_MODAL);
        ventanaProductos.setTitle("Todos los Productos");

        VBox contenedorProductos = new VBox(10);
        contenedorProductos.setPadding(new Insets(10));

        // Iterar sobre la lista de productos para mostrar cada uno en un cuadro
        for (Producto producto : listaProductos) {
            GridPane cuadroProducto = crearCuadroDeProductoCambio(producto, productoCambio);
            contenedorProductos.getChildren().add(cuadroProducto);
        }

        ScrollPane scrollPane = new ScrollPane(contenedorProductos);
        scrollPane.setFitToWidth(true);

        Scene scene = new Scene(scrollPane, 850, 500);
        ventanaProductos.setScene(scene);
        ventanaProductos.showAndWait(); // Para esperar hasta que se cierre la ventana emergente
    }


    // Función para solicitar una devolución de un producto
    private void solicitarDevolucion(Producto producto) {
        modelFactoryController.crearDevolucionProducto(producto);
        mostrarMensaje("Confirmacion devolucion", "Confirmacion devolucion producto del pedido","Se ha quitado ese prodcuto de su pedido", Alert.AlertType.INFORMATION);

        modificarCambiosCuadrosDevo();
        // Aquí implementa la lógica para la solicitud de devolución del producto
        // Puedes agregar el código necesario para manejar la devolución del producto comprado
    }
    private GridPane crearCuadroDePedido(Pedido pedido) {
        // Crear el GridPane
        GridPane cuadroPedido = new GridPane();
        cuadroPedido.setPrefSize(800, 80);

        // Configurar acciones del pedido
        Button btnDetallesPedido = new Button("Ver detalles del pedido");
        btnDetallesPedido.setOnAction(event -> verDetallesPedido(pedido));

        // Configurar elementos del pedido
        Label labelNit = new Label("NIT: " + pedido.getNit());
        Label labelFecha = new Label("Fecha del Pedido: " + pedido.getFechaPedido());
        Label labelTotal = new Label("Total: $" + pedido.getTotal());
        Label labelSubTotal = new Label("Subtotal: $" + pedido.getSubTotal());
        Label labelEmpleado = new Label("Empleado: " + pedido.getEmpleado().getNombre());
        Label labelCliente = new Label("Cliente: " + pedido.getCliente().getNombre());
        Label labelTipoPago = new Label("Tipo de Pago: " + pedido.getTipoPago());
        Label labelDireccion = new Label("Dirección de entrega: " + pedido.getDireccion());

        // Agregar elementos al GridPane
        cuadroPedido.add(labelNit, 0, 0);
        cuadroPedido.add(labelFecha, 1, 0);
        cuadroPedido.add(labelTotal, 2, 0);
        cuadroPedido.add(labelSubTotal, 3, 0);
        cuadroPedido.add(labelEmpleado, 4, 0);
        cuadroPedido.add(labelCliente, 5, 0);
        cuadroPedido.add(labelTipoPago, 6, 0);
        cuadroPedido.add(labelDireccion, 7, 0);
        cuadroPedido.add(btnDetallesPedido, 8, 0);

        // Configurar la distribución de columnas en el GridPane
        // (Ajusta según tus necesidades)
        for (int i = 0; i < 9; i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(100.0 / 9);
            cuadroPedido.getColumnConstraints().add(column);
        }

        // Estilo para el GridPane
        cuadroPedido.setStyle("-fx-border-color: #CCCCCC; -fx-border-width: 1px; -fx-padding: 10px;");

        return cuadroPedido;
    }

    private void verDetallesPedido(Pedido pedido) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Detalles del Pedido");
        alert.setHeaderText(null);
        alert.initStyle(StageStyle.UTILITY);
        alert.initModality(Modality.APPLICATION_MODAL);

        // Crear un GridPane para mostrar los detalles
        GridPane detallesGrid = new GridPane();
        detallesGrid.setHgap(10);
        detallesGrid.setVgap(5);

        // Agregar encabezados
        detallesGrid.add(new Label("ID"), 0, 0);
        detallesGrid.add(new Label("Cantidad"), 1, 0);
        detallesGrid.add(new Label("Producto"), 2, 0);

        // Obtener detalles del pedido
        ArrayList<DetallePedido> detalles = pedido.getDetallePedido();

        // Agregar detalles al GridPane
        for (int i = 0; i < detalles.size(); i++) {
            DetallePedido detalle = detalles.get(i);
            detallesGrid.add(new Label(detalle.getId()), 0, i + 1);
            detallesGrid.add(new Label(Integer.toString(detalle.getCantidad())), 1, i + 1);
            detallesGrid.add(new Label(detalle.getProducto().getNombre()), 2, i + 1);
        }

        alert.getDialogPane().setContent(detallesGrid);
        alert.showAndWait();
    }

    private GridPane crearCuadroDeProducto(Producto producto) {
        // Crear el GridPane
        GridPane cuadroProducto = new GridPane();
        cuadroProducto.setPrefSize(800, 80);


        Image favoritosNoSeleccion = new Image("/uniquindio/proyectosoftware/image/favoritosNoSeleccion.jpg", 20, 20, false, false);
        Image favoritosSiSeleccion = new Image("/uniquindio/proyectosoftware/image/favoritosSiSeleccion.jpg", 20, 20, false, false);
        int cont=0;
        if(modelFactoryController.getListaProductosFavoritos().contains(producto)){
            cont = 1;
        }else{
            cont =0;
        }
        ImageView imageViewFavoritos = (cont==0)?  new ImageView(favoritosNoSeleccion): new ImageView(favoritosSiSeleccion);
        Button btnFavoritos = new Button();
        btnFavoritos.setGraphic(imageViewFavoritos);
        btnFavoritos.setPrefSize(15, 15);

        btnFavoritos.setOnAction(event -> {
            // Puedes agregar la lógica aquí para cambiar la imagen o agregar el producto a favoritos
            if (imageViewFavoritos.getImage().equals(favoritosNoSeleccion)) {
                if(modelFactoryController.agregarProductoFav(producto)){
                    imageViewFavoritos.setImage(favoritosSiSeleccion);
                }
                // Aquí puedes agregar lógica para marcar el producto como favorito
            } else {
                modelFactoryController.eliminarProductoFav(producto);
                imageViewFavoritos.setImage(favoritosNoSeleccion);
                // Aquí puedes agregar lógica para desmarcar el producto como favorito
            }
        });

        // Crear la imagen del producto
        Image image = new Image(producto.getImagen());
        ImageView imagenProducto = new ImageView(image);
        imagenProducto.setFitWidth(50);
        imagenProducto.setFitHeight(50);

        // Crear etiquetas y botones
        Label productName = new Label(producto.getNombre());
        Label productPrice = new Label("$" + producto.getPrecio());
        Button btndetalleProducto = new Button("Ver detalle de producto");
        Button btnAgregarProductoCarrito = new Button("Agregar al carrito");

        // Configurar acciones de los botones
        btnAgregarProductoCarrito.setOnAction(event -> modelFactoryController.agregarProductoCarrito(producto));
        btndetalleProducto.setOnAction(event -> verDetalleProducto(producto));

        // Agregar elementos al GridPane
        cuadroProducto.add(imagenProducto, 0, 0); // Columna 0, Fila 0
        cuadroProducto.add(productName, 1, 0);     // Columna 1, Fila 0
        cuadroProducto.add(productPrice, 2, 0);    // Columna 2, Fila 0
        cuadroProducto.add(btnAgregarProductoCarrito, 3, 0); // Columna 3, Fila 0
        cuadroProducto.add(btndetalleProducto, 4, 0); // Columna 4, Fila 0
        cuadroProducto.add(btnFavoritos, 5, 0);

        // Configurar la distribución de columnas en el GridPane
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(10); // Ajusta el ancho de la columna 1

        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(40); // Ajusta el ancho de la columna 2

        ColumnConstraints column3 = new ColumnConstraints();
        column3.setPercentWidth(20); // Ajusta el ancho de la columna 3

        ColumnConstraints column4 = new ColumnConstraints();
        column4.setPercentWidth(25); // Ajusta el ancho de la columna 4

        ColumnConstraints column5 = new ColumnConstraints();
        column5.setPercentWidth(30); // Ajusta el ancho de la columna 5
        ColumnConstraints column6 = new ColumnConstraints();
        column6.setPercentWidth(25); // Ajusta el ancho de la columna 5

        // Agregar las columnas al GridPane
        cuadroProducto.getColumnConstraints().addAll(column1, column2, column3, column4, column5, column6);

        // Estilo para el GridPane
        cuadroProducto.setStyle("-fx-border-color: #CCCCCC; -fx-border-width: 1px; -fx-padding: 10px;");

        return cuadroProducto;
    }

    private GridPane crearCuadroDeProductoCambio(Producto producto, Producto productoCambio) {
        // Crear el GridPane
        GridPane cuadroProducto = new GridPane();
        cuadroProducto.setPrefSize(800, 80);




        // Crear la imagen del producto
        Image image = new Image(producto.getImagen());
        ImageView imagenProducto = new ImageView(image);
        imagenProducto.setFitWidth(50);
        imagenProducto.setFitHeight(50);

        // Crear etiquetas y botones
        Label productName = new Label(producto.getNombre());
        Label productPrice = new Label("$" + producto.getPrecio());
        Button btndetalleProducto = new Button("Ver detalle de producto");
        Button btnAgregarProductoCarrito = new Button("Cambio prodcuto");

        // Configurar acciones de los botones
        btnAgregarProductoCarrito.setOnAction(event -> modelFactoryController.generarCambio(producto, productoCambio));
        btndetalleProducto.setOnAction(event -> verDetalleProductoCambio(producto));

        // Agregar elementos al GridPane
        cuadroProducto.add(imagenProducto, 0, 0); // Columna 0, Fila 0
        cuadroProducto.add(productName, 1, 0);     // Columna 1, Fila 0
        cuadroProducto.add(productPrice, 2, 0);    // Columna 2, Fila 0
        cuadroProducto.add(btnAgregarProductoCarrito, 3, 0); // Columna 3, Fila 0
        cuadroProducto.add(btndetalleProducto, 4, 0); // Columna 4, Fila 0
        //cuadroProducto.add(btnFavoritos, 5, 0);

        // Configurar la distribución de columnas en el GridPane
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(10); // Ajusta el ancho de la columna 1

        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(40); // Ajusta el ancho de la columna 2

        ColumnConstraints column3 = new ColumnConstraints();
        column3.setPercentWidth(20); // Ajusta el ancho de la columna 3

        ColumnConstraints column4 = new ColumnConstraints();
        column4.setPercentWidth(25); // Ajusta el ancho de la columna 4

        ColumnConstraints column5 = new ColumnConstraints();
        column5.setPercentWidth(30); // Ajusta el ancho de la columna 5
        //ColumnConstraints column6 = new ColumnConstraints();
        //column6.setPercentWidth(25); // Ajusta el ancho de la columna 5

        // Agregar las columnas al GridPane
        cuadroProducto.getColumnConstraints().addAll(column1, column2, column3, column4, column5);

        // Estilo para el GridPane
        cuadroProducto.setStyle("-fx-border-color: #CCCCCC; -fx-border-width: 1px; -fx-padding: 10px;");

        return cuadroProducto;
    }




    private void verDetalleProducto(Producto producto) {

        // Crear una nueva ventana emergente
        Stage ventanaEmergente = new Stage();
        ventanaEmergente.initStyle(StageStyle.UTILITY);
        ventanaEmergente.setTitle("Detalle del Producto");

        // Crear un VBox para organizar los atributos
        VBox vbox = new VBox(10); // Espacio de 10 píxeles entre los elementos
        vbox.setPadding(new javafx.geometry.Insets(10));

        // Crear etiquetas para mostrar los atributos
        Label idLabel = new Label("ID: " + producto.getId());
        Label nombreLabel = new Label("Nombre: " + producto.getNombre());
        Label precioLabel = new Label("Precio: $" + producto.getPrecio());
        Label descripcionLabel = new Label("Descripción: " + producto.getDescripcion());
        Label porcionesLabel = new Label("Porciones: " + producto.getPorciones());
        Button botonComprar = new Button("Comprar");
       botonComprar.setOnAction(event -> comprarProducto(producto));

        // Agregar las etiquetas al VBox
        vbox.getChildren().addAll(idLabel, nombreLabel, precioLabel, descripcionLabel, porcionesLabel, botonComprar);

        // Crear una ventana emergente de tipo Alert con contenido personalizado
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle("Detalle del Producto");
        alert.setHeaderText(null);
        alert.getDialogPane().setContent(vbox);

        // Mostrar la ventana emergente
        alert.showAndWait();
    }

    private void verDetalleProductoCambio(Producto producto) {

        // Crear una nueva ventana emergente
        Stage ventanaEmergente = new Stage();
        ventanaEmergente.initStyle(StageStyle.UTILITY);
        ventanaEmergente.setTitle("Detalle del Producto");

        // Crear un VBox para organizar los atributos
        VBox vbox = new VBox(10); // Espacio de 10 píxeles entre los elementos
        vbox.setPadding(new javafx.geometry.Insets(10));

        // Crear etiquetas para mostrar los atributos
        Label idLabel = new Label("ID: " + producto.getId());
        Label nombreLabel = new Label("Nombre: " + producto.getNombre());
        Label precioLabel = new Label("Precio: $" + producto.getPrecio());
        Label descripcionLabel = new Label("Descripción: " + producto.getDescripcion());
        Label porcionesLabel = new Label("Porciones: " + producto.getPorciones());
//        Button botonComprar = new Button("Comprar");
//        botonComprar.setOnAction(event -> comprarProducto(producto));

        // Agregar las etiquetas al VBox
        vbox.getChildren().addAll(idLabel, nombreLabel, precioLabel, descripcionLabel, porcionesLabel);

        // Crear una ventana emergente de tipo Alert con contenido personalizado
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle("Detalle del Producto");
        alert.setHeaderText(null);
        alert.getDialogPane().setContent(vbox);

        // Mostrar la ventana emergente
        alert.showAndWait();
    }



    private void comprarProducto(Producto producto) {

        // Crear una nueva ventana emergente
        Stage ventanaEmergente = new Stage();
        ventanaEmergente.initStyle(StageStyle.UTILITY);
        ventanaEmergente.setTitle("Confirmar Compra");

        // Crear un VBox para organizar los elementos
        VBox vbox = new VBox(10); // Espacio de 10 píxeles entre los elementos
        vbox.setPadding(new javafx.geometry.Insets(10));

        ComboBox<String> comboBoxTipoPago = new ComboBox<>();
        comboBoxTipoPago.getItems().addAll("Efectivo", "Tarjeta");
        comboBoxTipoPago.setPromptText("Seleccione Tipo de Pago");
        // Crear etiquetas y campos de texto
        Label direccionLabel = new Label("Dirección de Envío:");
        TextField direccionTextField = new TextField();
        Label cedulaClietne = new Label("Cedula del cliente:");
        TextField cedulaClienteTxt = new TextField();
        cedulaClienteTxt.setEditable(false);
        cedulaClienteTxt.setText(modelFactoryController.getClienteActual().getCedula());

        Label precioLabel = new Label("Precio del Producto: $" + producto.getPrecio());

        // Crear un botón de confirmación de compra
        Button confirmarButton = new Button("Confirmar Compra");
        confirmarButton.setOnAction(event -> {
            // Aquí puedes realizar la lógica de confirmación de compra
            String direccion = direccionTextField.getText();
            String cedulaCliente = cedulaClienteTxt.getText();
            modelFactoryController.mandarCorreoNotificacionCliente(cedulaCliente, direccion,producto, comboBoxTipoPago.getValue());

            // Realiza las acciones necesarias, como enviar la dirección y el producto para el procesamiento
           // System.out.println("Compra confirmada: Producto=" + producto.getNombre() + ", Dirección=" + direccion);
            mostrarMensaje("Confirmacion compra", "Confirmar Compra","Compra confirmada: Producto=" + producto.getNombre() + ", Dirección=" + direccion, Alert.AlertType.INFORMATION );
            // Cierra la ventana emergente
            direccionTextField.setText("");
            cedulaClienteTxt.setText("");
        ventanaEmergente.close();
        });

        // Agregar elementos al VBox
        vbox.getChildren().addAll(comboBoxTipoPago,direccionLabel, direccionTextField, precioLabel,cedulaClietne, cedulaClienteTxt, confirmarButton);

        // Crear una ventana emergente de tipo Alert con contenido personalizado
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle("Confirmar Compra");
        alert.setHeaderText(null);
        alert.getDialogPane().setContent(vbox);

        // Mostrar la ventana emergente
        ventanaEmergente.initOwner(alert.getOwner()); // Establece la ventana principal como propietaria para el enfoque correcto
        alert.showAndWait();
    }

    public void salir(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/uniquindio/proyectosoftware/productoView.fxml"));

        mostrarPestaniaPrincipal(event);
    }
    public void mostrarPestaniaPrincipal(ActionEvent event){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/uniquindio/proyectosoftware/loginView.fxml"));


        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }
    private void mostrarMensaje(String titulo, String head, String content, Alert.AlertType tipo) {
        Alert alerta = new Alert(null);
        alerta.setTitle(titulo);
        alerta.setHeaderText(head);
        alerta.setContentText(content);
        alerta.setAlertType(tipo);
        alerta.show();
    }

    public void buscarProductos(ActionEvent event) {
        String txtBuscar = txtBuscarProducto.getText();
        if(txtBuscar!= null){
            productosVBox.getChildren().clear();
            ArrayList<Producto> productos = modelFactoryController.obtenerListaDeProductosByBuscador(txtBuscar);
            for (Producto producto : productos) {
                GridPane productoHBox = crearCuadroDeProducto(producto);
                productosVBox.getChildren().add(productoHBox);
            }
        }else{
            mostrarMensaje("Datos incorrectos", "Datos incorrectos","Ingrese un nombre para filtrar", Alert.AlertType.INFORMATION );
            List<Producto> listaProductos = modelFactoryController.obtenerListaDeProductos();


            for (Producto producto : listaProductos) {
                GridPane productoHBox = crearCuadroDeProducto(producto);
                productosVBox.getChildren().add(productoHBox);
            }

        }

    }

    public void mostrarCarrito(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/uniquindio/proyectosoftware/carritoView.fxml"));


        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);

    }

    public void mostrarFav(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/uniquindio/proyectosoftware/favoritosView.fxml"));


        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    public void buscarPedido(ActionEvent event) {
    }

    public void mostrarPqrs(ActionEvent event) {
        Stage ventanaPqrs = new Stage();
        ventanaPqrs.initModality(Modality.APPLICATION_MODAL);
        ventanaPqrs.setTitle("Crear PQRS");

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Label mensajeLabel = new Label("Mensaje:");
        TextArea mensajeTextArea = new TextArea();
        mensajeTextArea.setPrefRowCount(4);
        mensajeTextArea.setPrefColumnCount(15);

        Label tipoLabel = new Label("Tipo de PQRS:");
        ComboBox<String> tipoComboBox = new ComboBox<>();
        tipoComboBox.getItems().addAll("Petición", "Queja", "Reclamo", "Sugerencia");

        Button guardarBtn = new Button("Guardar");
        guardarBtn.setOnAction(e -> {
            // Aquí puedes guardar la PQRS o realizar alguna acción con los datos ingresados
            String mensaje = mensajeTextArea.getText();
            String tipoPQRS = tipoComboBox.getValue();
            // Por ejemplo, imprimirlos en consola
//            System.out.println("Mensaje: " + mensaje);
//            System.out.println("Tipo de PQRS: " + tipoPQRS);
            modelFactoryController.crearPqrs(tipoPQRS, mensaje);
            mostrarMensaje("Confirmacion PQRS", "Confirmacion PQRS", "Su solicitud se ha mandado exitosamente", Alert.AlertType.INFORMATION);
            ventanaPqrs.close();
        });

        gridPane.add(mensajeLabel, 0, 0);
        gridPane.add(mensajeTextArea, 1, 0);
        gridPane.add(tipoLabel, 0, 1);
        gridPane.add(tipoComboBox, 1, 1);
        gridPane.add(guardarBtn, 1, 2);

        Scene scene = new Scene(gridPane, 400, 200);
        ventanaPqrs.setScene(scene);
        ventanaPqrs.showAndWait();
    }

    public void buscarProdcutosCliente(ActionEvent event) {
    }
}
