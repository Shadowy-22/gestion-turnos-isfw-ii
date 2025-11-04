package utils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import core.Turno;
import gestion.IGestionTurnos;

public class InputUtils {
    
    private static final DateTimeFormatter FORMAT_FECHA = DateTimeFormatter.ISO_LOCAL_DATE;
    private static final DateTimeFormatter FORMAT_HORA = DateTimeFormatter.ofPattern("HH:mm");

    private static final Scanner scanner = new Scanner(System.in, "UTF-8");

    /* =========================================================
       SECCIÓN 1 — MÉTODOS ESPECÍFICOS (con validación de dominio)
       ========================================================= */
    
    public static int leerIdTurno(String mensaje, List<Turno> turnos) {
        int id = -1;
        Turno turnoSeleccionado = null;

        do {
            System.out.print(mensaje + "(0 para cancelar): ");
            String input = scanner.nextLine().trim();

            if (!input.matches("\\d+")) {
                System.out.println("Debe ingresar un número válido.");
                continue;
            }

            id = Integer.parseInt(input);

            if (id == 0) {
                // Salida rápida
                return 0;
            }

            // Buscar turno con ese ID
            turnoSeleccionado = null;
            for (Turno t : turnos) {
                if (t.getId() == id) {
                    turnoSeleccionado = t;
                    break;
                }
            }

            if (turnoSeleccionado == null) {
                System.out.println("No existe un turno con ese ID. Intente nuevamente.");
            }

        } while (turnoSeleccionado == null);

        return id;
    }


    public static String leerNombrePaciente() {
        String nombre;
        do {
            System.out.print("Nombre completo del paciente: ");
            nombre = scanner.nextLine().trim();
            if (!ValidadorPaciente.nombreValido(nombre)) {
                System.out.println("Nombre inválido. Debe contener nombre y apellido (ej: Juan Pérez), usar solo letras y tener entre 3-50 caracteres.");
                nombre = "";
            } else { 
                // Formatear a capitalización correcta
                nombre = formatearNombre(nombre);   
            }
        } while (nombre.isEmpty());
        return nombre;
    }

    /**
     * Metodo helper que formatea un nombre a capitalización correcta 
     * (primera letra de cada palabra en mayúscula)
    */
    public static String formatearNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return nombre;
        }
        
        String[] palabras = nombre.toLowerCase().split("\\s+");
        StringBuilder resultado = new StringBuilder();
        
        for (String palabra : palabras) {
            if (!palabra.isEmpty()) {
                resultado.append(Character.toUpperCase(palabra.charAt(0)))
                        .append(palabra.substring(1))
                        .append(" ");
            }
        }
        
        return resultado.toString().trim();
    }

    public static String leerDni(IGestionTurnos gestion) {
        String dni;
        do {
            System.out.print("DNI (solo números, puede incluir puntos): ");
            dni = scanner.nextLine().trim();
            dni = dni.replaceAll("[^0-9]", "");

            if (!ValidadorPaciente.dniValido(dni)) {
                System.out.println("DNI inválido. Debe tener entre 7 y 8 dígitos.");
                dni = "";
                continue;
            }

            if (gestion.existeDni(dni)) {
                System.out.println("Este DNI ya tiene un turno registrado.");
                dni = "";
            }

        } while (dni.isEmpty());
        return dni;
    }


    public static String leerTelefono() {
        String tel;
        do {
            System.out.print("Teléfono: ");
            tel = scanner.nextLine().trim();
            if (!ValidadorPaciente.telefonoValido(tel)) {
                System.out.println("Teléfono inválido. Ingrese solo números (6 a 15 dígitos).");
                tel = "";
            }
        } while (tel.isEmpty());
        return tel;
    }

    public static String leerObraSocial() {
        String obra;
        do {
            System.out.print("Obra social: ");
            obra = scanner.nextLine().trim();
            if (!ValidadorPaciente.obraSocialValida(obra)) {
                System.out.println("Obra social inválida. Debe tener entre 3 y 30 caracteres.");
                obra = "";
            } else {
                // Convertir a mayúsculas después de validar
                obra = obra.toUpperCase();
            }
        } while (obra.isEmpty());
        return obra;
    }

/* =========================================================
         SECCIÓN 2 — FECHA Y HORA
       ========================================================= */
    
    // Metodo que lee la fecha en distintos formatos y los almacena en formato ISO
    public static LocalDate leerFecha() {
        LocalDate fecha = null;
        DateTimeFormatter[] formatos = {
            FORMAT_FECHA,                // AAAA-MM-DD
            DateTimeFormatter.ofPattern("dd-MM-yyyy"),       // DD-MM-AAAA
            DateTimeFormatter.ofPattern("dd/MM/yyyy"),       // DD/MM/AAAA
            DateTimeFormatter.ofPattern("yyyy/MM/dd")        // AAAA/MM/DD
        };

        do {
            System.out.print("Ingrese fecha (acepta AAAA-MM-DD, DD-MM-AAAA, etc.): ");
            String input = scanner.nextLine().trim();

            for (DateTimeFormatter formato : formatos) {
                try {
                    fecha = LocalDate.parse(input, formato);
                    break; // si logra parsear, corta el bucle
                } catch (Exception ignored) {}
            }

            if (fecha == null) {
                System.out.println("Formato inválido. Ejemplo válido: 2025-11-01 o 01/11/2025");
            }
        } while (fecha == null);

        return fecha; // siempre formato ISO (AAAA-MM-DD)
    }


    // Metodo para leer hora en un formato especifico (HH:mm)
    public static LocalTime leerHora() {
        LocalTime hora = null;
        do {
            try {
                System.out.print("Ingrese hora (HH:mm): ");
                String input = scanner.nextLine().trim();
                hora = LocalTime.parse(input, FORMAT_HORA);
            } catch (Exception e) {
                System.out.println("Formato de hora inválido. Use HH:mm (ej: 18:30)");
            }
        } while (hora == null);
        return hora;
    }

  /* =========================================================
       SECCIÓN 3 — MÉTODOS GENÉRICOS (sin validación de dominio)
     ========================================================= */
    
    /** 
     * Método que lee un campo de texto validando que no esté vacio
    */
    public static String leerCampoTexto(String mensaje){
        String valor;
        do { 
            System.out.print(mensaje + ": ");
            valor = scanner.nextLine().trim();
            if (valor.isEmpty()){
                System.out.println("Este campo no puede estar vacío");
            }
        } while (valor.isEmpty());
        return valor;
    }

    /**
     * Método auxiliar para leer un campo numérico.
     * Valida que el usuario ingrese solo dígitos.
     */
    public static String leerCampoNumerico(String mensaje){
        String valor;
        do {
            System.out.print(mensaje + ": ");
            valor = scanner.nextLine().trim();
            if (!valor.matches("\\d+")){ // expresión regular: solo números
                System.out.println("Ingrese solo números (sin puntos ni letras).");
                valor = "";
            }
        } while (valor.isEmpty());
        return valor;
    }


    
}
