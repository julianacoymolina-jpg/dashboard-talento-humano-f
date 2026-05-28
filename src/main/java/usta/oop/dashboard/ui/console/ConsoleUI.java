package usta.oop.dashboard.ui.console;

import usta.oop.dashboard.data.DataSource;
import usta.oop.dashboard.model.AdministrativeSituation;
import usta.oop.dashboard.model.Employee;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

/*
 * Este ConsoleUI solo era para probar si DataSource funcionaba bien antes de hacer el dashboard, pueden usarlo tambien
 * para probar pero la idea es que funcione en DesktopUI
 */

public class ConsoleUI {
    private final DataSource dataSource;
    private final Scanner scanner;

    public ConsoleUI(DataSource dataSource) {
        this.dataSource = dataSource;
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        int option = -1;

        while (option != 0) {

            System.out.println("\n===== HUMAN RESOURCES MENU =====");
            System.out.println("1. Show employee statistics");
            System.out.println("2. List all employees");
            System.out.println("3. Save employee");
            System.out.println("4. Delete employee");
            System.out.println("5. List all administrative situations");
            System.out.println("6. Save administrative situation");
            System.out.println("7. Delete administrative situation");
            System.out.println("0. Exit");
            System.out.print("Select an option: ");

            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {

                case 1:
                    showEmployeeStatistics(dataSource);
                    break;

                case 2:
                    listAllEmployees(dataSource);
                    break;

                case 3:
                    saveEmployee(scanner, dataSource);
                    break;

                case 4:
                    deleteEmployee(scanner, dataSource);
                    break;

                case 5:
                    listAllAdministrativeSituations(dataSource);
                    break;

                case 6:
                    saveAdministrativeSituation(scanner, dataSource);
                    break;

                case 7:
                    deleteAdministrativeSituation(scanner, dataSource);
                    break;

                case 0:
                    System.out.println("Program finished.");
                    break;

                default:
                    System.out.println("Invalid option.");
            }
        }

        scanner.close();
    }

    public static void showEmployeeStatistics(DataSource dataSource) {
        System.out.println("\n--- EMPLOYEE STATISTICS ---");
        System.out.println("Employees on vacations: " + dataSource.getEmployeeCountInVacations());
        System.out.println("Employees on permit: " + dataSource.getEmployeeCountInPermit());
        System.out.println("Employees with pending vacations: " + dataSource.getEmployeeCountWithPendingVacations());
        System.out.println("\nInactive employees in the last year by month:");
        List<Integer> inactiveCounts = dataSource.getLastYearInactiveEmployeeCountInMonths();

        for (int i = 0; i < inactiveCounts.size(); i++) {
            System.out.println("Month " + (i + 1) + ": " + inactiveCounts.get(i));
        }

        System.out.println("\nAdministrative situations history:");

        List<AdministrativeSituation> history = dataSource.findAllAdministrativeSituations();

        for (AdministrativeSituation situation : history) {
            System.out.println("----------------------------");
            System.out.println("ID: " + situation.getId());
            System.out.println("Employee ID: " + situation.getEmployeeId());
            System.out.println("Category: " + situation.getCategory());
            System.out.println("Starts At: " + situation.getStartsAt());
            System.out.println("Ends At: " + situation.getEndsAt());
        }
    }

    public static void listAllEmployees(DataSource dataSource) {
        System.out.println("\n--- EMPLOYEES ---");
        List<Employee> employees = dataSource.findAllEmployees();
        for (Employee employee : employees) {
            System.out.println("----------------------------");
            System.out.println("ID: " + employee.getId());
            System.out.println("Name: " + employee.getName());
            System.out.println("Birth Date: " + employee.getBirthDate());
            System.out.println("Gender: " + employee.getGender());
            System.out.println("Marital Status: " + employee.getMaritalStatus());
            System.out.println("Phone: " + employee.getPhone());
            System.out.println("Department: " + employee.getDepartment());
            System.out.println("Role: " + employee.getRole());
            System.out.println("Code: " + employee.getCode());
            System.out.println("Employment Type: " + employee.getEmploymentType());
            System.out.println("Joined At: " + employee.getJoinedAt());
            System.out.println("Active: " + employee.isActive());
        }
    }

    public static void saveEmployee(Scanner scanner, DataSource dataSource) {
        System.out.println("\n--- SAVE EMPLOYEE ---");
        System.out.print("ID: ");
        int employeeId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Name: ");
        String employeeName = scanner.nextLine();
        System.out.print("Gender: ");
        String gender = scanner.nextLine();
        System.out.print("Marital Status: ");
        String maritalStatus = scanner.nextLine();
        System.out.print("Phone: ");
        int phone = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Department: ");
        String department = scanner.nextLine();
        System.out.print("Role: ");
        String role = scanner.nextLine();
        System.out.print("Code: ");
        String code = scanner.nextLine();
        System.out.print("Employment Type: ");
        String employmentType = scanner.nextLine();
        System.out.print("Is Active (true/false): ");
        boolean isActive = scanner.nextBoolean();
        scanner.nextLine();

        Date birthDate = new Date();
        Date joinedAt = new Date();
        Employee employee = new Employee(employeeId, employeeName, birthDate, gender, maritalStatus, phone, department, role, code, employmentType, joinedAt, isActive);
        dataSource.saveEmployee(employee);
        System.out.println("Employee saved successfully.");
    }

    public static void deleteEmployee(Scanner scanner, DataSource dataSource) {
        System.out.println("\n--- DELETE EMPLOYEE ---");
        System.out.print("Employee ID: ");
        int deleteEmployeeId = scanner.nextInt();
        scanner.nextLine();
        dataSource.deleteEmployee(deleteEmployeeId);
        System.out.println("Employee deleted successfully.");
    }

    public static void listAllAdministrativeSituations(DataSource dataSource) {
        System.out.println("\n--- ADMINISTRATIVE SITUATIONS ---");
        List<AdministrativeSituation> situations = dataSource.findAllAdministrativeSituations();
        for (AdministrativeSituation situation : situations) {
            System.out.println("----------------------------");
            System.out.println("ID: " + situation.getId());
            System.out.println("Employee ID: " + situation.getEmployeeId());
            System.out.println("Category: " + situation.getCategory());
            System.out.println("Starts At: " + situation.getStartsAt());
            System.out.println("Ends At: " + situation.getEndsAt());
        }
    }

    public static void saveAdministrativeSituation(Scanner scanner, DataSource dataSource) {
        System.out.println("\n--- SAVE ADMINISTRATIVE SITUATION ---");
        System.out.print("Situation ID: ");
        int situationId = scanner.nextInt();
        System.out.print("Employee ID: ");
        int situationEmployeeId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Category: ");
        String category = scanner.nextLine();
        Date startsAt = new Date();
        Date endsAt = new Date();
        AdministrativeSituation administrativeSituation = new AdministrativeSituation(situationId, situationEmployeeId, category, startsAt, endsAt);
        dataSource.saveAdministrativeSituation(administrativeSituation);
        System.out.println("Administrative situation saved successfully.");
    }

    public static void deleteAdministrativeSituation(Scanner scanner, DataSource dataSource) {
        System.out.println("\n--- DELETE ADMINISTRATIVE SITUATION ---");
        System.out.print("Situation ID: ");
        int deleteSituationId = scanner.nextInt();
        scanner.nextLine();
        dataSource.deleteAdministrativeSituation(deleteSituationId);
        System.out.println("Administrative situation deleted successfully.");
    }
}
