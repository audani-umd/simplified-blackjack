package edu.umd.audani.blackjack.model;

import edu.umd.audani.blackjack.enums.Rank;
import edu.umd.audani.blackjack.enums.Suit;

/**
 * 
 * @author Akash Udani
 * This class represents a playing card. 
 * Each card object is uniquely identified by the combination of its rank and suit. 
 *
 */
public class Card {
	
	private Suit suit;
	private Rank rank;
	
	public Card(Suit suit,Rank rank) {
		this.suit=suit;
		this.rank=rank;
	}
	
	public Suit getSuit() {
		return suit;
	}
	
	public Rank getRank() {
		return rank;
	}

}
