abstract class Difficulty {
    String translation;
    int minPlatformLength;
    int maxPlatformLength;
    private MenuState menu;

    Difficulty(MenuState newMenu) {
        this.menu = newMenu;
    }
}
