package usta.oop.dashboard.ui.desktop.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import usta.oop.dashboard.data.jdbi.JdbiDataSource;
import usta.oop.dashboard.model.Employee;

import java.util.Date;
import java.util.List;

public class EmployeesCrudController {

    // --- Cajas de texto del formulario ---
    @FXML private TextField txtId;
    @FXML private TextField txtNombre;
    @FXML private TextField txtDepartamento;
    @FXML private TextField txtRol;

    // --- La tabla y sus columnas ---
    @FXML private TableView<Employee> tablaEmpleados;
    @FXML private TableColumn<Employee, Integer> colId;
    @FXML private TableColumn<Employee, String> colNombre;
    @FXML private TableColumn<Employee, String> colDepartamento;
    @FXML private TableColumn<Employee, String> colRol;

    // Conectamos nuestro motor de base de datos
    private final JdbiDataSource dataSource = new JdbiDataSource();

    // 1. EL READ: Este método arranca solito al abrir la pestaña
    @FXML
    public void initialize() {
        // Le enseñamos a las columnas de qué atributo del Empleado deben sacar la info
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDepartamento.setCellValueFactory(new PropertyValueFactory<>("department"));
        colRol.setCellValueFactory(new PropertyValueFactory<>("role"));

        cargarTabla();
    }

    // Método auxiliar para refrescar la tabla
    private void cargarTabla() {
        List<Employee> listaDb = dataSource.findAllEmployees();
        ObservableList<Employee> listaObservable = FXCollections.observableArrayList(listaDb);
        tablaEmpleados.setItems(listaObservable);
    }

    // 2. EL CREATE: Cuando le dan clic a "Guardar Servidor"
    @FXML
    public void handleGuardar(ActionEvent event) {
        try {
            // Recogemos lo que el usuario escribió
            int id = Integer.parseInt(txtId.getText());
            String nombre = txtNombre.getText();
            String departamento = txtDepartamento.getText();
            String rol = txtRol.getText();

            // Armamos el objeto usando las palabras exactas que exige la BD
            Employee nuevoEmpleado = new Employee(
                    id,
                    nombre,
                    new Date(),
                    "M",            // <--- CAMBIADO: Solo una letra (M o F)
                    "soltero",
                    0,
                    departamento,
                    rol,
                    "000",
                    "contractual",
                    new Date(),
                    true
            );

            // Lo mandamos a la base de datos
            dataSource.saveEmployee(nuevoEmpleado);

            // Refrescamos la tabla y limpiamos el formulario
            cargarTabla();
            txtId.clear();
            txtNombre.clear();
            txtDepartamento.clear();
            txtRol.clear();

            System.out.println("¡Servidor guardado con éxito!");

        } catch (NumberFormatException e) {
            System.out.println("Error: Asegúrate de que la cédula sea un número.");
        }
    }

    // 3. EL DELETE: Cuando le dan clic a "Eliminar Seleccionado"
    @FXML
    public void handleBorrar(ActionEvent event) {
        // Miramos quién está seleccionado en la tabla
        Employee seleccionado = tablaEmpleados.getSelectionModel().getSelectedItem();

        if (seleccionado != null) {
            // Le pasamos el ID a nuestro motor para que lo fulmine
            dataSource.deleteEmployee(seleccionado.getId());

            // Recargamos la tabla para que desaparezca visualmente
            cargarTabla();
            System.out.println("¡Servidor eliminado!");
        } else {
            System.out.println("Por favor selecciona un servidor de la tabla primero.");
        }
    }
}