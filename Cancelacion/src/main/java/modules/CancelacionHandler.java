/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */



package modules;

import core.*;
import utils.ResultadoOperacion;

/**
 * Handler responsable de gestionar la lógica específica
 * del caso de uso "Cancelar Turno".
 *
 * Se encarga de validar la existencia del turno a cancelar
 * y de eliminarlo del repositorio si está registrado.
 */
public class CancelacionHandler {

    private ITurnoRepository repo;

    /**
     * Constructor del handler, recibe el repositorio
     * donde se almacenan los turnos.
     */
    public CancelacionHandler(ITurnoRepository repo) {
        this.repo = repo;
    }

    /**
     * Ejecuta la cancelación de un turno, validando primero
     * si existe en el repositorio.
     *
     * @param idTurno ID del turno que se desea cancelar
     * @return ResultadoOperacion indicando éxito o error.
     */
    public ResultadoOperacion ejecutar(int idTurno) {
        Turno turno = repo.buscarPorId(idTurno);

        if (turno == null) {
            return ResultadoOperacion.error("No se encontró un turno con el ID especificado.");
        }

        boolean eliminado = repo.eliminar(idTurno);

        if (eliminado) {
            return ResultadoOperacion.ok("Turno cancelado correctamente.");
        } else {
            return ResultadoOperacion.error("No se pudo eliminar el turno. Intente nuevamente.");
        }
    }
}
