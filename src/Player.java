import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

class Player  extends Creature {

    private Animation[] standAnimation = new Animation[2];
    private Animation[] walkAnimation = new Animation[2];
    private Animation[] flyAnimation = new Animation[2];

    boolean isHeadingRight = true;
    boolean isFlying = false;
    boolean isWalking = false;

    private static Player instance;

    static Player getInstance() throws SlickException {
        if(instance != null) {
            return instance;
        }
        instance = new Player();
        return instance;
    }

    private Player() throws SlickException {

        for (int i = 0; i < 2; i++) {
            String name = (i==0?"l":"r");
            standAnimation[i] = new Animation(new SpriteSheet("src\\stand"+ name +".png", 64,128), 400);
            walkAnimation[i] = new Animation(new SpriteSheet("src\\walkr"+ name +".png", 64,128), 150);
            flyAnimation[i] = new Animation(new SpriteSheet("src\\jump"+ name +".png", 64,128), 300);
        }

        sizex = 64f;
        sizey = 128f;
        speedx = 0f;
        speedy = 0f;

        animation = standAnimation[1];
        isCollectible = false;
        isDangerous = false;
    }

    void updateState() {
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
