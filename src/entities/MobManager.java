package entities;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import gamestates.Playing;
import levels.Level;
import utilz.LoadSave;

import static utilz.Constants.MobConstants.*;

public class MobManager {

    private Playing playing;
    private BufferedImage[][] antArr, mantisArr, guiderArr, bossArr;
    private Level currentLevel;

    public MobManager(Playing playing) {
        this.playing = playing;
        loadMobImgs();
    }

    public void loadMobs(Level level) {
        this.currentLevel = level;
    }

    public void update(int[][] lvlData) {
        for (Ant a : currentLevel.getAnts())
            if (a.isActive()) {
                a.update(lvlData, playing);
            }

        for (Mantis m : currentLevel.getMantis())
            if (m.isActive()) {
                m.update(lvlData, playing);
            }

        for (Guider g : currentLevel.getGuider())
            if (g.isActive()) {
                g.update(lvlData, playing);
            }

        for (Boss b : currentLevel.getBoss())
            if (b.isActive()) {
                b.update(lvlData, playing);
            }
    }

    public void draw(Graphics g, int xLvlOffset) {
        drawAnt(g, xLvlOffset);
        drawMantis(g, xLvlOffset);
        drawGuider(g, xLvlOffset);
        drawBoss(g, xLvlOffset);
    }

    private void drawAnt(Graphics g, int xLvlOffset) {
        for (Ant a : currentLevel.getAnts())
            if (a.isActive()) {
                g.drawImage(antArr[a.getState()][a.getAniIndex()], (int) a.getHitbox().x - xLvlOffset - ANT_DRAWOFFSET_X + a.flipX(), (int) a.getHitbox().y - ANT_DRAWOFFSET_Y,
                        ANT_WIDTH * a.flipW(), ANT_HEIGHT, null);

//                a.drawHitbox(g, xLvlOffset);
//                a.drawAttackBox(g, xLvlOffset);
            }
    }

    private void drawMantis(Graphics g, int xLvlOffset) {
        for (Mantis m : currentLevel.getMantis())
            if (m.isActive()) {
                g.drawImage(mantisArr[m.getState()][m.getAniIndex()], (int) m.getHitbox().x - xLvlOffset - MANTIS_DRAWOFFSET_X + m.flipX(), (int) m.getHitbox().y - MANTIS_DRAWOFFSET_Y,
                        MANTIS_WIDTH * m.flipW(), MANTIS_HEIGHT, null);

//                m.drawHitbox(g, xLvlOffset);
//                m.drawAttackBox(g, xLvlOffset);
            }
    }

    private void drawGuider(Graphics g, int xLvlOffset) {
        for (Guider gu : currentLevel.getGuider())
            if (gu.isActive() && gu.isSeeingPlayer()) {
                g.drawImage(guiderArr[gu.getState()][gu.getAniIndex()], (int) gu.getHitbox().x - xLvlOffset - GUIDER_DRAWOFFSET_X + gu.flipX(), (int) gu.getHitbox().y - GUIDER_DRAWOFFSET_Y,
                        GUIDER_WIDTH * gu.flipW(), GUIDER_HEIGHT, null);

                gu.renderDialogue(g, xLvlOffset);
//                gu.drawHitbox(g, xLvlOffset);
//                gu.drawAttackBox(g, xLvlOffset);
            }
    }

    private void drawBoss(Graphics g, int xLvlOffset) {
        for (Boss b : currentLevel.getBoss())
            if (b.isActive()) {
                g.drawImage(bossArr[b.getState()][b.getAniIndex()], (int) b.getHitbox().x - xLvlOffset - BOSS_DRAWOFFSET_X + b.flipX(), (int) b.getHitbox().y - BOSS_DRAWOFFSET_Y,
                        BOSS_WIDTH * b.flipW(), BOSS_HEIGHT, null);

                b.renderDialogue(g, xLvlOffset);
//                b.drawHitbox(g, xLvlOffset);
//                b.drawAttackBox(g, xLvlOffset);
            } else
                playing.setLevelCompleted(true);

    }

    public void checkMobHit(Rectangle2D.Float attackBox) {
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

        for (Guider g : currentLevel.getGuider())
            if (g.isActive())
                if (g.getState() != DEAD)
                    if (attackBox.intersects(g.getHitbox())) {
                        g.hurt(10);
                        return;
                    }

        for (Boss b : currentLevel.getBoss())
            if (b.isActive())
                if (b.getState() != DEAD)
                    if (attackBox.intersects(b.getHitbox())) {
                        b.hurt(10);
                        return;
                    }
    }

    private void loadMobImgs() {
        mantisArr = getImgArr(LoadSave.GetSpriteAtlas(LoadSave.MANTIS_SPRITE), 7, 4, MANTIS_WIDTH_DEFAULT, MANTIS_HEIGHT_DEFAULT);
        antArr = getImgArr(LoadSave.GetSpriteAtlas(LoadSave.ANT_SPRITE), 5, 4, ANT_WIDTH_DEFAULT, ANT_HEIGHT_DEFAULT);
        guiderArr = getImgArr(LoadSave.GetSpriteAtlas(LoadSave.GUIDER_SPRITE), 6, 6, GUIDER_WIDTH_DEFAULT, GUIDER_HEIGHT_DEFAULT);
        bossArr = getImgArr(LoadSave.GetSpriteAtlas(LoadSave.BOSS_SPRITE), 6, 6, BOSS_WIDTH_DEFAULT, BOSS_HEIGHT_DEFAULT);
    }

    private BufferedImage[][] getImgArr(BufferedImage atlas, int xSize, int ySize, int spriteW, int spriteH) {
        BufferedImage[][] tempArr = new BufferedImage[ySize][xSize];
        for (int j = 0; j < tempArr.length; j++)
            for (int i = 0; i < tempArr[j].length; i++)
                tempArr[j][i] = atlas.getSubimage(i * spriteW, j * spriteH, spriteW, spriteH);
        return tempArr;
    }

    public void resetAllMobs() {
        for (Ant a : currentLevel.getAnts())
            a.resetEnemy();
        for (Mantis m : currentLevel.getMantis())
            m.resetEnemy();
        for (Guider g : currentLevel.getGuider()) {
            g.resetEnemy();
            g.resetDialogue();
        }
        for (Boss b : currentLevel.getBoss()) {
            b.resetEnemy();
            b.resetDialogue();
        }

    }

}
