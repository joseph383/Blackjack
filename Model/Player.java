package model;

public class Player extends CardPlayer {
	
	private int moneyTotal;
	private Hand [] hand;
	private int wins;
	private int loses;
	private int handIndex;
	
	public Player(String name) {
		super(name);
		wins = loses = handIndex = 0;
		moneyTotal = 100;
		hand = new Hand[3];
		hand[0] = new Hand();
		hand[1] = new Hand();
		hand[2] = new Hand();
	}
	
	public Hand getHand() {
		return hand[handIndex];
	}
	
	public void playerReset() {
		hand[0].resetHand();
		hand[1].resetHand();
		hand[2].resetHand();
		handIndex = 0;
	}
	
	public void advanceHand() {
		handIndex++;
	}
	
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

}
