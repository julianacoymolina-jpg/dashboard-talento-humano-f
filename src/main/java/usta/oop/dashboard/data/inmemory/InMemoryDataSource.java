package usta.oop.dashboard.data.inmemory;

import usta.oop.dashboard.data.DataSource;
import usta.oop.dashboard.model.AdministrativeSituation;
import usta.oop.dashboard.model.Employee;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/*
 * Esta clase funciona como "base de datos" de prueba mientras creamos la implementacion real en JDBI
 */
public class InMemoryDataSource implements DataSource {

    private final List<Employee> employees = new ArrayList<>();
    private final List<AdministrativeSituation> administrativeSituations = new ArrayList<>();

    /* Aqui tienes que retornar el conteo de empleados que estan en vacaciones
     * Ese conteo se basa en la lista de empleados que tienen una situacion administrativa de tipo "vacaciones"
     * y que este activa, osea que la fecha de finalizacion (el atributo endsAt de AdministrativeSituation) sea
     * posterior a la fecha actual.
     */
    @Override
    public int getEmployeeCountInVacations() {

        int count = 0;
        Date currentDate = new Date();

        for (AdministrativeSituation situation : administrativeSituations) {

            boolean isVacation =
                    situation.getCategory().equalsIgnoreCase("vacaciones");

            boolean isActive =
                    situation.getEndsAt().after(currentDate);

            if (isVacation && isActive) {
                count++;
            }
        }

        return count;
    }

    /* Aqui tienes que retornar el conteo de empleados que estan en permiso
     * Ese conteo se basa en la lista de empleados que tienen una situacion administrativa de tipo "permiso"
     * y que este activa, osea que la fecha de finalizacion (el atributo endsAt de AdministrativeSituation) sea
     * posterior a la fecha actual.
     */
    @Override
    public int getEmployeeCountInPermit() {

        int count = 0;
        Date currentDate = new Date();

        for (AdministrativeSituation situation : administrativeSituations) {

            boolean isPermit =
                    situation.getCategory().equalsIgnoreCase("permiso");

            boolean isActive =
                    situation.getEndsAt().after(currentDate);

            if (isPermit && isActive) {
                count++;
            }
        }

        return count;
    }

    /* Devuelve la cantidad de empleados que tienen vacaciones pendientes, este numero lo sacas de consultar en la
     * lista de empleados quienes llevan mas de 1 año sin una situacion administrativa de tipo "vacaciones"
     */
    @Override
    public int getEmployeeCountWithPendingVacations() {

        int count = 0;

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -1);

        Date oneYearAgo = calendar.getTime();

        for (Employee employee : employees) {

            boolean hasRecentVacations = false;

            for (AdministrativeSituation situation : administrativeSituations) {

                boolean sameEmployee =
                        situation.getEmployeeId() == employee.getId();

                boolean isVacation =
                        situation.getCategory().equalsIgnoreCase("vacaciones");

                boolean recentVacation =
                        situation.getEndsAt().after(oneYearAgo);

                if (sameEmployee && isVacation && recentVacation) {
                    hasRecentVacations = true;
                    break;
                }
            }

            if (!hasRecentVacations) {
                count++;
            }
        }

        return count;
    }

    /* Devuelve una lista de conteo de empleados inactivos (osea con cualquier tipo de situacion administrativa
     * que no haya terminado) en los ultimos 12 meses, ejemplo [4, 6, 6, 2, 0, 8, 1, 10, 5, 3, 1, 0]
     * SI O SI DEBE DEVOLVER UNA LISTA DE 12 NUMEROS ENTEROS
     */
    @Override
    public List<Integer> getLastYearInactiveEmployeeCountInMonths() {

        List<Integer> counts = new ArrayList<>();

        for (int i = 0; i < 12; i++) {
            counts.add(0);
        }

        Calendar currentCalendar = Calendar.getInstance();

        for (Employee employee : employees) {

            if (!employee.isActive()) {

                Calendar joinedCalendar = Calendar.getInstance();
                joinedCalendar.setTime(employee.getJoinedAt());

                int monthDifference =
                        currentCalendar.get(Calendar.MONTH)
                                - joinedCalendar.get(Calendar.MONTH);

                if (monthDifference >= 0 && monthDifference < 12) {

                    int currentCount = counts.get(monthDifference);

                    counts.set(monthDifference, currentCount + 1);
                }
            }
        }

        return counts;
    }

    @Override
    public List<Employee> findAllEmployees() {
        return employees;
    }

    @Override
    public void deleteEmployee(int id) {

        Employee employeeToDelete = null;

        for (Employee employee : employees) {

            if (employee.getId() == id) {
                employeeToDelete = employee;
                break;
            }
        }

        if (employeeToDelete != null) {
            employees.remove(employeeToDelete);
        }
    }

    @Override
    public void saveEmployee(Employee employee) {
        employees.add(employee);
    }

    @Override
    public List<AdministrativeSituation> findAllAdministrativeSituations() {
        return administrativeSituations;
    }

    @Override
    public void deleteAdministrativeSituation(int id) {

        AdministrativeSituation situationToDelete = null;

        for (AdministrativeSituation situation : administrativeSituations) {

            if (situation.getId() == id) {
                situationToDelete = situation;
                break;
            }
        }

        if (situationToDelete != null) {
            administrativeSituations.remove(situationToDelete);
        }
    }

    @Override
    public void saveAdministrativeSituation(
            AdministrativeSituation administrativeSituation
    ) {

        administrativeSituations.add(administrativeSituation);
    }
}
