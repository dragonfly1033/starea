package org.openjfx.starea;

import javafx.scene.web.WebEngine;

public class MyClass {
    private WebEngine webEngine;
    public void doIt(String path) throws Exception {
//        System.out.println("toggleShape() being called");
//        webEngine.executeScript("toggleShape()");
        Backend back = new Backend();
        System.out.println("Done");
    }

    public MyClass(WebEngine we) {
        webEngine = we;
    }
}
