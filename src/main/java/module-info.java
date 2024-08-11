module com.yb.digilib {
    requires javafx.controls;
    requires javafx.fxml;
    requires de.jensd.fx.glyphs.fontawesome;
    requires java.sql;
    requires org.xerial.sqlitejdbc;

    opens com.yb.digilib.Controllers to javafx.fxml;
    opens com.yb.digilib.Controllers.Layout to javafx.fxml;
    opens com.yb.digilib to javafx.fxml;
    exports com.yb.digilib;
    exports com.yb.digilib.Controllers.Layout;
    exports com.yb.digilib.Controllers.Admin;
    exports com.yb.digilib.Views;
    exports com.yb.digilib.Models;
}