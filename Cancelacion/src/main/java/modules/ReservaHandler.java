/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modules;

import core.*;
import utils.ResultadoOperacion;
import utils.ValidadorTurno;

import java.util.List;

/**
 * Handler responsable de gestionar la lógica específica
 * del caso de uso "Reservar Turno".
 * 
 * Encapsula todas las validaciones y operaciones necesarias
 * para garantizar que el turno sea válido antes de ser
 * persistido por el repositorio.
 */

public class ReservaHandler {
    
    private ITurnoRepository repo;

    /**
     * Constructor del handler, recibe el repositorio
     * donde se almacenarán los turnos.
     */
    public ReservaHandler(ITurnoRepository repo){
        this.repo = repo;
    }

    /**
     * Ejecuta la reserva de un turno, validando las reglas de negocio.
     */
    public ResultadoOperacion ejecutar(Turno turno){
         List<Turno> turnosExistentes = repo.obtenerTodos();

        ResultadoOperacion resultado = ValidadorTurno.validarTurno(turno, turnosExistentes);
        if (!resultado.esValido()) {
            return resultado; // devuelve el error exacto
        }

        repo.agregar(turno);
        String msg = """
                Turno reservado con éxito:
                Nombre: %s
                Fecha: %s
                Hora: %s hs
                """.formatted(
                    turno.getPaciente().getNombreCompleto(),
                    turno.getFecha(),
                    turno.getHora()
                );
        return ResultadoOperacion.ok(msg);
    }
}