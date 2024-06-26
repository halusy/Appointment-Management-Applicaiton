package com.example.AMP.helper;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * This class holds the relevant methods for Program Alers
 *
 * @author Nicholas Ryan
 * @version 1.0
 */
public class AlertHelper {

    /**
     * This method takes two strings and alerts the user with a warning using the given Strings
     *
     * @param warningTitle
     * @param warningContent
     */
    public static void warning(String warningTitle, String warningContent){

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(LocaleDesignation.LocalLang.getString("warningTitle"));
        alert.setHeaderText(warningTitle);
        alert.setContentText(warningContent);
        alert.showAndWait();
    }

    /**
     *
     * This method takes two strings and gets user confrimation of an action they want to take, and uses the given Strings
     *
     * @param confirmationHeader
     * @param confrimationContent
     * @return
     */
    public static boolean confirmation(String confirmationHeader, String confrimationContent){

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(LocaleDesignation.LocalLang.getString("areYouSureTitle"));
        alert.setTitle(confirmationHeader);
        alert.setContentText(confrimationContent);
        Optional<ButtonType> Confirm = alert.showAndWait();
        if (Confirm.get() == ButtonType.OK){
            return true;
        } else {
            return false;
        }
    }
}
