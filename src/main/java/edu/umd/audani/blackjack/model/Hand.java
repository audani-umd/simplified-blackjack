package edu.umd.audani.blackjack.model;

import java.util.List;

/**
 * 
 * @author Akash Udani
 * This is a model class that represents the player/dealer hand at different stages in the game
 *
 */
public class Hand {
	
	private int totalScore;
	private List<Card> currentHand;
	private boolean isHandSoft;
	
	public Hand() {
		
	}
	
	public Hand(int totalScore,boolean isHandSoft,List<Card> currentHand) {
		this.totalScore = totalScore;
		this.isHandSoft = isHandSoft;
		this.currentHand = currentHand;
	}
	
	/**
	 * @return the totalScore
	 */
	public int getTotalScore() {
		return totalScore;
	}
	/**
	 * @param totalScore the totalScore to set
	 */
	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}
	/**
	 * @return the currentHand
	 */
	public List<Card> getCurrentHand() {
		return currentHand;
	}
	/**
	 * @param currentHand the currentHand to set
	 */
	public void setCurrentHand(List<Card> currentHand) {
		this.currentHand = currentHand;
	}
	/**
	 * @return the isHandSoft
	 */
	public boolean isHandSoft() {
		return isHandSoft;
	}
	/**
	 * @param isHandSoft the isHandSoft to set
	 */
	public void setHandSoft(boolean isHandSoft) {
		this.isHandSoft = isHandSoft;
	}
	
}
