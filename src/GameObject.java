import org.newdawn.slick.*;

public abstract class GameObject {
    public float posx;
    public float posy;
    public float sizex;
    public float sizey;
    //public Image defaultImage;

    public float getLeft() {
        return posx;
    }

    public float getRight() {
        return posx+sizex;
    }

    public float getTop() {
        return posy;
    }

    public float getBottom() {
        return posy+sizey;
    }
}
