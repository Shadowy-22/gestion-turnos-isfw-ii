package modules.cancelacion;

import core.*;

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
     * @return Turno cancelado si la operación fue exitosa, null si no se encontró
     */
    public boolean ejecutar(int idTurno) {      
        // Verificar si el turno existe en el repositorio
        if (repo.buscarPorId(idTurno) == null) {
            return false;
        }

         // Eliminar y devolver resultado
        return repo.eliminar(idTurno);
    }
}