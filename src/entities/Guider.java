package entities;

import static utilz.Constants.MobConstants.*;
import static utilz.HelpMethods.IsFloor;

import gamestates.Playing;

import java.awt.*;

public class Guider extends Mob {

    private boolean canSeePlayerNow = false;
    private boolean showDialogue = false;

    private boolean moveTriggered = false;
    private boolean jumpTriggered = false;
    private boolean attackTriggered = false;

    private final DialogueManager dialogueManager;

    public Guider(float x, float y) {
        super(x, y, GUIDER_WIDTH, GUIDER_HEIGHT, GUIDER);
        initHitbox(22, 22);
        initAttackBox(42, 19, 10);
        this.dialogueManager = new DialogueManager(GUIDER);
    }

    public void update(int[][] lvlData, Playing playing) {
        updateBehavior(lvlData, playing);
        updateAnimationTick();
        updateAttackBox();
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
                    } else {
                        inAir = true;
                    }
                    break;

                case APPEARING:
                    if (aniIndex == 5)
                        newState(TALKING);
                    break;

                case TALKING:
                    showDialogue = true;

                    if (playing.getPlayer().isAttacking() && playing.getPlayer().getHitbox().intersects(this.hitbox)) {
                        showDialogue = false;
                        newState(ATTACK);
                        break;
                    }// If guider is attacked during talk

                    if (!dialogueManager.isFinished()) {
                        DialogueAction action = dialogueManager.getCurrentRequiredAction();

                        switch (action) {
                            case CHECK_NONE -> dialogueManager.updateAutoAdvance();

                            case CHECK_MOVE -> {
                                if (playing.getPlayer().isRight() && !moveTriggered) {
                                    moveTriggered = true;
                                    dialogueManager.advance();
                                }
                            }

                            case CHECK_JUMP -> {
                                if (moveTriggered && playing.getPlayer().isJump() && !jumpTriggered) {
                                    jumpTriggered = true;
                                    dialogueManager.advance();
                                }
                            }

                            case CHECK_ATTACK -> {
                                if (jumpTriggered && playing.getPlayer().isAttacking() && !attackTriggered) {
                                    attackTriggered = true;
                                    dialogueManager.advance();
                                }
                            }
                        }
                    } else {
                        showDialogue = false;
                        newState(DEAD);
                    }
                    break;

                case ATTACK:
                    if (aniIndex == 0)
                        attackChecked = false;
                    if (aniIndex == 4 && !attackChecked)
                        checkPlayerHit(attackBox, playing.getPlayer());
                    break;

                case DEAD:
                    if (aniIndex == 5) {
                        showDialogue = false;
                        canSeePlayerNow = false;
                        setActive(false);
                    }
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
        moveTriggered = jumpTriggered = attackTriggered = false;
        setActive(true);
        newState(IDLE);
    }
}
