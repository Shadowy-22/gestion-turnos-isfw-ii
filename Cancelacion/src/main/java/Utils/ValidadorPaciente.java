/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import core.Paciente;

public class ValidadorPaciente {
    
    public static boolean nombreValido(String nombre) {
        return nombre != null && nombre.matches("^[A-Za-zÁÉÍÓÚáéíóúñÑ ]{3,50}$");
    }

    public static boolean dniValido(String dni) {
        return dni != null && dni.matches("\\d{7,8}");
    }

    public static boolean telefonoValido(String telefono) {
        return telefono != null && telefono.matches("\\d{6,15}");
    }

    public static boolean obraSocialValida(String obra) {
        if (obra == null || obra.trim().isEmpty()) return false;
    
        // Validar formato que tenga solo mayusculas y entre 3 y 30 caracteres.
        return obra.matches("^[A-Za-zÁÉÍÓÚáéíóúñÑ\\s()-]{3,30}$");
    }

    public static boolean pacienteValido(Paciente paciente) {
        return nombreValido(paciente.getNombreCompleto()) &&
               dniValido(paciente.getDni()) &&
               telefonoValido(paciente.getTelefono()) &&
               obraSocialValida(paciente.getObraSocial());
    }
}
