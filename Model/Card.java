package model;

public class Card {

	private Suit suit;
	private Face face;
	private int value;
	
	public enum Face {
		Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, King, Queen, Jack, Ace
	}
	
	public enum Suit {
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
	
}
