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

}
