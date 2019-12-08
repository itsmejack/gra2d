public abstract class Difficulty {
    public String translation;
    public int minPlatformLength;
    public int maxPlatformLength;
    public MenuState menu;

    public Difficulty(MenuState newMenu) {
        this.menu = newMenu;
    }
}
