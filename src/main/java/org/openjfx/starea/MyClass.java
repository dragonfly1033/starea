package org.openjfx.starea;

import javafx.scene.web.WebEngine;

public class MyClass {
    private WebEngine webEngine;
    public void doIt(String path) {
//        System.out.println("toggleShape() being called");
//        webEngine.executeScript("toggleShape()");
        System.out.println(path);
    }

    public MyClass(WebEngine we) {
        webEngine = we;
    }
}
