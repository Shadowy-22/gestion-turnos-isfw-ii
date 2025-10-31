

import java.time.LocalDate;
import java.time.LocalTime;



public class Turno {
    private String nombrePaciente;
    private LocalDate fecha;
    private LocalTime hora;
    private String estado;

    public Turno (String nombrePaciente, LocalDate fecha, LocalTime hora, String estado) {
        this.nombrePaciente = nombrePaciente;
        this.fecha = fecha;
        this.hora = hora;
        this.estado = estado;

    }

    public String getNombrePaciente() { return nombrePaciente; }
    public LocalDate getFecha() { return fecha;}
    public LocalTime getHora() {return hora;}
    public String getEstado () { return estado;}

    @Override
    public String toString() {
      
        return hora + " | " + nombrePaciente + " | Estado: " + estado;
    }   
}
