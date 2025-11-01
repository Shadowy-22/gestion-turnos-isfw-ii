package modules.reserva;

import core.*;
import utils.ValidadorTurno;

import java.time.*;
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
    public boolean ejecutar(Turno turno){
        if(!validarTurno(turno)){
            return false;
        }

        // Si pasa las validaciones, se guarda el turno en el repositorio
        repo.agregar(turno);
        return true;
    }

    /**
     * Valida las reglas de negocio del consultorio:
     * - No se aceptan fechas pasadas.
     * - Solo se permiten días hábiles (lunes a viernes).
     * - El horario debe estar entre las 08:00 y 19:00 hs.
     * - No puede haber otro turno en el mismo horario.
     */
    private boolean validarTurno(Turno turno){
        // Verificamos que no haya datos vacios
        if (turno == null || turno.getFecha() == null || turno.getHora() == null)
            return false;

        LocalDate fecha = turno.getFecha();
        LocalTime hora = turno.getHora();

        // Validaciones básicas de fecha/hora
        if (!ValidadorTurno.fechaFutura(fecha)) return false;
        if (!ValidadorTurno.diaHabil(fecha)) return false;
        if (!ValidadorTurno.horarioValido(hora)) return false;

        // Obtenemos los turnos del mismo dia para ver que no se solapen
        List<Turno> turnosMismoDia = repo.obtenerTodos()
                                 .stream()
                                 .filter(t -> t.getFecha().equals(turno.getFecha()))
                                 .toList();

        // Evitar superposición de turnos
        return !ValidadorTurno.haySuperposicion(turno, turnosMismoDia);
    }
}
