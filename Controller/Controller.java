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
	
	private static void promptUserForBetAmount (Player p1, UserInput input, SystemOutput output) {
		
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
		
	}
	
	public static void main(String[] args) {
		
		Game game = new Game();
		UserInput input = new CommandLineInput();
		SystemOutput output = new ConsoleOutput();
		
		game.createCardArray();
		game.shuffleCards();
		
		Dealer dealer = new Dealer("Dealer");
		Player p1 = new Player("You");
		
		boolean promptForUserInput = true;
		
		//deal cards
		while (p1.getMoneyTotal() > 0) {
		
			if (game.cardsNeedToBeReshuffled()) {
				game.shuffleCards();
				output.displayMessage("RESHUFFLING");
			}
			
		View.startupGame(output);
		int choice;
		
		output.displayMessage("Dealer " + new String(dealer.getDealerRule() + " ").replace("_", " "));
		output.displayMessage("You have $" + p1.getMoneyTotal() + ". How much would you like to bet on this round?");
		
		promptUserForBetAmount(p1, input, output);
		
		game.dealCards(p1, dealer, output);
		
	if (!dealer.getHand().isBlackJack()) {
		
		do {
			
			output.displayMessage("Please Enter '0' to stand or '1' to hit:");
			choice = input.makePlayerChoice();
			
			switch (choice) {
			
				case Constants.PLAYER_STAY: {
					p1.playerStay(game, output);
					promptForUserInput = false;
					break;
				}
				case Constants.PLAYER_HIT: {
					p1.playerHit(game, output);
					break;
				}
				default: {
					output.displayMessage(choice + " is not a valid choice. Please Enter '0' to stand or '1' to hit: ");	
				}
			
			}
			
		
		} while (!p1.getHand().isBust() && promptForUserInput && true);
		
		if (p1.getHand().isBust()) {
			output.displayMessage("You Bust\n");
		}

		dealer.dealerPlay(game, output);
	}
		
		game.determineWinner(p1, dealer, output);
		
		p1.getHand().resetHand();
		dealer.getHand().resetHand();
		promptForUserInput = true;
		output.displayMessage("Player wins: " + p1.getWins() + "\nPlayer loses: " + p1.getLoses());
		}
		
		output.displayMessage("Game over. You ran out of money.");
		
		((CommandLineInput) input).closeInput();
	}

}
