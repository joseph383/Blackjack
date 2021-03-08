package model;

import model.Card.Face;
import model.Card.Suit;
import model.Constants;

public class Game {

    // Total playing card index
	private int index;
	// Data structure to hold all playing cards
	private Card [] playingCards;
    private boolean split;
    
    public Game() {
        index = 0;
        playingCards = new Card[Constants.DEFAULT_NUM_DECKS * 52];
        split = false;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Card[] getPlayingCards() {
        return playingCards;
    }
    
    public void setPlayingCards(Card[] playingCards) {
        this.playingCards = playingCards;
    }

    public boolean getSplit() {
        return split;
    }

    public void setSplit(boolean split) {
        this.split = split;
    }
	
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
		
		public String splitErrorMsg(Player p1) {
			
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
		
		public void determineWinner(Player p1, Dealer dealer) {
			
			if (dealer.getHand().isBlackJack() && !p1.getHand().isBlackJack()) {
				dealer.getHand().printHandDetails();
				System.out.println(dealer.getName() + " has Blackjack, You lose $" + p1.getHand().getBet() + "\n");
				p1.loseGame(p1.getHand().getBet());
			}
			else if (p1.getHand().isBlackJack() && !dealer.getHand().isBlackJack()) {
				System.out.println(p1.getName() + " have Blackjack and win $" + (p1.getHand().getBet() + p1.getHand().getBet() / 2) + "\n");
				p1.winGame(true, p1.getHand().getBet());
			}
			else if (p1.getHand().isBust() && dealer.getHand().isBust()) {
				System.out.println(dealer.getName() + " Bust, but " + p1.getName() + " Still lose $" + p1.getHand().getBet() + "\n");
				p1.loseGame(p1.getHand().getBet());
			}
			else if (!p1.getHand().isBust() && dealer.getHand().isBust()) {
				System.out.println(p1.getName() + " win $" + p1.getHand().getBet() + "\n");
				p1.winGame(false, p1.getHand().getBet());
			}
			else if (p1.getHand().isBust() && !dealer.getHand().isBust()) {
				System.out.println(p1.getName() + " lose $" + p1.getHand().getBet() + "\n");
				p1.loseGame(p1.getHand().getBet());
			}
			else if (p1.getHand().getHandValue() > dealer.getHand().getHandValue()) {
				System.out.println(p1.getName() + " win $" + p1.getHand().getBet() + "\n");
				p1.winGame(false, p1.getHand().getBet());
			}
			else if (p1.getHand().getHandValue() < dealer.getHand().getHandValue()) {
				System.out.println(p1.getName() + " lose $" + p1.getHand().getBet() + "\n");
				p1.loseGame(p1.getHand().getBet());
			}
			else {
				System.out.println("Tie");
			}
			
		}
		
		public void dealCards(Player p1, Dealer dealer) {
			
			p1.getHand().addCardToHand(getPlayingCards()[getIndex()]);
			setIndex(getIndex()+1);
			p1.getHand().addCardToHand(getPlayingCards()[getIndex()]);
			setIndex(getIndex()+1);
			
			System.out.println("Your hand:");
			p1.getHand().printHandDetails();
			
			System.out.println("_________________________________________");

			dealer.getHand().addCardToHand(getPlayingCards()[getIndex()]);
			setIndex(getIndex()+1);
			
			System.out.println("Dealer hand:");
			dealer.getHand().printHandDetails();
			
			dealer.getHand().addCardToHand(getPlayingCards()[getIndex()]);
			setIndex(getIndex()+1);

		}

}
