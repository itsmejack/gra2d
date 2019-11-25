import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.util.Random;

public class MapFragment  extends GameObject {
    public Image image;

    public MapFragment(float posx, float posy) throws SlickException {
        Random generator = new Random();
        this.posx = posx;
        this.posy = posy;
        this.sizex = 64f;
        this.sizey = 64f;
        this.image = (generator.nextBoolean()?new Image("C:\\Users\\Student236794\\Desktop\\aaa1080p.png"):new Image("C:\\Users\\Student236794\\Desktop\\bbb1080p.png"));
    }

}
