package model;

import model.Card.Face;
import model.Card.Suit;
import model.Constants;

public class Game {

    // Total playing card index
	private int cardIndex;
	// Data structure to hold all playing cards
	private Card [] playingCards;
    
    public Game() {
    	cardIndex = 0;
        playingCards = new Card[Constants.DEFAULT_NUM_DECKS * Constants.NUMBER_OF_CARDS_IN_DECK];
    }

    public int getCardIndex() {
        return cardIndex;
    }

    public Card[] getPlayingCards() {
        return playingCards;
    }
    
    public void setPlayingCards(Card[] playingCards) {
        this.playingCards = playingCards;
    }
    
    public Card dealNextCard() {
    	cardIndex++;
    	return playingCards[cardIndex];
    }
	
	// Create array of cards sequentially (A-K: Spades, Hearts, Clubs, Diamonds)
	public void createCardArray() {
		
		int index = 0;
		// control how many decks
		for (int deckIndex = 1; deckIndex <= Constants.DEFAULT_NUM_DECKS; deckIndex++) {
			// each face in a single deck
			for (int faceIndex = 0; faceIndex < Constants.NUMBER_OF_FACES_IN_DECK; faceIndex++) {	
				// each suit in single deck
				for (int suitIndex = 0; suitIndex < Constants.NUMBER_OF_SUITS_IN_DECK; suitIndex++) {
					playingCards[index] = new Card(Suit.values()[suitIndex], Face.values()[faceIndex]);
					playingCards[index].setValue();
					
					index++;
				}
				
			}
		}		

	}
		
	public void shuffleCards() {
			
		Card[] randomArray = new Card[playingCards.length];
		int[] placeHolder = new int[playingCards.length];
			
		for (int i = 0; i < playingCards.length;) {
				
			int randomIndex = (int) (Math.random() * (Constants.NUMBER_OF_CARDS_IN_DECK * Constants.DEFAULT_NUM_DECKS));
				
			// If this card has not been shuffled, shuffle it and update the index
			if (placeHolder[randomIndex] == 0) {
				placeHolder[randomIndex] = -1;
					
				randomArray[i] = playingCards[randomIndex];
				i++;
					
			}
			
		}
			
		playingCards = randomArray;
		cardIndex = 0;
			
	}
		
	public boolean cardsNeedToBeReshuffled() {
		return getCardIndex() > Constants.RESHUFFLE_CARD_INDEX;
	}
		
	public void determineWinner(Player p1, Dealer dealer, SystemOutput output) {
		
		if (dealer.getHand().isBlackJack() && !p1.getHand().isBlackJack()) {
			dealer.getHand().printHandDetails(output);
			output.displayMessage(dealer.getName() + " has Blackjack, You lose $" + p1.getHand().getBet() + "\n");
			p1.loseGame(p1.getHand().getBet());
		}
		else if (p1.getHand().isBlackJack() && !dealer.getHand().isBlackJack()) {
			output.displayMessage(p1.getName() + " have Blackjack and win $" + (p1.getHand().getBet() + p1.getHand().getBet() / 2) + "\n");
			p1.winGame(true, p1.getHand().getBet());
		}
		else if (p1.getHand().isBust() && dealer.getHand().isBust()) {
			output.displayMessage(dealer.getName() + " Bust, but " + p1.getName() + " Still lose $" + p1.getHand().getBet() + "\n");
			p1.loseGame(p1.getHand().getBet());
		}
		else if (!p1.getHand().isBust() && dealer.getHand().isBust()) {
			output.displayMessage(p1.getName() + " win $" + p1.getHand().getBet() + "\n");
			p1.winGame(false, p1.getHand().getBet());
		}
		else if (p1.getHand().isBust() && !dealer.getHand().isBust()) {
			output.displayMessage(p1.getName() + " lose $" + p1.getHand().getBet() + "\n");
			p1.loseGame(p1.getHand().getBet());
		}
		else if (p1.getHand().calculateHandTotal() > dealer.getHand().calculateHandTotal()) {
			output.displayMessage(p1.getName() + " win $" + p1.getHand().getBet() + "\n");
			p1.winGame(false, p1.getHand().getBet());
		}
		else if (p1.getHand().calculateHandTotal() < dealer.getHand().calculateHandTotal()) {
			output.displayMessage(p1.getName() + " lose $" + p1.getHand().getBet() + "\n");
			p1.loseGame(p1.getHand().getBet());
		}
		else {
			output.displayMessage("Tie");
		}
			
	}
		
	public void dealCards(Player p1, Dealer dealer, SystemOutput output) {
			
		Card nextCard = dealNextCard();
		p1.getHand().addCardToHand(nextCard);
			
		nextCard = dealNextCard();
		p1.getHand().addCardToHand(nextCard);
			
		output.displayMessage("Your hand:");
		p1.getHand().printHandDetails(output);
			
		output.displayMessage("_________________________________________");

		nextCard = dealNextCard();
		dealer.getHand().addCardToHand(nextCard);
			
		output.displayMessage("Dealer hand:");
		dealer.getHand().printHandDetails(output);
			
		nextCard = dealNextCard();
		dealer.getHand().addCardToHand(nextCard);

	}

}
