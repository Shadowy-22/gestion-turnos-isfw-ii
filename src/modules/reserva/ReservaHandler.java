package modules.reserva;

import core.*;
import java.time.*;

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
            System.err.println("El turno no es válido. Revise fecha, hora o disponibilidad");
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
        if (turno == null || turno.getFecha() == null || turno.getHora() == null)
            return false;

        LocalDate fecha = turno.getFecha();
        LocalTime hora = turno.getHora();

        // No permitir fechas pasadas
        if (fecha.isBefore(LocalDate.now()))
            return false;

        // Solo lunes a viernes
        DayOfWeek dia = fecha.getDayOfWeek();
        if (dia == DayOfWeek.SATURDAY || dia == DayOfWeek.SUNDAY)
            return false;

        // Horario permitido
        LocalTime inicio = LocalTime.of(8, 0);
        LocalTime fin = LocalTime.of(19, 0);
        if (hora.isBefore(inicio) || hora.isAfter(fin.minusMinutes(40)))
            return false;

        // Evitar superposición de turnos
        return repo.listarPorFecha(fecha).stream().noneMatch(t -> t.getHora().equals(hora));


    }
}
