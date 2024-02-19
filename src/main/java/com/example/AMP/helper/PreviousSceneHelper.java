package com.example.AMP.helper;

public class PreviousSceneHelper {

    public static Boolean Ps = false;

    public static void PsSetterTrue(){

        Ps = true;

    }

    public static void PsSetterFalse(){

        Ps = false;

    }
    public static Boolean PreviousScene() {

        return Ps;

    }

    public static boolean Li = false;

    public static void loginPageSetterFalse(){
        Li = false;
    }
    public static void loginPageSetter(){
        Li = true;
    }
    public static boolean loginGetter(){
        if (Li){
            return true;
        } else {
            return false;
        }
    }
}
