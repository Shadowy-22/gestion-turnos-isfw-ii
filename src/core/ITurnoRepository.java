package core;

import java.time.LocalDate;
import java.util.List;

// Utilizamos el Patron Repository para no depender de implementaciones concretas
public interface ITurnoRepository {
    void agregar(Turno turno);
    boolean eliminar(int id);
    Turno buscarPorId(int id);
    List<Turno> listarPorFecha(LocalDate fecha);
    List<Turno> obtenerTodos();
}
