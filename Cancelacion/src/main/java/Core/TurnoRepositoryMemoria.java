/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

// Implementacion concreta del Repositorio de Turnos, representa la operación en memoria.
// La parte de la logica de negocio del caso de uso la implementamos en los modulos.
public class TurnoRepositoryMemoria implements ITurnoRepository {
    private List<Turno> turnos = new ArrayList<>();
    private int nextId = 1;

    
    public void agregar(Turno turno) {
        turno = new Turno(nextId++, turno.getPaciente(), turno.getFecha(), turno.getHora());
        turnos.add(turno);
    }

    
    public boolean eliminar(int id) {
        return turnos.removeIf(t -> t.getId() == id);
    }

    
    public Turno buscarPorId(int id) {
        return turnos.stream()
                .filter(t -> t.getId() == id)
                .findFirst()
                .orElse(null);
    }

    
    public List<Turno> listarPorFecha(LocalDate fecha) {
        return turnos.stream()
                .filter(t -> t.getFecha().equals(fecha))
                .collect(Collectors.toList());
    }

    
    public List<Turno> obtenerTodos() {
        return new ArrayList<>(turnos);
    }
}
