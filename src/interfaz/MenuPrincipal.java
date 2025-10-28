package interfaz;

import core.*;
import gestion.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class MenuPrincipal {

    private final IGestionTurnos gestion;
    private final Scanner scanner = new Scanner(System.in, "UTF-8");

    public MenuPrincipal(IGestionTurnos gestion) {
        this.gestion = gestion;
    }

    public void mostrarMenu() {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n=== Sistema de Gestión de Turnos ===");
            System.out.println("1. Reservar turno");
            System.out.println("0. Salir");
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
     * Solicita al usuario los datos necesarios para crear un nuevo turno
     * y delega la reserva a la capa de gestión.
     */
    private void reservarTurno() {
        System.out.println("\n--- Reserva de Turno ---");

        System.out.print("Nombre completo del paciente: ");
        String nombre = scanner.nextLine().trim();
        System.out.print("DNI: ");
        String dni = scanner.nextLine().trim();
        System.out.print("Teléfono: ");
        String tel = scanner.nextLine().trim();
        System.out.print("Obra social: ");
        String obra = scanner.nextLine().trim();

        Paciente p = new Paciente(nombre, dni, tel, obra);

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
            System.out.println("⚠️ Formato de fecha u hora inválido.");
            return;
        }

        // Crea el turno con ID temporal (el repo asignará el real)
        Turno nuevo = new Turno(0, p, fecha, hora);
        
        // Llama a la capa de gestión (que a su vez delega al handler)
        gestion.reservarTurno(nuevo);
    }
}
