package uniquindio.proyectosoftware.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.io.IOException;
import java.util.List;

import javafx.stage.Stage;
import javafx.stage.StageStyle;
import uniquindio.proyectosoftware.modelo.Producto;

public class ProductoView {
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

        btndetalleProducto.setOnAction(event -> verDetalleProducto(producto));

        // Agregar elementos al GridPane
        cuadroProducto.add(imagenProducto, 0, 0); // Columna 0, Fila 0
        cuadroProducto.add(productName, 1, 0);     // Columna 1, Fila 0
        cuadroProducto.add(productPrice, 2, 0);    // Columna 2, Fila 0
        cuadroProducto.add(btndetalleProducto, 3, 0); // Columna 3, Fila 0

        // Configurar la distribución de columnas en el GridPane
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(10); // Ajusta el ancho de la columna 1

        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(40); // Ajusta el ancho de la columna 2

        ColumnConstraints column3 = new ColumnConstraints();
        column3.setPercentWidth(20); // Ajusta el ancho de la columna 3

        ColumnConstraints column4 = new ColumnConstraints();
        column4.setPercentWidth(30); // Ajusta el ancho de la columna 4

        cuadroProducto.getColumnConstraints().addAll(column1, column2, column3, column4);

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


    private List<Producto> obtenerListaDeProductos() {
        // Aquí debes obtener la lista de productos de tu fuente de datos (por ejemplo, una base de datos o una lista en memoria)
        // En este ejemplo, creamos una lista de productos ficticia:
        return List.of(
                new Producto("1", "Producto 1", 19.99, "Descripción del producto 1", 5, "imagen1.jpg"),
                new Producto("2", "Producto 2", 29.99, "Descripción del producto 2", 8, "imagen2.jpg")
//                new Producto("3", "Producto 3", 39.99, "Descripción del producto 3", 10, "imagen3.jpg"),
 //               new Producto("3", "Producto 3", 39.99, "Descripción del producto 3", 10, "imagen3.jpg"),
 //               new Producto("3", "Producto 3", 39.99, "Descripción del producto 3", 10, "imagen3.jpg")
//                new Producto("3", "Producto 3", 39.99, "Descripción del producto 3", 10, "imagen3.jpg"),
//                new Producto("3", "Producto 3", 39.99, "Descripción del producto 3", 10, "imagen3.jpg"),
//                new Producto("3", "Producto 3", 39.99, "Descripción del producto 3", 10, "imagen3.jpg"),
//                new Producto("3", "Producto 3", 39.99, "Descripción del producto 3", 10, "imagen3.jpg"),
//                new Producto("3", "Producto 3", 39.99, "Descripción del producto 3", 10, "imagen3.jpg"),
//                new Producto("3", "Producto 3", 39.99, "Descripción del producto 3", 10, "imagen3.jpg"),
//                new Producto("3", "Producto 3", 39.99, "Descripción del producto 3", 10, "imagen3.jpg"),
//                new Producto("3", "Producto 3", 39.99, "Descripción del producto 3", 10, "imagen3.jpg")
        );
    }

    private void comprarProducto(Producto producto) {

        // Crear una nueva ventana emergente
        Stage ventanaEmergente = new Stage();
        ventanaEmergente.initStyle(StageStyle.UTILITY);
        ventanaEmergente.setTitle("Confirmar Compra");

        // Crear un VBox para organizar los elementos
        VBox vbox = new VBox(10); // Espacio de 10 píxeles entre los elementos
        vbox.setPadding(new javafx.geometry.Insets(10));

        // Crear etiquetas y campos de texto
        Label direccionLabel = new Label("Dirección de Envío:");
        TextField direccionTextField = new TextField();
        Label cedulaClietne = new Label("Ingrese la cedula del cliente:");
        TextField cedulaClienteTxt = new TextField();

        Label precioLabel = new Label("Precio del Producto: $" + producto.getPrecio());

        // Crear un botón de confirmación de compra
        Button confirmarButton = new Button("Confirmar Compra");
        confirmarButton.setOnAction(event -> {
            // Aquí puedes realizar la lógica de confirmación de compra
            String direccion = direccionTextField.getText();
            String cedulaCliente = cedulaClienteTxt.getText();
            modelFactoryController.mandarCorreoNotificacionCliente(cedulaCliente, direccion,producto);

            // Realiza las acciones necesarias, como enviar la dirección y el producto para el procesamiento
           // System.out.println("Compra confirmada: Producto=" + producto.getNombre() + ", Dirección=" + direccion);
            mostrarMensaje("Confirmacion compra", "Confirmar Compra","Compra confirmada: Producto=" + producto.getNombre() + ", Dirección=" + direccion, Alert.AlertType.INFORMATION );
            // Cierra la ventana emergente
            direccionTextField.setText("");
            cedulaClienteTxt.setText("");
        ventanaEmergente.close();
        });

        // Agregar elementos al VBox
        vbox.getChildren().addAll(direccionLabel, direccionTextField, precioLabel,cedulaClietne, cedulaClienteTxt, confirmarButton);

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


        //modelFactoryController.cambiarEstadoLoginEmpelado();

        //System.out.println(modelFactoryController.getMarketPlace().getAdmin().isLogin());

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
}
