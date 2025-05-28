package entities;

import static utilz.Constants.MobConstants.*;
import static utilz.HelpMethods.IsFloor;

import gamestates.Playing;

public class Mantis extends Mob {
//Mantis function: can see player and will follow to attack player
    public Mantis(float x, float y) {
        super(x, y, MANTIS_WIDTH, MANTIS_HEIGHT, MANTIS);
        initHitbox(22, 19);
        initAttackBox(22, 19, 20);
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

}