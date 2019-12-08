import org.newdawn.slick.SlickException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MapChunk {
    private float startX;
    private float startY;
    private boolean upExit;
    private boolean downExit;
    private boolean leftExit;
    private boolean rightExit;
    private List<GameObject> gameObjects;

    private int minPlatformLength;
    private int maxPlatformLength ;

    public MapChunk(boolean up, boolean down, boolean left, boolean right, float newX, float newY, boolean isFinish, int minPlatform, int maxPlatform) throws SlickException {
        upExit = up;
        downExit = down;
        leftExit = left;
        rightExit = right;
        startX = newX;
        startY = newY;
        minPlatformLength = minPlatform;
        maxPlatformLength = maxPlatform;
        gameObjects = isFinish ? generateFinishChunk() : generateNewChunk();
    }

    public MapChunk(float newX, float newY, boolean isFinish, int minPlatform, int maxPlatform) throws SlickException {
        this(true, true, true, true, newX, newY, isFinish, minPlatform, maxPlatform);
    }

    private List<GameObject> generateFinishChunk() throws SlickException {
        List<GameObject> temp = new ArrayList<>();
        for(int currentX=0; currentX<GameConstants.CHUNK_SIZE; currentX++) {
            temp.add(new MapFragment(startX + currentX*GameConstants.BLOCK_SIZE, startY + (GameConstants.CHUNK_SIZE-1)*GameConstants.BLOCK_SIZE));
        }
        temp.add(new MapFragment(startX + (GameConstants.CHUNK_SIZE-2)*GameConstants.BLOCK_SIZE, startY + (GameConstants.CHUNK_SIZE-2)*GameConstants.BLOCK_SIZE, true));
        return temp;
    }

    private List<GameObject> generateNewChunk() throws SlickException {
        List<GameObject> temp = new ArrayList<>();

        if(upExit) {
            temp.addAll(generateRandomPlatform(2,7,2,2));
        }

        if(downExit) {
            temp.addAll(generateRandomPlatform(2,7,9,9));
        }

        if(leftExit) {
            temp.addAll(generateRandomPlatform(0,1,5,6));
        }

        if(rightExit) {
            temp.addAll(generateRandomPlatform(0,1,5,6));
        }

        if(!(rightExit||leftExit)) {
            temp.addAll(generateRandomPlatform(2,7,5,6));
        }

        return temp;
    }

    public List<GameObject> getMapFragments() {
        return gameObjects;
    }

    private List<GameObject> generateRandomPlatform(int minX, int maxX, int minY, int maxY) throws SlickException {
        Random generator = new Random();

        int currentY = generator.nextInt(maxY-minY+minPlatformLength)+minY;
        int rStartX = generator.nextInt(maxX-minX+minPlatformLength)+minX;
        int rEndX = rStartX + generator.nextInt(maxPlatformLength)+minPlatformLength;

        return generatePlatform(rStartX, rEndX, currentY);
    }
    
    private List<GameObject> generatePlatform(int minX, int maxX, int newY) throws SlickException {
        List<GameObject> temp = new ArrayList<>();
        Random generator = new Random();
        for (int currentX=minX; currentX<=maxX; currentX++) {
            if(generator.nextInt(10) < 1) {
                temp.add(new MapItem(startX+currentX*GameConstants.BLOCK_SIZE, startY+(newY-1)*GameConstants.BLOCK_SIZE));
            }
            if(generator.nextInt(100) < 1) {
                temp.add(new Enemy(startX+currentX*GameConstants.BLOCK_SIZE, startY+(newY-1)*GameConstants.BLOCK_SIZE));
            }
            temp.add(new MapFragment(startX+currentX*GameConstants.BLOCK_SIZE, startY+newY*GameConstants.BLOCK_SIZE));
        }

        return temp;
    }
}
