package uniquindio.proyectosoftware.modelo;

import java.io.Serial;
import java.io.Serializable;

public class Cliente extends Persona implements Serializable {
    @Serial
    private static final long serialVersionUID = 1l;
    private String id;
    private String fecahRegistro;

    public Cliente() {
    }

    public Cliente(String cedula, String nombre, String telefono, String email, Usuario usuario, String imageUrl, String id, String fecahRegistro) {
        super(cedula, nombre, telefono, email, usuario, imageUrl);
        this.id = id;
        this.fecahRegistro = fecahRegistro;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFecahRegistro() {
        return fecahRegistro;
    }

    public void setFecahRegistro(String fecahRegistro) {
        this.fecahRegistro = fecahRegistro;
    }
}
