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

import java.io.*;


public class Main extends Application {
    private Scene scene;
    private static Backend backend;
    private static Browser browser;
    private static Main main;

    @Override public void start(Stage stage) throws Exception {
        stage.setTitle("Starea");
        browser = new Browser(backend);
        scene = new Scene(browser,375,667, Color.web("#555555"));
        stage.setScene(scene);
        stage.show();
        Main.main = this;
    }

    public static void main(String[] args) throws Exception {
        backend = new Backend();
        launch(args);
    }

    public static void showDocument(String url) { browser.webEngine.load(url); }
}
class Browser extends Region {

    final WebView browser = new WebView();
    final WebEngine webEngine = browser.getEngine();
    private Remote remote;

    public Browser(Backend backend) throws Exception {
        remote = new Remote(webEngine, backend);

        String RES = "src/main/resources/org/openjfx/starea/";
        String abspath = new File("src/main/resources/org/openjfx/starea").getAbsolutePath();
        remote.setPath("file://"+abspath+"/");
        getStyleClass().add("browser");

        backend.index_html = Files.readString(Paths.get(RES+"index.html"));
        String linkcss = "\n\t\t<link rel=\"stylesheet\" href=\"file://"+abspath+"/style.css\">";
        String scriptjs = "\n\t\t<script text=\"text/javascript\" src=\"file://"+abspath+"/script.js\"></script>";
        backend.index_html = backend.index_html.replace("<title>Starea</title>", "<title>Starea</title>"+linkcss+scriptjs);

        backend.map_html = Files.readString(Paths.get(RES+"map.html"));
        linkcss = "\n\t\t<link rel=\"stylesheet\" href=\"file://"+abspath+"/style-map.css\">";
        scriptjs = "\n\t\t<script text=\"text/javascript\" src=\"file://"+abspath+"/script-map.js\"></script>";
        backend.map_html = backend.map_html.replace("<title>Starea</title>", "<title>Starea</title>"+linkcss+scriptjs);



        //        System.out.println(html);
        webEngine.getLoadWorker().stateProperty().addListener(
            new ChangeListener() {
                @Override
                public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                    if (newValue != Worker.State.SUCCEEDED) { return; }

                    JSObject window = (JSObject) webEngine.executeScript("window");

                    window.setMember("remote", remote);

                    webEngine.executeScript("console.log = function(message) { remote.log(message); }");
                    webEngine.executeScript("startUp()");
                }
            }
        );

        webEngine.loadContent(backend.index_html, "text/html");
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
