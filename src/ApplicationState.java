import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public abstract class ApplicationState {

    public SimpleSlickGame game;

    public ApplicationState(SimpleSlickGame newGame) {
        this.game = newGame;
    }

    abstract void update(GameContainer gc, int delta) throws SlickException;
    abstract void drawState(GameContainer gc, Graphics g) throws SlickException;

}
