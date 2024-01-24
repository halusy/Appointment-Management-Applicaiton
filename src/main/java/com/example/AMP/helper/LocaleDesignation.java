package com.example.AMP.helper;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocaleDesignation {
    static Locale L =  new Locale("en","US");
    public static ResourceBundle LocalLang = ResourceBundle.getBundle("com.example.AMP.Bundle.Bundle", L);

}
