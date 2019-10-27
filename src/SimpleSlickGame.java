import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.lwjgl.Sys;
import org.newdawn.slick.*;


public class SimpleSlickGame extends BasicGame
{
    public SimpleSlickGame(String gamename)
    {
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
        player.sizex = 64f;//32f;
        player.sizey = 128f;//64f;
        player.speedx = 0f;
        player.speedy = 0f;
        player.defaultImage = new Image("C:\\Users\\Student236794\\Desktop\\testbody1080p.png");

        map = new Map();

        for (int i = 0; i < 100; i++) {
            //map.add(new MapFragment(i*32+50f, 620f));
            //map.add(new MapFragment(i*32+50f, 120f));

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
    public void update(GameContainer gc, int i) throws SlickException {
        handleInput(gc);
        updatePositions();
    }

    private void handleInput(GameContainer gc) throws SlickException {
        if(gc.getInput().isKeyPressed(Input.KEY_R)) {
            player.posx = 200f;
            player.posy = 300f;
        }

        if(gc.getInput().isKeyDown(Input.KEY_RIGHT)) {
            player.defaultImage = new Image("C:\\Users\\Student236794\\Desktop\\walkr.png");
            player.speedx+=1f;
            player.speedx=Math.min(player.speedx, maxSpeed);
        } else if(gc.getInput().isKeyDown(Input.KEY_LEFT)) {
            player.defaultImage = new Image("C:\\Users\\Student236794\\Desktop\\walkl.png");
            player.speedx-=1f;
            player.speedx=Math.max(player.speedx, -maxSpeed);
        } else {
            player.speedx-=Math.signum(player.speedx)*0.8;
            if(Math.abs(player.speedx)<1f) {
                player.speedx = 0f;
            }
        }

        if(player.speedy!=0f) {
            if(player.speedx>=0) {
                player.defaultImage = new Image("C:\\Users\\Student236794\\Desktop\\jumpr.png");
            } else {
                player.defaultImage = new Image("C:\\Users\\Student236794\\Desktop\\jumpl.png");
            }
        } else {
            if(player.speedx>=0) {
                player.defaultImage = new Image("C:\\Users\\Student236794\\Desktop\\walkr.png");
            } else {
                player.defaultImage = new Image("C:\\Users\\Student236794\\Desktop\\walkl.png");
            }
        }

        if(gc.getInput().isKeyPressed(Input.KEY_UP) && player.speedy==0f) {
            player.speedy=-25f;
        } else {
            player.speedy+=1f;
            player.speedy=Math.min(player.speedy, maxSpeed);
        }



    }

    private void updatePositions() {
        boolean isCollided = false;
        float newpos = player.posx+player.speedx;
        MapFragment block = null;

        if(player.speedx>0) {
            block = map.isCollidingWithLeft(player);
            if(block!=null) {
                newpos = Math.min(newpos, block.posx - player.sizex);
                isCollided = true;
            }
        }
        else if (player.speedx<0) {
            block = map.isCollidingWithRight(player);
            if(block!=null) {
                newpos = Math.max(newpos, block.posx + block.sizex);
                isCollided = true;
            }
        }

        if(!isCollided) {
            player.posx += player.speedx;
        } else {
            player.speedx = 0f;
            player.posx = newpos;
        }

        isCollided = false;
        newpos = player.posy+player.speedy;

        if(player.speedy>0) {
            block = map.isCollidingWithTop(player);
            if(block!=null) {
                newpos = Math.min(newpos, block.posy - player.sizey);
                isCollided = true;
            }
        }
        else if (player.speedy<0) {
            block = map.isCollidingWithBottom(player);
            if(block!=null) {
                newpos = Math.max(newpos, block.posy + block.sizey);
                isCollided = true;
            }
        }

        if(!isCollided) {
            player.posy += player.speedy;
        } else {
            if(player.speedy<0) {
                player.speedy = -player.speedy;
            } else {
                player.speedy = 0f;
            }
            player.posy = newpos;
        }



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

        player.defaultImage.draw(camerax, cameray);
        for (MapFragment block : map.getFragments()) {
            block.defaultImage.draw(block.posx-player.posx+camerax, block.posy-player.posy+cameray);
        }

        drawPos(g);
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