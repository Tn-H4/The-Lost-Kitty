package objects;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entities.Enemy;
import entities.Player;
import gamestates.Playing;
import levels.Level;
import main.Game;
import utilz.LoadSave;

import static utilz.Constants.ObjectConstants.*;
import static utilz.HelpMethods.CanBeeSeePlayer;
import static utilz.HelpMethods.IsStingHittingLevel;
import static utilz.Constants.Sting.*;

public class ObjectManager {

    private Playing playing;
    private BufferedImage fishImg, vinesImg, stingImg, entranceImg, treeImg, brushImg;
    private BufferedImage[] beeImgs;
    private ArrayList<Fish> fish;
    private ArrayList<Bee> bees;
    private ArrayList<Sting> stings = new ArrayList<>();

    private Level currentLevel;

    public ObjectManager(Playing playing) {
        this.playing = playing;
        currentLevel = playing.getLevelManager().getCurrentLevel();
        loadImgs();
    }

    public void checkVinesTouched(Player p) {
        for (Vines v : currentLevel.getVines())
            if (v.getHitbox().intersects(p.getHitbox()))
                p.kill();
    }

    public void checkVinesTouched(Enemy e) {
        for (Vines v : currentLevel.getVines())
            if (v.getHitbox().intersects(e.getHitbox()))
                e.hurt(200);
    }

    public void checkFishTouched(Rectangle2D.Float hitbox) {
        for (Fish f : fish)
            if (f.isActive()) {
                if (hitbox.intersects(f.getHitbox())) {
                    f.setActive(false);
                    applyEffectToPlayer();
                }
            }
    }

    public void checkEntranceTouched(Rectangle2D.Float hitbox) {
        for (Entrance e : currentLevel.getEntrance())
            if (e.isActive()) {
                if (hitbox.intersects(e.getHitbox())) {
                    playing.setLevelCompleted(true);
                }
            }
    }

    public void applyEffectToPlayer() {
            playing.getPlayer().changeHealth(FISH_VALUE);
    }

    public void loadObjects(Level newLevel) {
        currentLevel = newLevel;
        fish = new ArrayList<>(newLevel.getFishes());
        bees = newLevel.getBees();

        stings.clear();
    }

    private void loadImgs() {
        entranceImg = LoadSave.GetSpriteAtlas(LoadSave.ENTRANCE_IMG);

        fishImg = LoadSave.GetSpriteAtlas(LoadSave.FISH_IMG);

        vinesImg = LoadSave.GetSpriteAtlas(LoadSave.TRAP_IMG);

        treeImg = LoadSave.GetSpriteAtlas(LoadSave.TREE_IMG);

        brushImg = LoadSave.GetSpriteAtlas(LoadSave.BRUSH_IMG);

        beeImgs = new BufferedImage[7];
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.BEE_IMG);
        for (int i = 0; i < beeImgs.length; i++)
            beeImgs[i] = temp.getSubimage(i * 40, 0, 40, 26);

        stingImg = LoadSave.GetSpriteAtlas(LoadSave.STING_IMG);
    }

    public void update(int[][] lvlData, Player player) {
        for (Fish f : fish)
            if (f.isActive())
                f.update();

        for (Entrance e : currentLevel.getEntrance())
            if (e.isActive())
                e.update();

        updateBee(lvlData, player);
        updateSting(lvlData, player);
    }

    private void updateSting(int[][] lvlData, Player player) {
        for (Sting s : stings)
            if (s.isActive()) {
                s.updatePos();
                if (s.getHitbox().intersects(player.getHitbox())) {
                    player.changeHealth(-25);
                    s.setActive(false);
                } else if (IsStingHittingLevel(s, lvlData))
                    s.setActive(false);
            }
    }

    private boolean isPlayerInRange(Bee c, Player player) {
        int absValue = (int) Math.abs(player.getHitbox().x - c.getHitbox().x);
        return absValue <= Game.TILES_SIZE * 5;
    }

    private boolean isPlayerInfrontOfBee(Bee b, Player player) {
        if (b.getObjType() == BEE_LEFT) {
            if (b.getHitbox().x > player.getHitbox().x)
                return true;

        } else if (b.getHitbox().x < player.getHitbox().x)
            return true;
        return false;
    }

    private void updateBee(int[][] lvlData, Player player) {
        for (Bee b : bees) {
            if (!b.doAnimation)
                if (b.getTileY() == player.getTileY())
                    if (isPlayerInRange(b, player))
                        if (isPlayerInfrontOfBee(b, player))
                            if (CanBeeSeePlayer(lvlData, player.getHitbox(), b.getHitbox(), b.getTileY()))
                                b.setAnimation(true);

            b.update();
            if (b.getAniIndex() == 4 && b.getAniTick() == 0)
                shootSting(b);
        }
    }

    private void shootSting(Bee c) {
        int dir = 1;
        if (c.getObjType() == BEE_LEFT)
            dir = -1;

        stings.add(new Sting((int) c.getHitbox().x, (int) c.getHitbox().y, dir));
    }

    public void draw(Graphics g, int xLvlOffset) {
        drawFishes(g, xLvlOffset);
        drawVines(g, xLvlOffset);
        drawBees(g, xLvlOffset);
        drawSting(g, xLvlOffset);
        drawBrush(g, xLvlOffset);
        drawTree(g, xLvlOffset);
        drawEntrance(g, xLvlOffset);
    }

    public void drawTree(Graphics g, int xLvlOffset) {
        for (Tree tree : currentLevel.getTrees())
            g.drawImage(treeImg, tree.getX() - xLvlOffset + TREE_OFFSET_X, tree.getY() + TREE_OFFSET_Y, TREE_WIDTH, TREE_HEIGHT, null);
    }

    private void drawBrush(Graphics g, int xLvlOffset) {
        for (Brush brush : currentLevel.getBrushes())
            g.drawImage(brushImg, brush.getX() - xLvlOffset, brush.getY(), (int) (32 * Game.SCALE), (int) (32 * Game.SCALE), null);
    }

    private void drawSting(Graphics g, int xLvlOffset) {
        for (Sting s : stings)
            if (s.isActive())
                g.drawImage(stingImg, (int) (s.getHitbox().x - xLvlOffset), (int) (s.getHitbox().y), STING_WIDTH, STING_HEIGHT, null);

    }

    private void drawBees(Graphics g, int xLvlOffset) {
        for (Bee b : bees) {
            int x = (int) (b.getHitbox().x - xLvlOffset);
            int width = BEE_WIDTH;

            if (b.getObjType() == BEE_RIGHT) {
                x += width;
                width *= -1;
            }

            g.drawImage(beeImgs[b.getAniIndex()], x, (int) (b.getHitbox().y), width, BEE_HEIGHT, null);
        }

    }

    private void drawVines(Graphics g, int xLvlOffset) {
        for (Vines v : currentLevel.getVines())
            g.drawImage(vinesImg, (int) (v.getHitbox().x - xLvlOffset), (int) (v.getHitbox().y - v.getyDrawOffset()), VINE_WIDTH, VINE_HEIGHT, null);

    }

    private void drawFishes(Graphics g, int xLvlOffset) {
        for (Fish f : fish)
            if (f.isActive()) {
                g.drawImage(fishImg, (int) (f.getHitbox().x - f.getxDrawOffset() - xLvlOffset), (int) (f.getHitbox().y - f.getyDrawOffset()), FISH_WIDTH, FISH_HEIGHT,
                        null);
            }
    }

    private void drawEntrance(Graphics g, int xLvlOffset) {
        for (Entrance e : currentLevel.getEntrance())
            if (e.isActive()) {
                g.drawImage(entranceImg, (int) (e.getHitbox().x - e.getxDrawOffset() - xLvlOffset), (int) (e.getHitbox().y - e.getyDrawOffset()), ENTRANCE_WIDTH, ENTRANCE_HEIGHT,
                        null);
            }
    }

    public void resetAllObjects() {
        loadObjects(playing.getLevelManager().getCurrentLevel());
        for (Fish f : fish)
            f.reset();
        for (Bee b : bees)
            b.reset();
    }
}
