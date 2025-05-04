package levels;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entities.Ant;
import main.Game;
import objects.Bee;
import objects.Fish;
import objects.Vines;
import utilz.HelpMethods;

import static utilz.HelpMethods.GetLevelData;
import static utilz.HelpMethods.GetAnts;
import static utilz.HelpMethods.GetPlayerSpawn;

public class Level {

	private BufferedImage img;
	private int[][] lvlData;
	private ArrayList<Ant> ants;
	private ArrayList<Fish> fish;
	private ArrayList<Vines> vines;
	private ArrayList<Bee> bees;
	private int lvlTilesWide;
	private int maxTilesOffset;
	private int maxLvlOffsetX;
	private Point playerSpawn;

	public Level(BufferedImage img) {
		this.img = img;
		createLevelData();
		createEnemies();
		createPotions();
		createSpikes();
		createCannons();
		calcLvlOffsets();
		calcPlayerSpawn();
	}

	private void createCannons() {
		bees = HelpMethods.GetBees(img);
	}

	private void createSpikes() {
		vines = HelpMethods.GetVine(img);
	}

	private void createPotions() {
		fish = HelpMethods.GetFishes(img);
	}

	private void calcPlayerSpawn() {
		playerSpawn = GetPlayerSpawn(img);
	}

	private void calcLvlOffsets() {
		lvlTilesWide = img.getWidth();
		maxTilesOffset = lvlTilesWide - Game.TILES_IN_WIDTH;
		maxLvlOffsetX = Game.TILES_SIZE * maxTilesOffset;
	}

	private void createEnemies() {
		ants = GetAnts(img);
	}

	private void createLevelData() {
		lvlData = GetLevelData(img);
	}

	public int getSpriteIndex(int x, int y) {
		return lvlData[y][x];
	}

	public int[][] getLevelData() {
		return lvlData;
	}

	public int getLvlOffset() {
		return maxLvlOffsetX;
	}

	public ArrayList<Ant> getAnts() {
		return ants;
	}

	public Point getPlayerSpawn() {
		return playerSpawn;
	}

	public ArrayList<Fish> getPotions() {
		return fish;
	}


	public ArrayList<Vines> getSpikes() {
		return vines;
	}

	public ArrayList<Bee> getCannons(){
		return bees;
	}

}
