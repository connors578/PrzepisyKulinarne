package pl.fmi.przepisykulinarne.akcjeprzyciskow;

import java.util.ArrayList;

import pl.fmi.przepisykulinarne.interfejsyuzytkownika.OknoPrzepisu;
import pl.fmi.przepisykulinarne.model.Skladniki;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ModyfikujSkladnik implements EventHandler<ActionEvent>{
	private ArrayList<Skladniki> skladniki;
	private TextField skladnik;
	private OknoPrzepisu oknoPrzepisu;
	private boolean czModyfikacja = false;
	public ModyfikujSkladnik(ArrayList<Skladniki> skladniki, OknoPrzepisu oknoPrzepisu) {
		this.skladniki = skladniki;
		this.oknoPrzepisu = oknoPrzepisu;
	}
	
	@Override
	public void handle(ActionEvent event) {
		Stage stage = new Stage();
		FlowPane layout = new FlowPane();
		Button akceptuj = new Button("Akceptuj");
		Button anuluj = new Button("Anuluj");
		skladnik = new TextField();
		skladnik.setPrefWidth(400);
		layout.getChildren().addAll(akceptuj, skladnik, anuluj);
		Scene addScene = new Scene(layout, 535, 25);
		stage.setScene(addScene);
		stage.setTitle("Skladnik");
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.initOwner(((Node)event.getSource()).getScene().getWindow());
		stage.show();
		
		if (((Button)(event.getSource())).getText().equals("Modyfikuj składnik"))
			if (oknoPrzepisu.pobierzListeSklad().getSelectionModel().getSelectedIndex() > -1)
			{
				skladnik.setText(skladniki.get(oknoPrzepisu.pobierzListeSklad().getSelectionModel().getSelectedIndex()).getNazwa());
				czModyfikacja = true;
			}
		
		anuluj.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				stage.close();
			}
		});
		
		akceptuj.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (!czModyfikacja)
					skladniki.add(new Skladniki(skladnik.getText(), null));
				else
					skladniki.get(oknoPrzepisu.pobierzListeSklad().getSelectionModel().getSelectedIndex()).setNazwa(skladnik.getText());
				stage.close();
				czModyfikacja= false;
				oknoPrzepisu.uzupelnijListe();
			}
		});
	}
}
