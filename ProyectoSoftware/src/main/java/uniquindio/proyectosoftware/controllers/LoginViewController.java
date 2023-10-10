package uniquindio.proyectosoftware.controllers;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import uniquindio.proyectosoftware.hilos.HiloCorreo;
import uniquindio.proyectosoftware.exceptions.AccesoNoAutorizadoException;

import java.io.IOException;
import java.security.SecureRandom;


public class LoginViewController {
    ModelFactoryController modelFactoryController = ModelFactoryController.getInstance();

    public void setModelFactoryController(ModelFactoryController modelFactoryController) {
        this.modelFactoryController = modelFactoryController;
    }

    @FXML
    private TextField txtUsuario_loginView;
    @FXML
    private TextField txtContraseña_loginView;

    @FXML
    private Button btnCancelar;

    @FXML
    void btnCancelar_loginView(ActionEvent event) {
        salir();
    }

    public void salir() {

        Stage stage = (Stage) this.btnCancelar.getScene().getWindow();
        stage.close();
    }

    public void mostrarPestaniaPrincipal(ActionEvent event){
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
    }
    @FXML
    void btnIngresar_loginView(ActionEvent actionEvent) throws Exception {
        String usuario=txtUsuario_loginView.getText();
        String contrasenia=txtContraseña_loginView.getText();


        if (!(usuario.equals("") && contrasenia.equals(""))) {

            modelFactoryController.verificarLoginEmpleado(usuario, contrasenia);


            if (modelFactoryController.isLoginEmpleado()) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/uniquindio/proyectosoftware/loginView.fxml"));


                //modelFactoryController.cambiarEstadoLoginEmpelado();

                //System.out.println(modelFactoryController.getMarketPlace().getAdmin().isLogin());

                mostrarPestaniaPrincipal(actionEvent);

            }else{
                mostrarMensaje("Datos de acceso incorreectos", null, "Asegurese de introducir  los datos correctos ",
                        Alert.AlertType.ERROR);
                try {
                    throw new AccesoNoAutorizadoException("El acceso no fue autorizado");
                }catch (AccesoNoAutorizadoException e){
                    e.printStackTrace();
                    modelFactoryController.registrarAccionesSistema(e.toString(), 3, "Ingreso Login");
                }
            }
        }
        else {
            mostrarMensaje("Datos de acceso incompletos", null, "Asegurese de introducir todos los datos ",
                    Alert.AlertType.ERROR);
            try {
                throw new AccesoNoAutorizadoException("El acceso no fue autorizado");
            }catch (AccesoNoAutorizadoException e){
                e.printStackTrace();
                modelFactoryController.registrarAccionesSistema(e.toString(), 3, "Ingreso Login");
            }

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


    public void mostrarVentanaRecuperarContrasenia(MouseEvent mouseEvent) {
        mostrarVentanaEmergente();
    }



    public void mostrarVentanaResgitro(MouseEvent mouseEvent) {
        mostrarPestaniaRegistor(mouseEvent);

    }
    private void mostrarVentanaEmergente() {
        Stage ventana = new Stage();
        ventana.setTitle("Ventana de recuperacion contraseña");

        Label label = new Label("Ingrese un correo valido:");
        TextField textField = new TextField();
        Button imprimirBtn = new Button("Mandar codigo");
        imprimirBtn.setStyle("-fx-background-color: #3b5998; -fx-text-fill: white; -fx-font-weight: bold;");


        imprimirBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String texto = textField.getText();
                if (!texto.isEmpty()) {
                    System.out.println("Texto ingresado: " + texto);
                    String contraseniaTemp = generarContraseña(7);

                    modelFactoryController.agregarContraseniaTemp(texto, contraseniaTemp);
                    HiloCorreo hiloCorreo = new HiloCorreo(texto, "Esta es su contraseña temporal:", contraseniaTemp);
                    hiloCorreo.start();
                } else {
                    mostrarMensaje("Datos vacios", "Datos vacios", "La casilla de texto está vacía.", Alert.AlertType.ERROR);
                    System.out.println("La casilla de texto está vacía.");
                }
            }
        });

        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(label, textField);
        vbox.setAlignment(Pos.CENTER);

        HBox hbox = new HBox(10);
        hbox.getChildren().addAll(imprimirBtn);
        hbox.setAlignment(javafx.geometry.Pos.CENTER); // Centra horizontalmente el botón

        VBox contenedor = new VBox(20);
        contenedor.getChildren().addAll(vbox, hbox);
        contenedor.setAlignment(javafx.geometry.Pos.CENTER); // Centra todo verticalmente

        Scene scene = new Scene(contenedor, 300, 200);
        ventana.setScene(scene);
        ventana.initModality(Modality.APPLICATION_MODAL);
        ventana.showAndWait();
    }


    public static String generarContraseña(int longitud) {
         final String CARACTERES = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_=+[]{}|;:'\",.<>?";
        SecureRandom random = new SecureRandom();
        StringBuilder contraseñaGenerada = new StringBuilder();

        for (int i = 0; i < longitud; i++) {
            int indiceCaracter = random.nextInt(CARACTERES.length());
            char caracter = CARACTERES.charAt(indiceCaracter);
            contraseñaGenerada.append(caracter);
        }

        return contraseñaGenerada.toString();
    }
    private void mostrarPestaniaRegistor(MouseEvent event){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/uniquindio/proyectosoftware/registroView.fxml"));

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
