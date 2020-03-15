package edu.umd.audani.blackjack.service;

import edu.umd.audani.blackjack.model.Hand;

public interface BlackJackService {
	
	void initializeGame();
	
	void playerHits();
	
	void playerStands();
	
	boolean isBusted(Hand hand);
	
	String checkForResult();
	
	Hand getHand(String type);
	
	boolean checkIfBlackJack(Hand hand);

}
