import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class MapItem extends GameObject{
    public Image image;

    public MapItem(float posx, float posy) throws SlickException {
        this.posx = posx;
        this.posy = posy;
        this.sizex = GameConstants.BLOCK_SIZE;
        this.sizey = GameConstants.BLOCK_SIZE;
        this.image = new Image("src\\coin.png");
        isCollectible = true;
        isDangerous = false;
    }

    public void draw(float x, float y) {
        image.draw(x, y);
    }
}
