package model;

import model.Card.Face;
import model.Card.Suit;

public class Game {
	
	// Create array of cards sequentially (A-K: Spades, Hearts, Clubs, Diamonds)
		public Card[] createCardArray(int numDecks) {
			
			Card[] cards = new Card[numDecks * 52];
			int cardIndex = 0;
		
			// control how many decks
		for (int deckIndex = 1; deckIndex <= numDecks; deckIndex++) {
			// each face in a single deck
			for (int faceIndex = 0; faceIndex < 13; faceIndex++) {	
				// each suit in single deck
				for (int suitIndex = 0; suitIndex < 4; suitIndex++) {
					cards[cardIndex] = new Card(Suit.values()[suitIndex], Face.values()[faceIndex]);
					cards[cardIndex].setValue();
					
					cardIndex++;
				}
				
			}
		}
				
				return cards;		

		}
		
		public Card[] shuffleCards(Card[] cardArray, int numDecks) {
			
			Card[] randomArray = new Card[cardArray.length];
			int[] placeHolder = new int[cardArray.length];
			
			for (int i = 0; i < cardArray.length;) {
				
				int randomIndex = (int) (Math.random() * (52 * numDecks));
				
				// If this card has not been shuffled, shuffle it and update the index
				if (placeHolder[randomIndex] == 0) {
					placeHolder[randomIndex] = -1;
					
					randomArray[i] = cardArray[randomIndex];
					i++;
					
				}			
			
			}
			
			return randomArray;
			
		}
		
		public String splitErrorMsg(Player p1, boolean split) {
			
			String msg = "";
			
			if (split) {
				msg += ("You already split on this hand.\n");
			}
			if (!p1.validBet(p1.getHand().getBet() * 2)) {
				msg += ("You do not have enough money.\n");
			}
			if (p1.getHand().getCards().get(0).getValue() != p1.getHand().getCards().get(1).getValue()) {
				msg += ("You do not have a pair.\n");
			}
			if (p1.getHand().getCards().size() != 2) {
				msg += ("You can only split on initial two cards.");
			}
			
			return msg;
		}
		
		public String doubleErrorMsg(Player p1) {
			
			String msg = "";
			
			if (!p1.validBet(p1.getHand().getBet() * 2)) {
				msg += ("Player does not have enough money to make this move.\n");
			}
			if (p1.getHand().getCards().size() > 2) {
				msg += ("You can only double on initial hand.");
			}
					
			return msg;
			
		}

}
