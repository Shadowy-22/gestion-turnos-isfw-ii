    package gestion;

    import core.*;
    import java.time.LocalDate;
    import java.time.LocalTime;
    import java.util.List;
    import java.util.ArrayList;

    public class GestionTurnos implements IGestionTurnos {
        // Utilizamos la interfaz porque la implementacion se la pasamos por parametro en el constructor. 
        // Ésta va a contener todos los metodos a utilizar.
        private ITurnoRepository repo; 

        public GestionTurnos(ITurnoRepository repo) {
            this.repo = repo;
        }

        @Override
        public void reservarTurno(Turno turno) {

            // Validar el turno (Implementar validarTurno)
            /*if (validarTurno(turno)){
                System.out.println("El turno no es válido. Revise fecha, hora o disponibilidad.");
                return;
            }*/


            // Agregar el turno
            repo.agregar(turno);
            System.out.println("Turno reservado con éxito para: \n"
            + "Nombre: " + turno.getPaciente().getNombreCompleto() + "\n"
            + "Fecha: " + turno.getFecha() + "\n"
            + "Hora" + turno.getHora() + "hs.");
        }

        @Override
        public boolean modificarTurno(int id, LocalDate nuevaFecha, LocalTime nuevaHora) {
            return false; // Implementar 
        }

        @Override
        public boolean cancelarTurno(int id) {
            return false; // Implementar 
        }

        @Override
        public List<Turno> listarPorFecha(LocalDate fecha) {
            return new ArrayList<>(); // Implementar 
        }
    }
