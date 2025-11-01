package modules.modificacion;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import core.ITurnoRepository;
import core.Turno;
import utils.ValidadorTurno;

/**
 * Handler responsable de gestionar la lógica de negocio
 * del caso de uso "Modificar Turno".
 */
public class ModificacionHandler {
    private ITurnoRepository repo;

    public ModificacionHandler(ITurnoRepository repo){
        this.repo = repo;
    }

    public boolean ejecutar(int id, LocalDate nuevaFecha, LocalTime nuevaHora) {
        // Obtenes el turno existente
        Turno turnoExistente  = repo.buscarPorId(id);
        if (turnoExistente  == null) return false;

        // Probamos si el nuevo turno es valido segun las reglas de negocio
        if (!validarTurno(turnoExistente, nuevaFecha, nuevaHora)) {
            return false;
        }
        
        // Crear un nuevo turno con los datos actualizados
        Turno actualizado = new Turno(turnoExistente .getId(), turnoExistente.getPaciente(), nuevaFecha, nuevaHora);

        // Reemplazar el viejo turno por el nuevo actualizado al repositorio
        repo.eliminar(id);
        repo.agregar(actualizado);

        return true;
    }

    private boolean validarTurno(Turno turnoExistente, LocalDate nuevaFecha, LocalTime nuevaHora) {
        // Validaciones básicas de fecha/hora
        if (!ValidadorTurno.fechaFutura(nuevaFecha)) return false;
        if (!ValidadorTurno.diaHabil(nuevaFecha)) return false;
        if (!ValidadorTurno.horarioValido(nuevaHora)) return false;

        // Verificar superposición excluyendo el turno existente para evitar falsos positivos
        List<Turno> turnosMismoDia = repo.listarPorFecha(nuevaFecha)
                                         .stream()
                                         .filter(t -> t.getId() != turnoExistente.getId())
                                         .toList();

        Turno turnoPropuesto = new Turno(turnoExistente.getId(), turnoExistente.getPaciente(), nuevaFecha, nuevaHora);
        return !ValidadorTurno.haySuperposicion(turnoPropuesto, turnosMismoDia);
    }
}
