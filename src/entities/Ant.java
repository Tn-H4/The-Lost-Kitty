package entities;

import static utilz.Constants.EnemyConstants.*;

import java.awt.geom.Rectangle2D;

import static utilz.Constants.Directions.*;

import main.Game;
//Ant function: hit when the player go through it
public class Ant extends Enemy {

    private int attackBoxOffsetX, hitboxOffsetX;

    public Ant(float x, float y) {
        super(x, y, ANT_WIDTH, ANT_HEIGHT, ANT);
        initHitbox(42, 19);
        initAttackBox(42, 19, 30);
    }

    public void update(int[][] lvlData, Player player) {
        updateBehavior(lvlData, player);
        updateAnimationTick();
        updateAttackBox();
    }

    private void updateBehavior(int[][] lvlData, Player player) {
        if (firstUpdate)
            firstUpdateCheck(lvlData);

        if (inAir)
            updateInAir(lvlData);
        else {
            switch (state) {
                case IDLE:
                    newState(RUNNING);
                case RUNNING:

                        if (isPlayerCloseForAttack(player))
                            newState(ATTACK);

                    move(lvlData);
                    break;
                case ATTACK:
                    if (aniIndex == 0)
                        attackChecked = false;
                    if (aniIndex == 1 && !attackChecked)
                        checkPlayerHit(attackBox, player);
                    break;
                case DEAD:
                    break;
            }
        }
    }

    public int flipX() {
        if (walkDir == RIGHT)
            return width;
        else
            return 0;
    }

    public int flipW() {
        if (walkDir == RIGHT)
            return -1;
        else
            return 1;
    }
}