package pl.fmi.przepisykulinarne.interfejsyuzytkownika;

import java.util.ArrayList;
import java.util.Iterator;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import pl.fmi.przepisykulinarne.Komunikaty;
import pl.fmi.przepisykulinarne.akcjeprzyciskow.PrzyciskDodaj;
import pl.fmi.przepisykulinarne.kontroler.BazaDanych;
import pl.fmi.przepisykulinarne.kontroler.ZapytaniePoNazwiePrzepisu;
import pl.fmi.przepisykulinarne.kontroler.ZapytaniePoOpisiePrzy;
import pl.fmi.przepisykulinarne.kontroler.ZapytaniePoSkladnikach;
import pl.fmi.przepisykulinarne.kontroler.ZapytanieWyszukiwania;
import pl.fmi.przepisykulinarne.model.Przepis;

public class OknoGlowne {
	BorderPane ukladOkna = new BorderPane();
	FlowPane panelPrzyciskow = new FlowPane();
	FlowPane panelWyszukiwania = new FlowPane();
	
	Button dodaj = new Button("Dodaj");
	Button modyfikuj = new Button("Modyfikuj");
	Button usun = new Button("Usuń");
	Label etykietaSzukaj = new Label("Szukaj: ");
	TextField szukaj = new TextField();
	ListView<String> listaPrzepisow = new ListView<String>();
	TextArea opisPrzygotowania = new TextArea();
	ArrayList<Przepis> przepisy = new ArrayList<Przepis>();
	Button przyciskSzukaj = new Button("Szukaj");
	public static boolean czyModyfikacja = false;
	
	public OknoGlowne() {
		opisPrzygotowania.setEditable(false);
		szukaj.setPrefWidth(450);
		listaPrzepisow.setPrefWidth(200);
		
		panelPrzyciskow.getChildren().addAll(dodaj,modyfikuj,usun);
		panelWyszukiwania.getChildren().addAll(etykietaSzukaj, szukaj,przyciskSzukaj);
		
		ukladOkna.setBottom(panelPrzyciskow);
		ukladOkna.setTop(panelWyszukiwania);
		ukladOkna.setLeft(listaPrzepisow);
		ukladOkna.setCenter(opisPrzygotowania);
		dodaj.setOnAction(new PrzyciskDodaj(przepisy,this));
		modyfikuj.setOnAction(new PrzyciskDodaj(przepisy,this));
		BazaDanych.polaczZBaza();
		BazaDanych.pobierzWszystkieRekordyZBazy(przepisy);
		uzupelnijListe();
		listaPrzepisow.getSelectionModel().selectedItemProperty().addListener(
        new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> ov, 
                String old_val, String new_val) {
            	if (listaPrzepisow.getSelectionModel().getSelectedIndex() < 0)
            		opisPrzygotowania.setText("");
            	else
            		opisPrzygotowania.setText(przepisy.get(listaPrzepisow.getSelectionModel().getSelectedIndex()).getOpisPrzygotowania());
        }
    });
		
		usun.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				if (listaPrzepisow.getSelectionModel().getSelectedIndex() < 0)
					return;
				else
				{
					przepisy.remove(listaPrzepisow.getSelectionModel().getSelectedIndex());
					uzupelnijListe();
				}
			}
		});
		
		przyciskSzukaj.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				//TODO zaimplementowana strategia
				ArrayList<ZapytanieWyszukiwania> zapytania = new ArrayList<ZapytanieWyszukiwania>();
				zapytania.add(new ZapytaniePoNazwiePrzepisu());
				zapytania.add(new ZapytaniePoOpisiePrzy());
				zapytania.add(new ZapytaniePoSkladnikach());
				StringBuilder wyniki = new StringBuilder();
				for (ZapytanieWyszukiwania zap : zapytania)
				{
					wyniki.append(BazaDanych.wyszukaj(zap, szukaj.getText()));
				}
				Komunikaty.pokazInformacje(wyniki.toString());
			}
		});
	}
	
	public BorderPane pobierzGlowneOkno() {
		return ukladOkna;
	}
	
	public ListView<String> pobierzListePrzepisow() {
		return listaPrzepisow;
	}
	
	public void uzupelnijListe() {
		//wzorzec projektowy iterator
		Iterator<Przepis> iterator = przepisy.iterator();
		ArrayList<String> nazwySklad = new ArrayList<String>();
		while (iterator.hasNext())
		{
			Przepis skladnik = iterator.next();
			nazwySklad.add(skladnik.getNazwa());
		}
		//wzorzec observator
		ObservableList<String> sklad = FXCollections.observableArrayList(nazwySklad);
		listaPrzepisow.setItems(sklad);
	}
}
