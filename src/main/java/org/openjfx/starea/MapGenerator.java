package org.openjfx.starea;

import org.json.JSONObject;

import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Key;

public final class MapGenerator {
    private static String APIkey = "";
    public static String generateMapImageURL(int width, int height, double currLat, double currLon, double targetLat, double targetLon) throws Exception {
        // Label Y is You, label T is Target

        if (APIkey == "") {
            byte[] encrypted = Files.readAllBytes(Paths.get("src/main/resources/org/openjfx/starea/key"));

            String key = "SAFALSAFALSAFAL!";
            Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");

            cipher.init(Cipher.DECRYPT_MODE, aesKey);
            String decrypted = new String(cipher.doFinal(encrypted));

            APIkey = decrypted;
        }

        String url = "https://maps.googleapis.com/maps/api/staticmap?" +
                "center=" + currLat + "," + currLon +
                "&size=" + width + "x" + height +
                "&maptype=roadmap" +
                "&markers=color:red|label:Y|" + currLat + "," + currLon +
                "&markers=color:blue|label:T|" + targetLat + "," + targetLon +
                "&key=" + APIkey +
                "&style=feature:poi|element:labels|visibility:off";

        return url;
    }

    public static String getMapsURL(double targetLat, double targetLon)
    {
        return "https://www.google.com/maps?t=m&q=loc:" +
                targetLat + "," + targetLon;
    }
}
