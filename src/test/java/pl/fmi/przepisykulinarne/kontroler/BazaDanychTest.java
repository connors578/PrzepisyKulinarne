package pl.fmi.przepisykulinarne.kontroler;

import static org.junit.Assert.*;

import org.junit.Test;

public class BazaDanychTest {

	@Test
	public void polaczenieTest() {
		assertTrue(BazaDanych.polaczZBaza());
	}
	
	@Test
	public void bazaTest() {
		assertFalse(BazaDanych.czyBazaIstnieje());
	}
}
