package uniquindio.proyectosoftware.modelo;

import java.io.Serial;
import java.io.Serializable;

public class Cargo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1l;
    private String id;
    private String nombre;

    public Cargo() {
    }

    public Cargo(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
