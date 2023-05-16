package org.openjfx.starea;

import javafx.scene.web.WebEngine;

public class Remote {
    private WebEngine webEngine;
    public void func2() {
        System.out.println("run func2 in java from js");
        webEngine.executeScript("func3()");
    }

    public void log(String text) {
        System.out.println(text);
    }

    public Remote(WebEngine we) {
        webEngine = we;
    }
}
