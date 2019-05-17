package main;

import java.util.List;
import java.util.OptionalDouble;
import java.util.Random;

public class FeatureOfInterest {
	private int north, west;
	private boolean endGame;
	private String name;
	private String reasonForInterest;
	private String observableHint;
	private static String[] nameList = {"Sword", "Dragon", "Shield", "Teleport", "Amulet"};
	private static boolean[] endGameList = {false, true, false, false, false};
	private static String[] reasonsForInterest = {"You see a box sitting on the plain. Its filled with treasure! You draw a sword from the pile!",
			"You stumble across the entrance to a dragon's lair! You get eaten! The end.", 
			"You find the ruins of a small hut. You pull a shield from within!",
			"You find some strange glowing runes etched into the ground. Your vision is obscured by a flash of light!",
			"You trip over a chain on the ground. On closer inspection it looks to be an amulet!"};
	private static String[] observableHints = {"There appears to be something on the ground a short ways away",
			"The clouds around here seem to be thicker... is that the smell of brimstone?",
			"Is that a collapsed building?",
			"The clouds here seem to have a strange glow to them.",
			"You can feel something tugging on your mind, but you're not sure where from."};
	
	public FeatureOfInterest() {
		Random rand = new Random();
		
		north = rand.nextInt(51)-25;
		west = rand.nextInt(51)-25;
		
		int choice = rand.nextInt(reasonsForInterest.length);
		name = nameList[choice];
		endGame = endGameList[choice];
		reasonForInterest = reasonsForInterest[choice];
		observableHint = observableHints[choice];
	}
	
	public FeatureOfInterest(int input) {
		Random rand = new Random();
		
		north = rand.nextInt(51)-25;
		west = rand.nextInt(51)-25;
		
		name = nameList[input];
		endGame = endGameList[input];
		reasonForInterest = reasonsForInterest[input];
		observableHint = observableHints[input];
	}
	
	public double distanceFromPlayer(int playerNorth, int playerWest) {
		int relativeNorth = north - playerNorth;
		int relativeWest = west - playerWest;
		
		return Math.sqrt((relativeNorth*relativeNorth)+(relativeWest*relativeWest));
	}
	
	public boolean distanceMatch(int playerNorth, int playerWest, OptionalDouble distance) {
			
		int relativeNorth = north - playerNorth;
		int relativeWest = west - playerWest;
		
		double currentDistance = Math.sqrt((relativeNorth*relativeNorth)+(relativeWest*relativeWest));
		double nearest = distance.getAsDouble();
		
		return currentDistance == nearest;
		
	}
	
	public String giveReason() {
		return reasonForInterest;
	}
	
	public String giveReason(List<String> gear) {
		if(gear.stream().filter(g -> g.equals("Sword")).count() > 0 && gear.stream().filter(g -> g.equals("Shield")).count() > 0)
			return "You stumble across the entrance to a dragon's lair! You catch its firey breath on your shield and draw your sword and slay the beast! The end.";
		else if(gear.stream().filter(g -> g.equals("Sword")).count() > 0)
			return "You stumble across the entrance to a dragon's lair! You draw your sword and slay the beast but in doing so are burned in its flames and die! The end.";
		else if(gear.stream().filter(g -> g.equals("Shield")).count() > 0)
			return "You stumble across the entrance to a dragon's lair! You catch its firey breath on your shield and stagger backwards only to be caught by its mighty claws! The end.";
		else
			return reasonForInterest;
	}
	
	public boolean gameOver() {
		return endGame;
	}
	
	public String getName() {
		return name;
	}
	
	public String getHint() {
		return observableHint;
	}
}
