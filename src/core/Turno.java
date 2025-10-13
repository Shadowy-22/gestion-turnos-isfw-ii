package core;

import java.time.LocalDate;
import java.time.LocalTime;

public class Turno {
    private int id;                
    private Paciente paciente;     
    private LocalDate fecha;       
    private LocalTime hora;        

    public Turno(int id, Paciente paciente, LocalDate fecha, LocalTime hora) {
        this.id = id;
        this.paciente = paciente;
        this.fecha = fecha;
        this.hora = hora;
    }

    // Getters y setters
    public int getId() { return id; }
    public Paciente getPaciente() { return paciente; }
    public LocalDate getFecha() { return fecha; }
    public LocalTime getHora() { return hora; }

    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public void setHora(LocalTime hora) { this.hora = hora; }

    // Para mejorar el formato del texto
    @Override
    public String toString() {
        return String.format(
            "Turno #%d - %s - %s a las %s hs",
            id,
            paciente.getNombreCompleto(),
            fecha,
            hora
        );
    }
}
