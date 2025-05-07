package entities;

import static utilz.Constants.EnemyConstants.*;
import static utilz.HelpMethods.IsFloor;

import gamestates.Playing;
//Ant function: hit when the player go through it
public class Ant extends Enemy {

    public Ant(float x, float y) {
        super(x, y, ANT_WIDTH, ANT_HEIGHT, ANT);
        initHitbox(42, 19);
        initAttackBox(42, 19, 30);
    }

    public void update(int[][] lvlData, Playing playing) {
        updateBehavior(lvlData, playing);
        updateAnimationTick();
        updateAttackBox();
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
                    if (isPlayerCloseForAttack(playing.getPlayer()))
                        newState(ATTACK);

                    move(lvlData);
                    break;

                case ATTACK:
                    if (aniIndex == 0)
                        attackChecked = false;
                    if (aniIndex == 1 && !attackChecked)
                        checkPlayerHit(attackBox, playing.getPlayer());
                    break;
                case DEAD:
                    break;
            }
        }
    }
}