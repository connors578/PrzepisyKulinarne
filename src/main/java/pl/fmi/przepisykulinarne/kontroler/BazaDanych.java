package pl.fmi.przepisykulinarne.kontroler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import pl.fmi.przepisykulinarne.model.Przepis;
import pl.fmi.przepisykulinarne.model.Skladniki;

public class BazaDanych {
	private static Connection polaczenie;

	public static boolean polaczZBaza() {
		try {
			Class.forName("org.sqlite.JDBC");
			polaczenie = DriverManager
					.getConnection("jdbc:sqlite:przepisykulinarne.db");
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static boolean czyBazaIstnieje() {
		if (polaczenie == null)
			return false;
		ResultSet resultSet = null;
		try {
			resultSet = polaczenie.getMetaData().getCatalogs();
			while (resultSet.next()) {
				String databaseName = resultSet.getString(1);
				if ("przepisykulinarne".equals(databaseName)) {
					return true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public static void utworzBaze() {
		Statement zapytanie = null;
		try {
			zapytanie = polaczenie.createStatement();
			zapytanie
					.executeUpdate("CREATE TABLE PRZEPISY ( ID INTEGER PRIMARY KEY AUTOINCREMENT, NAZWA TEXT, OPIS TEXT)");
			zapytanie
					.executeUpdate("CREATE TABLE SKLADNIKI ( ID INTEGER PRIMARY KEY AUTOINCREMENT, NAZWA TEXT , ID_PRZEPISU INTEGER, FOREIGN KEY(ID_PRZEPISU) REFERENCES PRZEPISY(ID))");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("BAZA JUZ ISTNIEJE");
		} finally {
			try {
				zapytanie.close();
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Blad przy zamykaniu polaczenia");
			}
		}
	}

	public static ArrayList<Przepis> pobierzWszystkieRekordyZBazy(
			ArrayList<Przepis> listaPrzepisow) {
		Statement zapytanie = null;
		try {
			zapytanie = polaczenie.createStatement();
			ResultSet executeQuery = zapytanie.executeQuery("SELECT * FROM PRZEPISY");
			if (listaPrzepisow.size() > 0)
				listaPrzepisow.clear();
			while (executeQuery.next()) {
				Przepis nowyPrzepis = new Przepis();
				nowyPrzepis.setNazwa(executeQuery.getString("NAZWA"));
				nowyPrzepis.setOpisPrzygotowania(executeQuery.getString("OPIS"));
				nowyPrzepis.setSkladniki(pobierzListeSkladnikow(executeQuery
						.getInt("ID")));
				listaPrzepisow.add(nowyPrzepis);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				zapytanie.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return listaPrzepisow;
	}

	public static ArrayList<Skladniki> pobierzListeSkladnikow(Integer id) {
		if (id == null || id <= 0)
			return null;
		Statement zapytanieDlaSklad = null;
		ArrayList<Skladniki> listaSkladnikow = new ArrayList<Skladniki>();
		try {
			zapytanieDlaSklad = polaczenie.createStatement();
			ResultSet executeQuery = zapytanieDlaSklad
					.executeQuery("SELECT * FROM SKLADNIKI WHERE ID_PRZEPISU = " + id);
			while (executeQuery.next()) {
				Skladniki nowySkladnik = new Skladniki();
				nowySkladnik.setNazwa(executeQuery.getString("NAZWA"));
				listaSkladnikow.add(nowySkladnik);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				zapytanieDlaSklad.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return listaSkladnikow;
	}

	public static boolean dodajNowyPrzepis(Przepis przepis) {
		Statement zapytanie = null;

		try {
			zapytanie = polaczenie.createStatement();
			zapytanie.executeUpdate("INSERT INTO PRZEPISY (NAZWA, OPIS) VALUES ('"
					+ przepis.getNazwa() + "','" + przepis.getOpisPrzygotowania() + "')");
			for (Skladniki skl : przepis.getSkladniki())
				zapytanie
						.execute("insert into skladniki (nazwa, id_przepisu) values ('"
								+ skl.getNazwa() + "',(select id from przepisy where nazwa = '"
								+ przepis.getNazwa() + "'))");
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				zapytanie.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	public static boolean modyfikujIstniejacyPrzepis(Przepis przepis) {
		Statement zapytanie = null;

		try {
			zapytanie = polaczenie.createStatement();
			ResultSet executeQuery = zapytanie
					.executeQuery("SELECT ID FROM PRZEPISY WHERE NAZWA = '"
							+ przepis.getNazwa() + "'");
			int idPrzepisu = 0;
			if (executeQuery.next())
				idPrzepisu = executeQuery.getInt("ID");
			zapytanie.executeUpdate("UPDATE PRZEPISY SET NAZWA = '"
					+ przepis.getNazwa() + "', OPIS = '" + przepis.getOpisPrzygotowania()
					+ "' WHERE ID = " + idPrzepisu);
			zapytanie.execute("DELETE FROM SKLADNIKI WHERE ID_PRZEPISU = "
					+ idPrzepisu);
			for (Skladniki skl : przepis.getSkladniki())
				zapytanie
						.execute("insert into skladniki (nazwa, id_przepisu) values ('"
								+ skl.getNazwa() + "',(select id from przepisy where nazwa = '"
								+ przepis.getNazwa() + "'))");
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				zapytanie.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	public static String wyszukaj(ZapytanieWyszukiwania zapytanieZWarunkiem,
			String warunek) {
		Statement zapytanie = null;

		StringBuilder wynik = new StringBuilder();
		try {
			zapytanie = polaczenie.createStatement();
			ResultSet executeQuery = zapytanie.executeQuery(zapytanieZWarunkiem.pobierzZapytanieKomunikatu(warunek));
			if (executeQuery.next())
			{
				wynik.append(executeQuery.getString("NAZWA")).append(",");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return "";
		} finally {
			try {
				zapytanie.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return wynik.toString();
	}

}
