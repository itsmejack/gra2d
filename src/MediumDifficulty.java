class MediumDifficulty extends Difficulty {
    MediumDifficulty(MenuState newMenu) {
        super(newMenu);
        translation = "MEDIUM";
        minPlatformLength = GameConstants.MIN_PLATFORM_LENGTH_MEDIUM;
        maxPlatformLength = GameConstants.MAX_PLATFORM_LENGTH_MEDIUM;
    }
}
