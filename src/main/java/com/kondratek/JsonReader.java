package com.kondratek;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class JsonReader {

    /**
    * @return array of JSON objects received from url
    */
    public static JSONArray readJsonArrayFromUrl(String url) throws IOException, JSONException {
        String jsonText = fetchJsonString(url);
        return new JSONArray(jsonText);
    }

    /**
     * @return JSON object received from url
     */
    public static JSONObject readJsonObjectFromUrl(String url) throws IOException, JSONException {
        String jsonText = fetchJsonString(url);
        return new JSONObject(jsonText);
    }

    public static String fetchJsonString(String url) throws IOException {
        try (InputStream is = new URL(url).openStream()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            return readAll(rd);
        }
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

}
