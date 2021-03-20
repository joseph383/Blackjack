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
		moneyTotal = Constants.STARTING_BALANCE;
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
	
	public void playerHit(Game game) {
		
		getHand().addCardToHand(game.getPlayingCards()[game.getIndex()]);
		System.out.println("New card is: " + game.getPlayingCards()[game.getIndex()].toString());
		
		game.setIndex(game.getIndex()+1);
		getHand().printHandDetails();
		
		if (getHand().isBust() && getHandIndex() == 1 && game.getSplit()) {
			advanceHand();
			System.out.println("You Bust\nSecond split hand:");
			getHand().printHandDetails();
		}
		
	}
	
	public boolean playerStay(Game game) {
		
		getHand().printHandDetails();
		
		if (game.getSplit() && getHandIndex() == 1) {
			advanceHand();
			System.out.println("Second split hand:");
			getHand().printHandDetails();
			return true;
		}
		
		return false;
		
	}
	
	public boolean playerDouble(Game game) {
		
		String err = game.doubleErrorMsg(this);
		
		if(err.length() != 0) {
			System.out.println(err);
			return true;
		}
		
		getHand().placeBet(getHand().getBet() * 2);
	
		getHand().addCardToHand(game.getPlayingCards()[game.getIndex()]);
		System.out.println("New card is: " + game.getPlayingCards()[game.getIndex()].toString());
		
		game.setIndex(game.getIndex()+1);
		
		getHand().printHandDetails();
		
		if (game.getSplit() && getHandIndex() == 1) {
			advanceHand();
			System.out.println("Second split hand:");
			getHand().printHandDetails();
			return true;
		}
		
		return false;
		
	}
	
	public void playerSplit(Game game) {
		
		String err = game.splitErrorMsg(this);
		
		if(err.length() != 0) {
			System.out.println(err);
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
			getHand().addCardToHand(game.getPlayingCards()[game.getIndex()]);
			game.setIndex(game.getIndex()+1);
			System.out.println("First split hand:");
			getHand().printHandDetails();
			
			prevHand();
			
			Card secondTemp = getHand().getCards().get(1);
			advanceHand();
			advanceHand();
			
			getHand().addCardToHand(secondTemp);
			getHand().addCardToHand(game.getPlayingCards()[game.getIndex()]);
			game.setIndex(game.getIndex()+1);
			System.out.println("Second split hand:");
			getHand().printHandDetails();
			prevHand();
		
	}

}
