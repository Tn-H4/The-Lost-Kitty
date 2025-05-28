package entities;

import static utilz.Constants.MobConstants.*;
import static utilz.HelpMethods.IsFloor;

import gamestates.Playing;

import java.awt.*;

public class Boss extends Mob {

    private boolean canSeePlayerNow = false;
    private boolean showDialogue = false;

    private final DialogueManager dialogueManager;

    public Boss(float x, float y) {
        super(x, y, BOSS_WIDTH, BOSS_HEIGHT, BOSS);
        initHitbox(22, 19);
        initAttackBox(22, 19, 20);
        this.dialogueManager = new DialogueManager(BOSS);
    }

    public void update(int[][] lvlData, Playing playing) {
        updateBehavior(lvlData, playing);
        updateAnimationTick();
        updateAttackBoxFlip();
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
                            showDialogue = true;
                            newState(TALKING);
                        }
                    } else {
                        inAir = true;
                    }
                    break;

                case RUNNING:
                    if (canSeePlayer(lvlData, playing.getPlayer())) {
                        turnTowardsPlayer(playing.getPlayer());
                        if (isPlayerCloseForAttack(playing.getPlayer())) {
                            newState(ATTACK);
                        }
                    }
                    if(aniTick == 3)
                        bossMove(lvlData);
                    break;

                case TALKING:
                    if (!dialogueManager.isFinished()) {
                        DialogueAction action = dialogueManager.getCurrentRequiredAction();

                        switch (action) {
                            case CHECK_NONE -> dialogueManager.updateAutoAdvance();
                        }
                    } else {
                        showDialogue = false;
                        newState(RUNNING);
                    }
                    break;

                case ATTACK:
                    if (aniIndex == 0)
                        attackChecked = false;
                    if (aniIndex == 2 && !attackChecked)
                        checkPlayerHit(attackBox, playing.getPlayer());
                    break;

                case DEAD:
                    showDialogue = false;
                    canSeePlayerNow = false;
                    setActive(false);
                    break;
            }
        }
    }

    public void renderDialogue(Graphics g, int xLvlOffset) {
        if (showDialogue && !dialogueManager.isFinished()) {
            dialogueManager.drawDialogueBox(g, (int) hitbox.x, (int) hitbox.y, xLvlOffset);
        }
    }

    public boolean isSeeingPlayer() {
        return canSeePlayerNow;
    }

    public void resetDialogue() {
        dialogueManager.reset();
        showDialogue = false;
        canSeePlayerNow = false;
        setActive(true);
        newState(IDLE);
    }
}
