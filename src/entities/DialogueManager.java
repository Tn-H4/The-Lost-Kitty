package entities;

import static utilz.Constants.MobConstants.BOSS;
import static utilz.Constants.MobConstants.GUIDER;

public class DialogueManager {
    private final String[] dialogueLines;
    private final DialogueAction[] requiredActions;
    private int currentIndex = 0;
    private long elapsed;

    private long dialogueStartTime = 0;
    private boolean waitingForDelay = false;
    private static final long AUTO_ADVANCE_DELAY_MS = 1500; // 2 seconds

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
            elapsed = System.currentTimeMillis() - dialogueStartTime;
            if (elapsed >= AUTO_ADVANCE_DELAY_MS) {
                advance();
                waitingForDelay = false;
                elapsed = 0;
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

        final int BOX_WIDTH = 300;
        final int BOX_HEIGHT = 80;

        int boxX = entityX - xLvlOffset - 10;
        int boxY = entityY - BOX_HEIGHT - 20;

        g.setColor(new java.awt.Color(0, 0, 0, 180));
        g.fillRoundRect(boxX, boxY, BOX_WIDTH, BOX_HEIGHT, 15, 15);

        g.setColor(java.awt.Color.WHITE);
        g.drawRoundRect(boxX, boxY, BOX_WIDTH, BOX_HEIGHT, 15, 15);

        g.setFont(new java.awt.Font("Chiller", java.awt.Font.PLAIN, 27));
        g.drawString(getCurrentLine(), boxX + 10, boxY + 30);
    }

    public static String[] getDialogueForEnemy(int enemyType) {
        return switch (enemyType) {
            case GUIDER -> new String[]{
                    "You need to get out of the wood!",
                    "Use A and D to move around",
                    "Press SPACE to jump.",
                    "Right Click to attack.",
                    "Left Click to sprint",
                    "Eat fish to regain health",
                    "Find your way out now!"
            };
            case BOSS -> new String[]{
                    "Prepare to be dead, little kitty!"
            };
            default -> new String[]{"Hello, adventurer!"};
        };
    }

    public static DialogueAction[] getActionsForEnemy(int enemyType) {
        return switch (enemyType) {
            case GUIDER -> new DialogueAction[]{
                    DialogueAction.CHECK_NONE,
                    DialogueAction.CHECK_MOVE,
                    DialogueAction.CHECK_JUMP,
                    DialogueAction.CHECK_ATTACK,
                    DialogueAction.CHECK_SPRINT,
                    DialogueAction.CHECK_NONE,
                    DialogueAction.CHECK_NONE
            };
            case BOSS -> new DialogueAction[]{
                    DialogueAction.CHECK_NONE
            };
            default -> new DialogueAction[]{DialogueAction.CHECK_NONE};
        };
    }
}
