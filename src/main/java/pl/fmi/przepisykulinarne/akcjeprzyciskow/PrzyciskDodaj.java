package pl.fmi.przepisykulinarne.akcjeprzyciskow;

import java.util.ArrayList;

import pl.fmi.przepisykulinarne.interfejsyuzytkownika.OknoGlowne;
import pl.fmi.przepisykulinarne.interfejsyuzytkownika.OknoPrzepisu;
import pl.fmi.przepisykulinarne.model.Przepis;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PrzyciskDodaj implements EventHandler<ActionEvent>{
	OknoPrzepisu oknoPrzepisu;
	ArrayList<Przepis> przepisy;
	OknoGlowne oknoGlowne;
	
	public PrzyciskDodaj(ArrayList<Przepis> przepisy, OknoGlowne oknoGlowne) {
		this.przepisy = przepisy;
		this.oknoGlowne = oknoGlowne;
	}
	@Override
	public void handle(ActionEvent event) {
		Stage stage = new Stage();
		if (((Button)(event.getSource())).getText().equals("Modyfikuj"))
			oknoGlowne.czyModyfikacja = true;
		else
			oknoGlowne.czyModyfikacja = false;
		oknoPrzepisu = new OknoPrzepisu(przepisy,stage,oknoGlowne);
		Scene addScene = new Scene(oknoPrzepisu.pobierzOknoPrzepisu(), 600, 600);
		stage.setScene(addScene);
		stage.setTitle("Przepis");
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.initOwner(((Node)event.getSource()).getScene().getWindow());
		stage.show();
	}

}
