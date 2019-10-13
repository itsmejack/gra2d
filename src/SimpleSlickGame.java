import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.lwjgl.Sys;
import org.newdawn.slick.*;

import static java.lang.Thread.sleep;

public class SimpleSlickGame extends BasicGame
{
    //test
    public SimpleSlickGame(String gamename)
    {
        super(gamename);
    }

    public ArrayList<MapFragment> map; //arraylista
    public Player player;
    //public Enemy[] enemies; //arraylista
    //public MapItem[] items; //arraylista


    public float posx = 0f;
    public float posy = 600f;
    public float spd = 0f;
    public float spdx = 0f;

    @Override
    public void init(GameContainer gc) throws SlickException {
        player = new Player();
        player.posx = 200f;
        player.posy = 300f;
        player.sizex = 110f;
        player.sizey = 200f;
        player.speedx = 0f;
        player.speedy = 0f;
        player.defaultImage = new Image("C:\\Users\\Student236794\\Desktop\\myimage.jpg");

        map = new ArrayList<>();
        map.add(new MapFragment(1400f, 320f));
        //map.add(new MapFragment(100f, 320f));

        //for (int i = 0; i < 100; i++) {
            //map.add(new MapFragment(i*32+50f, 520f));
        //}
        gc.setVSync(true);
        gc.setMaximumLogicUpdateInterval(10);
        gc.setTargetFrameRate(60);


    }

    @Override
    public void update(GameContainer gc, int i) throws SlickException {


        handleInput(gc);
        updatePositions();

    }

    private void handleInput(GameContainer gc) {
        if(gc.getInput().isKeyDown(Input.KEY_RIGHT)) {
            player.speedx = 1f;
        } else if(gc.getInput().isKeyDown(Input.KEY_LEFT)) {
            player.speedx = -0.1f;
        } else {
            player.speedx = 0;
        }


    }

    private void updatePositions() {

        if(player.speedx>0) {
            for (MapFragment block : map) {
                if(isCollidingWithLeft(player, block)) {
                    player.posx = block.posx - player.sizex;
                }
            }
        }
        else if (player.speedx<0) {
            for (MapFragment block : map) {
                if(isCollidingWithRight(player, block)) {
                    player.posx = block.posx + block.sizex;
                }
            }
        }

        if(not collided) {
            player.posx += player.speedx;
        }



    }

    //is collider colliding with top of the destination, for example player jumping on top of block
    private boolean isCollidingWithTop(Creature collider, GameObject destination) {
        if(collider.getBottom()+collider.speedy>destination.getTop() && collider.getBottom()<=destination.getTop()) {
            if(collider.getRight()>destination.getLeft() && destination.getLeft()>collider.getLeft() || collider.getRight()>destination.getRight() && destination.getRight()>collider.getLeft()) {
                return true;
            }
        }
        return false;
    }

    private boolean isCollidingWithBottom(Creature collider, GameObject destination){
        if(collider.getTop()>=destination.getBottom() && collider.getTop()+collider.speedy<destination.getBottom()) {
            if(collider.getRight()>destination.getLeft() && destination.getLeft()>collider.getLeft() || collider.getRight()>destination.getRight() && destination.getRight()>collider.getLeft()) {
                return true;
            }
        }
        return false;
    }

    private boolean isCollidingWithRight(Creature collider, GameObject destination) {
        if(collider.getLeft()>=destination.getRight() && collider.getLeft()+collider.speedx<destination.getRight()){
            if(collider.getBottom()>destination.getTop() && destination.getTop()>collider.getTop() || collider.getBottom()>destination.getBottom() && destination.getBottom()>collider.getTop()) {
                return true;
            }
        }
        return false;
    }

    private boolean isCollidingWithLeft(Creature collider, GameObject destination) {
        if(collider.getRight()+collider.speedx>destination.getLeft() && collider.getRight()<=destination.getLeft()){
            if(collider.getBottom()>destination.getTop() && destination.getTop()>collider.getTop() || collider.getBottom()>destination.getBottom() && destination.getBottom()>collider.getTop()) {
                return true;
            }

        }
        return false;

    }

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException
    {
        //drawBackground();
        //drawPlayer();
        //drawMap();
        //drawCreatures();

        player.defaultImage.draw(300, 300);
        for (MapFragment block : map) {
            block.defaultImage.draw(block.posx-player.posx+300, block.posy-player.posy+300);
        }

        g.drawString(Float.toString(player.speedx), 100, 100);
        g.drawString(Float.toString(player.posx), 100, 150);
        g.drawString(Float.toString(map.get(0).posx), 100, 200);

        Image img = new Image("C:\\Users\\Student236794\\Desktop\\myimage.jpg");
        Image bgg = new Image("C:\\Users\\Student236794\\Desktop\\aaa.png");


    }

    public static void main(String[] args)
    {
        try
        {
            AppGameContainer appgc;
            appgc = new AppGameContainer(new SimpleSlickGame("SUPER GIERA HEHEH"));
            appgc.setDisplayMode(1920, 1080, false);
            appgc.start();
        }
        catch (SlickException ex)
        {
            Logger.getLogger(SimpleSlickGame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}