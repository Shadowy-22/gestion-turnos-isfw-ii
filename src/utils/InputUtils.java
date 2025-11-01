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

    public static LocalDate leerFecha() {
        try {
            System.out.print("Ingrese fecha (AAAA-MM-DD): ");
            return LocalDate.parse(scanner.nextLine().trim(), FORMAT_FECHA);
        } catch (Exception e) {
            System.out.println("Formato de fecha inválido. Intente nuevamente.");
            return null;
        }
    }

    public static LocalTime leerHora() {
        try {
            System.out.print("Ingrese hora (HH:mm): ");
            return LocalTime.parse(scanner.nextLine().trim(), FORMAT_HORA);
        } catch (Exception e) {
            System.out.println("Formato de hora inválido. Intente nuevamente.");
            return null;
        }
    }
}
