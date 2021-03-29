package model;

import model.Card.Face;
import model.Card.Suit;
import model.Constants;

public class Game {

    // Total playing card index
	private int cardIndex;
	// Data structure to hold all playing cards
	private Card [] playingCards;
    private boolean split;
    
    public Game() {
    	cardIndex = 0;
        playingCards = new Card[Constants.DEFAULT_NUM_DECKS * Constants.NUMBER_OF_CARDS_IN_DECK];
        split = false;
    }

    public int getCardIndex() {
        return cardIndex;
    }

    public void setCardIndex(int cardIndex) {
        this.cardIndex = cardIndex;
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
			else if (p1.getHand().getHandValue() > dealer.getHand().getHandValue()) {
				output.displayMessage(p1.getName() + " win $" + p1.getHand().getBet() + "\n");
				p1.winGame(false, p1.getHand().getBet());
			}
			else if (p1.getHand().getHandValue() < dealer.getHand().getHandValue()) {
				output.displayMessage(p1.getName() + " lose $" + p1.getHand().getBet() + "\n");
				p1.loseGame(p1.getHand().getBet());
			}
			else {
				output.displayMessage("Tie");
			}
			
		}
		
		public void dealCards(Player p1, Dealer dealer, SystemOutput output) {
			
			p1.getHand().addCardToHand(getPlayingCards()[getCardIndex()]);
			setCardIndex(getCardIndex()+1);
			p1.getHand().addCardToHand(getPlayingCards()[getCardIndex()]);
			setCardIndex(getCardIndex()+1);
			
			output.displayMessage("Your hand:");
			p1.getHand().printHandDetails(output);
			
			output.displayMessage("_________________________________________");

			dealer.getHand().addCardToHand(getPlayingCards()[getCardIndex()]);
			setCardIndex(getCardIndex()+1);
			
			output.displayMessage("Dealer hand:");
			dealer.getHand().printHandDetails(output);
			
			dealer.getHand().addCardToHand(getPlayingCards()[getCardIndex()]);
			setCardIndex(getCardIndex()+1);

		}
		
		public void handleSplitHand(Player p1, Dealer dealer, SystemOutput output) {
			
			p1.prevHand();
			
			output.displayMessage("First split hand:");
			p1.getHand().printHandDetails(output);
			determineWinner(p1, dealer, output);

			p1.advanceHand();
			
			output.displayMessage("Second split hand:");
			p1.getHand().printHandDetails(output);
			determineWinner(p1, dealer, output);
			setSplit(false);
			
		}

}
