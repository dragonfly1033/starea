package org.openjfx.starea;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public final class MapGenerator {
    private static final File imagePath = new File("src/main/resources/org/openjfx/starea/map/map.png");
    public static File generateNewMapImage(int width, int height, double currLat, double currLon, double targetLat, double targetLon) throws IOException {
        // Label Y is You, label T is Target
        if (imagePath.exists())
        {
            imagePath.delete();
        }

        String url = "https://maps.googleapis.com/maps/api/staticmap?" +
                "center=" + currLat + "," + currLon +
                "&size=" + width + "x" + height +
                "&maptype=roadmap" +
                "&markers=color:red|label:Y|" + currLat + "," + currLon +
                "&markers=color:blue|label:T|" + targetLat + "," + targetLon +
                "&key=AIzaSyCt3rLh_aV-YRwbOIpZ1fdx70iT4hxGktw" +
                "&style=feature:poi|element:labels|visibility:off";

        InputStream in = new URL(url).openStream();
        Files.copy(in, Paths.get(imagePath.getPath()));

        return imagePath;
    }

    public static String getMapsURL(double targetLat, double targetLon)
    {
        return "https://www.google.com/maps?t=m&q=loc:" +
                targetLat + "," + targetLon;
    }
}
