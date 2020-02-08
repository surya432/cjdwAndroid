package com.suryaheho.projectb.Helper;

public class Http {

    public static String server = "http://cjdwapiandroit.000webhostapp.com/";

    public static String string(String string) {
        return string.replace(" ", "%20");
    }

    public static String server() {
        return server;
    }
}