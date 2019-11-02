import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Player  extends Creature {

    public Animation[] standAnimation = new Animation[2];
    public Animation[] walkAnimation = new Animation[2];
    public Animation[] crouchAnimation = new Animation[2];
    public Animation[] crabWalkAnimation = new Animation[2];
    public Animation[] flyAnimation = new Animation[2];
    public Animation[] duckJumpAnimation = new Animation[2];

    public boolean isHeadingRight = true;
    public boolean isCrouching = false;
    public boolean isFlying = false;
    public boolean isWalking = false;

    public Player() throws SlickException {

        for (int i = 0; i < 2; i++) {
            String name = (i==0?"l":"r");
            standAnimation[i] = new Animation(new SpriteSheet("C:\\Users\\Student236794\\Desktop\\stand"+ name +".png", 64,128), 300);
            walkAnimation[i] = new Animation(new SpriteSheet("C:\\Users\\Student236794\\Desktop\\walkr"+ name +".png", 64,128), 150);
            crouchAnimation[i] = new Animation(new SpriteSheet("C:\\Users\\Student236794\\Desktop\\stand"+ name +".png", 64,128), 300);
            crabWalkAnimation[i] = new Animation(new SpriteSheet("C:\\Users\\Student236794\\Desktop\\stand"+ name +".png", 64,128), 300);
            flyAnimation[i] = new Animation(new SpriteSheet("C:\\Users\\Student236794\\Desktop\\jump"+ name +".png", 64,128), 300);
            duckJumpAnimation[i] = new Animation(new SpriteSheet("C:\\Users\\Student236794\\Desktop\\stand"+ name +".png", 64,128), 300);
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

        //tutaj trzeba tez ustawic wymiary gracza
        if(isCrouching && isFlying) {
            animation = duckJumpAnimation[direction];
        } else if(!isCrouching && isFlying) {
            animation = flyAnimation[direction];
        } else if(isWalking && isCrouching && !isFlying) {
            animation = crabWalkAnimation[direction];
        } else if(isWalking && !isCrouching && !isFlying) {
            animation = walkAnimation[direction];
        } else if(!isWalking && isCrouching && !isFlying) {
            animation = crouchAnimation[direction];
        } else if(!isWalking && !isCrouching && !isFlying) {
            animation = standAnimation[direction];
        }
    }

}
