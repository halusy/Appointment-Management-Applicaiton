package com.example.AMP.helper;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class AlertHelper {

    public static void warning(String warningTitle, String warningContent){

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(LocaleDesignation.LocalLang.getString("warningTitle"));
        alert.setHeaderText(warningTitle);
        alert.setContentText(warningContent);
        alert.showAndWait();

    }

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
