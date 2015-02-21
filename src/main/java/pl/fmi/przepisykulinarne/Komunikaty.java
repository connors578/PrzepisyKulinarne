package pl.fmi.przepisykulinarne;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Komunikaty {
	public static void pokazInformacje(String komunikat){
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Informacja");
		alert.setHeaderText("Informacja");
		alert.setContentText(komunikat);
		alert.showAndWait();
	}
	
	public static void pokazBlad(String komunikat){
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Błąd");
			alert.setHeaderText(null);
			alert.setContentText(komunikat);
			alert.showAndWait();
	}
}
