module org.openjfx.test {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires org.json;
    requires jdk.jsobject;


    opens org.openjfx.starea to javafx.fxml;
    exports org.openjfx.starea;
}