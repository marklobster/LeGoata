package legoata.engine.game;

import java.util.ArrayList;
import java.util.Scanner;

import legoata.engine.gamecharacter.GameCharacter;

public class QueueBasedGameRunner {
	
	private Scanner scanner = new Scanner(System.in);
	
	private ArrayList<GameCharacter> heroes = new ArrayList<GameCharacter>();
	private ArrayList<GameCharacter> enemies = new ArrayList<GameCharacter>();
	
	public void addTestHero(GameCharacter hero) {
		heroes.add(hero);
	}
	
	public void addTestEnemy(GameCharacter enemy) {
		enemies.add(enemy);
	}

	public QueueBasedGameRunner() {
		// TODO Auto-generated constructor stub
	}

	public void run() {
		
	}
	
}
