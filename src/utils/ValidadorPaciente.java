package utils;

import core.Paciente;

public class ValidadorPaciente {
    
    // Debe contener como solo letras y un espacio
    public static boolean nombreValido(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return false;
        }
        
        // Regex para nombres en español
        String regex = "^[A-Za-zÁÉÍÓÚáéíóúñÑ]+(?:\\s+[A-Za-zÁÉÍÓÚáéíóúñÑ]+)+$";
        
        return nombre.matches(regex) && 
            nombre.length() >= 3 && 
            nombre.length() <= 50 &&
            nombre.trim().split("\\s+").length >= 2;
    }

    // Debe contener de 7 a 8 numeros
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
