package levels;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entities.Boss;
import entities.Guider;
import entities.Mantis;
import entities.Ant;
import main.Game;
import objects.*;

import static utilz.Constants.MobConstants.*;
import static utilz.Constants.ObjectConstants.*;

public class Level {

	private BufferedImage img;
	private int[][] lvlData;

	private ArrayList<Mantis> mantis = new ArrayList<>();
	private ArrayList<Ant> ants = new ArrayList<>();
	private ArrayList<Guider> guider = new ArrayList<>();
	private ArrayList<Boss> boss = new ArrayList<>();
	private ArrayList<Fish> fishes = new ArrayList<>();
	private ArrayList<Vines> vines = new ArrayList<>();
	private ArrayList<Bee> bees = new ArrayList<>();
	private ArrayList<Entrance> entrance = new ArrayList<>();
	private ArrayList<Tree> trees = new ArrayList<>();
	private ArrayList<Brush> brushes = new ArrayList<>();

	private int lvlTilesWide;
	private int maxTilesOffset;
	private int maxLvlOffsetX;
	private Point playerSpawn;

	public Level(BufferedImage img) {
		this.img = img;
		lvlData = new int[img.getHeight()][img.getWidth()];
		loadLevel();
		calcLvlOffsets();
	}

	private void loadLevel() {

		for (int y = 0; y < img.getHeight(); y++)
			for (int x = 0; x < img.getWidth(); x++) {
				Color c = new Color(img.getRGB(x, y));
				int red = c.getRed();
				int green = c.getGreen();
				int blue = c.getBlue();

				loadLevelData(red, x, y);
				loadEntities(green, x, y);
				loadObjects(blue, x, y);
			}
	}

	private void loadLevelData(int redValue, int x, int y) {
		if (redValue >= 50)
			lvlData[y][x] = 0;
		else
			lvlData[y][x] = redValue;

		switch (redValue) {
			case 30, 31 -> brushes.add(new Brush((int) (x * Game.TILES_SIZE), (int) (y * Game.TILES_SIZE) - Game.TILES_SIZE));
		}
	}

	private void loadEntities(int greenValue, int x, int y) {
		switch (greenValue) {
			case MANTIS -> mantis.add(new Mantis(x * Game.TILES_SIZE, y * Game.TILES_SIZE));
			case ANT -> ants.add(new Ant(x * Game.TILES_SIZE, y * Game.TILES_SIZE));
			case GUIDER -> guider.add(new Guider(x * Game.TILES_SIZE, y * Game.TILES_SIZE));
			case BOSS -> boss.add(new Boss(x * Game.TILES_SIZE, y * Game.TILES_SIZE));
			case 100 -> playerSpawn = new Point(x * Game.TILES_SIZE, y * Game.TILES_SIZE);
		}
	}

	private void loadObjects(int blueValue, int x, int y) {
		switch (blueValue) {
			case FISH -> fishes.add(new Fish(x * Game.TILES_SIZE, y * Game.TILES_SIZE, blueValue));
			case ENTRANCE -> entrance.add(new Entrance(x * Game.TILES_SIZE, y * Game.TILES_SIZE, blueValue));
			case VINES -> vines.add(new Vines(x * Game.TILES_SIZE, y * Game.TILES_SIZE, VINES));
			case BEE_LEFT, BEE_RIGHT -> bees.add(new Bee(x * Game.TILES_SIZE, y * Game.TILES_SIZE, blueValue));
			case TREE -> trees.add(new Tree(x * Game.TILES_SIZE, y * Game.TILES_SIZE, blueValue));
		}
	}

	private void calcLvlOffsets() {
		lvlTilesWide = img.getWidth();
		maxTilesOffset = lvlTilesWide - Game.TILES_IN_WIDTH;
		maxLvlOffsetX = Game.TILES_SIZE * maxTilesOffset;
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

	public Point getPlayerSpawn() {
		return playerSpawn;
	}

	public ArrayList<Mantis> getMantis() {
		return mantis;
	}

	public ArrayList<Ant> getAnts() {
		return ants;
	}

	public ArrayList<Guider> getGuider() {
		return guider;
	}

	public ArrayList<Boss> getBoss() {
		return boss;
	}

	public ArrayList<Fish> getFishes() {
		return fishes;
	}

	public ArrayList<Vines> getVines() {
		return vines;
	}

	public ArrayList<Bee> getBees() {
		return bees;
	}

	public ArrayList<Tree> getTrees() {
		return trees;
	}

	public ArrayList<Brush> getBrushes() {
		return brushes;
	}

	public ArrayList<Entrance> getEntrance() {
		return entrance;
	}

}
