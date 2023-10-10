package uniquindio.proyectosoftware.modelo;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

public class Usuario implements Serializable {
    @Serial
    private static final long serialVersionUID = 1l;
    private String nombreUsuario;
    private String contrasenia;
    private Boolean estadoLogin ;
    private String contraseniaTemp;

    public Usuario() {
    }

    public Usuario(String nombreUsuario, String contrasenia) {
        this.nombreUsuario = nombreUsuario;
        this.contrasenia = contrasenia;
        this.estadoLogin = false;

    }

    public String getContraseniaTemp() {
        return contraseniaTemp;
    }

    public void setContraseniaTemp(String contraseniaTemp) {
        this.contraseniaTemp = contraseniaTemp;
    }

    public Boolean getEstadoLogin() {
        return estadoLogin;
    }

    public void setEstadoLogin(Boolean estadoLogin) {
        this.estadoLogin = estadoLogin;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }
}
