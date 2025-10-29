package interfaz;

import core.*;
import gestion.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 * Clase responsable de la interacción directa con el usuario
 * a través de la consola.
 * 
 * Presenta las opciones del sistema y captura los datos necesarios
 * para operar con los módulos de negocio.
 */
public class MenuPrincipal {

    private final IGestionTurnos gestion;
    private final Scanner scanner = new Scanner(System.in, "UTF-8");

    public MenuPrincipal(IGestionTurnos gestion) {
        this.gestion = gestion;
    }

    /**
     * Muestra el menú principal y mantiene el bucle de ejecución
     * hasta que el usuario elige la opción de salida.
     */
    public void mostrarMenu() {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("""
                === Sistema de Gestión de Turnos ===
                1. Reservar turno
                0. Salir
                """);

            System.out.print("Seleccione una opción: ");
            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                opcion = -1;
            }

            switch (opcion) {
                case 1:
                    reservarTurno();
                    break;
                case 0:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        }
    }

    /**
     * Opción del menú encargada de registrar una nueva reserva.
     * Captura los datos del paciente y la fecha/hora del turno.
     */
    private void reservarTurno() {
        System.out.println("\n--- Reserva de Turno ---");

        String nombre = leerCampoTexto("Nombre completo del paciente");
        String dni = leerCampoNumerico("DNI");
        String tel = leerCampoNumerico("Telefono");
        String obra = leerCampoTexto("Obra social");

        // Crear objeto paciente
        Paciente p = new Paciente(nombre, dni, tel, obra);

        // Formatos esperados
        DateTimeFormatter fFecha = DateTimeFormatter.ISO_LOCAL_DATE;
        DateTimeFormatter fHora = DateTimeFormatter.ofPattern("HH:mm");
        LocalDate fecha;
        LocalTime hora;

        try {
            System.out.print("Ingrese fecha (AAAA-MM-DD): ");
            fecha = LocalDate.parse(scanner.nextLine().trim(), fFecha);

            System.out.print("Ingrese hora (HH:mm): ");
            hora = LocalTime.parse(scanner.nextLine().trim(), fHora);
        } catch (Exception e) {
            System.out.println("Formato de fecha u hora inválido.");
            return;
        }

        // Crea el turno con ID temporal (el repo asignará el real)
        Turno nuevo = new Turno(0, p, fecha, hora);
        
        // Llama a la capa de gestión (que a su vez delega al handler)
        System.out.println(); // línea separadora visual
        gestion.reservarTurno(nuevo);
        System.out.println("--------------------------------------------\n");
    }

    /**
     * Método auxiliar para mostrar un mensaje, leer una entrada de texto
     * y devolverla sin espacios sobrantes.
     */
    private String leerCampoTexto(String mensaje){
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
    private String leerCampoNumerico(String mensaje){
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
