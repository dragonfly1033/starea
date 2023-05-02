package org.openjfx.starea;

import java.nio.file.Files;
import java.nio.file.Paths;
import netscape.javascript.JSObject;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.concurrent.Worker;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;


public class Main extends Application {
    private Scene scene;
    @Override public void start(Stage stage) throws Exception {
        stage.setTitle("Starea");
        scene = new Scene(new Browser(),750,500, Color.web("#555555"));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}
class Browser extends Region {

    final WebView browser = new WebView();
    final WebEngine webEngine = browser.getEngine();

    public Browser() throws Exception {
        String RES = "./src/main/resources/org/openjfx/starea/";
        getStyleClass().add("browser");
        String html = Files.readString(Paths.get(RES+"index.html"));
        webEngine.loadContent(html, "text/html");

        webEngine.getLoadWorker().stateProperty().addListener(
            new ChangeListener() {
                @Override
                public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                    if (newValue != Worker.State.SUCCEEDED) { return; }

                    JSObject window = (JSObject) webEngine.executeScript("window");
                    window.setMember("myObject", new MyClass(webEngine));
                }
            }
        );
        getChildren().add(browser);
    }

    @Override protected void layoutChildren() {
        double w = getWidth();
        double h = getHeight();
        layoutInArea(browser,0,0,w,h,0, HPos.CENTER, VPos.CENTER);
    }

    @Override protected double computePrefWidth(double height) {
        return 800;
    }

    @Override protected double computePrefHeight(double width) {
        return 500;
    }
}
