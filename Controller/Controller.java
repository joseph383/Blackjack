package controller;
import view.View;
import model.Dealer;
import model.Game;
import model.Player;
import model.Constants;

import java.util.Random;
import java.util.Scanner;

public class Controller {
	
	static Game game = new Game();
	
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
		
		game.dealCards(p1, dealer);
		
	if (!dealer.getHand().isBlackJack()) {
		
		do {
			
			System.out.println("Please Enter '0' to stand, '1' to hit, '2' to double, or '3' to split:");
			choice = sc.nextInt();
			
			if (choice == 1)	{
				
				p1.playerHit(game);
				
			}
			else if (choice == 0) {
				
				if (p1.playerStay(game)) {
					continue;
				}
				
				break;
			}
			else if (choice == 2) {
				
				if (p1.playerDouble(game)) {
					continue;
				}
				
				break;
				
			}
			else if (choice == 3) {
				
				p1.playerSplit(game);
				
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