

package gestion;

import core.*;
import modules.ListadoHandler;
import modules.ModificacionHandler;
import modules.ReservaHandler;
import modules.CancelacionHandler;
import utils.ResultadoOperacion;
import utils.ValidadorPaciente;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class GestionTurnos implements IGestionTurnos {
    // Utilizamos la interfaz porque la implementacion se la pasamos por parametro en el constructor. 
    // Ésta va a contener todos los metodos a utilizar.
    private ITurnoRepository repo;
    private ReservaHandler reservaHandler;
    private ModificacionHandler modificacionHandler;
    private ListadoHandler listadoHandler;
    private CancelacionHandler cancelacionHandler; // ← agregado

    /**
    * Constructor que inicializa la gestión con un repositorio de turnos.
    * Además, instancia los handlers para manejar las distintas operaciones.
    */
    public GestionTurnos(ITurnoRepository repo) {
        this.repo = repo;
        this.reservaHandler = new ReservaHandler(repo);
        this.modificacionHandler = new ModificacionHandler(repo);
        this.listadoHandler = new ListadoHandler(repo);
        this.cancelacionHandler = new CancelacionHandler(repo); // ← agregado
    }

    /**
    * Gestiona la operación de reserva de turno. 
    * Delegará la validación y persistencia al handler
    * especializado en reservas.
    */
    @Override
    public ResultadoOperacion reservarTurno(Turno turno) {
        
        // Validación general del paciente antes de validar el turno
        Paciente paciente = turno.getPaciente();
        if (!ValidadorPaciente.pacienteValido(paciente)) {
            return ResultadoOperacion.error("Datos del paciente inválidos. Verifique nombre, DNI, teléfono u obra social.");
        }
        
        // Delegacion al Handler de Reserva y retorna una respuesta
        return reservaHandler.ejecutar(turno);

    }

    @Override
    public ResultadoOperacion modificarTurno(int id, LocalDate nuevaFecha, LocalTime nuevaHora) {
        return modificacionHandler.ejecutar(id, nuevaFecha, nuevaHora);
    }

    /**
    * Gestiona la cancelación (eliminación) de un turno existente.
    * Usa el handler especializado para esta operación.
    */
    @Override
    public ResultadoOperacion cancelarTurno(int id) {
        return cancelacionHandler.ejecutar(id);
    }

    @Override
    public List<Turno> listarPorFecha(LocalDate fecha) {
        return listadoHandler.ejecutar(fecha);
    }

    // Metodos helpers para algunas operaciones
    public List<Turno> obtenerTodosLosTurnos() {
        return repo.obtenerTodos();
    }

    public boolean existeDni(String dni) {
        return repo.obtenerTodos().stream()
               .anyMatch(t -> t.getPaciente().getDni().equals(dni));
    }
}
