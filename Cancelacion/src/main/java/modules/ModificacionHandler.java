/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modules;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import core.ITurnoRepository;
import core.Turno;
import utils.ResultadoOperacion;
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

    public ResultadoOperacion ejecutar(int id, LocalDate nuevaFecha, LocalTime nuevaHora) {
        Turno existente = repo.buscarPorId(id);
        if (existente == null) return ResultadoOperacion.error("No se encontró el turno especificado.");

        Turno modificado = new Turno(id, existente.getPaciente(), nuevaFecha, nuevaHora);

        List<Turno> turnosMismoDia = repo.obtenerTodos().stream()
            .filter(t -> t.getId() != id)
            .toList();

        ResultadoOperacion resultado = ValidadorTurno.validarTurno(modificado, turnosMismoDia);
        if (!resultado.esValido()) {
            return resultado;
        }

        repo.eliminar(id);
        repo.agregar(modificado);
        return ResultadoOperacion.ok("Turno modificado exitosamente.");
    }
}