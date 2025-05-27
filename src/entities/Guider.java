package entities;

import static utilz.Constants.EnemyConstants.*;
import static utilz.HelpMethods.IsFloor;

import gamestates.Playing;

import java.awt.*;

public class Guider extends Enemy {

    private boolean canSeePlayerNow = false;
    private final String[] dialogueLines;
    private int currentDialogueIndex = 0;
    private boolean showDialogue = false;
    private boolean dialogueFinished = false;

    private boolean moveTriggered = false;
    private boolean jumpTriggered = false;
    private boolean attackTriggered = false;

    // Dialogue box dimensions
    private static final int DIALOGUE_BOX_WIDTH = 260;
    private static final int DIALOGUE_BOX_HEIGHT = 80;

    public Guider(float x, float y) {
        super(x, y, GUIDER_WIDTH, GUIDER_HEIGHT, GUIDER);
        initHitbox(22, 22);
        initAttackBox(42, 19, 10);
        dialogueLines = DialogueManager.getDialogueForEnemy(enemyType);
    }

    public void update(int[][] lvlData, Playing playing) {
        updateBehavior(lvlData, playing);
        updateAnimationTick();
        updateAttackBox();
    }

    public boolean isSeeingPlayer() {
        return canSeePlayerNow;
    }

    public void advanceDialogue() {
        if (!showDialogue || dialogueFinished) return;

        currentDialogueIndex++;
        if (currentDialogueIndex >= dialogueLines.length) {
            endDialogue();
        }
    }

    private void endDialogue() {
        showDialogue = false;
        dialogueFinished = true;
        currentDialogueIndex = 0;
//        newState(DEAD); // Optional: Guider disappears after talking
    }

    private final DialogueAction[] requiredActions = {
            DialogueAction.NONE,
            DialogueAction.MOVE,
            DialogueAction.JUMP,
            DialogueAction.ATTACK
    };

    public void drawDialogueBox(Graphics g, int xLvlOffset) {
        if (!showDialogue || dialogueFinished) return;

        String text = dialogueLines[currentDialogueIndex];

        int boxX = (int) hitbox.x - xLvlOffset - 10;
        int boxY = (int) hitbox.y - DIALOGUE_BOX_HEIGHT - 20;

        g.setColor(new Color(0, 0, 0, 180));
        g.fillRoundRect(boxX, boxY, DIALOGUE_BOX_WIDTH, DIALOGUE_BOX_HEIGHT, 15, 15);

        g.setColor(Color.WHITE);
        g.drawRoundRect(boxX, boxY, DIALOGUE_BOX_WIDTH, DIALOGUE_BOX_HEIGHT, 15, 15);

        g.setFont(new Font("Arial", Font.PLAIN, 14));
        g.drawString(text, boxX + 10, boxY + 30);
    }

    private void updateBehavior(int[][] lvlData, Playing playing) {
        if (firstUpdate)
            firstUpdateCheck(lvlData);

        if (inAir) {
            inAirChecks(lvlData, playing);
        } else {
            switch (state) {
                case IDLE:
                    if (IsFloor(hitbox, lvlData)) {
                        if (canSeePlayer(lvlData, playing.getPlayer())) {
                            canSeePlayerNow = true;
                            newState(APPEARING);
                        }
                    }
                    else
                        inAir = true;
                    break;
                case APPEARING:
                    if (aniIndex == 5)
                        newState(TALKING);
                    break;

                case TALKING:
                    turnTowardsPlayer(playing.getPlayer());
                    showDialogue = true;

                    DialogueAction action = requiredActions[currentDialogueIndex];

                    switch (action) {
                        case NONE:
                            advanceDialogue();
                            break;

                        case MOVE:
                            if (currentDialogueIndex == 1 && playing.getPlayer().isRight()) {
                                if (!moveTriggered) {
                                    moveTriggered = true;
                                    advanceDialogue();
                                }
                            }
                            break;

                        case JUMP:
                            if (currentDialogueIndex == 2 && moveTriggered && playing.getPlayer().isJump()) {
                                if (!jumpTriggered) {
                                    jumpTriggered = true;
                                    advanceDialogue();
                                }
                            }
                            break;

                        case ATTACK:
                            if (currentDialogueIndex == 3 && moveTriggered && jumpTriggered && playing.getPlayer().isAttacking()) {
                                if (!attackTriggered) {
                                    attackTriggered = true;
                                    advanceDialogue();
                                }
                            }
                            break;
                    }
                    break;




                case ATTACK:
                    if (aniIndex == 0)
                        attackChecked = false;
                    if (aniIndex == 2 && !attackChecked)
                        checkPlayerHit(attackBox, playing.getPlayer());
//                    newState(DEAD);
                    break;
                case DEAD:
                    showDialogue = false;
                    canSeePlayerNow = false;
                    setActive(false);
                    break;



            }
        }
    }
    public void resetDialogue() {
        currentDialogueIndex = 0;
        dialogueFinished = false;
        showDialogue = false;

        // Reset action triggers
        moveTriggered = false;
        jumpTriggered = false;
        attackTriggered = false;

        // Reset state and make Guider active
        setActive(true);
        canSeePlayerNow = false;
        newState(IDLE); // or APPEARING if you want to trigger the entrance animation again
    }

}