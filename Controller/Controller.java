package controller;
import view.View;
import model.CommandLineInput;
import model.ConsoleOutput;
import model.Dealer;
import model.Game;
import model.Player;
import model.Constants;
import model.SystemOutput;
import model.UserInput;

public class Controller {
	
	// Driver method for blackjack should be in game class
	// controller can just take input from user
	public static void main(String[] args) {
		
		Game game = new Game();
		UserInput input = new CommandLineInput();
		SystemOutput output = new ConsoleOutput();
		
		game.createCardArray();
		game.shuffleCards();
		
		Dealer dealer = new Dealer("Dealer");
		Player p1 = new Player("You");
		
		//deal cards
		while (p1.getMoneyTotal() > 0) {
		
			// try to test this
			if (game.cardsNeedToBeReshuffled()) {
				game.shuffleCards();
				output.displayMessage("RESHUFFLING");
			}
			
		View.startupGame(output);
		int choice;
		
		output.displayMessage("Dealer " + new String(dealer.getDealerRule() + " ").replace("_", " "));
		output.displayMessage("You have $" + p1.getMoneyTotal() + ". How much would you like to bet on this round?");
		
		// put this in method
		int bet = 0;
		boolean isNotValidBet = false;
		
		do {
			
			bet = input.makeBet(output);
				
			if (!p1.validBet(bet) && bet != -1) {
				output.displayMessage("Not a valid bet. Try again.");
			} else if (bet == -1) {
				// Do nothing
			}
			else {
				p1.getHand().placeBet(bet);
				isNotValidBet = true;
			}			
			
		} while (!isNotValidBet);
		
		game.dealCards(p1, dealer, output);
		
	if (!dealer.getHand().isBlackJack()) {
		
		do {
			
			output.displayMessage("Please Enter '0' to stand, '1' to hit, '2' to double, or '3' to split:");
			choice = input.makePlayerChoice();
			
			if (choice == Constants.PLAYER_STAY) {
				
				if (p1.playerStay(game, output)) {
					continue;
				}
				
				break;
			} else if (choice == Constants.PLAYER_HIT)	{
				
				p1.playerHit(game, output);
				
			}
			else if (choice == Constants.PLAYER_DOUBLE) {
				
				if (p1.playerDouble(game, output)) {
					continue;
				}
				
				break;
				
			}
			else if (choice == Constants.PLAYER_SPLIT) {
				
				p1.playerSplit(game, output);
				
			}
			else {
				output.displayMessage(choice + " is not a valid choice. Please Enter '0' to stand, '1' to hit, '2' to double, or '3' to split: ");	
			}
		
		} while (!p1.getHand().isBust() && true);
		
		if (p1.getHand().isBust()) {
			output.displayMessage("You Bust\n");
		}

		dealer.dealerPlay(game.getPlayingCards(), game.getCardIndex(), output);
	}
		
		if (game.getSplit()) {
			game.handleSplitHand(p1, dealer, output);
		}
		else {
			game.determineWinner(p1, dealer, output);
		}
		
		p1.getHand().resetHand();
		dealer.getHand().resetHand();
		p1.playerReset();
		output.displayMessage("Player wins: " + p1.getWins() + "\nPlayer loses: " + p1.getLoses());
		}
		
		output.displayMessage("Game over. You ran out of money.");
		
		((CommandLineInput) input).closeInput();
	}

}
