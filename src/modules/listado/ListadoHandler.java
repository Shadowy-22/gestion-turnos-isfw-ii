package modules.listado;

import core.ITurnoRepository;
import core.Turno;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


// Handler responsable de listar los turnos por fecha, ordenados por hora.
 
public class ListadoHandler {

    private final ITurnoRepository repo;

    public ListadoHandler(ITurnoRepository repo) {
        this.repo = repo;
    }

    
    // Devuelve los turnos correspondientes a una fecha, ordenados por hora.
     
    public List<Turno> ejecutar(LocalDate fecha) {
        if (fecha == null) {
            System.out.println(" Fecha inválida.");
            return List.of();
        }

        List<Turno> turnos = repo.listarPorFecha(fecha);

        if (turnos.isEmpty()) {
            System.out.println("No hay turnos registrados para la fecha seleccionada.");
            return List.of();
        }

        return turnos.stream()
                .sorted(Comparator.comparing(Turno::getHora))
                .collect(Collectors.toList());
    }
}

