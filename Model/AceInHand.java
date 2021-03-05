package model;

/**
 * This class handles the value of one or more Ace cards that may be in a players hand
 */
public class AceInHand {
	
	private int count;
	private int valOne;
	private int [] indexVal;
	private int currIndx;
	
	public AceInHand() {
		count = valOne = currIndx = 0;
		indexVal = new int [21];
	}
	
	/*
	Reset properties of this class when individual game is over
	*/
	public void resetAceObj() {
		for (int i = 0; i < count; i++) {
			indexVal[i] = 0;
		}
		count = valOne = currIndx = 0;
	}
	
	public int getCount() {
		return count;
	}
	
	/*
	Soft Ace is when Ace is valued at 11
	*/
	public boolean softAce() {
		return count != valOne;
	}
	
	public void updateAceVal() {
		for (int i = 0; i < indexVal.length; i++) {
			if (indexVal[i] == 11) {
				indexVal[i] = 1;
				valOne++;
				return;
			}
		}
		
	}
	
	public void addIndex(int val) {
		if (val == 1) {
			valOne++;
		}
		indexVal[currIndx] = val;
		currIndx++;
		count++;
	}

}
