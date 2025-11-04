/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import core.Turno;

public class ValidadorTurno {

    private static final LocalTime HORA_INICIO = LocalTime.of(8, 0);
    private static final LocalTime HORA_FIN = LocalTime.of(19, 0);
    private static final int DURACION_MINUTOS = 40;

    public static boolean fechaFutura(LocalDate fecha) {
        return fecha != null && !fecha.isBefore(LocalDate.now());
    }

    public static boolean diaHabil(LocalDate fecha) {
        DayOfWeek dia = fecha.getDayOfWeek();
        return dia != DayOfWeek.SATURDAY && dia != DayOfWeek.SUNDAY;
    }
    
    public static boolean horarioValido(LocalTime hora) {
        return hora != null &&
               !hora.isBefore(HORA_INICIO) &&
               !hora.isAfter(HORA_FIN.minusMinutes(DURACION_MINUTOS));
    }

    
    public static boolean horarioNoPasado(LocalDate fecha, LocalTime hora) {
        if (fecha == null || hora == null) return false;
        
        LocalDate hoy = LocalDate.now();
        
        // Si la fecha es hoy, verificar que la hora no sea anterior a la actual
        if (fecha.equals(hoy)) {
            return !hora.isBefore(LocalTime.now());
        }
        
        // Si la fecha es futura, siempre es válido
        return fecha.isAfter(hoy);
    }

    public static boolean haySuperposicion(Turno nuevo, List<Turno> existentes) {
        LocalDate fechaNuevo = nuevo.getFecha();
        LocalTime inicioNuevo = nuevo.getHora();
        LocalTime finNuevo = inicioNuevo.plusMinutes(DURACION_MINUTOS);

        for (Turno turno : existentes) {
            // Solo comparar turnos del mismo día
            if (!turno.getFecha().equals(fechaNuevo)) continue;

            LocalTime inicioExistente = turno.getHora();
            LocalTime finExistente = inicioExistente.plusMinutes(DURACION_MINUTOS);

            // Verificamos si se solapan los turnos y devolvemos true 
            boolean seSolapan = !(finNuevo.isBefore(inicioExistente) || inicioNuevo.isAfter(finExistente));
            if (seSolapan) {
                return true;
            }
        }
        return false;
    }

    // Validacion que retorna resultado booleano
    public static boolean turnoValido(LocalDate fecha, LocalTime hora) {
        return fechaFutura(fecha) && diaHabil(fecha) && horarioValido(hora) && horarioNoPasado(fecha, hora);
    }

    // Validacion que retorna un resultado del tipo ResultadoValidacion
    public static ResultadoOperacion validarTurno(Turno turno, List<Turno> existentes) {
        if (turno == null) return ResultadoOperacion.error("El turno es nulo.");
        if (turno.getFecha() == null) return ResultadoOperacion.error("La fecha del turno no puede ser nula.");
        if (turno.getHora() == null) return ResultadoOperacion.error("La hora del turno no puede ser nula.");

        LocalDate fecha = turno.getFecha();
        LocalTime hora = turno.getHora();

        if (!fechaFutura(fecha)) return ResultadoOperacion.error("La fecha debe ser futura.");
        if (!horarioNoPasado(turno.getFecha(), turno.getHora())) return ResultadoOperacion.error("No se puede reservar un turno en horarios ya pasados.");
        if (!diaHabil(fecha)) return ResultadoOperacion.error("El turno debe ser en día hábil (lunes a viernes).");
        if (!horarioValido(hora)) 
        return ResultadoOperacion.error("El turno debe comenzar entre " + HORA_INICIO + " y " 
                + HORA_FIN.minusMinutes(DURACION_MINUTOS) + " para poder durar " + DURACION_MINUTOS + " minutos antes de que cierre el horario de atención.");

        if (haySuperposicion(turno, existentes))
            return ResultadoOperacion.error("Ya existe un turno en ese rango horario.");

        return ResultadoOperacion.ok();
    }

}
