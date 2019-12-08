import org.newdawn.slick.*;

public abstract class GameObject {
    public float posx;
    public float posy;
    public float sizex;
    public float sizey;
    public boolean isSolid = false;
    public boolean isDestinationPoint = false;
    public boolean isCollected = false;
    public boolean isCollectible;
    public boolean isDangerous;

    public abstract void draw(float x, float y);

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
