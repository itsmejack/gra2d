import org.newdawn.slick.*;

public class MenuState extends ApplicationState{
    private Difficulty difficulty = new HardDifficulty(this);
    private MapSize mapSize = new SmallSize(this);
    private int selectedOption = 0;

    private enum Option {
        NEW, RESTART, DIFFICULTY, SIZE;

        public static Option[] OPTIONS = {NEW, RESTART, DIFFICULTY, SIZE};
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
    }

    @Override
    void update(GameContainer gc) throws SlickException {
        handleMenu(gc);
    }

    @Override
    void drawState(GameContainer gc, Graphics g) {
        g.setBackground(GameConstants.MAIN_BACKGROUND_COLOR);
        g.drawString("Start a new game", 100, 100);
        g.drawString("Retry",100, 200);
        g.drawString("Difficulty " + difficulty.translation, 100, 300);
        g.drawString("Map size " + mapSize.translation, 100, 400);
        g.drawString(game.menuText, 500, 300);
        g.drawString("->", 70, 100 + 100*selectedOption);
    }

    private void handleMenu(GameContainer gc) throws SlickException {
        if(gc.getInput().isKeyPressed(GameConstants.MOVE_DOWN_INPUT)) {
            selectedOption = Math.min(selectedOption+1, Option.values().length-1);
        }
        if(gc.getInput().isKeyPressed(GameConstants.MOVE_UP_INPUT)) {
            selectedOption = Math.max(selectedOption-1, 0);
        }
        if(gc.getInput().isKeyPressed(GameConstants.ENTER_INPUT)) {
            if(Option.OPTIONS[selectedOption] == Option.NEW) {
                game.player = Player.getInstance();
                game.map = new Map();
                game.player.posx = GameConstants.BLOCK_SIZE/2;
                game.startPosY = game.map.generateMap(mapSize.mapSizeX, mapSize.mapSizeY, difficulty.minPlatformLength, difficulty.maxPlatformLength);
                game.player.posy = game.startPosY;
                game.changeState(new GameState(game));
            }
            if(Option.OPTIONS[selectedOption] == Option.RESTART && game.map != null) {
                game.player = Player.getInstance();
                game.player.posx = GameConstants.BLOCK_SIZE/2;
                game.player.posy = game.startPosY;
                game.map.isFinished = false;
                game.changeState(new GameState(game));
            }
        }

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
}
