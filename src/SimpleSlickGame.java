import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.lwjgl.Sys;
import org.newdawn.slick.*;


public class SimpleSlickGame extends BasicGame
{
    public SimpleSlickGame(String gamename) {
        super(gamename);
    }

    public Player player;
    public Map map;
    //public Enemy[] enemies; //arraylista
    //public MapItem[] items; //arraylista

    private float maxSpeed = 15f;

    @Override
    public void init(GameContainer gc) throws SlickException {
        player = new Player();
        player.posx = 200f;
        player.posy = 300f;
        map = new Map();

        for (int i = 0; i < 100; i++) {
            map.add(new MapFragment(i*64f, 16*64f));
            map.add(new MapFragment(i*64f, 2*64f));
            map.add(new MapFragment(i*64f, 320+i*64f));
            map.add(new MapFragment(i*64f, 100*64f));
        }
        gc.setVSync(true);
        gc.setMaximumLogicUpdateInterval(10);
        gc.setTargetFrameRate(60);


    }

    @Override
    public void update(GameContainer gc, int i) {
        handleInput(gc);
        updatePositionsAndSpeedAndStates();
    }

    private void addRestartHandler(GameContainer gc) {
        if(gc.getInput().isKeyPressed(Input.KEY_R)) {
            player.posx = 200f;
            player.posy = 300f;
        }
    }

    private void addXMovementHandler(GameContainer gc) {
        if(gc.getInput().isKeyDown(Input.KEY_RIGHT)) {
            player.isHeadingRight = true;
            player.speedx+=1f;
            player.speedx=Math.min(player.speedx, maxSpeed);
        } else if(gc.getInput().isKeyDown(Input.KEY_LEFT)) {
            player.isHeadingRight = false;
            player.speedx-=1f;
            player.speedx=Math.max(player.speedx, -maxSpeed);
        } else {
            player.speedx-=Math.signum(player.speedx)*0.8;
            if(Math.abs(player.speedx)<1f) {
                player.speedx = 0f;
            }
        }
    }

    private void addYMovementHandler(GameContainer gc) {
        if(gc.getInput().isKeyPressed(Input.KEY_UP) && player.speedy==0f) {
            player.speedy=-25f;
        } else {
            player.speedy+=1f;
            player.speedy=Math.min(player.speedy, maxSpeed);
        }
    }

    private void handleInput(GameContainer gc){
        addRestartHandler(gc);
        addXMovementHandler(gc);
        addYMovementHandler(gc);
        player.updateState();
    }

    private void updateXPosAndSpeed() {
        MapFragment block;
        if(player.speedx>0) {
            block = map.isCollidingWithLeft(player);
            if(block!=null) {
                player.posx = Math.min(player.posx+player.speedx, block.posx - player.sizex);
                player.speedx = 0f;
            }
        }
        else if (player.speedx<0) {
            block = map.isCollidingWithRight(player);
            if(block!=null) {
                player.posx = Math.max(player.posx+player.speedx, block.posx + block.sizex);
                player.speedx = 0f;
            }
        }
        player.posx += player.speedx;
    }

    private void updateYPosAndSpeed() {
        MapFragment block = null;

        if(player.speedy>0) {
            block = map.isCollidingWithTop(player);
            if(block!=null) {
                player.posy = Math.min(player.posy+player.speedy, block.posy - player.sizey);
                player.speedy = 0f;
            }
        }
        else if (player.speedy<0) {
            block = map.isCollidingWithBottom(player);
            if(block!=null) {
                player.posy = Math.max(player.posy+player.speedy, block.posy + block.sizey);
                player.speedy = -player.speedy;
            }
        }

        if(block==null) {
            player.posy += player.speedy;
        }
    }

    private void updatePlayerStates() {
        player.isWalking = (player.speedx != 0f);
        player.isFlying = (player.speedy != 0f);
    }

    private void updatePositionsAndSpeedAndStates() {
        updateXPosAndSpeed();
        updateYPosAndSpeed();
        updatePlayerStates();
        player.updateState();
    }


    public void drawPos(Graphics g) {
        g.drawString(Float.toString(player.speedx), 100, 100);
        g.drawString(Float.toString(player.speedy), 160, 100);
        g.drawString(Float.toString(player.posx), 100, 150);
        g.drawString(Float.toString(player.posy), 160, 150);
    }

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException
    {
        //drawBackground();
        //drawPlayer();
        //drawMap();
        //drawCreatures();

        //g.scale(gc.getScreenWidth()/1920f,gc.getScreenHeight()/1080f); << this final
        g.scale(1920/1920f,1080/1080f);
        g.setBackground(new Color(255,255,255));
        float camerax = 960f - player.sizex/2;
        float cameray = 540f - player.sizey/2;

        player.animation.draw(camerax, cameray);
        for (MapFragment block : map.getFragments()) {
            block.image.draw(block.posx-player.posx+camerax, block.posy-player.posy+cameray);
        }
    }

    public static void main(String[] args)
    {
        try
        {
            AppGameContainer appgc;
            appgc = new AppGameContainer(new SimpleSlickGame("SUPER GIERA HEHEH"));
            appgc.setDisplayMode(1920,1080, false); //for me only xd
            //appgc.setDisplayMode(appgc.getScreenWidth(),appgc.getScreenHeight(), true); <<this final
            appgc.start();
        }
        catch (SlickException ex)
        {
            Logger.getLogger(SimpleSlickGame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}