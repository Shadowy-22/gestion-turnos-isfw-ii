package interfaz;

import core.*;
import gestion.*;
import utils.InputUtils;

import java.time.LocalDate;
import java.time.LocalTime;
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

        String nombre = InputUtils.leerCampoTexto("Nombre completo del paciente");
        String dni = InputUtils.leerCampoNumerico("DNI");
        String tel = InputUtils.leerCampoNumerico("Telefono");
        String obra = InputUtils.leerCampoTexto("Obra social");

        // Crear objeto paciente
        Paciente nuevoPaciente = new Paciente(nombre, dni, tel, obra);

        // Usar los nuevos métodos utilitarios para fecha y hora
        LocalDate fecha = InputUtils.leerFecha();
        LocalTime hora = InputUtils.leerHora();

        // Validar que se hayan leído correctamente
        if (fecha == null || hora == null) {
            System.out.println("No se pudo crear el turno debido a errores en fecha/hora.");
            return;
        }

        // Crea el turno con ID temporal (el repo asignará el real)
        Turno nuevo = new Turno(0, nuevoPaciente, fecha, hora);
        
        // Llama a la capa de gestión (que a su vez delega al handler)
        System.out.println(); // línea separadora visual
        gestion.reservarTurno(nuevo);
        System.out.println("--------------------------------------------\n");
    }
}
