package usta.oop.dashboard;

import javafx.application.Application;
import usta.oop.dashboard.data.DataSource;
import usta.oop.dashboard.data.inmemory.InMemoryDataSource;
import usta.oop.dashboard.ui.console.ConsoleUI;
import usta.oop.dashboard.ui.desktop.DesktopUI;

public class Main {
    public static void main(String[] args) {
        // Esta es la clase principal de la aplicacion y es donde llamamos a DesktopUI para que inicie el dashboard
        Application.launch(DesktopUI.class, args);
    }
}
