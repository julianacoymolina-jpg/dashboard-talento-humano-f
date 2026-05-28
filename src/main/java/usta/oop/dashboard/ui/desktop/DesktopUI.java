package usta.oop.dashboard.ui.desktop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import usta.oop.dashboard.data.DataSource;
import usta.oop.dashboard.data.jdbi.JdbiDataSource;
import usta.oop.dashboard.ui.desktop.controller.DashboardController;

import java.io.IOException;

/*
 * La clase 'Application' es una clase abstracta que contiene
 * toda la funcionalidad necesaria para poner en marcha el motor
 * JavaFX. Esta se inicia al llamar `launch()`.
 * Posteriormente, `launch()` llamará a `start()`. Aquí colocaremos
 * todo el código personalizado de la aplicación.
 */
public class DesktopUI extends Application {
    private final DataSource dataSource;

    public DesktopUI() {
        // Cuando este lista la implementación en Jdbi aqui la cambias para poder usarla
        dataSource = new JdbiDataSource();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader();

        // Controlador para enviar datos a JavaFX
        DashboardController dashboardController = new DashboardController(dataSource);

        // Llamamos al archivo dashboard.fxml
        loader.setLocation(DesktopUI.class.getResource("dashboard.fxml"));
        loader.setController(dashboardController);

        /*
         * Scene es el componente que alberga el contenido de tu ventana.
         */
        Scene scene = new Scene(loader.load(), 1280, 720);
        
        /*
         * Stage es el elemento que corresponde a la ventana en
         * tu pantalla. Si quieres tener varias ventanas, generalmente
         * necesitarás varios Stage.
         */
        stage.setTitle("Dashboard - Gobernación");
        stage.setScene(scene);
        stage.show();
    }
}
