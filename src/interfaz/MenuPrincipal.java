package interfaz;

import core.*;
import gestion.*;
import utils.InputUtils;
import utils.ResultadoOperacion;
import utils.ValidadorTurno;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
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
                2. Modificar turno
                3. Eliminar turno
                4. Listar turnos por fecha
                5. Listar todos los turnos
                10. Generar datos de prueba
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
                case 2:
                    modificarTurno();
                    break;
                case 3:
                    cancelarTurno();
                    break;
                case 4: 
                    listarTurnosPorFecha();  
                    break;
                case 5:
                    listarTodosLosTurnos();
                    break;
                case 10:
                    generarDatosPrueba();
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

        String nombre = InputUtils.leerNombrePaciente();
        String dni = InputUtils.leerDni(gestion);
        String tel = InputUtils.leerTelefono();
        String obra = InputUtils.leerObraSocial();

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
        System.out.println(); 
        ResultadoOperacion resultado = gestion.reservarTurno(nuevo);
        System.out.println(resultado);
        System.out.println("--------------------------------------------\n");
    }

    /**
     * Opcion del menu que permite modificar un turno existente seleccionándolo por ID
     * y actualizando su fecha y hora.
    */
    private void modificarTurno(){
        System.out.println("\n--- Modificación de Turno ---");
        // Obtenemos la lista de turnos y verificamos que no este vacia
        List<Turno> turnos = gestion.obtenerTodosLosTurnos();

        if (turnos.isEmpty()) {
            System.out.println("No hay turnos registrados.");
            return;
        }

        // Mostrar los turnos (con ID, paciente, fecha, hora)
        System.out.println("Turnos disponibles:");
        for (Turno turno : turnos) {
            Paciente p = turno.getPaciente();
            System.out.printf("[%d] %s | DNI: %s | Obra Social: %s | Fecha: %s | Hora: %s\n",
                    turno.getId(),
                    p.getNombreCompleto(),
                    p.getDni(),
                    p.getObraSocial(),
                    turno.getFecha(),
                    turno.getHora()
            );
        }   

        int id = InputUtils.leerIdTurno("Ingrese el ID del turno ", turnos);

        if (id == 0) {
            System.out.println("Modificación cancelada.");
            return;
        }

        // Pedimos nueva fecha y hora
        LocalDate nuevaFecha = InputUtils.leerFecha();
        LocalTime nuevaHora = InputUtils.leerHora();

        // Llamamos a gestion y damos una respuesta
        ResultadoOperacion resultado = gestion.modificarTurno(id, nuevaFecha, nuevaHora);
        
        System.out.println(resultado);
        System.out.println("--------------------------------------------\n");
    }

    /**
     * Opcion del menu que permite cancelar (eliminar) 
     * un turno existente seleccionándolo por ID.
     */

    private void cancelarTurno() {
        System.out.println("\n--- Cancelación de Turno ---");

        List<Turno> turnos = gestion.obtenerTodosLosTurnos();

        if (turnos.isEmpty()) {
            System.out.println("No hay turnos registrados.");
            return;
        }

        System.out.println("Turnos disponibles:");

        for (Turno turno : turnos) {
            Paciente p = turno.getPaciente();

            System.out.printf("[%d] %s | DNI: %s | Fecha: %s | Hora: %s\n",
                    turno.getId(),
                    p.getNombreCompleto(),
                    p.getDni(),
                    turno.getFecha(),
                    turno.getHora()
            );
        }

        int id = InputUtils.leerIdTurno("Ingrese el ID del turno a cancelar ", turnos);

        if (id == 0) {
            System.out.println("Operación cancelada por el usuario.");
            return;
        }

        ResultadoOperacion resultado = gestion.cancelarTurno(id);

        System.out.println(resultado);

        System.out.println("--------------------------------------------\n");
    }


    /**
     * Opción del menú encargada de mostrar los turnos filtrados por la fecha que el usuario introduce
     */
    private void listarTurnosPorFecha() {
        System.out.println("\n --- Listado de Turnos por Fecha ---");

        LocalDate fecha = utils.InputUtils.leerFecha();

        if (!ValidadorTurno.diaHabil(fecha)) {
            System.out.println("Advertencia: " + fecha.getDayOfWeek() + " no es día hábil");
            return;
        }

        List<Turno> turnos = gestion.listarPorFecha(fecha);
        
        if(turnos.isEmpty()) { 
            System.out.println("No hay turnos registrados para la fecha seleccionada.");
        } else {
            System.out.println("Turnos del "+ fecha + ":");
            
            for (Turno turno : turnos) {
                Paciente paciente = turno.getPaciente();
                System.out.printf("- %s | %s | DNI: %s\n",
                    turno.getHora(),
                    paciente.getNombreCompleto(),
                    paciente.getDni()
                );
            }
        }
        System.out.println("--------------------");
    }

    /**
     * Opción del menú que muestra todos los turnos registrados
     * incluyendo datos completos del paciente y del turno.
    */
    private void listarTodosLosTurnos() {
        System.out.println("\n--- Listado Completo de Turnos ---");

        List<Turno> turnos = gestion.obtenerTodosLosTurnos();

        if (turnos.isEmpty()) {
            System.out.println("No hay turnos registrados.");
        } else {
            for (Turno t : turnos) {
                Paciente p = t.getPaciente();
                System.out.printf("ID: %d | Fecha: %s | Hora: %s | Paciente: %s | DNI: %s | Tel: %s | Obra Social: %s\n",
                    t.getId(),
                    t.getFecha(),
                    t.getHora(),
                    p.getNombreCompleto(),
                    p.getDni(),
                    p.getTelefono(),
                    p.getObraSocial()
                );
            }
        }

        System.out.println("--------------------------------------------\n");
    }


    /**
     * Opcion del menu que permite generar datos de prueba basado en el dia actual + 1
    */
    private void generarDatosPrueba(){
        System.out.println("\n--- Generando Datos de Prueba ---");
        
        // Lista de pacientes de prueba
        Paciente[] pacientesPrueba = {
            new Paciente("María González", "30123456", "1156789012", "OSDE"),
            new Paciente("Carlos López", "32987654", "1165432109", "SWISS MEDICAL"),
            new Paciente("Ana Martínez", "35234567", "1145678901", "GALENO"),
            new Paciente("Juan Pérez", "37876543", "1132109876", "MEDIFÉ"),
            new Paciente("Laura Rodríguez", "39456789", "1121098765", "OSEP"),
            new Paciente("Pedro Sánchez", "40654321", "1178901234", "OSDE"),
            new Paciente("Sofía Fernández", "41234567", "1189012345", "SWISS MEDICAL"),
            new Paciente("Diego García", "42345678", "1190123456", "GALENO"),
            new Paciente("Elena Costa", "43456789", "1101234567", "MEDIFÉ"),
            new Paciente("Miguel Ángel Ruiz", "44567890", "1112345678", "OSEP"),
            new Paciente("Lucía Morales", "45678901", "1123456789", "OSDE"),
            new Paciente("Javier Romero", "46789012", "1134567890", "SWISS MEDICAL"),
            new Paciente("Carmen Vargas", "47890123", "1145678901", "GALENO"),
            new Paciente("Roberto Silva", "48901234", "1156789012", "MEDIFÉ"),
            new Paciente("Isabel Torres", "49012345", "1167890123", "OSEP")
        };
        
        // Horas de prueba con turnos de 40 minutos (08:00 - 18:20)
        LocalTime[] horas = {
            LocalTime.of(8, 0),   // Ej. 08:00
            LocalTime.of(8, 40),  
            LocalTime.of(9, 20),  
            LocalTime.of(10, 0),  
            LocalTime.of(10, 40), 
            LocalTime.of(11, 20), 
            LocalTime.of(12, 0),  
            LocalTime.of(14, 0),  
            LocalTime.of(14, 40),
            LocalTime.of(15, 20), 
            LocalTime.of(16, 0),  
            LocalTime.of(16, 40), 
            LocalTime.of(17, 20), 
            LocalTime.of(18, 0),  
            LocalTime.of(18, 20)  
        };

    // Buscar los próximos 3 días hábiles
    LocalDate fechaBase = LocalDate.now().plusDays(1);
    List<LocalDate> diasHabiles = new ArrayList<>();
    
    // Encontrar 3 días hábiles consecutivos (excluyendo sábados y domingos)
    LocalDate fechaBusqueda = fechaBase;
    while (diasHabiles.size() < 3) {
        if (ValidadorTurno.diaHabil(fechaBusqueda)) {
            diasHabiles.add(fechaBusqueda);
        }
        fechaBusqueda = fechaBusqueda.plusDays(1);
    }
    
    System.out.println("Generando turnos para los días: " + diasHabiles);
        
        for (int i = 0; i < pacientesPrueba.length; i++) {
            try {
                // Turnos repartidos en 3 días
                LocalDate fecha = diasHabiles.get(i % diasHabiles.size()); 
                LocalTime hora = horas[i % horas.length];
                
                Turno turno = new Turno(0, pacientesPrueba[i], fecha, hora);
                ResultadoOperacion resultado = gestion.reservarTurno(turno);

                System.out.println(resultado);
                System.out.println("--------------------------------------------");

            } catch (Exception e) {
                System.out.println("Error al crear turno para: " + pacientesPrueba[i].getNombreCompleto());
            }
        }
        
        System.out.println("\n Se generaron turnos de prueba.");
        System.out.println("--------------------------------------------\n");
    }
}
