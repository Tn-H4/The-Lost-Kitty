package objects;

import main.Game;

public class Entrance extends GameObject {

    private float hoverOffset;
    private int maxHoverOffset, hoverDir = 1;

    public Entrance(int x, int y, int objType) {
        super(x, y, objType);

        initHitbox(32, 32);

        xDrawOffset = 0;
        yDrawOffset = (int)(Game.SCALE * 16);

        maxHoverOffset = (int) (10 * Game.SCALE);
    }

    public void update() {
        updateHover();
    }

    private void updateHover() {
        hoverOffset += (0.075f * Game.SCALE * hoverDir);

        if (hoverOffset >= maxHoverOffset)
            hoverDir = -1;
        else if (hoverOffset < 0)
            hoverDir = 1;

        hitbox.y = y + hoverOffset;
    }
}
