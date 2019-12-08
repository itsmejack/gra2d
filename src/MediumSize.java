class MediumSize extends MapSize  {
    MediumSize(MenuState newMenu) {
        super(newMenu);
        translation = "MEDIUM";
        mapSizeX = GameConstants.MEDIUM_MAP_X;
        mapSizeY = GameConstants.MEDIUM_MAP_Y;
        maxTime = GameConstants.MEDIUM_TIME;
    }
}
