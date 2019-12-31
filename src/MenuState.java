import org.newdawn.slick.*;

import java.util.Optional;

public class MenuState extends ApplicationState{
    private Difficulty difficulty = new EasyDifficulty(this);
    private MapSize mapSize = new SmallSize(this);
    private int selectedOption = 0;
    private int highScore;

    private enum Option {
        NEW, RESTART, DIFFICULTY, SIZE, EXIT;

        public static Option[] OPTIONS = {NEW, RESTART, DIFFICULTY, SIZE, EXIT};
    }

    private void increaseDifficulty() {
        if(difficulty.translation.equals("EASY")) {
            difficulty = new MediumDifficulty(this);
        } else if(difficulty.translation.equals("MEDIUM")) {
            difficulty = new HardDifficulty(this);
        } else {
            difficulty = new EasyDifficulty(this);
        }
    }

    private void decreaseDifficulty() {
        if(difficulty.translation.equals("HARD")) {
            difficulty = new MediumDifficulty(this);
        } else if(difficulty.translation.equals("EASY")) {
            difficulty = new HardDifficulty(this);
        } else {
            difficulty = new EasyDifficulty(this);
        }
    }

    private void increaseMap() {
        if(mapSize.translation.equals("SMALL")) {
            mapSize = new MediumSize(this);
        } else if(mapSize.translation.equals("MEDIUM")) {
            mapSize = new LargeSize(this);
        } else {
            mapSize = new SmallSize(this);
        }
    }

    private void decreaseMap() {
        if(mapSize.translation.equals("LARGE")) {
            mapSize = new MediumSize(this);
        } else if(mapSize.translation.equals("SMALL")) {
            mapSize = new LargeSize(this);
        } else {
            mapSize = new SmallSize(this);
        }
    }

    public MenuState(SimpleSlickGame game) {

        super(game);
        highScore = game.timer * Optional.ofNullable(game.map).map(e -> e.collectedCoins).orElse(1);
    }

    private void drawRetry(Graphics g) {
        if(game.map == null) {
            g.setColor(Color.gray);
        }
        g.drawString("Retry",100, 200);
        g.setColor(Color.white);
    }

    private void drawOptions(Graphics g) {
        g.setColor(Color.white);
        g.drawString("New game", 100, 100);
        g.drawString("< Difficulty " + difficulty.translation + " >", 100, 300);
        g.drawString("< Map size " + mapSize.translation + " >", 100, 400);
        g.drawString("Exit", 100, 500);
        drawRetry(g);
    }

    private void drawInfo(Graphics g) {

        g.drawString(game.menuText, 500, 300);
        if(game.menuText.equals("You won")) {
            g.drawString("Your score " + highScore, 500, 500);
        }
    }

    private void drawSelection(Graphics g) {
        g.drawString("->", 70, 100 + 100*selectedOption);
    }

    private void setupMenuGraphics(Graphics g, GameContainer gc) {
        g.scale(1.5f*gc.getScreenWidth()/1920f,1.5f*gc.getScreenHeight()/1080f);
        g.setBackground(GameConstants.MAIN_BACKGROUND_COLOR);
    }

    @Override
    void update(GameContainer gc, int delta) throws SlickException {
        handleMenuOptions(gc);
    }

    @Override
    void drawState(GameContainer gc, Graphics g) {
        setupMenuGraphics(g, gc);
        drawMenuElements(g);
    }

    private void drawMenuElements(Graphics g) {
        drawOptions(g);
        drawInfo(g);
        drawSelection(g);
    }

    private void handleMovingThroughMenu(GameContainer gc) {
        if(gc.getInput().isKeyPressed(GameConstants.MOVE_DOWN_INPUT)) {
            selectedOption = Math.min(selectedOption+1, Option.values().length-1);
        }
        if(gc.getInput().isKeyPressed(GameConstants.MOVE_UP_INPUT)) {
            selectedOption = Math.max(selectedOption-1, 0);
        }
    }

    private void prepareGame(boolean isNewGame) throws SlickException {

        game.player = Player.getInstance();
        if(isNewGame) {
            game.mapSizeX = mapSize.mapSizeX;
            game.mapSizeY = mapSize.mapSizeY;
            game.map = new Map();
            game.startPosY = game.map.generateMap(mapSize.mapSizeX, mapSize.mapSizeY, difficulty.minPlatformLength, difficulty.maxPlatformLength);
            game.prevSize = mapSize;
        }
        game.player.posx = GameConstants.BLOCK_SIZE*5/2;
        game.player.posy = game.startPosY;
        game.map.isFinished = false;
        game.map.isHit = false;
        game.map.collectedCoins = 0;
        game.map.getFragments().stream().filter(e -> e instanceof MapItem).forEach(e -> e.isCollected = false);
        game.timer = game.prevSize.maxTime;
        game.changeState(new GameState(game));

    }

    private void handleEnterOptions(GameContainer gc) throws SlickException {
        if(gc.getInput().isKeyPressed(GameConstants.ENTER_INPUT)) {
            if(Option.OPTIONS[selectedOption] == Option.NEW) {
                prepareGame(true);
            }
            if(Option.OPTIONS[selectedOption] == Option.RESTART && game.map != null) {
                prepareGame(false);
            }
            if(Option.OPTIONS[selectedOption] == Option.EXIT) {
                gc.exit();
            }
        }
    }

    private void handleSelectOptions(GameContainer gc) {
        if(gc.getInput().isKeyPressed(GameConstants.MOVE_RIGHT_INPUT)) {
            if(Option.OPTIONS[selectedOption] == Option.SIZE) {
                increaseMap();
            }
            if(Option.OPTIONS[selectedOption] == Option.DIFFICULTY) {
                increaseDifficulty();
            }
        }

        if(gc.getInput().isKeyPressed(GameConstants.MOVE_LEFT_INPUT)) {
            if(Option.OPTIONS[selectedOption] == Option.SIZE) {
                decreaseMap();
            }
            if(Option.OPTIONS[selectedOption] == Option.DIFFICULTY) {
                decreaseDifficulty();
            }
        }
    }

    private void handleMenuOptions(GameContainer gc) throws SlickException {
        handleMovingThroughMenu(gc);
        handleEnterOptions(gc);
        handleSelectOptions(gc);
    }
}
