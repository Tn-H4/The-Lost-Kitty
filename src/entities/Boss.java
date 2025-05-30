package entities;

import static utilz.Constants.ANI_SPEED;
import static utilz.Constants.MobConstants.*;
import static utilz.Constants.PlayerConstants.DEAD;
import static utilz.Constants.PlayerConstants.GetSpriteAmount;
import static utilz.HelpMethods.IsFloor;

import audio.AudioPlayer;
import gamestates.Playing;

import java.awt.*;

public class Boss extends Mob {

    private boolean canSeePlayerNow = false;
    private boolean showDialogue = false;
    private long stateStartTime;
    private static final int RUN_DURATION = 410;
    private static final int IDLE_DURATION = 500;

    private final DialogueManager dialogueManager;

    public Boss(float x, float y) {
        super(x, y, BOSS_WIDTH, BOSS_HEIGHT, BOSS);
        initHitbox(50, 30);
        initAttackBox(90, 30, 20);
        this.dialogueManager = new DialogueManager(BOSS);
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
                            if (!dialogueManager.isFinished()) {
                                canSeePlayerNow = true;
                                showDialogue = true;
                                newState(TALKING);
                            } else if (System.currentTimeMillis() - stateStartTime >= IDLE_DURATION) {
                                switchState(RUNNING);
                            }
                        }
                    } else {
                        inAir = true;
                    }
                    break;

                case RUNNING:
                    if (System.currentTimeMillis() - stateStartTime >= RUN_DURATION) {
                        switchState(IDLE);
                        break;
                    }
                    if (canSeePlayer(lvlData, playing.getPlayer())) {
                        turnTowardsPlayer(playing.getPlayer());
                        if (isPlayerCloseForAttack(playing.getPlayer())) {
                            newState(ATTACK);
                        }
                    }
                        moveBoss(lvlData);
                    break;

                case TALKING:
                    if (!dialogueManager.isFinished()) {
                        if (canSeePlayer(lvlData, playing.getPlayer()))
                            turnTowardsPlayer(playing.getPlayer());
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
                    playing.getGame().getAudioPlayer().playEffect(AudioPlayer.BOSS_ATTACK);
                    break;

                case DEAD:
                    if (aniIndex == GetSpriteAmount(DEAD) - 1 && aniTick >= ANI_SPEED - 1) {
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

    private void switchState(int newState) {
        state = newState;
        stateStartTime = System.currentTimeMillis();
    }

    public void resetDialogue() {
        dialogueManager.reset();
        showDialogue = false;
        canSeePlayerNow = false;
        setActive(true);
        newState(IDLE);
    }
}
