import org.newdawn.slick.Color;
import org.newdawn.slick.Input;

public final class GameConstants {

    static float BLOCK_SIZE = 64f;
    static int CHUNK_SIZE = 10;

    static int NEW_GAME_INPUT = Input.KEY_N;
    static int RESTART_GAME_INPUT = Input.KEY_R;
    static int ENTER_INPUT = Input.KEY_ENTER;

    static int MOVE_RIGHT_INPUT = Input.KEY_RIGHT;
    static int MOVE_LEFT_INPUT = Input.KEY_LEFT;
    static int MOVE_UP_INPUT = Input.KEY_UP;
    static int MOVE_DOWN_INPUT = Input.KEY_DOWN;

    static int SMALL_MAP_X = 10;
    static int SMALL_MAP_Y = 5;

    static int MEDIUM_MAP_X = 30;
    static int MEDIUM_MAP_Y = 10;

    static int LARGE_MAP_X = 100;
    static int LARGE_MAP_Y = 20;

    static int MIN_PLATFORM_LENGTH_EASY = 3;
    static int MIN_PLATFORM_LENGTH_MEDIUM = 2;
    static int MIN_PLATFORM_LENGTH_HARD = 1;

    static int MAX_PLATFORM_LENGTH_EASY = 10;
    static int MAX_PLATFORM_LENGTH_MEDIUM = 5;
    static int MAX_PLATFORM_LENGTH_HARD = 2;

    static Color MAIN_BACKGROUND_COLOR = new Color(145,40,153);

}
