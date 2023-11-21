package uniquindio.proyectosoftware.modelo;

public class Pqrs {
   private Cliente cliente;
   private String tipoMensaje;
   private String mensaje;

    public Pqrs(Cliente cliente, String tipoMensaje, String mensaje) {
        this.cliente = cliente;
        this.tipoMensaje = tipoMensaje;
        this.mensaje = mensaje;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getTipoMensaje() {
        return tipoMensaje;
    }

    public void setTipoMensaje(String tipoMensaje) {
        this.tipoMensaje = tipoMensaje;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
