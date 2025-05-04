package utilz;

import main.Game;

public class Constants {
	public static final float GRAVITY = 0.04f * Game.SCALE;
	public static final int ANI_SPEED = 25;

	public static class Projectiles{
		public static final int STING_DEFAULT_WIDTH = 15;
		public static final int STING_BALL_DEFAULT_HEIGHT = 15;

		public static final int STING_WIDTH = (int)(Game.SCALE * STING_DEFAULT_WIDTH);
		public static final int STING_HEIGHT = (int)(Game.SCALE * STING_BALL_DEFAULT_HEIGHT);
		public static final float SPEED = 0.75f * Game.SCALE;
	}

	public static class ObjectConstants {

		public static final int FISH = 0;
		public static final int SPIKE = 4;
		public static final int BEE_LEFT = 5;
		public static final int BEE_RIGHT = 6;

		public static final int FISH_VALUE = 15;

		public static final int FISH_WIDTH_DEFAULT = 16;
		public static final int FISH_HEIGHT_DEFAULT = 16;
		public static final int FISH_WIDTH = (int) (Game.SCALE * FISH_WIDTH_DEFAULT);
		public static final int FISH_HEIGHT = (int) (Game.SCALE * FISH_HEIGHT_DEFAULT);

		public static final int VINE_WIDTH_DEFAULT = 32;
		public static final int VINE_HEIGHT_DEFAULT = 32;
		public static final int VINE_WIDTH = (int) (Game.SCALE * VINE_WIDTH_DEFAULT);
		public static final int VINE_HEIGHT = (int) (Game.SCALE * VINE_HEIGHT_DEFAULT);

		public static final int BEE_WIDTH_DEFAULT = 40;
		public static final int BEE_HEIGHT_DEFAULT = 26;
		public static final int BEE_WIDTH = (int) (BEE_WIDTH_DEFAULT * Game.SCALE);
		public static final int BEE_HEIGHT = (int) (BEE_HEIGHT_DEFAULT * Game.SCALE);

		public static int GetSpriteAmount(int object_type) {
			switch (object_type) {
				case FISH:
					return 1;
				case BEE_LEFT, BEE_RIGHT:
					return 7;
			}
			return 1;
		}
	}

	public static class EnemyConstants {
		public static final int ANT = 0;

		public static final int RUNNING = 0;
		public static final int DEAD = 1;

		public static final int ANT_WIDTH_DEFAULT = 72;
		public static final int ANT_HEIGHT_DEFAULT = 32;

		public static final int ANT_WIDTH = (int) (ANT_WIDTH_DEFAULT * Game.SCALE);
		public static final int ANT_HEIGHT = (int) (ANT_HEIGHT_DEFAULT * Game.SCALE);

		public static final int ANT_DRAWOFFSET_X = (int) (26 * Game.SCALE);
		public static final int ANT_DRAWOFFSET_Y = (int) (9 * Game.SCALE);

		public static int GetSpriteAmount(int enemy_type, int enemy_state) {
			switch (enemy_type) {
				case ANT:
					switch (enemy_state) {
						case RUNNING:
						case DEAD:
							return 5;
					}
			}
			return 0;
		}

		public static int GetMaxHealth(int enemy_type) {
			switch (enemy_type) {
				case ANT:
					return 10;
				default:
					return 1;
			}
		}

		public static int GetEnemyDmg(int enemy_type) {
			switch (enemy_type) {
				case ANT:
					return 15;
				default:
					return 0;
			}
		}
	}

		public static class UI {
			public static class Buttons {
				public static final int B_WIDTH_DEFAULT = 140;
				public static final int B_HEIGHT_DEFAULT = 56;
				public static final int B_WIDTH = (int) (B_WIDTH_DEFAULT * Game.SCALE);
				public static final int B_HEIGHT = (int) (B_HEIGHT_DEFAULT * Game.SCALE);
			}

			public static class PauseButtons {
				public static final int SOUND_SIZE_DEFAULT = 42;
				public static final int SOUND_SIZE = (int) (SOUND_SIZE_DEFAULT * Game.SCALE);
			}

			public static class URMButtons {
				public static final int URM_DEFAULT_SIZE = 56;
				public static final int URM_SIZE = (int) (URM_DEFAULT_SIZE * Game.SCALE);

			}

			public static class VolumeButtons {
				public static final int VOLUME_DEFAULT_WIDTH = 28;
				public static final int VOLUME_DEFAULT_HEIGHT = 44;
				public static final int SLIDER_DEFAULT_WIDTH = 215;

				public static final int VOLUME_WIDTH = (int) (VOLUME_DEFAULT_WIDTH * Game.SCALE);
				public static final int VOLUME_HEIGHT = (int) (VOLUME_DEFAULT_HEIGHT * Game.SCALE);
				public static final int SLIDER_WIDTH = (int) (SLIDER_DEFAULT_WIDTH * Game.SCALE);
			}
		}

		public static class Directions {
			public static final int LEFT = 0;
			public static final int UP = 1;
			public static final int RIGHT = 2;
			public static final int DOWN = 3;
		}

		public static class PlayerConstants {
			public static final int IDLE = 0;
			public static final int RUNNING = 1;
			public static final int JUMP = 2;
			public static final int FALLING = 3;
			public static final int ATTACK = 4;
			public static final int HIT = 5;
			public static final int DEAD = 7;


			public static int GetSpriteAmount(int player_action) {
				switch (player_action) {
					case RUNNING:
					case IDLE:
						return 6;
					case JUMP:
					case ATTACK:
						return 3;
					case FALLING:
						return 1;
					case HIT:
					case DEAD:
					default:
						return 4;
				}
			}
		}
	}