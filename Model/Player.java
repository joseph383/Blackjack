package model;

import java.util.ArrayList;

public class Player extends CardPlayer {
	
	private int moneyTotal;
	private ArrayList<Hand> hand;
	private int wins;
	private int loses;
	private int handIndex;
	
	public Player(String name) {
		super(name);
		wins = loses = handIndex = 0;
		moneyTotal = Constants.STARTING_BALANCE;
		hand = new ArrayList<Hand>();
		hand.add(new Hand());
	}
	
	public Hand getHand() {
		return hand.get(handIndex);
	}
	
	public void playerReset() {
		for (int i = 0; i < hand.size(); i++) {
			hand.get(0).resetHand();
		}
		handIndex = 0;
	}
	
	/*
	Move to next player hand in the event of split
	*/
	public void advanceHand() {
		handIndex++;
	}
	
	/*
	Move to previous player hand in the event of split
	*/
	public void prevHand() {
		handIndex--;
	}
	
	public int getHandIndex() {
		return handIndex;
	}
	
	public int getWins() {
		return wins;
	}
	
	public int getLoses() {
		return loses;
	}
	
	public int getMoneyTotal() {
		return moneyTotal;
	}
	
	public boolean validBet (int amount) {
		return (moneyTotal >= amount && amount > 0);
	}
	
	public void winGame(boolean blackjack, int bet) {
		if (blackjack) {
			moneyTotal += (bet / 2);
		}
		moneyTotal += bet;
		wins++;
	}
	
	public void loseGame(int bet) {
		moneyTotal -= bet;
		loses++;
	}
	
	public void playerHit(Game game, SystemOutput output) {
		
		getHand().addCardToHand(game.getPlayingCards()[game.getCardIndex()]);
		output.displayMessage("New card is: " + game.getPlayingCards()[game.getCardIndex()].toString());
		
		game.setCardIndex(game.getCardIndex()+1);
		getHand().printHandDetails(output);
		
		if (getHand().isBust() && getHandIndex() == 1 && game.getSplit()) {
			advanceHand();
			output.displayMessage("You Bust\nSecond split hand:");
			getHand().printHandDetails(output);
		}
		
	}
	
	public void playerStay(Game game, SystemOutput output) {
		
		getHand().printHandDetails(output);
		
		/*if (game.getSplit() && getHandIndex() == 1) {
			advanceHand();
			output.displayMessage("Second split hand:");
			getHand().printHandDetails(output);
			return true;
		}
		
		return false;*/
		
	}
	
	public boolean playerDouble(Game game, SystemOutput output) {
		
		String err = game.doubleErrorMsg(this);
		
		if(err.length() != 0) {
			output.displayMessage(err);
			return true;
		}
		
		getHand().placeBet(getHand().getBet() * 2);
	
		getHand().addCardToHand(game.getPlayingCards()[game.getCardIndex()]);
		output.displayMessage("New card is: " + game.getPlayingCards()[game.getCardIndex()].toString());
		
		game.setCardIndex(game.getCardIndex()+1);
		
		getHand().printHandDetails(output);
		
		if (game.getSplit() && getHandIndex() == 1) {
			advanceHand();
			output.displayMessage("Second split hand:");
			getHand().printHandDetails(output);
			return true;
		}
		
		return false;
		
	}
	
	public void playerSplit(Game game, SystemOutput output) {
		
		String err = game.splitErrorMsg(this);
		
		if(err.length() != 0) {
			output.displayMessage(err);
			return;
		}
				
			int firstHandBet = getHand().getBet();
			advanceHand();
			getHand().placeBet(firstHandBet);
			
			advanceHand();
			getHand().placeBet(firstHandBet);
			
			game.setSplit(true);
			
			getHand().setSplit();
			prevHand();
			getHand().setSplit();
			prevHand();
			
			Card firstTemp = getHand().getCards().get(0);
			advanceHand();
			getHand().addCardToHand(firstTemp);
			getHand().addCardToHand(game.getPlayingCards()[game.getCardIndex()]);
			game.setCardIndex(game.getCardIndex()+1);
			output.displayMessage("First split hand:");
			getHand().printHandDetails(output);
			
			prevHand();
			
			Card secondTemp = getHand().getCards().get(1);
			advanceHand();
			advanceHand();
			
			getHand().addCardToHand(secondTemp);
			getHand().addCardToHand(game.getPlayingCards()[game.getCardIndex()]);
			game.setCardIndex(game.getCardIndex()+1);
			output.displayMessage("Second split hand:");
			getHand().printHandDetails(output);
			prevHand();
		
	}

}
