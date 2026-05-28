package usta.oop.dashboard.ui.desktop.controller;

import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.chart.TilesFXSeries;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableView;
import usta.oop.dashboard.data.DataSource;
import usta.oop.dashboard.model.AdministrativeSituation;
import usta.oop.dashboard.model.Employee;
import usta.oop.dashboard.ui.desktop.entity.AdminSituationTableRow;

import java.util.List;

/*
 * Esta clase es el "cerebro" del dashboard, tiene como atributo una implementacion de dataSource, la cual le asignamos
 * en la clase principal de JavaFX llamada "DesktopUI", ahi le podemos acoplar la implementacion de JdbiDataSource o
 * la que les deje en memoria llamada InMemoryDataSource.
 * Utilizando este dataSource, llama sus respectivos metodos para enviar la informacion a la pantalla del dashboard.
 */
public class DashboardController {
    private final DataSource dataSource;

    public DashboardController(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @FXML
    private Tile employeesInVacationsIndicator;

    @FXML
    private Tile employeesInPermitIndicator;

    @FXML
    private Tile employeesWithPendingVacationsIndicator;

    @FXML
    private Tile inactiveEmployeesChart;

    @FXML
    private TableView<AdminSituationTableRow> adminSituationTable;

    @FXML
    public void initialize() {
        // Convertimos la informacion obtenida a String pq el dashboard exige puro String, nada mas.
        String employeeCountInVacations = Integer.toString(dataSource.getEmployeeCountInVacations());
        String employeeCountInPermit = Integer.toString(dataSource.getEmployeeCountInPermit());
        String employeeCountWithPendingVacations = Integer.toString(dataSource.getEmployeeCountWithPendingVacations());
        List<Integer> lastYearInactiveEmployeeCount = dataSource.getLastYearInactiveEmployeeCountInMonths();

        employeesInVacationsIndicator.setDescription(employeeCountInVacations);
        employeesInPermitIndicator.setDescription(employeeCountInPermit);
        employeesWithPendingVacationsIndicator.setDescription(employeeCountWithPendingVacations);

        fillInactiveEmployeesChart(lastYearInactiveEmployeeCount);
        fillAdminSituationTable();
    }

    // Este metodo se encarga de interpretar la informacion y enviarla a la grafica del dashboard.
    private void fillInactiveEmployeesChart(List<Integer> lastYearInactiveEmployeeCount) {
        XYChart.Series<String, Number> inactiveEmployeeCountSeries = new XYChart.Series<>();

        inactiveEmployeeCountSeries.setName("Servidores públicos inactivos en los ultimos 12 meses");

        for (int monthIndex = 0; monthIndex < 12; monthIndex++) {
            inactiveEmployeeCountSeries.getData().add(new XYChart.Data<>(Integer.toString(monthIndex), lastYearInactiveEmployeeCount.get(monthIndex)));
        }

        TilesFXSeries<String, Number> tilesFXSeries = new TilesFXSeries<>(inactiveEmployeeCountSeries);

        inactiveEmployeesChart.setTilesFXSeries(tilesFXSeries);
    }

    // Este metodo llena los datos del dataSource a la tabla del dashboard
    private void fillAdminSituationTable() {
        List<AdministrativeSituation> administrativeSituations = dataSource.findAllAdministrativeSituations();
        List<Employee> employees = dataSource.findAllEmployees();

        ObservableList<AdminSituationTableRow> rows = FXCollections.observableArrayList();

        administrativeSituations.forEach(administrativeSituation -> {
            String employeeName = employees.stream()
                    .filter(employee -> employee.getId() == administrativeSituation.getEmployeeId())
                    .findFirst()
                    .get()
                    .getName();

            rows.add(new AdminSituationTableRow(employeeName, administrativeSituation));
        });

        adminSituationTable.setItems(rows);
    }
}
