public class SmallSize extends MapSize {
    public SmallSize(MenuState newMenu) {
        super(newMenu);
        translation = "SMALL";
        mapSizeX = GameConstants.SMALL_MAP_X;
        mapSizeY = GameConstants.SMALL_MAP_Y;
        maxTime = GameConstants.SMALL_TIME;
    }
}
