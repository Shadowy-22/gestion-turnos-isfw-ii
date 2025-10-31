

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner (System.in);
        Agenda agenda = new Agenda();

        System.out.println("=== Consulta de Turnos por Fecha ===");

        try{
            System.out.println("Ingrese el día: ");
            int dia = sc.nextInt();
            System.out.println("Ingrese el mes: ");
            int mes = sc.nextInt();
            System.out.println("Ingrese el año: ");
            int anio = sc.nextInt();

            LocalDate fecha = LocalDate.of(anio, mes ,dia);
            List<Turno> turnos = agenda.listarTurnosPorFecha(fecha);

            if (turnos.isEmpty()) { 
                System.out.println("No hay turnos registrados para la fecha ingresada.");
                
            }   else {
                System.out.println( "\n Turnos del " + fecha + ":");
                for (Turno t : turnos){
                    System.out.println("-------");
                    System.out.println("--> " + t);
                    System.out.println("-------");

                }
            }
        } catch (Exception e) {
            System.out.println("Fecha inválida. Intente nuevamente con el formato correcto [DIA, MES, AÑO]");
        }

    }

}

    
        



