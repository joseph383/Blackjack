package controller;
import view.View;
import model.Dealer;
import model.Game;
import model.Player;
import model.Constants;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Controller {
	
	static Game game = new Game();
	
	public static int retreiveBet(Scanner sc) {
		
		int bet = 0;
		
		try {
			bet = sc.nextInt();
			return bet;
		} catch (InputMismatchException ex) {
			System.out.println("Not a valid bet. Expected an integer value. Try again.");
			System.out.println("How much would you like to bet on this round?");
			sc.next();
			return -1;
		}
		
	}
	
	// Driver method for blackjack should be in game class
	// controller can just take input from user
	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		
		game.setPlayingCards(game.shuffleCards(game.createCardArray(Constants.DEFAULT_NUM_DECKS), Constants.DEFAULT_NUM_DECKS));
		
		Dealer dealer = new Dealer("Dealer");
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
		
		System.out.println("Dealer " + new String(dealer.getDealerRule() + " ").replace("_", " "));
		System.out.println("You have $" + p1.getMoneyTotal() + ". How much would you like to bet on this round?");
		
		int bet = 0;
		boolean isNotValidBet = false;
		
		do {
			
			bet = retreiveBet(sc);
				
			if (!p1.validBet(bet) && bet != -1) {
				System.out.println("Not a valid bet. Try again.");
			} else if (bet == -1) {
				// Do nothing
			}
			else {
				p1.getHand().placeBet(bet);
				isNotValidBet = true;
			}			
			
		} while (!isNotValidBet);
		
		game.dealCards(p1, dealer);
		
	if (!dealer.getHand().isBlackJack()) {
		
		do {
			
			System.out.println("Please Enter '0' to stand, '1' to hit, '2' to double, or '3' to split:");
			choice = sc.nextInt();
			
			if (choice == Constants.PLAYER_STAY) {
				
				if (p1.playerStay(game)) {
					continue;
				}
				
				break;
			} else if (choice == Constants.PLAYER_HIT)	{
				
				p1.playerHit(game);
				
			}
			else if (choice == Constants.PLAYER_DOUBLE) {
				
				if (p1.playerDouble(game)) {
					continue;
				}
				
				break;
				
			}
			else if (choice == Constants.PLAYER_SPLIT) {
				
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
			game.handleSplitHand(p1, dealer);
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
