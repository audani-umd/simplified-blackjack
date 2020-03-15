package edu.umd.audani.blackjack.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.umd.audani.blackjack.enums.Rank;
import edu.umd.audani.blackjack.enums.Suit;
import edu.umd.audani.blackjack.model.Card;
import edu.umd.audani.blackjack.model.DealerHand;
import edu.umd.audani.blackjack.model.Deck;
import edu.umd.audani.blackjack.model.Hand;
import edu.umd.audani.blackjack.model.PlayerHand;

/**
 * 
 * @author Akash Udani
 * This is a test class for BlackJackServiceImpl
 *
 */
public class BlackJackServiceImplTest {

	private static BlackJackServiceImpl service;
	private static Deck deck;
	
	@BeforeClass
	public static void initializeServiceInstance() {
		service = new BlackJackServiceImpl();
		deck = new Deck();
	}
	
	@AfterClass
	public static void cleanUp() {
		service=null;
		deck=null;
	}
	
	@Test
	public void testInitializeGame() {
		service.initializeGame();
		Hand playerHand = service.getHand("player");
		Hand dealerHand = service.getHand("dealer");
		
		assertNotNull(playerHand.getCurrentHand().get(0));
		assertNotNull(playerHand.getCurrentHand().get(1));
		
		assertNotNull(dealerHand.getCurrentHand().get(0));
		assertNotNull(dealerHand.getCurrentHand().get(1));
		
		assertTrue(dealerHand.getCurrentHand().size()==2);
		assertTrue(playerHand.getCurrentHand().size()==2);
		
		assertTrue(dealerHand.getTotalScore()>0);
		assertTrue(playerHand.getTotalScore()>0);
	}

	@Test
	public void testPlayerHits() {
		Stack<Card> stk= new Stack<Card>();
		service.shuffle(deck.getDeckOfCards(),stk);
		service.setFinalPlayingDeck(stk);
		
		Card firstCard = new Card(Suit.CLUBS,Rank.FOUR);
		Card secondCard = new Card(Suit.HEARTS,Rank.TWO);
		Card thirdCard = new Card(Suit.SPADES,Rank.EIGHT);
		Card fourthCard = new Card(Suit.DIAMONDS,Rank.ACE);
		
		List<Card> currentHand = new ArrayList<Card>();
		currentHand.add(firstCard);
		currentHand.add(secondCard);
		currentHand.add(thirdCard);
		currentHand.add(fourthCard);
		
		Hand playerHand = new PlayerHand();
		playerHand.setCurrentHand(currentHand);
		playerHand.setTotalScore(15);
		service.setPlayerHand(playerHand);
		playerHand=service.getPlayerHand();
		
		service.playerHits();
		
		assertEquals(5,playerHand.getCurrentHand().size());
		// This condition can pass or fail depending on the random card generated
		assertTrue(service.isBusted(playerHand) || service.checkIfBlackJack(playerHand));
	}


	@Test
	public void testIsBusted() {
		
		Hand hand = new Hand();
		hand.setTotalScore(26);
		
		assertEquals(true,service.isBusted(hand));
	}

	@Test
	public void testCheckForResult() {
		Hand playerHand = new PlayerHand();
		playerHand.setTotalScore(20);
		
		Hand dealerHand = new DealerHand();
		dealerHand.setTotalScore(18);
		
		service.setDealerHand(dealerHand);
		service.setPlayerHand(playerHand);
		
		String actualResult = service.checkForResult();
		assertEquals("Player wins !!!",actualResult);
	}

	@Test
	public void testCheckIfBlackJack() {
		Hand hand = new Hand();
		hand.setTotalScore(21);
		assertTrue(service.checkIfBlackJack(hand));
	}

}
