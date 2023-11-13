package uniquindio.proyectosoftware.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import uniquindio.proyectosoftware.hilos.HiloCorreo;
import uniquindio.proyectosoftware.hilos.HiloTomarFoto;
import uniquindio.proyectosoftware.modelo.Cliente;
import uniquindio.proyectosoftware.modelo.Empleado;
import uniquindio.proyectosoftware.modelo.Usuario;

import java.io.IOException;

public class RegistroView {
    public TextField cedulaTextField;
    public PasswordField confirmarContrasenaPasswordField;
    public TextField telefonoTextField;
    public Button btnSalir;
    ModelFactoryController modelFactoryController = ModelFactoryController.getInstance();
    public ImageView imagenRegistry;
    @FXML
    private TextField nombreTextField;
    @FXML
    private TextField correoTextField;
    @FXML
    private PasswordField contrasenaPasswordField;

    String imagenUrl;
    public void registrarCuenta(ActionEvent actionEvent) {

        String cedula = cedulaTextField.getText();
        String nombre = nombreTextField.getText();
        String telefono = telefonoTextField.getText();
        String correo = correoTextField.getText();
        String contrasena = contrasenaPasswordField.getText();
        String contrasenaConfirm = confirmarContrasenaPasswordField.getText();

        if (cedula.isEmpty()|| nombre.isEmpty() || correo.isEmpty() || contrasena.isEmpty()) {
            mostrarAlerta("Todos los campos son obligatorios.");
            return;
        }

        if (!contrasenaCumpleRequisitos(contrasena)) {
            mostrarAlerta("La contraseña debe contener al menos una mayúscula, un número y un carácter especial.");
            return;
        }

        if(imagenUrl.isEmpty()){
            mostrarAlerta("Tome la foto para registrar");
            return;
        }
        if(!contrasena.equals(contrasenaConfirm)){
            mostrarAlerta("Las contraseñas no coinciden");
            return;
        }
        // Si llegamos aquí, todos los datos son válidos
        // Puedes realizar el registro de la cuenta aquí
        Cliente empleado = new Cliente(cedula, nombre, telefono, correo, new Usuario(correo, contrasena),imagenUrl, "Minimo", "10/03/2015");

        modelFactoryController.agregarEmpleado(empleado);
        limpiarCampos();
        String htmlBienvenida = "<html>\n" +
                "<head>\n" +
                "    <title>Bienvenido a la App</title>\n" +
                "    <style>\n" +
                "        body {\n" +
                "            font-family: 'Arial', sans-serif;\n" +
                "            background-color: #f4f4f4;\n" +
                "            color: #333;\n" +
                "            margin: 20px;\n" +
                "        }\n" +
                "        h1 {\n" +
                "            color: #007BFF;\n" +
                "        }\n" +
                "        p {\n" +
                "            line-height: 1.6;\n" +
                "        }\n" +
                "        ul {\n" +
                "            list-style-type: none;\n" +
                "            padding: 0;\n" +
                "        }\n" +
                "        li {\n" +
                "            margin-bottom: 10px;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div style='background-color: #fff; padding: 20px; border-radius: 10px; box-shadow: 0 0 10px rgba(0,0,0,0.1);'>\n" +
                "        <h1>Bienvenido, " + nombre + "!</h1>\n" +
                "        <p>Gracias por registrarte en nuestra aplicación.</p>\n" +
                "        <p>Aquí están los detalles de tu cuenta:</p>\n" +
                "        <ul>\n" +
                "            <li>Cédula: " + cedula + "</li>\n" +
                "            <li>Nombre: " + nombre + "</li>\n" +
                "            <li>Teléfono: " + telefono + "</li>\n" +
                "            <li>Correo Electrónico: " + correo + "</li>\n" +
                "        </ul>\n" +
                "        <p>¡Esperamos que disfrutes de nuestra aplicación!</p>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>";

        HiloCorreo hiloCorreo = new HiloCorreo(correo, htmlBienvenida);
        hiloCorreo.start();
        mostrarMensaje("Registro completado", "Registro completado", "Bienvenida a la pasteleria Tentacion", Alert.AlertType.INFORMATION);

    }
    private void mostrarMensaje(String titulo, String head, String content, Alert.AlertType tipo) {
        Alert alerta = new Alert(null);
        alerta.setTitle(titulo);
        alerta.setHeaderText(head);
        alerta.setContentText(content);
        alerta.setAlertType(tipo);
        alerta.show();
    }

    public void limpiarCampos(){
        cedulaTextField.setText("");
        nombreTextField.setText("");
        telefonoTextField.setText("");
        correoTextField.setText("");
        confirmarContrasenaPasswordField.setText("");
        contrasenaPasswordField.setText("");
        imagenRegistry.setImage(null);

    }


    public void tomarFoto(ActionEvent actionEvent) throws InterruptedException {

        int posicion = modelFactoryController.obtenerCantUsuarios();
        HiloTomarFoto hiloTomarFoto = new HiloTomarFoto("imagen"+posicion);
        hiloTomarFoto.start();
        hiloTomarFoto.join();

        Image image = new Image(hiloTomarFoto.obtenerUrl());
        imagenUrl = hiloTomarFoto.obtenerUrl();
        imagenRegistry.setImage(image);

    }
    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error de Registro");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    private boolean contrasenaCumpleRequisitos(String contrasena) {
        // Verifica si la contraseña cumple con los requisitos
        return contrasena.matches(".*[A-Z].*") && contrasena.matches(".*\\d.*") && contrasena.matches(".*[!@#$%^&*()].*");
    }
    public void salir(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/uniquindio/proyectosoftware/registroView.fxml"));
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
}
