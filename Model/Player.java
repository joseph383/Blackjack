package model;

public class Player extends CardPlayer {
	
	private int moneyTotal;
	private Hand hand;
	private int wins;
	private int loses;
	
	public Player(String name) {
		super(name);
		wins = loses = 0;
		moneyTotal = Constants.STARTING_BALANCE;
		hand = new Hand();
	}
	
	public Hand getHand() {
		return hand;
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
		
		Card nextCard = game.dealNextCard();
		getHand().addCardToHand(nextCard);
		output.displayMessage("New card is: " + nextCard);
		
		getHand().printHandDetails(output);
		
	}
	
	public void playerStay(Game game, SystemOutput output) {
		
		getHand().printHandDetails(output);
		
	}
	
}
