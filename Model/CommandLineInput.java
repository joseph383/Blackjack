package model;

import java.util.InputMismatchException;
import java.util.Scanner;

public class CommandLineInput implements UserInput {
	
	Scanner scanner;
	
	public CommandLineInput () {
		scanner = new Scanner(System.in);
	}
	
	public Scanner getScanner() {
		return scanner;
	}
	
	public void setScanner(Scanner scanner) {
		this.scanner = scanner;
	}
	
	public void closeInput() {
		scanner.close();
	}

	@Override
	public int makeBet(SystemOutput output) {
		
		int bet = 0;
		
		try {
			bet = scanner.nextInt();
			return bet;
		} catch (InputMismatchException ex) {
			output.displayMessage("Not a valid bet. Expected an integer value. Try again.");
			output.displayMessage("How much would you like to bet on this round?");
			scanner.next();
			return -1;
		}
		
	}

	@Override
	public int makePlayerChoice() {
		
		int choice = scanner.nextInt();
		
		return choice;
		
	}

}
