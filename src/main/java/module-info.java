module com.yb.digilib {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.xerial.sqlitejdbc;
    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.material2;

    opens com.yb.digilib.controller to javafx.fxml;
    opens com.yb.digilib to javafx.fxml;
    exports com.yb.digilib;
    exports com.yb.digilib.controller;
    exports com.yb.digilib.model;
    exports com.yb.digilib.view;
}
