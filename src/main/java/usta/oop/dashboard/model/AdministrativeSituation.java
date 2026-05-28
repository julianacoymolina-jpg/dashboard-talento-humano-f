package usta.oop.dashboard.model;

import java.util.Date;


public class AdministrativeSituation {
    private int id;
    private int employeeId;
    // vacaciones | permiso | remunerada | maternidad | paternidad | enfermedad
    private String category;
    // Cuando inicia y termina la situacion administrativa
    private Date startsAt;
    private Date endsAt;

    public AdministrativeSituation(int id, int employeeId, String category, Date startsAt, Date endsAt) {
        this.id = id;
        this.employeeId = employeeId;
        this.category = category;
        this.startsAt = startsAt;
        this.endsAt = endsAt;
    }

    // TODO: hacer getters para cada atributo, ejemplo:
    public int getId() {
        return this.id;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public String getCategory() {
        return category;
    }

    public Date getStartsAt() {
        return startsAt;
    }

    public Date getEndsAt() {
        return endsAt;
    }
}
