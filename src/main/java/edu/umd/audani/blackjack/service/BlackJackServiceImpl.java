package edu.umd.audani.blackjack.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import edu.umd.audani.blackjack.enums.Rank;
import edu.umd.audani.blackjack.model.Card;
import edu.umd.audani.blackjack.model.DealerHand;
import edu.umd.audani.blackjack.model.Deck;
import edu.umd.audani.blackjack.model.Hand;
import edu.umd.audani.blackjack.model.PlayerHand;

/**
 * 
 * @author Akash Udani
 * This class encapsulates the business logic for the game play.
 *
 */
@Service
public class BlackJackServiceImpl implements BlackJackService {

	private static final Logger logger = Logger.getLogger(BlackJackServiceImpl.class);
	private static final int noOfDecks = 4;
	private Stack<Card> finalPlayingDeck;
	
	private Hand playerHand;
	private Hand dealerHand;
	
	/**
	 * @return the playerHand
	 */
	public Hand getPlayerHand() {
		return playerHand;
	}

	/**
	 * @param playerHand the playerHand to set
	 */
	public void setPlayerHand(Hand playerHand) {
		this.playerHand = playerHand;
	}

	/**
	 * @return the dealerHand
	 */
	public Hand getDealerHand() {
		return dealerHand;
	}

	/**
	 * @param dealerHand the dealerHand to set
	 */
	public void setDealerHand(Hand dealerHand) {
		this.dealerHand = dealerHand;
	}

	/**
	 * @return the finalPlayingDeck
	 */
	public Stack<Card> getFinalPlayingDeck() {
		return finalPlayingDeck;
	}

	/**
	 * @param finalPlayingDeck the finalPlayingDeck to set
	 */
	public void setFinalPlayingDeck(Stack<Card> finalPlayingDeck) {
		this.finalPlayingDeck = finalPlayingDeck;
	}

	/**
	 * This method sets the initial state for the game. i.e. it initializes the player's and dealer's hand 
	 * after shuffling the cards 
	 */
	@Override
	public void initializeGame() {
		
		
		logger.info("Setting up the table!");
		
		List<Card> cardsToShuffle = new ArrayList<Card>();
		
		for(int deckNo=0;deckNo<noOfDecks;deckNo++) {
			Deck deck = new Deck();
			cardsToShuffle.addAll(deck.getDeckOfCards());
		}
		
		finalPlayingDeck = new Stack<Card>();
		shuffle(cardsToShuffle,finalPlayingDeck);
		
		dealInitialCards();
		
	}
	
	/*
	 * Shuffles the list of cards by generating a random number within the range
	 */
	public void shuffle(List<Card> unshuffledDeck,Stack<Card> deck) {
		 
		logger.info("Shuffling the decks");
		int cardCount = unshuffledDeck.size();
		
		for(int i=0;i<cardCount;i++) {
			int randomCardNumber = (int)(Math.random()*unshuffledDeck.size());
			deck.push(unshuffledDeck.get(randomCardNumber));
			unshuffledDeck.remove(randomCardNumber);
		}		
	}
	
	/*
	 * Creates new objects for player and dealer hands and calls methods to populate them
	 */
	private void dealInitialCards() {
		
		logger.info("Dealing the initial hand for the dealer and the player");
		playerHand = new PlayerHand();
		dealerHand = new DealerHand();
		
		populatePlayerHand();
		populateDealerHand();
	}
	
	/*
	 * Populates the initial state for the Player's hand i.e. sets the first 2 cards and updates the player score
	 */
	private void populatePlayerHand() {
		
		List<Card> playerCards = new ArrayList<Card>();
		int playerScore = 0;
		
		Card firstCard = finalPlayingDeck.pop();
		playerCards.add(firstCard);
		
		if(checkAceCard(firstCard)) {
			this.playerHand.setHandSoft(true);
		}
		
		playerScore+=firstCard.getRank().getValue();
		
		Card secondCard = finalPlayingDeck.pop();
		playerCards.add(secondCard);
		
		// Checking for Ace values and updating the player score as per the rules
		if(checkAceCard(secondCard)) {
			
			if(checkAceCard(firstCard)) {
				playerScore-=10;
			}
			this.playerHand.setHandSoft(true);
		}
	
		playerScore+=secondCard.getRank().getValue();
	
		
		logger.debug("Initial Player Hand\n"+firstCard.getRank()+" of "+firstCard.getSuit()+"\n"+secondCard.getRank()+" of "+secondCard.getSuit());
		
		this.playerHand.setCurrentHand(playerCards);
		this.playerHand.setTotalScore(playerScore);
	}
	
	/*
	 * Populates the initial state for the Dealer's hand i.e. sets the first 2 cards and updates the dealer score 
	*/
	private void populateDealerHand() {
		
		List<Card> dealerCards = new ArrayList<Card>();
		int dealerScore=0;
		
		Card firstCard = finalPlayingDeck.pop();
		dealerCards.add(firstCard);
		
		if(checkAceCard(firstCard)) {
			this.dealerHand.setHandSoft(true);
		}
		
		dealerScore+=firstCard.getRank().getValue();
		
		// Since the second hand is face down, the second card score is not added to the total
		Card secondCard = finalPlayingDeck.pop();
		dealerCards.add(secondCard);
		
		logger.debug("Initial Dealer Hand\n"+firstCard.getRank()+" of "+firstCard.getSuit()+"\n"+secondCard.getRank()+" of "+secondCard.getSuit());
		
		this.dealerHand.setCurrentHand(dealerCards);
		this.dealerHand.setTotalScore(dealerScore);
		
	}

	/**
	 * Populates player's hand every time the player clicks on the Hit button
	 */
	@Override
	public void playerHits() {
		logger.info("Player hits");
		populateHand(this.playerHand); 	
	}
	
	/**
	 * Populates dealer's hand and calculates the dealer's final score
	 */
	@Override
	public void playerStands() {
		
		logger.info("Player stands. Dealer hits till soft seventeen");
		// We have not added the value of the face down card to the dealer's total score till now
		Card faceDownCard = this.dealerHand.getCurrentHand().get(1);
		
		if(checkAceCard(faceDownCard)) {
			
			if(this.dealerHand.isHandSoft()) {
				this.dealerHand.setTotalScore(this.dealerHand.getTotalScore()-10);
			}
			this.dealerHand.setHandSoft(true);
		} 
		
		this.dealerHand.setTotalScore(this.dealerHand.getTotalScore()+faceDownCard.getRank().getValue());
		
		while(!checkIfDealerHitsSoftSeventeen()) {
			populateHand(this.dealerHand);
		}
	}

	/*
	 * Checks if the hand is busted
	 */
	@Override
	public boolean isBusted(Hand hand) {
		
		if(hand.getTotalScore()>21) {
			return true;
		}
		
		return false;
	}
	
	/*
	 * Populates player/dealer hand based on the passed Hand object.
	 * Checks if it is a soft hand and updates the dealer/player scores accordingly.
	 */
	private void populateHand(Hand hand) {
		
		Card card = finalPlayingDeck.pop();
		int cardValue = card.getRank().getValue();
		hand.getCurrentHand().add(card);
		
		/**
		 * If the current card is an Ace, 
		 */
		if(checkAceCard(card)) {
			
			if(hand.isHandSoft()) {
				hand.setTotalScore(hand.getTotalScore()-10);
			} 
			hand.setTotalScore(hand.getTotalScore()+cardValue);
			hand.setHandSoft(true);
			
			if(isBusted(hand)) {
				hand.setTotalScore(hand.getTotalScore()-10);
				hand.setHandSoft(false);
			}
			
		} else {
			
			/*
			 * Since the current card is not an Ace, we update the total score.
			 * After updating, we need to check if it is a soft hand and the player/dealer busts. 
			 * If that is the case we set the existing Ace value to 1 instead of 11.
			 */
			
			hand.setTotalScore(hand.getTotalScore()+cardValue);
			
			if(hand.isHandSoft() && isBusted(hand)) {
				hand.setTotalScore(hand.getTotalScore()-10);
				hand.setHandSoft(false);
			}
		}
	}
	
	/**
	 * Returns the result message based on comparison between the dealer's and player's final scores 
	 */
	@Override
	public String checkForResult() {
		
		logger.info("Fetching the result");
		String resultMessage = null;
		
		if(isBusted(this.dealerHand)) {
			resultMessage = "Dealer busted. Player wins !!!";
		} else if(checkIfBlackJack(this.dealerHand)) {
			resultMessage = "Black Jack. Dealer wins !!!";
		} else if (this.dealerHand.getTotalScore() > this.playerHand.getTotalScore()) {
			resultMessage = "Dealer wins !!!";
		} else if (this.dealerHand.getTotalScore() < this.playerHand.getTotalScore()) {
			resultMessage = "Player wins !!!";
		} else {
			resultMessage = "Game tied !!!";
		}
			
		return resultMessage;
	}
	
	/**
	 * Checks if the hand's total score equals the black jack condition
	 */
	@Override
	public boolean checkIfBlackJack(Hand hand) {
		if(hand.getTotalScore()==21) {
			return true;
		}
		return false;
	}
	
	/**
	 * Checks if the passed card is an ACE
	 * @param card
	 * @return boolean value
	 */
	private boolean checkAceCard(Card card) {
		return card.getRank() == Rank.ACE;
	}
	
	/**
	 * Checks for Soft Seventeen condition
	 * @return boolean value
	 */
	private boolean checkIfDealerHitsSoftSeventeen() {
		return this.dealerHand.getTotalScore()>=17;		
	}

	/**
	 * Returns the appropriate subclass object based on type
	 */
	@Override
	public Hand getHand(String type) {
		if(type.equals("player"))
			return this.playerHand;
		else if (type.equals("dealer"))
			return this.dealerHand;
		
		return null;
	}

}
