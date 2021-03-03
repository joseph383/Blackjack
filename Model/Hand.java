package model;

import java.util.ArrayList;

public class Hand {

	private int handValue;
	private ArrayList<Card> cards;
	private int numCards;
	private AceInHand aceObj;
	private boolean isSplit;
	private int bet;
	
	public Hand() {
		cards = new ArrayList<Card>();
		handValue = numCards = 0;
		aceObj = new AceInHand();
		isSplit = false;
	}
	
	public void resetHand() {
		handValue = numCards = bet = 0;
		cards.clear();
		aceObj.resetAceObj();
		isSplit = false;
	}
	
	public int getBet() {
		return bet;
	}
	
	public void placeBet(int amount) {
		bet = amount;
	}
	
	public void setSplit() {
		isSplit = true;
	}
	public ArrayList<Card> getCards() {
		return cards;
	}
	
	public int getNumCards() {
		return numCards;
	}
	
	public boolean isBlackJack() {
		return handValue == 21 && numCards == 2 && !isSplit;
	}
	
	public boolean isBust() {
		return handValue > 21;
	}
	
	public AceInHand getAceObj() {
		return aceObj;
	}
	
	public boolean containAce() {
		return aceObj.getCount() > 0;
	}
	
	public int getHandValue() {
		return handValue;
	}
	
	public void printHandDetails() {
		
		for (int i = 0; i < cards.size(); i++) {
			System.out.print(cards.get(i).toString() + " ");
		}
		System.out.println("\nTotal is: " + getHandValue() + "\n");
		
	}	
	
	public void addCardToHand(Card c) {
		
		cards.add(c);
		numCards++;
		
		// no ace in, hand less than 11, and adding ace at default 11 value
		if (!containAce() && c.getValue() == 11 && handValue <= 10) {
			handValue += c.getValue(); 
			aceObj.addIndex(c.getValue());
		}
		// adding ace at 1 value, AND either have no prev ace and hand over 10 OR have an ace that is valued at 1
		else if (c.getValue() == 11 && ((!containAce() && handValue > 10) || (containAce() && !aceObj.softAce()))) {
			handValue += 1;
			aceObj.addIndex(1);
		}
		// has ace, not adding ace, soft ace, and new total will go over 21, change to 1
		else if (containAce() && c.getValue() != 11 && aceObj.softAce() && (handValue + c.getValue() > 21)) {
			handValue = handValue - 10 + cards.get(cards.size() - 1).getValue();
			aceObj.updateAceVal(); 
		}
		// either don't have ace and adding non-ace OR have soft ace and adding non-ace and new total not bust OR not soft ace: add face value
		else if ((!containAce() && c.getValue() != 11) || containAce() && c.getValue() != 11 && 
				((aceObj.softAce() && (handValue + c.getValue() <= 21)) || (!aceObj.softAce()))) {
			handValue += cards.get(cards.size() - 1).getValue(); 
		}
		// has ace, adding ace, and soft ace: add val and change ace to 1
		else if (containAce() && c.getValue() == 11 && aceObj.softAce()) {
			handValue = handValue - 10 + cards.get(cards.size() - 1).getValue();
			aceObj.updateAceVal();
			aceObj.addIndex(c.getValue()); 
		}
		
	}
	
}
