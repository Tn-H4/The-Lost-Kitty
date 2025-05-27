package entities;

import static utilz.Constants.EnemyConstants.*;
import static utilz.HelpMethods.IsFloor;

import gamestates.Playing;

import java.awt.*;

public class Boss extends Enemy {

    private boolean canSeePlayerNow = false;
    private final String[] dialogueLines;
    private int currentDialogueIndex = 0;
    private boolean showDialogue = false;
    private boolean dialogueFinished = false;

    private static final int DIALOGUE_BOX_WIDTH = 260;
    private static final int DIALOGUE_BOX_HEIGHT = 80;

    public Boss(float x, float y) {
        super(x, y, BOSS_WIDTH, BOSS_HEIGHT, BOSS);
        initHitbox(22, 19);
        initAttackBox(22, 19, 20);
        dialogueLines = DialogueManager.getDialogueForEnemy(enemyType);
    }

    public void update(int[][] lvlData, Playing playing) {
        updateBehavior(lvlData, playing);
        updateAnimationTick();
        updateAttackBoxFlip();
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
                    if (IsFloor(hitbox, lvlData))
                        newState(RUNNING);
                    else
                        inAir = true;
                    break;

                case RUNNING:
                    if (canSeePlayer(lvlData, playing.getPlayer())) {
                        turnTowardsPlayer(playing.getPlayer());
                        if (isPlayerCloseForAttack(playing.getPlayer()))
                            newState(ATTACK);
                    }
                    move(lvlData);
                    break;

                case ATTACK:
                    if (aniIndex == 0)
                        attackChecked = false;
                    if (aniIndex == 2 && !attackChecked)
                        checkPlayerHit(attackBox, playing.getPlayer());
                    break;
            }
        }
    }
    public void resetDialogue() {
        currentDialogueIndex = 0;
        dialogueFinished = false;
        showDialogue = false;

        setActive(true);
        canSeePlayerNow = false;
        newState(IDLE); // or APPEARING if you want to trigger the entrance animation again
    }

}