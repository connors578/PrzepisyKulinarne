package pl.fmi.przepisykulinarne.interfejsyuzytkownika;

import java.util.ArrayList;
import java.util.Iterator;

import javax.jws.Oneway;

import pl.fmi.przepisykulinarne.akcjeprzyciskow.ModyfikujSkladnik;
import pl.fmi.przepisykulinarne.akcjeprzyciskow.PrzyciskDodaj;
import pl.fmi.przepisykulinarne.kontroler.BazaDanych;
import pl.fmi.przepisykulinarne.model.Przepis;
import pl.fmi.przepisykulinarne.model.Skladniki;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class OknoPrzepisu {

	BorderPane ukladOkna = new BorderPane();
	FlowPane panelPrzyciskow = new FlowPane();
	FlowPane panelWyszukiwania = new FlowPane();
	Button dodajSkladnik = new Button("Dodaj składnik");
	Button modyfikujSkladnik = new Button("Modyfikuj składnik");
	Button usunSkladnik = new Button("Usuń składnik");
	Button akceptuj = new Button("Akceptuj");
	Button anuluj = new Button("Anuluj");
	Label etykietaPrzepisu = new Label("Nazwa przepisu: ");
	TextField nazwaPrzepisu = new TextField();
	ListView<String> listaSkladnikow = new ListView<String>();
	TextArea opisPrzygotowania = new TextArea();
	ArrayList<Skladniki> skladniki = new ArrayList<Skladniki>();
	ArrayList<Przepis> przepisy = new ArrayList<Przepis>();
	Stage stage;
	OknoGlowne oknoGlowne;

	public OknoPrzepisu(ArrayList<Przepis> przepisy, Stage stage, OknoGlowne oknoGlowne) {
		this.przepisy = przepisy;
		this.stage = stage;
		this.oknoGlowne = oknoGlowne;
		nazwaPrzepisu.setPrefWidth(450);
		listaSkladnikow.setPrefWidth(200);
		
		if (oknoGlowne.czyModyfikacja == true)
		{
			if (oknoGlowne.pobierzListePrzepisow().getSelectionModel().getSelectedIndex() > -1)
			{
				skladniki = przepisy.get(oknoGlowne.pobierzListePrzepisow().getSelectionModel().getSelectedIndex()).getSkladniki();
				uzupelnijListe();
				opisPrzygotowania.setText(przepisy.get(oknoGlowne.pobierzListePrzepisow().getSelectionModel().getSelectedIndex()).getOpisPrzygotowania());
				nazwaPrzepisu.setText(przepisy.get(oknoGlowne.pobierzListePrzepisow().getSelectionModel().getSelectedIndex()).getNazwa());
			}
		}

		panelPrzyciskow.getChildren().addAll(dodajSkladnik, modyfikujSkladnik,
				usunSkladnik);
		panelWyszukiwania.getChildren().addAll(etykietaPrzepisu, nazwaPrzepisu,
				akceptuj, anuluj);

		ukladOkna.setBottom(panelPrzyciskow);
		ukladOkna.setTop(panelWyszukiwania);
		ukladOkna.setLeft(listaSkladnikow);
		ukladOkna.setCenter(opisPrzygotowania);
		
		dodajSkladnik.setOnAction(new ModyfikujSkladnik(skladniki, this));
		modyfikujSkladnik.setOnAction(new ModyfikujSkladnik(skladniki, this));
		usunSkladnik.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				if (listaSkladnikow.getSelectionModel().getSelectedIndex() < 0)
					return;
				else
				{
					skladniki.remove(listaSkladnikow.getSelectionModel().getSelectedIndex());
					uzupelnijListe();
				}
			}
		});
		
		anuluj.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				stage.close();
			}
		});
		
		akceptuj.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (OknoGlowne.czyModyfikacja)
				{
					Przepis przepis = przepisy.get(oknoGlowne.pobierzListePrzepisow().getSelectionModel().getSelectedIndex());
					przepis.setNazwa(nazwaPrzepisu.getText());
					przepis.setOpisPrzygotowania(opisPrzygotowania.getText());
					BazaDanych.modyfikujIstniejacyPrzepis(przepis);
				}
				else
				{
					Przepis przepis = new Przepis(nazwaPrzepisu.getText(), opisPrzygotowania.getText(), skladniki);
					przepisy.add(przepis);
					BazaDanych.dodajNowyPrzepis(przepis);
				}
				stage.close();
				oknoGlowne.uzupelnijListe();
			}
		});
	}

	public BorderPane pobierzOknoPrzepisu() {
		return ukladOkna;
	}
	
	public ListView<String> pobierzListeSklad() {
		return listaSkladnikow;
	}
	
	public void uzupelnijListe() {
		//wzorzec projektowy iterator
		Iterator<Skladniki> iterator = skladniki.iterator();
		ArrayList<String> nazwySklad = new ArrayList<String>();
		while (iterator.hasNext())
		{
			Skladniki skladnik = iterator.next();
			nazwySklad.add(skladnik.getNazwa());
		}
		ObservableList<String> sklad = FXCollections.observableArrayList(nazwySklad);
		listaSkladnikow.setItems(sklad);
	}
}