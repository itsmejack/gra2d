import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Enemy extends Creature {
    private Image image;

    Enemy(float posx, float posy) throws SlickException {
        isCollectible = false;
        isDangerous = true;

        this.posx = posx;
        this.posy = posy;
        this.sizex = GameConstants.BLOCK_SIZE;
        this.sizey = GameConstants.BLOCK_SIZE;
        this.image = new Image("src\\enemy.png");
    }

    public void draw(float x, float y) {
        image.draw(x, y);
    }
}
