import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MapBlock extends GameObject {
    private Image image;

    MapBlock(float posx, float posy) throws SlickException {
        this.posx = posx;
        this.posy = posy;
        this.sizex = GameConstants.BLOCK_SIZE;
        this.sizey = GameConstants.BLOCK_SIZE;
        this.image = getRandomImage();
        isCollectible = false;
        isDangerous = false;
    }

    MapBlock(float posx, float posy, boolean isDestinationPoint) throws SlickException {
        this.posx = posx;
        this.posy = posy;
        this.sizex = GameConstants.BLOCK_SIZE;
        this.sizey = GameConstants.BLOCK_SIZE;
        this.image = new Image("src\\img\\finish.png");
        this.isDestinationPoint = isDestinationPoint;
        isCollectible = false;
        isDangerous = false;
    }

    public void draw(float x, float y) {
        image.draw(x, y);
    }

    private Image getRandomImage() throws SlickException {
        Random generator = new Random();

        List<Image> blockImages = new ArrayList<>();
        blockImages.add(new Image("src\\img\\typeA.png"));
        blockImages.add(new Image("src\\img\\typeB.png"));
        blockImages.add(new Image("src\\img\\typeC.png"));
        blockImages.add(new Image("src\\img\\typeD.png"));
        return blockImages.get(generator.nextInt(blockImages.size()));
    }
}
