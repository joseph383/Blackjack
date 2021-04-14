package test;

import model.Player;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestPlayer {
	
	Player player;
	
	@Before
	public void setup() {
		player = new Player("TestPlayer");
	}
	
	@Test
	public void testValidBet() {
		
		assertEquals(100, player.getMoneyTotal());
		
		assertFalse(player.validBet(0));
		assertTrue(player.validBet(1));
		assertTrue(player.validBet(50));
		assertTrue(player.validBet(99));
		assertTrue(player.validBet(100));
		assertFalse(player.validBet(101));
		
	}
	
	@Test
	public void testLoseGame() {
		
		assertEquals(100, player.getMoneyTotal());
		assertEquals(0, player.getWins());
		assertEquals(0, player.getLoses());
		
		player.loseGame(50);
		
		assertEquals(50, player.getMoneyTotal());
		assertEquals(0, player.getWins());
		assertEquals(1, player.getLoses());
		
	}
	
	@Test
	public void testWinGame() {
		
		assertEquals(100, player.getMoneyTotal());
		assertEquals(0, player.getWins());
		assertEquals(0, player.getLoses());
		
		player.winGame(false, 50);
		
		assertEquals(150, player.getMoneyTotal());
		assertEquals(1, player.getWins());
		assertEquals(0, player.getLoses());
		
	}
	
	@Test
	public void testWinGameWithBlackJack() {
		
		assertEquals(100, player.getMoneyTotal());
		assertEquals(0, player.getWins());
		assertEquals(0, player.getLoses());
		
		player.winGame(true, 50);
		
		assertEquals(175, player.getMoneyTotal());
		assertEquals(1, player.getWins());
		assertEquals(0, player.getLoses());
		
	}

}
