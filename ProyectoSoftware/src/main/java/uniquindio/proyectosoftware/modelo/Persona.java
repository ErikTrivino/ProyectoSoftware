package uniquindio.proyectosoftware.modelo;

import java.io.Serial;
import java.io.Serializable;
import java.lang.ref.PhantomReference;

public class Persona implements Serializable {
    @Serial
    private static final long serialVersionUID = 1l;
    private String cedula;
    private String nombre;
    private  String telefono;
    private String email;
    private Usuario usuario;
    private String imageUrl;

    public Persona() {
    }

    public Persona(String cedula, String nombre, String telefono, String email, Usuario usuario, String imageUrl) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
        this.usuario = usuario;
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
