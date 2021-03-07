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

}
