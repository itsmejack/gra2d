public abstract class GameObject {
    float posx;
    float posy;
    float sizex;
    float sizey;
    boolean isSolid = false;
    boolean isDestinationPoint = false;
    boolean isCollected = false;
    boolean isCollectible;
    boolean isDangerous;

    public abstract void draw(float x, float y);

    float getLeft() {
        return posx;
    }

    float getRight() {
        return posx+sizex;
    }

    float getTop() {
        return posy;
    }

    float getBottom() {
        return posy+sizey;
    }
}
