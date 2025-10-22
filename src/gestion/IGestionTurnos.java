package gestion;

import core.Turno;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface IGestionTurnos {
    void reservarTurno(Turno turno);
    boolean modificarTurno(int id, LocalDate nuevaFecha, LocalTime nuevaHora);
    boolean cancelarTurno(int id);
    List<Turno> listarPorFecha(LocalDate fecha);
}