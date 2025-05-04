package utilz;

import java.awt.Color;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import entities.Ant;
import main.Game;

import static utilz.Constants.EnemyConstants.ANT;

public class LoadSave {

	public static final String PLAYER_SPRITE = "PlayerSprite.png";
	public static final String LEVEL_ATLAS = "Platform.png";
	public static final String MENU_BUTTONS = "MenuButton.png";
	public static final String PAUSE_BACKGROUND = "PauseMenu.png";
	public static final String SOUND_BUTTONS = "SoundButton.png";
	public static final String URM_BUTTONS = "GreenButton.png";
	public static final String VOLUME_BUTTONS = "VolumeBar.png";
	public static final String MENU_BACKGROUND = "BackgroundGame.png";
	public static final String PLAYING_BG_IMG = "BackgroundLevel.png";
	public static final String ANT_SPRITE = "AntSprite.png";
	public static final String STATUS_BAR = "HealthBar.png";
	public static final String COMPLETED_IMG = "LevelCompleted.png";
	public static final String POTION_ATLAS = "Fish.png";
	public static final String TRAP_ATLAS = "Vines.png";
	public static final String CANNON_ATLAS = "BeeSprite.png";
	public static final String CANNON_BALL = "Sting.png";
	public static final String DEATH_SCREEN = "DeathScreen.png";
	public static final String OPTIONS_MENU = "OptionMenu.png";

	public static BufferedImage GetSpriteAtlas(String fileName) {
		BufferedImage img = null;
		InputStream is = LoadSave.class.getResourceAsStream("/" + fileName);
		try {
			img = ImageIO.read(is);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return img;
	}

	public static BufferedImage[] GetAllLevels() {
		URL url = LoadSave.class.getResource("/lvls");
		File file = null;

		try {
			file = new File(url.toURI());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		File[] files = file.listFiles();
		File[] filesSorted = new File[files.length];

		for (int i = 0; i < filesSorted.length; i++)
			for (int j = 0; j < files.length; j++) {
				if (files[j].getName().equals((i + 1) + ".png"))
					filesSorted[i] = files[j];

			}

		BufferedImage[] imgs = new BufferedImage[filesSorted.length];

		for (int i = 0; i < imgs.length; i++)
			try {
				imgs[i] = ImageIO.read(filesSorted[i]);
			} catch (IOException e) {
				e.printStackTrace();
			}

		return imgs;
	}
}