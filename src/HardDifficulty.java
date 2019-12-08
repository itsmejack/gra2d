class HardDifficulty extends Difficulty {
    HardDifficulty(MenuState newMenu) {
        super(newMenu);
        translation = "HARD";
        minPlatformLength = GameConstants.MIN_PLATFORM_LENGTH_HARD;
        maxPlatformLength = GameConstants.MAX_PLATFORM_LENGTH_HARD;
    }
}
