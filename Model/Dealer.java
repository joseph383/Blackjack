package model;

public class Dealer extends CardPlayer {

	private Hand hand;
	private int hitSoft17;
	
	public Dealer(String name, int hitSoft17) {
		super(name);
		hand = new Hand();
		this.hitSoft17 = hitSoft17;
	}
	
	public Hand getHand() {
		return hand;
	}
	
	public int getDealerRule() {
		return hitSoft17;
	}
	
	public String dealerRuleString() {
		
		if (hitSoft17 == 0) {
			return "Dealer stays on soft 17";
		}
		return "Dealer hits on Soft 17";
	}


}
