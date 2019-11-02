import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class MapFragment  extends GameObject {
    public Image image;

    public MapFragment(float posx, float posy) throws SlickException {
        this.posx = posx;
        this.posy = posy;
        this.sizex = 64f;
        this.sizey = 64f;
        this.image = new Image("C:\\Users\\Student236794\\Desktop\\aaa1080p.png");
    }

}
