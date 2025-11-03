package utils;

public class ResultadoOperacion {
    private final boolean valido;
    private final String mensaje;

    private ResultadoOperacion(boolean valido, String mensaje) {
        this.valido = valido;
        this.mensaje = mensaje;
    }

    public static ResultadoOperacion ok(String mensaje) {
        return new ResultadoOperacion(true, mensaje);
    }

    public static ResultadoOperacion ok() {
        return new ResultadoOperacion(true, "Operación realizada con éxito.");
    }

    public static ResultadoOperacion error(String mensaje) {
        return new ResultadoOperacion(false, mensaje);
    }

    public boolean esValido() {
        return valido;
    }

    public String getMensaje() {
        return mensaje;
    }

    @Override
     public String toString() {
        return (valido ? "Exito: " : "Error: ") + mensaje;
    }
}
