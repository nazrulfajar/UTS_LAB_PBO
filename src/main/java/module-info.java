module com.utslab.contactmanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql;

    opens com.utslab.contactmanager to javafx.fxml;
    exports com.utslab.contactmanager;
}
