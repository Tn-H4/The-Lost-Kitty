package utilz;

import main.Game;

public class Constants {
	public static final float GRAVITY = 0.04f * Game.SCALE;
	public static final int ANI_SPEED = 25;

	public static class Sting {
		public static final int STING_DEFAULT_WIDTH = 15;
		public static final int STING_BALL_DEFAULT_HEIGHT = 15;

		public static final int STING_WIDTH = (int) (Game.SCALE * STING_DEFAULT_WIDTH);
		public static final int STING_HEIGHT = (int) (Game.SCALE * STING_BALL_DEFAULT_HEIGHT);
		public static final float SPEED = 0.75f * Game.SCALE;
	}

	public static class ObjectConstants {

		public static final int FISH = 0;
		public static final int ENTRANCE = 1;
		public static final int TREE = 2;
		public static final int VINES = 4;
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

		public static final int ENTRANCE_WIDTH_DEFAULT = 32;
		public static final int ENTRANCE_HEIGHT_DEFAULT = 32;
		public static final int ENTRANCE_WIDTH= (int) (ENTRANCE_WIDTH_DEFAULT * Game.SCALE);
		public static final int ENTRANCE_HEIGHT = (int) (ENTRANCE_HEIGHT_DEFAULT * Game.SCALE);

		public static final int TREE_WIDTH_DEFAULT = 39;
		public static final int TREE_HEIGHT_DEFAULT = 62;
		public static final int TREE_WIDTH= (int) (TREE_WIDTH_DEFAULT * Game.SCALE);
		public static final int TREE_HEIGHT = (int) (TREE_HEIGHT_DEFAULT * Game.SCALE);
		public static final int TREE_OFFSET_X = Game.TILES_SIZE/2 - TREE_WIDTH/2;
		public static final int TREE_OFFSET_Y = Game.TILES_SIZE - TREE_HEIGHT;

		public static int GetSpriteAmount(int object_type) {
			switch (object_type) {
				case BEE_LEFT, BEE_RIGHT:
					return 7;
			}
			return 1;
		}
	}

	public static class MobConstants {
		public static final int ANT = 0;
		public static final int MANTIS = 1;
		public static final int GUIDER = 2;
		public static final int BOSS = 3;

		public static final int IDLE = 0;
		public static final int RUNNING = 1;
		public static final int ATTACK = 2;
		public static final int DEAD = 3;
		public static final int APPEARING = 4;
		public static final int TALKING = 5;

		public static final int ANT_WIDTH_DEFAULT = 72;
		public static final int ANT_HEIGHT_DEFAULT = 32;
		public static final int ANT_WIDTH = (int) (ANT_WIDTH_DEFAULT * Game.SCALE);
		public static final int ANT_HEIGHT = (int) (ANT_HEIGHT_DEFAULT * Game.SCALE);
		public static final int ANT_DRAWOFFSET_X = (int) (26 * Game.SCALE);
		public static final int ANT_DRAWOFFSET_Y = (int) (9 * Game.SCALE);

		public static final int MANTIS_WIDTH_DEFAULT = 72;
		public static final int MANTIS_HEIGHT_DEFAULT = 32;
		public static final int MANTIS_WIDTH = (int) (MANTIS_WIDTH_DEFAULT * Game.SCALE);
		public static final int MANTIS_HEIGHT = (int) (MANTIS_HEIGHT_DEFAULT * Game.SCALE);
		public static final int MANTIS_DRAWOFFSET_X = (int) (26 * Game.SCALE);
		public static final int MANTIS_DRAWOFFSET_Y = (int) (9 * Game.SCALE);

		public static final int GUIDER_WIDTH_DEFAULT = 32;
		public static final int GUIDER_HEIGHT_DEFAULT = 32;
		public static final int GUIDER_WIDTH = (int) (GUIDER_WIDTH_DEFAULT * Game.SCALE * 1.5);
		public static final int GUIDER_HEIGHT = (int) (GUIDER_HEIGHT_DEFAULT * Game.SCALE * 1.5);
		public static final int GUIDER_DRAWOFFSET_X = (int) (8 * Game.SCALE);
		public static final int GUIDER_DRAWOFFSET_Y = (int) (19 * Game.SCALE);

		public static final int BOSS_WIDTH_DEFAULT = 64;
		public static final int BOSS_HEIGHT_DEFAULT = 40;
		public static final int BOSS_WIDTH = (int) (BOSS_WIDTH_DEFAULT * Game.SCALE * 1.75);
		public static final int BOSS_HEIGHT = (int) (BOSS_HEIGHT_DEFAULT * Game.SCALE * 1.75);
		public static final int BOSS_DRAWOFFSET_X = (int) (30 * Game.SCALE);
		public static final int BOSS_DRAWOFFSET_Y = (int) (26 * Game.SCALE);

		public static int GetSpriteAmount(int mob_type, int mob_state) {
			switch (mob_state) {
				case IDLE:
					if (mob_type == ANT)
						return 1;
					if(mob_type == MANTIS)
						return 4;
					else
						return 2;
				case RUNNING:
					if (mob_type == ANT)
						return 5;
					else
						return 4;
				case ATTACK:
					if (mob_type == ANT)
						return 3;
					if(mob_type == MANTIS)
						return 7;
					if(mob_type == GUIDER)
						return 5;
					else
						return 3;
				case DEAD:
					if(mob_type == GUIDER || mob_type == BOSS)
						return 6;
					else
						return 5;
				case APPEARING:
					return 6;
				case TALKING:
					return 2;
			}
			return 0;
		}

		public static int GetMaxHealth(int enemy_type) {
			switch (enemy_type) {
				case GUIDER:
					return 1000;
				case BOSS:
					return 50;
				default: //For faster demo
					return 1;
			}
		}

		public static int GetMobDmg(int enemy_type) {
			switch (enemy_type) {
				case GUIDER:
					return 1000;
				case BOSS:
					return 50;
				default:
					return 15;
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
			public static final int DEAD = 6;


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