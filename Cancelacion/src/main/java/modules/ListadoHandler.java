/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modules;

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
            return List.of();
        }

        List<Turno> turnos = repo.listarPorFecha(fecha);

        if (turnos.isEmpty()) {
            return List.of();
        }

        return turnos.stream()
                .sorted(Comparator.comparing(Turno::getHora))
                .collect(Collectors.toList());
    }
}
