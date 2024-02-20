package com.example.AMP.helper;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * This class manages the Local of the user
 *
 * @author Nicholas Ryan
 * @version 1.0
 *
 */
public class LocaleDesignation {
    static Locale L =  Locale.getDefault();
    public static ResourceBundle LocalLang = ResourceBundle.getBundle("com.example.AMP.Bundle.Bundle", L);

}
