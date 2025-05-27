package entities;

import static utilz.Constants.EnemyConstants.*;

public class DialogueManager {
    public static String[] getDialogueForEnemy(int enemy_type) {
        return switch (enemy_type) {
            case GUIDER -> new String[]{
                    "Welcome to Level 1!",
                    "Use arrow keys to move.",
                    "Press space to jump.",
                    "Click to attack."
            };
            case BOSS -> new String[]{
                    "Hello"
            };
            default -> new String[]{"Hello, adventurer!"};
        };
    }
}


