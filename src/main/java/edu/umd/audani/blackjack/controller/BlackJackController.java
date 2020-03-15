package edu.umd.audani.blackjack.controller;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import edu.umd.audani.blackjack.model.Hand;
import edu.umd.audani.blackjack.service.BlackJackService;
/**
 * 
 * @author Akash Udani
 *	Controller class for handling all incoming requests
 */

@Controller
public class BlackJackController {
	
	private static final Logger logger = Logger.getLogger(BlackJackController.class);
	@Autowired
	BlackJackService blackJackService;
	
	/**
	 * 
	 * @param session
	 * @return String representing the view name
	 */
	@GetMapping(value={"/","/home"})
	public String getHomePage(HttpSession session) {
		
		logger.debug("Inside controller method for fetching homepage");
		if(session.getAttribute("game")!=null) {
			session.removeAttribute("game");
		}
		return "BlackJackHome";
	}
	
	/**
	 * 
	 * @param session
	 * @return ModelAndView object representing the model data bound to the view
	 */
	@GetMapping("/newgame")
	public ModelAndView startNewGame(HttpSession session) {
		
		logger.debug("Inside Controller method for new game");
		if(session.getAttribute("game")==null) {
			session.setAttribute("game", "gameinSession");
		}
		ModelAndView mv = new ModelAndView("NewGame");
				
		blackJackService.initializeGame();
		
		Hand dealerHand = blackJackService.getHand("dealer");
		Hand playerHand = blackJackService.getHand("player");
		String result = null;
		
		/*
		 * Checking if the player can win based on his/her initial hand
		 */
		if(blackJackService.checkIfBlackJack(playerHand)) {
			result = "Black Jack!!! Player wins";
		}
		
		mv.addObject("dealerHand",dealerHand);
		mv.addObject("playerHand",playerHand);
		mv.addObject("dealerInitialState",true);
		mv.addObject("result", result);
		
		return mv;
	}
	
	/**
	 * 
	 * @param session
	 * @return ModelAndView object representing the model data bound to the view
	 */
	@RequestMapping(value={"/gameinprogress/hit"})
	public ModelAndView playerHits(HttpSession session) {
		
		ModelAndView mv = new ModelAndView();
		
		if(session.getAttribute("game")!=null) {
			
			logger.debug("Inside controller method for handling Hit action");
			mv.setViewName("NewGame");
			blackJackService.playerHits();
			Hand playerHand = blackJackService.getHand("player");
			Hand dealerHand = blackJackService.getHand("dealer");
			boolean isPlayerBusted = blackJackService.isBusted(playerHand);
			
			String result = null;
			
			// After each hit, we check if the player bust or hits black jack
			if(blackJackService.checkIfBlackJack(playerHand)) {
				result = "Black Jack!!! Player wins";
			}
			
			if(isPlayerBusted) {
				result = "Player Busted. Dealer wins !!!";
			}
			
			mv.addObject("playerHand",playerHand);
			mv.addObject("dealerHand", dealerHand);
			mv.addObject("dealerInitialState",true);
			mv.addObject("result",result);
		} else {
			mv.setViewName("redirect:/home");;
		}
		return mv;
	}
	
	/**
	 * 
	 * @param session
	 * @return ModelAndView object representing the model data bound to the view
	 */
	@RequestMapping(value={"/gameinprogress/stand"})
	public ModelAndView playerStands(HttpSession session) {
		ModelAndView mv = new ModelAndView();
		if(session.getAttribute("game")!=null) {
			
			logger.debug("Inside controller method for handling Stand action");
			mv.setViewName("NewGame");
			
			blackJackService.playerStands();
			
			Hand dealerHand = blackJackService.getHand("dealer");
			Hand playerHand = blackJackService.getHand("player");
			
			// Checking for result after the dealer hits soft seventeen
			String result = blackJackService.checkForResult();
			
			mv.addObject("dealerHand",dealerHand);
			mv.addObject("playerHand", playerHand);
			mv.addObject("result",result);
		} else {
			mv.setViewName("redirect:/home");
		}
		return mv;
	}

	/**
	 * @return the blackJackService
	 */
	public BlackJackService getBlackJackService() {
		return blackJackService;
	}

	/**
	 * @param blackJackService the blackJackService to set
	 */
	public void setBlackJackService(BlackJackService blackJackService) {
		this.blackJackService = blackJackService;
	}
	
}
