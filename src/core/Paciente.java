package core;

public class Paciente {
    private final String nombreCompleto;
    private final String dni;
    private final String telefono;
    private final String obraSocial;

    public Paciente(String nombreCompleto, String dni, String telefono, String obraSocial) {
        this.nombreCompleto = nombreCompleto;
        this.dni = dni;
        this.telefono = telefono;
        this.obraSocial = obraSocial;
    }

    // Getters y setters
    public String getNombreCompleto() { return nombreCompleto; }
    public String getDni() { return dni; }
    public String getTelefono() { return telefono; }
    public String getObraSocial() { return obraSocial; }
}
