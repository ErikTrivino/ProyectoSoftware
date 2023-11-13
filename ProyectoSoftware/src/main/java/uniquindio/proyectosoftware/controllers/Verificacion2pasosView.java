package uniquindio.proyectosoftware.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.io.IOException;

public class Verificacion2pasosView {
    public PasswordField txtContraseña_loginView;
    ModelFactoryController modelFactoryController = ModelFactoryController.getInstance();

    public void validarAcceso(ActionEvent event) {
        boolean verificacion = modelFactoryController.verificarAccesoLogin(txtContraseña_loginView.getText());
        if(verificacion){
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/uniquindio/proyectosoftware/productoView.fxml"));


            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
        }else{
            mostrarMensaje("Clave verificacion equivocada", "Verificacion incorrecta", "Por favor verifique su clave.", Alert.AlertType.INFORMATION);
        }
    }
    private void mostrarMensaje(String titulo, String head, String content, Alert.AlertType tipo) {
        Alert alerta = new Alert(null);
        alerta.setTitle(titulo);
        alerta.setHeaderText(head);
        alerta.setContentText(content);
        alerta.setAlertType(tipo);
        alerta.show();
    }

    public void salir(ActionEvent event) {
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
