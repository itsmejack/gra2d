import org.newdawn.slick.Animation;

public class Creature extends GameObject{
    float speedx;
    float speedy;
    Animation animation;

    public void draw(float x, float y) {
        animation.draw(x, y);
    }
}
