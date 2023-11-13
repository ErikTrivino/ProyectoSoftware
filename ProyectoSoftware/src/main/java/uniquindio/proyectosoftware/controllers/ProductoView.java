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

import javafx.stage.Stage;
import javafx.stage.StageStyle;
import uniquindio.proyectosoftware.modelo.Producto;

public class ProductoView {
    public TextField txtBuscarProducto;
    ModelFactoryController modelFactoryController = ModelFactoryController.getInstance();


    @FXML
    private VBox productosVBox;

    public void initialize() {
        // Aquí puedes configurar tus productos usando una lista de productos
        List<Producto> listaProductos = modelFactoryController.obtenerListaDeProductos();


        for (Producto producto : listaProductos) {
            GridPane productoHBox = crearCuadroDeProducto(producto);
            productosVBox.getChildren().add(productoHBox);
        }


    }
    private GridPane crearCuadroDeProducto(Producto producto) {
        GridPane cuadroProducto = new GridPane();
        cuadroProducto.setPrefSize(300, 60);

        Image image = new Image(producto.getImagen());
        ImageView imagenProducto = new ImageView(image);
        imagenProducto.setFitWidth(50);
        imagenProducto.setFitHeight(50);

        Label productName = new Label(producto.getNombre());
        Label productPrice = new Label("$" + producto.getPrecio());
        Button btndetalleProducto = new Button("Ver detalle de producto");
        Button btnAgregarProductoCarrito = new Button("Agregar producto carrito");

        btnAgregarProductoCarrito.setOnAction(event -> modelFactoryController.agregarProductoCarrito(producto));
        btndetalleProducto.setOnAction(event -> verDetalleProducto(producto));

        // Agregar elementos al GridPane
        cuadroProducto.add(imagenProducto, 0, 0); // Columna 0, Fila 0
        cuadroProducto.add(productName, 1, 0);     // Columna 1, Fila 0
        cuadroProducto.add(productPrice, 2, 0);    // Columna 2, Fila 0
        cuadroProducto.add(btnAgregarProductoCarrito, 3, 0); // Columna 3, Fila 0
        cuadroProducto.add(btndetalleProducto, 4, 0); // Columna 3, Fila 0

        // Configurar la distribución de columnas en el GridPane
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(10); // Ajusta el ancho de la columna 1

        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(40); // Ajusta el ancho de la columna 2

        ColumnConstraints column3 = new ColumnConstraints();
        column3.setPercentWidth(20); // Ajusta el ancho de la columna 3

        ColumnConstraints column4 = new ColumnConstraints();
        column4.setPercentWidth(15); // Ajusta el ancho de la columna 4
        ColumnConstraints column5 = new ColumnConstraints();
        column4.setPercentWidth(15); // Ajusta el ancho de la columna 4

        cuadroProducto.getColumnConstraints().addAll(column1, column2, column3, column4, column5);

        // Estilo para el GridPane
        cuadroProducto.setStyle("-fx-border-color: #CCCCCC; -fx-border-width: 1px; -fx-padding: 10px;");
        //cuadroProducto.setOnMouseClicked();

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
}
