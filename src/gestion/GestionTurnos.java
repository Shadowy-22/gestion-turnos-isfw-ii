    package gestion;

    import core.*;
    import modules.reserva.ReservaHandler;
    import java.time.LocalDate;
    import java.time.LocalTime;
    import java.util.List;
    import java.util.ArrayList;

    public class GestionTurnos implements IGestionTurnos {
        // Utilizamos la interfaz porque la implementacion se la pasamos por parametro en el constructor. 
        // Ésta va a contener todos los metodos a utilizar.
        private ITurnoRepository repo;
        private ReservaHandler reservahandler;

        /**
        * Constructor que inicializa la gestión con un repositorio de turnos.
        * Además, instancia el handler para manejar las reservas.
        */
        public GestionTurnos(ITurnoRepository repo) {
            this.repo = repo;
            this.reservahandler = new ReservaHandler(repo);
        }

        /**
        * Gestiona la operación de reserva de turno. 
        * Delegará la validación y persistencia al handler
        * especializado en reservas.
        */
        @Override
        public void reservarTurno(Turno turno) {
            boolean exito = reservahandler.ejecutar(turno);

            //el resultado se comunica a la capa de interfaz
            if (exito){
                System.out.println("""
                        Turno reservado con éxito:
                        Nombre: %s
                        Fecha: %s
                        Hora: %s hs
                        """.formatted(
                            turno.getPaciente().getNombreCompleto(),
                            turno.getFecha(),
                            turno.getHora()
                        ));
        } else {
            System.out.println("No fue posible reservar el turno. Revise los datos ingresados.");
            }
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
