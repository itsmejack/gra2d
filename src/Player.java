import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Player  extends Creature {

    public Animation[] standAnimation = new Animation[2];
    public Animation[] walkAnimation = new Animation[2];
    public Animation[] flyAnimation = new Animation[2];

    public boolean isHeadingRight = true;
    public boolean isFlying = false;
    public boolean isWalking = false;

    public Player() throws SlickException {

        for (int i = 0; i < 2; i++) {
            String name = (i==0?"l":"r");
            standAnimation[i] = new Animation(new SpriteSheet("src\\stand"+ name +".png", 64,128), 300);
            walkAnimation[i] = new Animation(new SpriteSheet("src\\walkr"+ name +".png", 64,128), 150);
            flyAnimation[i] = new Animation(new SpriteSheet("src\\jump"+ name +".png", 64,128), 300);
        }

        sizex = 64f;
        sizey = 128f;
        speedx = 0f;
        speedy = 0f;

        animation = standAnimation[1];

    }

    public void updateState() {
        int direction = 0;
        if(isHeadingRight) {
            direction = 1;
        }

        if(isFlying) {
            animation = flyAnimation[direction];
        } else if(isWalking) {
            animation = walkAnimation[direction];
        }  else {
            animation = standAnimation[direction];
        }
    }

}
