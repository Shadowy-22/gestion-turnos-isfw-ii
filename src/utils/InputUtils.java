package utils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class InputUtils {
    
    private static final DateTimeFormatter FORMAT_FECHA = DateTimeFormatter.ISO_LOCAL_DATE;
    private static final DateTimeFormatter FORMAT_HORA = DateTimeFormatter.ofPattern("HH:mm");

    private static final Scanner scanner = new Scanner(System.in, "UTF-8");

    /**
     * Método auxiliar para mostrar un mensaje, leer una entrada de texto
     * y devolverla sin espacios sobrantes.
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


    // Metodo para leer la fecha en un formato especifico (AAAA-MM-DD)
    public static LocalDate leerFecha() {
        LocalDate fecha = null;
        do {
            try {
                System.out.print("Ingrese fecha (AAAA-MM-DD): ");
                String input = scanner.nextLine().trim();
                fecha = LocalDate.parse(input, FORMAT_FECHA);
            } catch (Exception e) {
                System.out.println("Formato de fecha inválido. Use AAAA-MM-DD (ej: 2025-11-1).");
            }
        } while(fecha == null);
        return fecha;
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
}
