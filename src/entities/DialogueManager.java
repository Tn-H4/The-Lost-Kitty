package entities;

import static utilz.Constants.MobConstants.BOSS;
import static utilz.Constants.MobConstants.GUIDER;

public class DialogueManager {
    private final String[] dialogueLines;
    private final DialogueAction[] requiredActions;
    private int currentIndex = 0;

    private long dialogueStartTime = 0;
    private boolean waitingForDelay = false;
    private static final long AUTO_ADVANCE_DELAY_MS = 2000; // 2 seconds

    public DialogueManager(int enemyType) {
        this.dialogueLines = getDialogueForEnemy(enemyType);
        this.requiredActions = getActionsForEnemy(enemyType);
    }

    public String getCurrentLine() {
        if (currentIndex < dialogueLines.length)
            return dialogueLines[currentIndex];
        return "";
    }

    public DialogueAction getCurrentRequiredAction() {
        if (currentIndex >= dialogueLines.length || currentIndex >= requiredActions.length) {
            return DialogueAction.CHECK_NONE;
        }

        DialogueAction currentAction = requiredActions[currentIndex];

        if (currentAction == DialogueAction.CHECK_NONE && !waitingForDelay) {
            waitingForDelay = true;
            dialogueStartTime = System.currentTimeMillis();
        }

        return currentAction;
    }

    public void updateAutoAdvance() {
        if (waitingForDelay) {
            long elapsed = System.currentTimeMillis() - dialogueStartTime;
            if (elapsed >= AUTO_ADVANCE_DELAY_MS) {
                advance();
                waitingForDelay = false;
            }
        }
    }

    public void advance() {
        currentIndex++;
    }

    public boolean isFinished() {
        return currentIndex >= dialogueLines.length;
    }

    public void reset() {
        currentIndex = 0;
    }

    public void drawDialogueBox(java.awt.Graphics g, int entityX, int entityY, int xLvlOffset) {
        if (isFinished()) return;

        final int BOX_WIDTH = 260;
        final int BOX_HEIGHT = 80;

        int boxX = entityX - xLvlOffset - 10;
        int boxY = entityY - BOX_HEIGHT - 20;

        g.setColor(new java.awt.Color(0, 0, 0, 180));
        g.fillRoundRect(boxX, boxY, BOX_WIDTH, BOX_HEIGHT, 15, 15);

        g.setColor(java.awt.Color.WHITE);
        g.drawRoundRect(boxX, boxY, BOX_WIDTH, BOX_HEIGHT, 15, 15);

        g.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 14));
        g.drawString(getCurrentLine(), boxX + 10, boxY + 30);
    }

    public static String[] getDialogueForEnemy(int enemyType) {
        return switch (enemyType) {
            case GUIDER -> new String[]{
                    "Welcome to Level 1!",
                    "Use arrow keys to move.",
                    "Press space to jump.",
                    "Click to attack."
            };
            case BOSS -> new String[]{
                    "Prepare to be defeated!"
            };
            default -> new String[]{"Hello, adventurer!"};
        };
    }

    public static DialogueAction[] getActionsForEnemy(int enemyType) {
        return switch (enemyType) {
            case GUIDER -> new DialogueAction[]{
                    DialogueAction.CHECK_NONE,    // "Welcome to Level 1!"
                    DialogueAction.CHECK_MOVE,    // "Use arrow keys to move."
                    DialogueAction.CHECK_JUMP,    // "Press space to jump."
                    DialogueAction.CHECK_ATTACK   // "Click to attack."
            };
            case BOSS -> new DialogueAction[]{
                    DialogueAction.CHECK_NONE
            };
            default -> new DialogueAction[]{DialogueAction.CHECK_NONE};
        };
    }
}
