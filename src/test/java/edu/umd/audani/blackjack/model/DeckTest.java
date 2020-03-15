package edu.umd.audani.blackjack.model;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * 
 * @author Akash Udani
 * This class is a test class for Deck	
 */

public class DeckTest {
	private static Deck deck;
	
	@BeforeClass
	public static void initializeDeck() {
		deck = new Deck();
	}
	
	@AfterClass
	public static void cleanUp() {
		deck=null;
	}
	
	@Test
	public void testDeckSize() {
		assertTrue("Deck size should be 52", deck.getDeckOfCards().size()==52);
	}
	
	@Test
	public void testDeckCard() {
		assertTrue("Deck elements should be of type enum Card",deck.getDeckOfCards().get(1) instanceof Card);	
	}

}
