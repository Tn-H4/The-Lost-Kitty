package entities;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import gamestates.Playing;
import levels.Level;
import utilz.LoadSave;
import static utilz.Constants.EnemyConstants.*;

public class EnemyManager {

    private Playing playing;
    private BufferedImage[][] antArr;
    private ArrayList<Ant> ants = new ArrayList<>();

    public EnemyManager(Playing playing) {
        this.playing = playing;
        loadEnemyImgs();
    }

    public void loadEnemies(Level level) {
        ants = level.getAnts();
    }

    public void update(int[][] lvlData, Player player) {
        boolean isAnyActive = false;
        for (Ant c : ants)
            if (c.isActive()) {
                c.update(lvlData, player);
                isAnyActive = true;
            }
        if (!isAnyActive)
            playing.setLevelCompleted(true);
    }

    public void draw(Graphics g, int xLvlOffset) {
        drawAnt(g, xLvlOffset);
    }

    private void drawAnt(Graphics g, int xLvlOffset) {
        for (Ant c : ants)
            if (c.isActive()) {

                g.drawImage(antArr[c.getState()][c.getAniIndex()], (int) c.getHitbox().x - xLvlOffset - ANT_DRAWOFFSET_X + c.flipX(), (int) c.getHitbox().y - ANT_DRAWOFFSET_Y,
                        ANT_WIDTH * c.flipW(), ANT_HEIGHT, null);

                c.drawHitbox(g, xLvlOffset);
                c.drawAttackBox(g, xLvlOffset);
            }

    }

    public void checkEnemyHit(Rectangle2D.Float attackBox) {
        for (Ant c : ants)
            if (c.isActive())
                if (attackBox.intersects(c.getHitbox())) {
                    c.hurt(10);
                    return;
                }
    }

    private void loadEnemyImgs() {
        antArr = new BufferedImage[3][5];
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.ANT_SPRITE);
        for (int j = 0; j < antArr.length; j++)
            for (int i = 0; i < antArr[j].length; i++)
                antArr[j][i] = temp.getSubimage(i * ANT_WIDTH_DEFAULT, j * ANT_HEIGHT_DEFAULT, ANT_WIDTH_DEFAULT, ANT_HEIGHT_DEFAULT);
    }

    public void resetAllEnemies() {
        for (Ant a : ants)
            a.resetEnemy();
    }

}
