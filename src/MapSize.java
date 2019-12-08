public abstract class MapSize {
    public String translation;
    public int mapSizeX;
    public int mapSizeY;
    public int maxTime;
    public MenuState menu;

    public MapSize(MenuState newMenu) {
        this.menu = newMenu;
    }
}
