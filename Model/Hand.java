package model;

import java.util.ArrayList;

public class Hand {

	private ArrayList<Card> cards;
	private int bet;
	
	public Hand() {
		cards = new ArrayList<Card>();
	}
	
	public void resetHand() {
		bet = 0;
		cards.clear();
	}
	
	public int getBet() {
		return bet;
	}
	
	public void placeBet(int amount) {
		bet = amount;
	}
	
	public ArrayList<Card> getCards() {
		return cards;
	}
	
	public boolean isBlackJack() {
		return calculateHandTotal() == 21 && cards.size() == 2;
	}
	
	public boolean isBust() {
		return calculateHandTotal() > 21;
	}
	
	public void printHandDetails(SystemOutput output) {
		
		for (int i = 0; i < cards.size(); i++) {
			output.displayMessage(cards.get(i).toString() + " ");
		}
		output.displayMessage("\nTotal is: " + calculateHandTotal() + "\n");
		
	}	
	
	public void addCardToHand(Card c) {
		cards.add(c);
	}
	
	public int calculateHandTotal() {
		
		boolean aceInHand = false;
		int handValue = 0;
		
		for (int i = 0; i < cards.size(); i++) {
			
			int cardValue = cards.get(i).getValue();
			
			// Calculate first ace after other cards have been added
			if (cardValue == 11 && !aceInHand) {
				aceInHand = true;
			} // Subsequent aces will be valued at 1
			else if (cardValue == 11 && aceInHand){
				handValue += 1;
			} else {
				handValue += cardValue;
			}
			
		}
		
		// Set first ace to 11 if the rest of the hand is less than or equal to 10
		if (aceInHand && handValue <= 10) {
			handValue += 11;
		} // Set first ace to 1 if the rest of the hand is greater than 10
		else if (aceInHand && handValue > 10) {
			handValue += 1;
		}
		
		return handValue;
	}
	
}
