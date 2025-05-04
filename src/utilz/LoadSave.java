package utilz;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class LoadSave {

	public static final String PLAYER_ATLAS = "PlayerSprite.png";
	public static final String LEVEL_ATLAS = "Platform.png";
	public static final String LEVEL_ONE_DATA = "TestStage.png";
	public static final String MENU_BUTTONS = "MenuButton.png";
	public static final String PAUSE_BACKGROUND = "PauseMenu.png";
	public static final String SOUND_BUTTONS = "SoundButton.png";
	public static final String URM_BUTTONS = "GreenButton.png";
	public static final String VOLUME_BUTTONS = "VolumeBar.png";
	public static final String MENU_BACKGROUND = "BackgroundGame.png";
	public static final String PLAYING_BG_IMG = "BackgroundLevel.png";

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

	public static int[][] GetLevelData() {
		BufferedImage img = GetSpriteAtlas(LEVEL_ONE_DATA);
		int[][] lvlData = new int[img.getHeight()][img.getWidth()];

		for (int j = 0; j < img.getHeight(); j++)
			for (int i = 0; i < img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int value = color.getRed();
				if (value >= 48)
					value = 0;
				lvlData[j][i] = value;
			}
		return lvlData;

	}
}