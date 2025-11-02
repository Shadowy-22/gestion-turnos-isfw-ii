package modules.cancelacion;

import repository.ITurnoRepository;
import entities.Turno;

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
     * @param turno Turno que se desea cancelar
     * @return true si la cancelación fue exitosa, false si no se encontró
     */
    public boolean ejecutar(Turno turno) {
        // Verificar si el turno existe en el repositorio
        if (!repo.existe(turno)) {
            System.err.println("No se encontró el turno a cancelar. Verifique los datos ingresados.");
            return false;
        }

        // Si existe, se elimina del repositorio
        repo.eliminar(turno);

        // Mensaje de confirmación al usuario
        System.out.println("""
                Turno cancelado con éxito:
                Nombre: %s
                Fecha: %s
                Hora: %s hs
                """.formatted(
                    turno.getPaciente().getNombreCompleto(),
                    turno.getFecha(),
                    turno.getHora()
                ));

        return true;
    }
}

