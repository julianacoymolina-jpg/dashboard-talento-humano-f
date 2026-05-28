package usta.oop.dashboard.ui.desktop.entity;

import javafx.beans.property.SimpleStringProperty;
import usta.oop.dashboard.model.AdministrativeSituation;

import java.util.Date;

/*
 * Esta clase sirve para mostrar informacion en la tabla del historial de situaciones administrativas en el dashboard
 * representa cada fila de esa tabla
 */
public class AdminSituationTableRow {
    private String employeeName;
    private String category;
    private Date startsAt;
    private Date endsAt;

    public AdminSituationTableRow(String employeeName, AdministrativeSituation administrativeSituation) {
        this.employeeName = employeeName;
        this.category = administrativeSituation.getCategory();
        this.startsAt = administrativeSituation.getStartsAt();
        this.endsAt = administrativeSituation.getEndsAt();
        // this(employeeName, administrativeSituation.getCategory(), new SimpleStringProperty(administrativeSituation.getStartsAt().toString()), new SimpleStringProperty(administrativeSituation.getEndsAt().toString()));
    }

    public String getEmployeeName() {
        return employeeName;
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
