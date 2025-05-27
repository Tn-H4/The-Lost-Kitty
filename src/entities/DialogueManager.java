package entities;

import static utilz.Constants.EnemyConstants.GUIDER;

public class DialogueManager {
    public static String[] getDialogueForEnemy(int enemy_type) {
        return switch (enemy_type) {
            case GUIDER -> new String[]{
                    "Welcome to Level 1!",
                    "Use arrow keys to move.",
                    "Press space to jump.",
                    "Press Z to attack."
            };
//            case 2 -> new String[]{
//                    "Now you're in Level 2.",
//                    "Enemies are stronger here.",
//                    "Be careful!"
//            };
            default -> new String[]{"Hello, adventurer!"};
        };
    }
}


