package model;

public class Card {

	private Suit suit;
	private Face face;
	private int value;
	
	private enum Face {
		Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, King, Queen, Jack, Ace
	}
	
	private enum Suit {
		Spades, Hearts, Clubs, Diamonds
	}
	
	public Card(Suit suit, Face face) {
		this.suit = suit;
		this.face = face;
		this.value = -1;
	}
	
	public int getValue() {
		return value;
	}
	
	public void setValue() {
		
		switch (face) {
		
		case Two:
			value = 2;
			break;
		case Three:
			value = 3;
			break;
		case Four:
			value = 4;
			break;
		case Five:
			value = 5;
			break;
		case Six:
			value = 6;
			break;
		case Seven:
			value = 7;
			break;
		case Eight:
			value = 8;
			break;
		case Nine:
			value = 9;
			break;
		case Ace:
			value = 11;
			break;
		default:
			value = 10;
			break;
		}
		
	}
	
	public String toString() {
		return face + " " + suit;
	}
	
	// Create array of cards sequentially (A-K: Spades, Hearts, Clubs, Diamonds)
	public Card[] createCardArray(int numDecks) {
		
		Card[] cards = new Card[numDecks * 52];
		int cardIndex = 0;
	
		// control how many decks
	for (int deckIndex = 1; deckIndex <= numDecks; deckIndex++) {
		// each suit in a single deck
		for (int faceIndex = 0; faceIndex < 13; faceIndex++) {	
			// each face in single deck
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
			
			if (placeHolder[randomIndex] == 0) {
				placeHolder[randomIndex] = -1;
				
				randomArray[i] = cardArray[randomIndex];
				i++;
				
			}			
		
		}
		
		return randomArray;
		
	}
	
}
