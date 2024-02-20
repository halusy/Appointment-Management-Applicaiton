package com.example.AMP.helper;

/**
 * This class tracks which type of scene the appointment was last in. This helps prep the main-view based on the scene the user if coming from.
 *
 * @author Nicholas Ryan
 * @version 1.0
 */
public class PreviousSceneHelper {

    public static Boolean Ps = false;

    /**
     * Sets the previous scene value to True (indicating a customer scene was the previous scene)
     */
    public static void PsSetterTrue(){

        Ps = true;

    }
    /**
     * Sets the previous scene value to False (indicating an appointment scene was the previous scene)
     */
    public static void PsSetterFalse(){

        Ps = false;

    }

    /**
     * Returns the type of previous scene
     *
     * @return
     */
    public static Boolean PreviousScene() {

        return Ps;

    }

    public static boolean Li = false;

    /**
     * Sets value Li to false indicating the previous scene was not a login page
     *
     */
    public static void loginPageSetterFalse(){
        Li = false;
    }

    /**
     * Sets value Li to false indicating the previous scene was a login page
     *
     */
    public static void loginPageSetter(){
        Li = true;
    }

    /**
     * Returns weather or not the previous scene was the login page
     *
     * @return
     */
    public static boolean loginGetter(){
        if (Li){
            return true;
        } else {
            return false;
        }
    }
}
