



import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;



public class Agenda {

    private List<Turno> turnos;
    public Agenda() {
        turnos = new ArrayList<>();

        //Ejemplo de Turnos precargados (para probar... )
        turnos.add(new Turno("Carlos Pérez", LocalDate.of(2025, 10, 15), java.time.LocalTime.of(8, 45), "Activo"));
        turnos.add(new Turno("Alicia Guzmán", LocalDate.of(2025, 10, 15), java.time.LocalTime.of(10, 30), "Activo"));
        turnos.add(new Turno("Marta López", LocalDate.of(2025, 10, 16), java.time.LocalTime.of(9, 15), "Activo"));
    }

    public List<Turno> listarTurnosPorFecha(LocalDate fecha) {
        List<Turno> turnosFecha = new ArrayList<>();
        for (Turno t : turnos) {
            if (t.getFecha().equals(fecha)) {
                turnosFecha.add(t);
            }
        }
        // Ordenar los turnos por hora ascendente
        Collections.sort(turnosFecha, Comparator.comparing(Turno::getHora));
        return turnosFecha;
    }
}


