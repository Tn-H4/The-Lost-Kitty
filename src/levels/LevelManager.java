package levels;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import main.Game;
import utilz.LoadSave;

public class LevelManager {

	private Game game;
	private BufferedImage[] levelSprite;
	private ArrayList<Level> levels;
	private int lvlIndex = 0;
	private BufferedImage[][] allLevelSprites;


	public LevelManager(Game game) {
		this.game = game;
		importOutsideSprites();
		levels = new ArrayList<>();
		buildAllLevels();
	}

	public void loadNextLevel() {
		Level newLevel = levels.get(lvlIndex);
		game.getPlaying().getEnemyManager().loadEnemies(newLevel);
		game.getPlaying().getPlayer().loadLvlData(newLevel.getLevelData());
		game.getPlaying().setMaxLvlOffset(newLevel.getLvlOffset());
		game.getPlaying().getObjectManager().loadObjects(newLevel);
	}

	private void buildAllLevels() {
		BufferedImage[] allLevels = LoadSave.GetAllLevels();
		for (BufferedImage img : allLevels)
			levels.add(new Level(img));
	}

	private void importOutsideSprites() {
		BufferedImage[] atlases = LoadSave.GetAllTileAtlases(); // Add this method in LoadSave
		allLevelSprites = new BufferedImage[atlases.length][48];

		for (int lvl = 0; lvl < atlases.length; lvl++) {
			BufferedImage img = atlases[lvl];
			for (int j = 0; j < 4; j++) {
				for (int i = 0; i < 12; i++) {
					int index = j * 12 + i;
					allLevelSprites[lvl][index] = img.getSubimage(i * 32, j * 32, 32, 32);
				}
			}
		}
	}

	public void draw(Graphics g, int lvlOffset) {
		BufferedImage[] currentTileset = allLevelSprites[lvlIndex];

		for (int j = 0; j < Game.TILES_IN_HEIGHT; j++) {
			for (int i = 0; i < levels.get(lvlIndex).getLevelData()[0].length; i++) {
				int index = levels.get(lvlIndex).getSpriteIndex(i, j);
				int x = Game.TILES_SIZE * i - lvlOffset;
				int y = Game.TILES_SIZE * j;

				g.drawImage(currentTileset[index], x, y, Game.TILES_SIZE, Game.TILES_SIZE, null);
			}
		}
	}


	public Level getCurrentLevel() {
		return levels.get(lvlIndex);
	}

	public int getAmountOfLevels() {
		return levels.size();
	}

	public int getLevelIndex() {
		return lvlIndex;
	}

	public void setLevelIndex(int lvlIndex) {
		this.lvlIndex = lvlIndex;
	}
}
