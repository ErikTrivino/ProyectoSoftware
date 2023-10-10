package uniquindio.proyectosoftware.modelo;

import java.io.Serial;
import java.io.Serializable;

public class Empleado extends  Persona implements Serializable {
    @Serial
    private static final long serialVersionUID = 1l;
    private String salario;
    private String fechaContratacion;

    public Empleado() {
    }

    public Empleado(String cedula, String nombre, String telefono, String email, Usuario usuario, String imageUrl, String salario, String fechaContratacion) {
        super(cedula, nombre, telefono, email, usuario, imageUrl);
        this.salario = salario;
        this.fechaContratacion = fechaContratacion;
    }

    public String getSalario() {
        return salario;
    }

    public void setSalario(String salario) {
        this.salario = salario;
    }

    public String getFechaContratacion() {
        return fechaContratacion;
    }

    public void setFechaContratacion(String fechaContratacion) {
        this.fechaContratacion = fechaContratacion;
    }
}
