import java.util.logging.Level;
import java.util.logging.Logger;

import org.newdawn.slick.*;


public class SimpleSlickGame extends BasicGame
{
    //TODO
    //tekstury dokończyc

    private SimpleSlickGame(String gamename) {
        super(gamename);
    }

    Player player;
    Map map;
    int timer = 0;
    MapSize prevSize;
    float maxSpeed = 15f;
    int mapSizeX ;
    int mapSizeY ;
    float startPosY;
    String menuText = "";
    private ApplicationState state = new MenuState(this);

    @Override
    public void init(GameContainer gc) {
        gc.setVSync(true);
        gc.setMaximumLogicUpdateInterval(10);
        gc.setTargetFrameRate(60);
    }

    @Override
    public void update(GameContainer gc, int delta) throws SlickException {
        state.update(gc, delta);
    }
    
    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException {
        state.drawState(gc, g);
    }

    void changeState(ApplicationState newState) {
        this.state = newState;
    }

    public static void main(String[] args)
    {
        try
        {
            AppGameContainer appgc;
            appgc = new AppGameContainer(new SimpleSlickGame("Chasing"));
            appgc.setDisplayMode(appgc.getScreenWidth(),appgc.getScreenHeight(), true);
            appgc.start();
        }
        catch (SlickException ex)
        {
            Logger.getLogger(SimpleSlickGame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}