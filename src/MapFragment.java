import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class MapFragment  extends GameObject {
    public MapFragment(float posx, float posy) throws SlickException {
        this.posx = posx;
        this.posy = posy;
        this.sizex = 32f;
        this.sizey = 32f;
        this.defaultImage = new Image("C:\\Users\\Student236794\\Desktop\\aaa.png");
    }

}
