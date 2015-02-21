package pl.fmi.przepisykulinarne;

import pl.fmi.przepisykulinarne.interfejsyuzytkownika.OknoGlowne;
import pl.fmi.przepisykulinarne.kontroler.BazaDanych;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Przepisy kulinarne");
		primaryStage.setScene(new Scene(new OknoGlowne().pobierzGlowneOkno(), 600, 800));
		primaryStage.show();
		
		
//		bd.czyBazaIstnieje();
//		bd.utworzBaze();
	}
}
