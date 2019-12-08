import org.newdawn.slick.Animation;

public class Creature extends GameObject{
    public float speedx;
    public float speedy;
    public Animation animation;

    public void draw(float x, float y) {
        animation.draw(x, y);
    }
}
