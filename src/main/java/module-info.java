module org.openjfx.test {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires org.json;
    requires jdk.jsobject;
    requires java.net.http;
    requires java.desktop;

    opens org.openjfx.starea to javafx.fxml;
    exports org.openjfx.starea;
}