public class EasyDifficulty extends Difficulty {
    public EasyDifficulty(MenuState newMenu) {
        super(newMenu);
        translation = "EASY";
        minPlatformLength = GameConstants.MIN_PLATFORM_LENGTH_EASY;
        maxPlatformLength = GameConstants.MAX_PLATFORM_LENGTH_EASY;
    }
}
