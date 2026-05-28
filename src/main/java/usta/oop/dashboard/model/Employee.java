package usta.oop.dashboard.model;

import java.util.Date;
import java.nio.charset.StandardCharsets;

public class Employee {
    private int id;
    private String name;
    private Date birthDate;
    private String gender;
    private String maritalStatus;
    private int phone;
    private String department;
    private String role;
    private String code;
    private String employmentType;
    private Date joinedAt;
    private boolean isActive;

    public Employee(int id, String name, Date birthDate, String gender, String maritalStatus, int phone, String department, String role, String code, String employmentType, Date joinedAt, boolean isActive) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.gender = gender;
        this.maritalStatus = maritalStatus;
        this.phone = phone;
        this.department = department;
        this.role = role;
        this.code = code;
        this.employmentType = employmentType;
        this.joinedAt = joinedAt;
        this.isActive = isActive;
    }

    public int getId() {
        return this.id;
    }

    // 1. Traductor inteligente para el Nombre
    public String getName() {
        try {
            String arreglado = new String(this.name.getBytes("ISO-8859-1"), StandardCharsets.UTF_8);
            // Si la traducción genera el rombo de error, devuelve el original que ya estaba bien
            if (arreglado.contains("\uFFFD")) {
                return this.name;
            }
            return arreglado;
        } catch (Exception e) {
            return this.name;
        }
    }

    // 2. Traductor inteligente para el Departamento
    public String getDepartment() {
        if (department == null) return null;
        try {
            String arreglado = new String(this.department.getBytes("ISO-8859-1"), StandardCharsets.UTF_8);
            if (arreglado.contains("\uFFFD")) {
                return this.department;
            }
            return arreglado;
        } catch (Exception e) {
            return this.department;
        }
    }

    // 3. Traductor inteligente para el Rol
    public String getRole() {
        if (role == null) return null;
        try {
            String arreglado = new String(this.role.getBytes("ISO-8859-1"), StandardCharsets.UTF_8);
            if (arreglado.contains("\uFFFD")) {
                return this.role;
            }
            return arreglado;
        } catch (Exception e) {
            return this.role;
        }
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public String getGender() {
        return gender;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public int getPhone() {
        return phone;
    }

    public String getCode() {
        return code;
    }

    public String getEmploymentType() {
        return employmentType;
    }

    public Date getJoinedAt() {
        return joinedAt;
    }

    public boolean isActive() {
        return isActive;
    }
}