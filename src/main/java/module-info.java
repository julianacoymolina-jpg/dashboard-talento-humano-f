module usta.oop.dashboard {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires io.leangen.geantyref;
    requires org.slf4j;
    requires java.sql;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires org.jdbi.v3.core;
    requires kotlin.stdlib;

    opens usta.oop.dashboard to javafx.fxml;
    exports usta.oop.dashboard;
    opens usta.oop.dashboard.ui.desktop to javafx.fxml, javafx.graphics;
    opens usta.oop.dashboard.ui.desktop.controller to javafx.fxml, javafx.graphics;
    opens usta.oop.dashboard.ui.desktop.entity to javafx.base;
    opens usta.oop.dashboard.model to javafx.base;
}