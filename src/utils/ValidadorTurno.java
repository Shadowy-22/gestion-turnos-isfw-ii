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


    public static boolean turnoValido(LocalDate fecha, LocalTime hora) {
        return fechaFutura(fecha) && diaHabil(fecha) && horarioValido(hora);
    }
}

