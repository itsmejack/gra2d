class LargeSize extends MapSize  {
    LargeSize(MenuState newMenu) {
        super(newMenu);
        translation = "LARGE";
        mapSizeX = GameConstants.LARGE_MAP_X;
        mapSizeY = GameConstants.LARGE_MAP_Y;
        maxTime = GameConstants.LARGE_TIME;
    }
}
