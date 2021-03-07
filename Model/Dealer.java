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


}
