import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.lwjgl.Sys;
import org.newdawn.slick.*;


public class SimpleSlickGame extends BasicGame
{
    public SimpleSlickGame(String gamename) throws SlickException {
        super(gamename);
    }

    public Player player;
    public Map map;
    protected int timer = 0;

    protected float maxSpeed = 15f;
    protected int mapSizeX ;
    protected int mapSizeY ;
    protected boolean isPaused = false;
    protected float startPosY;
    protected String menuText = "";
    private ApplicationState state = new MenuState(this);

    @Override
    public void init(GameContainer gc) throws SlickException {
        gc.setVSync(true);
        gc.setMaximumLogicUpdateInterval(10);
        gc.setTargetFrameRate(60);
    }

    @Override
    public void update(GameContainer gc, int delta) throws SlickException {
        state.update(gc, delta);
    }
    
    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException
    {
        state.drawState(gc, g);
    }

    public void changeState(ApplicationState newState) {
        this.state = newState;
    }

    public static void main(String[] args)
    {
        try
        {
            AppGameContainer appgc;
            appgc = new AppGameContainer(new SimpleSlickGame("Chasing"));
            //appgc.setDisplayMode(1920,1080, false); //for me only xd
            appgc.setDisplayMode(appgc.getScreenWidth(),appgc.getScreenHeight(), true); //<<this final
            appgc.start();
        }
        catch (SlickException ex)
        {
            Logger.getLogger(SimpleSlickGame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}