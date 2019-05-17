package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.OptionalDouble;
import java.util.Scanner;

public class Game {
	
	Player player;
	List<FeatureOfInterest> foiList;
	boolean playGame;
	String[] validInputs = {"north", "south", "east", "west", "e", "quit"};
	
	public Game() {
		player = new Player();
		foiList = new ArrayList<>();
		
		for(int i = 0; i < 50; i++) {
			foiList.add(new FeatureOfInterest());
		}
		
		if(foiList.stream().filter(f -> f.getName().equals("Dragon")).count() < 1)
			foiList.add(new FeatureOfInterest(1));
		
		if(foiList.stream().filter(f -> f.getName().equals("Sword")).count() < 1)
			foiList.add(new FeatureOfInterest(0));
		
		if(foiList.stream().filter(f -> f.getName().equals("Shield")).count() < 1)
			foiList.add(new FeatureOfInterest(2));
		
		userInput();
	}
	
	public Game(int test) {
		player = new Player();
		foiList = new ArrayList<>();
		
		for(int i = 0; i < 50; i++) {
			foiList.add(new FeatureOfInterest());
		}
		
		if(foiList.stream().filter(f -> f.getName().equals("Dragon")).count() < 1)
			foiList.add(new FeatureOfInterest(1));
		
		if(foiList.stream().filter(f -> f.getName().equals("Sword")).count() < 1)
			foiList.add(new FeatureOfInterest(0));
		
		if(foiList.stream().filter(f -> f.getName().equals("Shield")).count() < 1)
			foiList.add(new FeatureOfInterest(2));
	}
	
	public void userInput() {
		System.out.println("Grey foggy clouds float oppressively close to you,");
		System.out.println("reflected in the murky grey water which reaches up your shins.");
		System.out.println("Some black plants barely poke out of the shallow water.");
		System.out.println("Try \"north\",\"south\",\"east\",or \"west\"");
		System.out.println("You notice a small watch-like device in your left hand.");
		System.out.println("It has hands like a watch, but the hands don't seem to tell time.");
		System.out.println("To quit the game at any time enter \"quit\"");
		
		playGame = true;
		Scanner sc = new Scanner(System.in);
		String input;
		
		while(playGame) {
			OptionalDouble distanceToFOIOptional = player.nearestFOI(foiList);
			if(distanceToFOIOptional.isPresent()) {
				double distanceToFOI = distanceToFOIOptional.getAsDouble();
				String distance = Double.toString(distanceToFOI * 5);
				distance = removeExcessPrecision(distance);
				System.out.println("The dial reads '" + distance + "m'");
				if(player.checkGear("Amulet"))
					System.out.println(player.amuletPull(foiList));
				System.out.print(">");
				input = sc.next();
				if(validInput(input))
					System.out.println(player.explore(input, foiList, this));
				else
					System.out.println("That input is not recognised");
			}
			else {
				System.out.println("There is nothing in this swamp");
				playGame = false;
			}
		}
	}
	
	public boolean validInput(String input) {
		return Arrays.stream(validInputs).filter(v -> v.equals(input)).count() > 0;
	}
	
	public String removeExcessPrecision(String distance) {
		int decimal = 0;
		for(int i = 0; i < distance.length(); i++) {
			if(distance.charAt(i) == '.')
				decimal = i;
			if(i > decimal + 3) {
				distance=distance.substring(0, i);
				i = distance.length();
			}
		}
		
		return distance;
	}
	
	public void endGame() {
		playGame = false;
	}
	
	public void removeLocation(FeatureOfInterest foi) {
		foiList.remove(foi);
	}
	
	public static void main(String[] args) {
		new Game();
	}
	
}

