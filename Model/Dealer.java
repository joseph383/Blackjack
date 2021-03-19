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
	
	public void dealerPlay(Dealer dealer, Card [] playingCards, int index) {
		
		System.out.println("Dealer hand:");
		dealer.getHand().printHandDetails();
		
		while (dealer.getHand().getHandValue() < 17 || (dealer.getHand().getHandValue() == 17 && 
				dealer.getHand().getAceObj().softAce() && dealer.getDealerRule() == DealerRule.Hits_on_Soft_17)) {
			
			dealer.getHand().addCardToHand(playingCards[index]);
			System.out.println("New card is: " + playingCards[index].toString());
			index++;
			
			System.out.println("Dealer hand:");
			dealer.getHand().printHandDetails();
		}
	}


}
