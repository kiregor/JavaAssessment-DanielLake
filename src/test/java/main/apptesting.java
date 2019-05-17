package main;

import static org.junit.Assert.assertEquals;

import java.util.OptionalDouble;

import org.junit.Test;

public class apptesting {
	
	Game game = new Game(1);
	
	@Test
	public void testNearestPOI() {
		OptionalDouble output = game.player.nearestFOI(game.foiList);
		
		assert(output.isPresent());
	}
	
	@Test
	public void testRemovePrecision() {
		String expected = "10.123";
		String test = game.removeExcessPrecision("10.123456");
		
		assertEquals(expected, test);
	}
	
	@Test
	public void testFeatureGen() {
		boolean check = game.foiList.size() == 50 || game.foiList.size() == 51 || game.foiList.size() == 52 || game.foiList.size() == 53;
		assert(check);
	}
}
