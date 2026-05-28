package usta.oop.dashboard.data.jdbi;

import org.jdbi.v3.core.Jdbi;
import usta.oop.dashboard.data.DataSource;
import usta.oop.dashboard.model.AdministrativeSituation;
import usta.oop.dashboard.model.Employee;

import java.util.ArrayList;
import java.util.List;

public class JdbiDataSource implements DataSource {
    private final Jdbi connection;

    public JdbiDataSource() {
        this.connection = Jdbi.create(
                     "jdbc:mysql://localhost:3306/governorship",
                "root",
                "poo20261"
        );
    }

    @Override
    public int getEmployeeCountInVacations() {
        /*
         * Usamos DISTINCT employee_id para no contar doble a los empleados
         * que tengan más de un periodo de vacaciones activo.
         */
        return connection.withHandle(handle -> {
            return handle.createQuery("""
                    SELECT COUNT(DISTINCT employee_id) 
                    FROM administrative_situation 
                    WHERE category = 'vacaciones' AND ends_at > CURDATE()
                    """)
                    .mapTo(Integer.class)
                    .one();
        });
    }

    @Override
    public int getEmployeeCountInPermit() {
        /*
         * Usamos DISTINCT employee_id para no contar doble al mismo empleado con varios permisos.
         */
        return connection.withHandle(handle -> {
            return handle.createQuery("""
                    SELECT COUNT(DISTINCT employee_id) 
                    FROM administrative_situation 
                    WHERE category = 'permiso' AND ends_at > CURDATE()
                    """)
                    .mapTo(Integer.class)
                    .one();
        });
    }

    @Override
    public int getEmployeeCountWithPendingVacations() {
        return connection.withHandle(handle -> {
            return handle.createQuery("""
                    SELECT COUNT(*) 
                    FROM employee 
                    WHERE employee_id NOT IN (
                        SELECT DISTINCT employee_id 
                        FROM administrative_situation 
                        WHERE category = 'vacaciones'
                    )
                    """)
                    .mapTo(Integer.class)
                    .one();
        });
    }

    @Override
    public List<Integer> getLastYearInactiveEmployeeCountInMonths() {
        List<Integer> grafica = new ArrayList<>();

        for (int i = 0; i < 12; i++) {
            final int mes = i + 1;
            int count = connection.withHandle(handle -> {
                return handle.createQuery("""
                        SELECT COUNT(DISTINCT e.employee_id)
                        FROM employee e
                        JOIN administrative_situation s ON e.employee_id = s.employee_id
                        WHERE e.is_active = false 
                          AND s.ends_at > CURDATE()
                          AND MONTH(s.starts_at) = :mes
                        """)
                        .bind("mes", mes)
                        .mapTo(Integer.class)
                        .one();
            });
            grafica.add(count);
        }
        return grafica;
    }

    @Override
    public List<Employee> findAllEmployees() {
        return connection.withHandle(handle ->
                handle.createQuery("SELECT * FROM employee")
                        .map((rs, ctx) -> new Employee(
                                rs.getInt("employee_id"),
                                rs.getString("name"),
                                rs.getDate("birth_date"),
                                rs.getString("gender"),
                                rs.getString("marital_status"),
                                rs.getInt("phone"),
                                rs.getString("department"),
                                rs.getString("role"),
                                rs.getString("code"),
                                rs.getString("employment_type"),
                                rs.getDate("joined_at"),
                                rs.getBoolean("is_active")
                        )).list()
        );
    }

    @Override
    public void deleteEmployee(int id) {
        connection.useHandle(handle -> {
            // Primero borramos sus situaciones administrativas por la llave foránea
            handle.createUpdate("DELETE FROM administrative_situation WHERE employee_id = :id")
                    .bind("id", id)
                    .execute();

            // Luego borramos al empleado
            handle.createUpdate("DELETE FROM employee WHERE employee_id = :id")
                    .bind("id", id)
                    .execute();
        });
    }
    @Override
    public void saveEmployee(Employee emp) {
        connection.useHandle(handle -> {
            handle.createUpdate("INSERT INTO employee " +
                            "(employee_id, name, birth_date, gender, marital_status, phone, department, role, code, employment_type, joined_at, is_active) " +
                            "VALUES (:id, :name, :birthDate, :gender, :maritalStatus, :phone, :department, :role, :code, :employmentType, :joinedAt, :isActive)")
                    .bind("id", emp.getId())
                    .bind("name", emp.getName())
                    .bind("birthDate", emp.getBirthDate())
                    .bind("gender", emp.getGender())
                    .bind("maritalStatus", emp.getMaritalStatus())
                    .bind("phone", emp.getPhone())
                    .bind("department", emp.getDepartment())
                    .bind("role", emp.getRole())
                    .bind("code", emp.getCode())
                    .bind("employmentType", emp.getEmploymentType())
                    .bind("joinedAt", emp.getJoinedAt())
                    .bind("isActive", emp.isActive())
                    .execute();
        });
    }

    @Override
    public List<AdministrativeSituation> findAllAdministrativeSituations() {
        return connection.withHandle(handle -> {
            return handle.createQuery("SELECT * FROM administrative_situation")
                    // Usamos getTimestamp para igualar el formato exacto del profesor
                    .map((rs, ctx) -> new AdministrativeSituation(
                            rs.getInt("situation_id"),
                            rs.getInt("employee_id"),
                            rs.getString("category"),
                            rs.getTimestamp("starts_at"),
                            rs.getTimestamp("ends_at")
                    ))
                    .list();
        });
    }

    @Override
    public void deleteAdministrativeSituation(int id) {
        connection.withHandle(handle -> {
            return handle.createUpdate("DELETE FROM administrative_situation WHERE situation_id = :id")
                    .bind("id", id)
                    .execute();
        });
    }

    @Override
    public void saveAdministrativeSituation(AdministrativeSituation situation) {
        connection.withHandle(handle -> {
            return handle.createUpdate("""
                    INSERT INTO administrative_situation (
                        situation_id, employee_id, category, starts_at, ends_at
                    ) VALUES (
                        :id, :employeeId, :category, :startsAt, :endsAt
                    )
                    """)
                    .bind("id", situation.getId())
                    .bind("employeeId", situation.getEmployeeId())
                    .bind("category", situation.getCategory())
                    .bind("startsAt", situation.getStartsAt())
                    .bind("endsAt", situation.getEndsAt())
                    .execute();
        });
    }
}