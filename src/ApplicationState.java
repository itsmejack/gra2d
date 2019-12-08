import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

abstract class ApplicationState {

    SimpleSlickGame game;

    ApplicationState(SimpleSlickGame newGame) {
        this.game = newGame;
    }

    abstract void update(GameContainer gc, int delta) throws SlickException;
    abstract void drawState(GameContainer gc, Graphics g) throws SlickException;

}
