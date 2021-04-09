package model;

import java.util.Random;

public class Dealer extends CardPlayer {

	private Hand hand;
	private DealerRule dealerRule;
	
	protected enum DealerRule {
		Stays_on_Soft_17, Hits_on_Soft_17
	}
	
	public Dealer(String name) {
		super(name);
		hand = new Hand();
		this.dealerRule = initDealerRule();
	}
	
	public Hand getHand() {
		return hand;
	}
	
	public DealerRule getDealerRule() {
		return dealerRule;
	}
	
	public boolean generateDealerRule() {
		return new Random().nextBoolean();
	}
	
	public DealerRule initDealerRule() {
		if (generateDealerRule()) {
			return DealerRule.Stays_on_Soft_17;
		} else {
			return DealerRule.Hits_on_Soft_17;
		}
	}
	
	public void dealerPlay(Game game, SystemOutput output) {
		
		output.displayMessage("Dealer hand:");
		getHand().printHandDetails(output);
		
		// move this condition to method to make it easier to read
		while (getHand().calculateHandTotal() < 17 || (getHand().calculateHandTotal() == 17 && 
				getDealerRule() == DealerRule.Hits_on_Soft_17)) {
			
			Card nextCard = game.dealNextCard();
			getHand().addCardToHand(nextCard);
			output.displayMessage("New card is: " + nextCard);
			
			output.displayMessage("Dealer hand:");
			getHand().printHandDetails(output);
		}
	}


}
