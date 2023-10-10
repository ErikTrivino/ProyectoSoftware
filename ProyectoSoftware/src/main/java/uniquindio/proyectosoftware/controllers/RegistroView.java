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
import uniquindio.proyectosoftware.hilos.HiloTomarFoto;
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
        Empleado empleado = new Empleado(cedula, nombre, telefono, correo, new Usuario(correo, contrasena),imagenUrl, "Minimo", "10/03/2015");

        modelFactoryController.agregarEmpleado(empleado);
        limpiarCampos();

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
