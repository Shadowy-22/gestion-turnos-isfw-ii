package modules.reserva;

import core.*;
import java.time.*;

/**
 * Clase encargada de manejar la lógica del caso de uso "Reservar Turno".
 * Aplica las validaciones de negocio (horarios, fechas válidas, superposición)
 * y utiliza el repositorio para guardar el turno si es válido.
 */

public class ReservaHandler {
    
    private ITurnoRepository repo;

    /**
     * El handler recibe el repositorio donde se almacenan los turnos.
     * No se comunica con la interfaz, solo maneja validaciones y persistencia.
     */
    public ReservaHandler(ITurnoRepository repo){
        this.repo = repo;
    }

    /**
     * Método principal que ejecuta la operación de reserva de turno.
     * Primero valida la información del turno y, si es correcta,
     * lo guarda en el repositorio.
     */
    public boolean ejecutar(Turno turno){
        if(!validarTurno(turno)){
            System.err.println("El turno no es válido. Revise fecha, hora o disponibilidad");
            return false;
        }

        repo.agregar(turno);

        // Salida formateada al usuario
        System.out.println("""
                Turno reservado con éxito:
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
