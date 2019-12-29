import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Enemy extends Creature {
    Enemy(float posx, float posy) throws SlickException {
        isCollectible = false;
        isDangerous = true;

        this.posx = posx+1;
        this.posy = posy+1;
        this.sizex = GameConstants.BLOCK_SIZE-2;
        this.sizey = GameConstants.BLOCK_SIZE-2;
        this.animation = new Animation(new SpriteSheet("src\\enemy.png",64,64),50);
    }

    public void draw(float x, float y) {
        animation.draw(x, y);
    }
}
