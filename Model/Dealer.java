package model;

public class Dealer extends CardPlayer {

	private Hand hand;
	private boolean hitSoft17;
	
	public Dealer(String name, boolean hitSoft17) {
		super(name);
		hand = new Hand();
		this.hitSoft17 = hitSoft17;
	}
	
	public Hand getHand() {
		return hand;
	}
	
	public boolean getDealerRule() {
		return hitSoft17;
	}
	
	public String dealerRuleString() {
		
		if (hitSoft17) {
			return "Dealer stays on soft 17";
		}
		return "Dealer hits on Soft 17";
	}
	
	public void dealerPlay(Dealer dealer, Card [] playingCards, int index) {
		
		System.out.println("Dealer hand:");
		dealer.getHand().printHandDetails();
		
		while (dealer.getHand().getHandValue() < 17 || (dealer.getHand().getHandValue() == 17 && 
				dealer.getHand().getAceObj().softAce() && dealer.getDealerRule())) {
			
			dealer.getHand().addCardToHand(playingCards[index]);
			System.out.println("New card is: " + playingCards[index].toString());
			index++;
			
			System.out.println("Dealer hand:");
			dealer.getHand().printHandDetails();
		}
	}


}
