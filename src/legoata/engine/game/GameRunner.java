package legoata.engine.game;

import java.util.ArrayList;
import java.util.Scanner;
import legoata.engine.gamecharacter.GameCharacter;
import legoata.engine.utils.Utils;

public class GameRunner {
	
	private Scanner scanner = new Scanner(System.in);
	
	private ArrayList<GameCharacter> heroes = new ArrayList<GameCharacter>();
	private ArrayList<GameCharacter> enemies = new ArrayList<GameCharacter>();
	
	public void addTestHero(GameCharacter hero) {
		heroes.add(hero);
	}
	
	public void addTestEnemy(GameCharacter enemy) {
		enemies.add(enemy);
	}
	
	public void run() {
		
	}
	
	private void pause() {
		scanner.nextLine();
	}
	
	private void postGameDebrief() {
		boolean goodTeamRemains = false;
		for (GameCharacter member : heroes) {
			if (!member.isFallen()) {
				goodTeamRemains = true;
				break;
			}
		}
		
		System.out.println("");
		System.out.println("The winner is... " + (goodTeamRemains ? "you!!" : "the bad guys!!"));
		
		System.out.println("");
		System.out.println("The stats:");
		System.out.println("");
		for (GameCharacter gc : heroes) {
			gc.printStats(System.out);
		}
		for (GameCharacter gc : enemies) {
			gc.printStats(System.out);
		}
	}
	
	private GameCharacter selectTarget(ArrayList<GameCharacter> targets) {
		return Utils.pickRandom(targets);
	}
}
