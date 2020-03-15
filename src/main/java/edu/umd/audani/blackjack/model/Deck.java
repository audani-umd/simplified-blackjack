package edu.umd.audani.blackjack.model;

import java.util.ArrayList;
import java.util.List;

import edu.umd.audani.blackjack.enums.Rank;
import edu.umd.audani.blackjack.enums.Suit;

/**
 * 
 * @author Akash Udani
 * This class represents a standard deck of playing cards (52 cards)
 *
 */
public class Deck {
	
	private List<Card> deckOfCards = new ArrayList<Card>();
	
	public Deck() {
		
		for(Suit suit:Suit.values()) {
			for(Rank rank:Rank.values()) {
				Card card = new Card(suit,rank);
				deckOfCards.add(card);
			}
		}
		
	}
	
	public List<Card> getDeckOfCards() {
		return deckOfCards;
	}

}
