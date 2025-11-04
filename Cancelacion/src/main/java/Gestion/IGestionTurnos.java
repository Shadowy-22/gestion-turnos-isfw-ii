/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestion;

import core.Turno;
import utils.ResultadoOperacion;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface IGestionTurnos {
    ResultadoOperacion reservarTurno(Turno turno);
    ResultadoOperacion modificarTurno(int id, LocalDate nuevaFecha, LocalTime nuevaHora);
    ResultadoOperacion cancelarTurno(int id);
    List<Turno> listarPorFecha(LocalDate fecha);
    List<Turno> obtenerTodosLosTurnos();
    boolean existeDni(String dni);  
}