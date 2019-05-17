package main;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;
import java.util.Random;
import java.util.stream.Collectors;

public class Player {
	private int north, west;
	private OptionalDouble distanceToFOIOptional;
	private double distanceToFOI;
	private List<String> gear;
	
	public Player() {
		gear = new ArrayList<>();
	}
	
	public OptionalDouble nearestFOI(List<FeatureOfInterest> foiList) {
		distanceToFOIOptional = foiList.stream().mapToDouble(f -> (double)f.distanceFromPlayer(north, west)).reduce((x, y) -> Math.min(x, y));
		distanceToFOI = distanceToFOIOptional.getAsDouble();
		return distanceToFOIOptional;
		
	}
	
	private void move(String direction) {
		switch(direction) {
			case "north":
				north+=1;
				if(north > 25) north = 25;
				break;
			case "east":
				west-=1;
				if(west < -25) west = -25;
				break;
			case "south":
				north-=1;
				if(north < -25) north = -25;
				break;
			case "west":
				west+=1;
				if(west > 25) west = 25;
				break;
		}
	}
	
	public String explore(String input, List<FeatureOfInterest> foiList, Game game) {
		
		List<FeatureOfInterest> nearbyFeatures = foiList.stream().filter(f -> f.distanceMatch(north, west, distanceToFOIOptional)).collect(Collectors.toList());
		
		if(input.equals("e")) {
			if(distanceToFOI > 1) {
				return "There is nothing to be found here";
			}
			else {
				if(nearbyFeatures.size() > 0) {
					if(nearbyFeatures.get(0).gameOver()) {
						game.endGame();
						return nearbyFeatures.get(0).giveReason(gear);
					}
					else {
						if(nearbyFeatures.get(0).getName().equals("Teleport")) {
							Random rand = new Random();
							north = rand.nextInt(51)-25;
							west = rand.nextInt(51)-25;
							return nearbyFeatures.get(0).giveReason();
						}
						else {
							addGear(nearbyFeatures.get(0).getName());
							game.removeLocation(nearbyFeatures.get(0));
							return nearbyFeatures.get(0).giveReason();
						}
					}
				}
				else {
					return "There is nothing to be found here";
				}
			}
		}
		else if(input.equals("quit")) {
			game.endGame();
			return "Thank you for playing";
		}
		else {
			if(canMove(input)) {
				move(input);
				if(distanceToFOI < 3)
					return nearbyFeatures.get(0).getHint();
				else
					return "Nothing of interest catches your eye";
			}
			else {
				return "A mysterious force keeps you from moving in this direction";
			}
		}
	}
	
	public void addGear(String newGear) {
		gear.add(newGear);
	}
	
	public void removeGear(String lostGear) {
		gear.remove(lostGear);
	}
	
	public boolean canMove(String direction) {
		switch(direction) {
			case "north":
				return north < 25;
			case "south":
				return north > -25;
			case "east":
				return west > -25;
			case "west":
				return west < 25;
			default:
				return true;
		}
	}
	
	public boolean checkGear(String gearToCheck) {
		return gear.stream().filter(g -> g.equals(gearToCheck)).count() > 0;
	}
	
	public String amuletPull(List<FeatureOfInterest> foiList) {
		if(gear.stream().filter(g -> g.equals("Sword")).count() > 0 && gear.stream().filter(g -> g.equals("Shield")).count() > 0 && foiList.stream().filter(f -> f.distanceMatch(north, west, distanceToFOIOptional)).collect(Collectors.toList()).get(0).getName().equals("Dragon")) {
			return "The amulet you found earlier seems to agree with your compass";
		}
		else if(gear.stream().filter(g -> g.equals("Sword")).count() > 0 && foiList.stream().filter(f -> f.distanceMatch(north, west, distanceToFOIOptional)).collect(Collectors.toList()).get(0).getName().equals("Dragon")) {
			return "The amulet you found earlier seems to pull away from the direction your compass is indicating";
		}
		else if(gear.stream().filter(g -> g.equals("Shield")).count() > 0 && foiList.stream().filter(f -> f.distanceMatch(north, west, distanceToFOIOptional)).collect(Collectors.toList()).get(0).getName().equals("Dragon")) {
			return "The amulet you found earlier seems to pull away from the direction your compass is indicating";
		}
		else if(foiList.stream().filter(f -> f.distanceMatch(north, west, distanceToFOIOptional)).collect(Collectors.toList()).get(0).getName().equals("Dragon")) {
			return "The amulet you found earlier seems to pull away from the direction your compass is indicating";
		}
		else {
			return "The amulet you found earlier lies dormant";
		}
	}
}
