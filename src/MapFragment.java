import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class MapFragment  extends GameObject {
    private Image image;

    MapFragment(float posx, float posy) throws SlickException {
        this.posx = posx;
        this.posy = posy;
        this.sizex = GameConstants.BLOCK_SIZE;
        this.sizey = GameConstants.BLOCK_SIZE;
        this.image = new Image("src\\aaa1080p.png");
        isCollectible = false;
        isDangerous = false;
    }

    MapFragment(float posx, float posy, boolean isDestinationPoint) throws SlickException {
        this.posx = posx;
        this.posy = posy;
        this.sizex = GameConstants.BLOCK_SIZE;
        this.sizey = GameConstants.BLOCK_SIZE;
        this.image = new Image("src\\bbb1080p.png");
        this.isDestinationPoint = isDestinationPoint;
        isCollectible = false;
        isDangerous = false;
    }

    public void draw(float x, float y) {
        image.draw(x, y);
    }
}
