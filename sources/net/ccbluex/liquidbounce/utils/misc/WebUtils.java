package net.ccbluex.liquidbounce.utils.misc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/* loaded from: Jackey Client b2.jar:net/ccbluex/liquidbounce/utils/misc/WebUtils.class */
public class WebUtils {
    public static String get(String url) throws IOException {
        HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        StringBuilder response = new StringBuilder();
        while (true) {
            String inputLine = in.readLine();
            if (inputLine != null) {
                response.append(inputLine);
                response.append("\n");
            } else {
                in.close();
                return response.toString();
            }
        }
    }
}
