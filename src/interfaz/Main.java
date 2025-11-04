package interfaz;

import core.*;
import gestion.*;

public class Main {
    public static void main(String[] args) {
        ITurnoRepository repo = new TurnoRepositoryMemoria();
        IGestionTurnos gestion = new GestionTurnos(repo);
        System.out.println("Sistema de Gestión de Turnos.");
        MenuPrincipal menu = new MenuPrincipal(gestion);
        menu.mostrarMenu();
    }
}
