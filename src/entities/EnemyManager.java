package entities;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import gamestates.Playing;
import levels.Level;
import utilz.LoadSave;

import static utilz.Constants.EnemyConstants.*;

public class EnemyManager {

    private Playing playing;
    private BufferedImage[][] antArr, mantisArr;
    private Level currentLevel;

    public EnemyManager(Playing playing) {
        this.playing = playing;
        loadEnemyImgs();
    }

    public void loadEnemies(Level level) {
        this.currentLevel = level;
    }

    public void update(int[][] lvlData) {
        boolean isAnyActive = false;
        for (Ant a : currentLevel.getAnts())
            if (a.isActive()) {
                a.update(lvlData, playing);
                isAnyActive = true;
            }

        for (Mantis m : currentLevel.getMantis())
            if (m.isActive()) {
                m.update(lvlData, playing);
                isAnyActive = true;
            }

        if (!isAnyActive)
            playing.setLevelCompleted(true);
    }

    public void draw(Graphics g, int xLvlOffset) {
        drawAnt(g, xLvlOffset);
        drawMantis(g, xLvlOffset);
    }

    private void drawAnt(Graphics g, int xLvlOffset) {
        for (Ant a : currentLevel.getAnts())
            if (a.isActive()) {
                g.drawImage(antArr[a.getState()][a.getAniIndex()], (int) a.getHitbox().x - xLvlOffset - ANT_DRAWOFFSET_X + a.flipX(), (int) a.getHitbox().y - ANT_DRAWOFFSET_Y,
                        ANT_WIDTH * a.flipW(), ANT_HEIGHT, null);

                a.drawHitbox(g, xLvlOffset);
                a.drawAttackBox(g, xLvlOffset);
            }
    }

    private void drawMantis(Graphics g, int xLvlOffset) {
        for (Mantis m : currentLevel.getMantis())
            if (m.isActive()) {
                g.drawImage(mantisArr[m.getState()][m.getAniIndex()], (int) m.getHitbox().x - xLvlOffset - MANTIS_DRAWOFFSET_X + m.flipX(), (int) m.getHitbox().y - MANTIS_DRAWOFFSET_Y,
                        MANTIS_WIDTH * m.flipW(), MANTIS_HEIGHT, null);

                m.drawHitbox(g, xLvlOffset);
                m.drawAttackBox(g, xLvlOffset);
            }
    }

    public void checkEnemyHit(Rectangle2D.Float attackBox) {
        for (Ant a : currentLevel.getAnts())
            if (a.isActive())
                if (a.getState() != DEAD)
                    if (attackBox.intersects(a.getHitbox())) {
                        a.hurt(10);
                    return;
                    }

        for (Mantis m : currentLevel.getMantis())
            if (m.isActive())
                if (m.getState() != DEAD)
                    if (attackBox.intersects(m.getHitbox())) {
                        m.hurt(10);
                        return;
                        }
    }


//    private void loadEnemyImgs() {
//        antArr = new BufferedImage[4][5];
//        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.ANT_SPRITE);
//        for (int j = 0; j < antArr.length; j++)
//            for (int i = 0; i < antArr[j].length; i++)
//                antArr[j][i] = temp.getSubimage(i * ANT_WIDTH_DEFAULT, j * ANT_HEIGHT_DEFAULT, ANT_WIDTH_DEFAULT, ANT_HEIGHT_DEFAULT);
//    }

    private void loadEnemyImgs() {
        mantisArr = getImgArr(LoadSave.GetSpriteAtlas(LoadSave.MANTIS_SPRITE), 7, 4, MANTIS_WIDTH_DEFAULT, MANTIS_HEIGHT_DEFAULT);
        antArr = getImgArr(LoadSave.GetSpriteAtlas(LoadSave.ANT_SPRITE), 5, 4, ANT_WIDTH_DEFAULT, ANT_HEIGHT_DEFAULT);
    }

    private BufferedImage[][] getImgArr(BufferedImage atlas, int xSize, int ySize, int spriteW, int spriteH) {
        BufferedImage[][] tempArr = new BufferedImage[ySize][xSize];
        for (int j = 0; j < tempArr.length; j++)
            for (int i = 0; i < tempArr[j].length; i++)
                tempArr[j][i] = atlas.getSubimage(i * spriteW, j * spriteH, spriteW, spriteH);
        return tempArr;
    }

    public void resetAllEnemies() {
        for (Ant a : currentLevel.getAnts())
            a.resetEnemy();
        for (Mantis m : currentLevel.getMantis())
            m.resetEnemy();
    }

}
