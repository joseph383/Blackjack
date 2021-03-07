package controller;
import view.View;
import model.Card;
import model.Dealer;
import model.Game;
import model.Player;
import model.Constants;

import java.util.Random;
import java.util.Scanner;

public class Controller {
	
	static Game game = new Game();
	
	public void dealCards(Player p1, Dealer dealer) {
		
		p1.getHand().addCardToHand(game.getPlayingCards()[game.getIndex()]);
		game.setIndex(game.getIndex()+1);
		p1.getHand().addCardToHand(game.getPlayingCards()[game.getIndex()]);
		game.setIndex(game.getIndex()+1);
		
		System.out.println("Your hand:");
		p1.getHand().printHandDetails();
		
		System.out.println("_________________________________________");

		dealer.getHand().addCardToHand(game.getPlayingCards()[game.getIndex()]);
		game.setIndex(game.getIndex()+1);
		
		System.out.println("Dealer hand:");
		dealer.getHand().printHandDetails();
		
		dealer.getHand().addCardToHand(game.getPlayingCards()[game.getIndex()]);
		game.setIndex(game.getIndex()+1);

	}
	
	public void retreiveBet(Player p1, Scanner sc) {
		
		int bet;
		
		do {
			
			bet = sc.nextInt();
			if (!p1.validBet(bet)) {
				System.out.println("Not a valid bet. Try again.");
			}
			else {
				p1.getHand().placeBet(bet);
				return;
			}
		} while (true);
		
	}
	
	public void handleSplitHand(Player p1, Dealer dealer) {
		
		p1.prevHand();
		
		System.out.println("First split hand:");
		p1.getHand().printHandDetails();
		game.determineWinner(p1, dealer);

		p1.advanceHand();
		
		System.out.println("Second split hand:");
		p1.getHand().printHandDetails();
		game.determineWinner(p1, dealer);
		game.setSplit(false);
		
	}
	
	public void playerHit(Player p1) {
		
		p1.getHand().addCardToHand(game.getPlayingCards()[game.getIndex()]);
		System.out.println("New card is: " + game.getPlayingCards()[game.getIndex()].toString());
		
		game.setIndex(game.getIndex()+1);
		p1.getHand().printHandDetails();
		
		if (p1.getHand().isBust() && p1.getHandIndex() == 1 && game.getSplit()) {
			p1.advanceHand();
			System.out.println("You Bust\nSecond split hand:");
			p1.getHand().printHandDetails();
		}
		
	}
	
	public boolean playerStay(Player p1) {
		
		p1.getHand().printHandDetails();
		
		if (game.getSplit() && p1.getHandIndex() == 1) {
			p1.advanceHand();
			System.out.println("Second split hand:");
			p1.getHand().printHandDetails();
			return true;
		}
		
		return false;
		
	}
	
	public boolean playerDouble(Player p1) {
		
		String err = game.doubleErrorMsg(p1);
		
		if(err.length() != 0) {
			System.out.println(err);
			return true;
		}
		
		p1.getHand().placeBet(p1.getHand().getBet() * 2);
	
		p1.getHand().addCardToHand(game.getPlayingCards()[game.getIndex()]);
		System.out.println("New card is: " + game.getPlayingCards()[game.getIndex()].toString());
		
		game.setIndex(game.getIndex()+1);
		
		p1.getHand().printHandDetails();
		
		if (game.getSplit() && p1.getHandIndex() == 1) {
			p1.advanceHand();
			System.out.println("Second split hand:");
			p1.getHand().printHandDetails();
			return true;
		}
		
		return false;
		
	}
	
	public void playerSplit(Player p1) {
		
		String err = game.splitErrorMsg(p1);
		
		if(err.length() != 0) {
			System.out.println(err);
			return;
		}
				
			int firstHandBet = p1.getHand().getBet();
			p1.advanceHand();
			p1.getHand().placeBet(firstHandBet);
			
			p1.advanceHand();
			p1.getHand().placeBet(firstHandBet);
			
			game.setSplit(true);
			
			p1.getHand().setSplit();
			p1.prevHand();
			p1.getHand().setSplit();
			p1.prevHand();
			
			Card firstTemp = p1.getHand().getCards().get(0);
			p1.advanceHand();
			p1.getHand().addCardToHand(firstTemp);
			p1.getHand().addCardToHand(game.getPlayingCards()[game.getIndex()]);
			game.setIndex(game.getIndex()+1);
			System.out.println("First split hand:");
			p1.getHand().printHandDetails();
			
			p1.prevHand();
			
			Card secondTemp = p1.getHand().getCards().get(1);
			p1.advanceHand();
			p1.advanceHand();
			
			p1.getHand().addCardToHand(secondTemp);
			p1.getHand().addCardToHand(game.getPlayingCards()[game.getIndex()]);
			game.setIndex(game.getIndex()+1);
			System.out.println("Second split hand:");
			p1.getHand().printHandDetails();
			p1.prevHand();
		
	}
	
	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		Controller ctl = new Controller();
		
		// Generate random boolean for dealer rules: true is stay and false is hit on soft 17
		boolean hitSoft17 = new Random().nextBoolean();
		
		game.setPlayingCards(game.shuffleCards(game.createCardArray(Constants.DEFAULT_NUM_DECKS), Constants.DEFAULT_NUM_DECKS));
		
		Dealer dealer = new Dealer("Dealer", hitSoft17);
		Player p1 = new Player("You");
		
		//deal cards
		while (p1.getMoneyTotal() > 0) {
		
			if (game.getIndex() > 260) {
				game.setPlayingCards(game.shuffleCards(game.getPlayingCards(), Constants.DEFAULT_NUM_DECKS));
				game.setIndex(0);
				System.out.println("RESHUFFLING");
			}
			
		View.startupGame();
		int choice;
		
		System.out.println(dealer.dealerRuleString());
		System.out.println("You have $" + p1.getMoneyTotal() + ". How much would you like to bet on this round?");
		
		ctl.retreiveBet(p1, sc);
		
		ctl.dealCards(p1, dealer);
		
	if (!dealer.getHand().isBlackJack()) {
		
		do {
			
			System.out.println("Please Enter '0' to stand, '1' to hit, '2' to double, or '3' to split:");
			choice = sc.nextInt();
			
			if (choice == 1)	{
				
				ctl.playerHit(p1);
				
			}
			else if (choice == 0) {
				
				if (ctl.playerStay(p1)) {
					continue;
				}
				
				break;
			}
			else if (choice == 2) {
				
				if (ctl.playerDouble(p1)) {
					continue;
				}
				
				break;
				
			}
			else if (choice == 3) {
				
				ctl.playerSplit(p1);
				
			}
			else {
				System.out.println(choice + " is not a valid choice. Please Enter '0' to stand, '1' to hit, '2' to double, or '3' to split: ");	
			}
		
		} while (!p1.getHand().isBust() && true);
		
		if (p1.getHand().isBust()) {
			System.out.println("You Bust\n");
		}

		dealer.dealerPlay(dealer, game.getPlayingCards(), game.getIndex());
	}
		
		if (game.getSplit()) {
			ctl.handleSplitHand(p1, dealer);
		}
		else {
			game.determineWinner(p1, dealer);
		}
		
		p1.getHand().resetHand();
		dealer.getHand().resetHand();
		p1.playerReset();
		System.out.println("Player wins: " + p1.getWins() + "\nPlayer loses: " + p1.getLoses());
		}
		
		System.out.println("Game over. You ran out of money.");
		
		sc.close();
	}

}
