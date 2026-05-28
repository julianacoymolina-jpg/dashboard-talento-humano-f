package usta.oop.dashboard.data;

import usta.oop.dashboard.model.AdministrativeSituation;
import usta.oop.dashboard.model.Employee;

import java.util.List;

public interface DataSource {
    int getEmployeeCountInVacations();
    int getEmployeeCountInPermit();
    int getEmployeeCountWithPendingVacations();
    List<Integer> getLastYearInactiveEmployeeCountInMonths();

    List<Employee> findAllEmployees();
    void deleteEmployee(int id);
    void saveEmployee(Employee employee);

    List<AdministrativeSituation> findAllAdministrativeSituations();
    void deleteAdministrativeSituation(int id);
    void saveAdministrativeSituation(AdministrativeSituation administrativeSituation);
}