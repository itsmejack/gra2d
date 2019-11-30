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
    private int mapSizeX = 10;
    private int mapSizeY = 2;
    private float blockSize = 64f;
    private int chunkSize = 10;
    private boolean isFinished = false;
    private boolean isPaused = false;
    private float startPosY;
    private String menuText = "";

    @Override
    public void init(GameContainer gc) throws SlickException {
        player = new Player();
        map = new Map();
        player.posx = blockSize/2;
        startPosY = map.generateMap(mapSizeX, mapSizeY);
        player.posy = startPosY;

        gc.setVSync(true);
        gc.setMaximumLogicUpdateInterval(10);
        gc.setTargetFrameRate(60);

    }

    @Override
    public void update(GameContainer gc, int i) throws SlickException {
        if(!isPaused) {
            if(isDead()) {
                menuText = "You are dead";
                isPaused = true;
            }
            if(map.isFinished) {
                menuText = "You won";
                isPaused = true;
            }
            handleGame(gc);
            updatePositionsAndSpeedAndStates();
        } else {
            handleMenu(gc);
        }
    }

    private void handleMenu(GameContainer gc) throws SlickException {
        if(gc.getInput().isKeyPressed(Input.KEY_N)) {
            player = new Player();
            map = new Map();
            player.posx = blockSize/2;
            startPosY = map.generateMap(mapSizeX, mapSizeY);
            player.posy = startPosY;
            isPaused = false;
        } else if(gc.getInput().isKeyPressed(Input.KEY_R)) {
            player = new Player();
            player.posx = blockSize/2;
            player.posy = startPosY;
            map.isFinished = false;
            isPaused = false;
        }
    }
    
    private void addRestartHandler(GameContainer gc){
        if(gc.getInput().isKeyPressed(Input.KEY_R)) {
            player.posx = blockSize/2;
            player.posy = startPosY;
        }
    }

    private boolean isDead() {
        if(player.posy > (mapSizeY+5)*blockSize*chunkSize) {
            return true;
        }
        return false;
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
        } else if(gc.getInput().isKeyPressed(Input.KEY_DOWN) && player.speedy==0f) {
            player.posy+=1f;
        } else{
            player.speedy+=1f;
            player.speedy=Math.min(player.speedy, maxSpeed);
        }
    }

    private void handleGame(GameContainer gc) throws SlickException {
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

    private void drawBackground(float camerax, float cameray, Graphics g) throws SlickException {
        g.setBackground(new Color(145,40,153));

        Image back2 = new Image("src\\bak2.png");
        Image back3 = new Image("src\\bak3.png");
        Image backg = new Image("src\\backg.png");

        for(int i=0; i<10; i++) {
            backg.draw((-player.posx+camerax)/10+3840*i-100, (-player.posy+cameray)/40-100);
            back3.draw((-player.posx+camerax)/8+3840*i-100, (-player.posy+cameray)/32-100);
            back2.draw((-player.posx+camerax)/6+3840*i-100, (-player.posy+cameray)/24-100);
        }
    }

    private void drawMap(float camerax, float cameray) {
        for (MapFragment block : map.getFragments()) {
            block.image.draw(block.posx-player.posx+camerax, block.posy-player.posy+cameray);
        }
    }

    private void drawPlayer(float camerax, float cameray) {
        player.animation.draw(camerax, cameray);
    }

    private void drawGame(GameContainer gc, Graphics g) throws SlickException {
        float camerax = 960f - player.sizex/2;
        float cameray = 540f - player.sizey/2;

        //g.scale(gc.getScreenWidth()/1920f,gc.getScreenHeight()/1080f); //<< this final
        g.scale(1920/1920f,1080/1080f);
        
        drawBackground(camerax, cameray, g);
        drawMap(camerax, cameray);
        drawPlayer(camerax, cameray);
    }
    
    private void drawMenu(GameContainer gc, Graphics g) {
        g.setBackground(new Color(145,40,153));

        g.drawString("N to start a new game", 100, 100);
        g.drawString("R to retry",100, 200);
        g.drawString(menuText, 100, 300);
    }
    
    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException
    {
        if(isPaused) {
            drawMenu(gc, g);
        } else {
            drawGame(gc, g);
        }
    }

    public static void main(String[] args)
    {
        try
        {
            AppGameContainer appgc;
            appgc = new AppGameContainer(new SimpleSlickGame("SUPER GIERA HEHEH"));
            appgc.setDisplayMode(1920,1080, false); //for me only xd
            //appgc.setDisplayMode(appgc.getScreenWidth(),appgc.getScreenHeight(), true); //<<this final
            appgc.start();
        }
        catch (SlickException ex)
        {
            Logger.getLogger(SimpleSlickGame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}