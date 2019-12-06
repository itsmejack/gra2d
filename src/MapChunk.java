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
    private List<MapFragment> mapFragments;

    private final int minPlatformLength = 1;
    private final int maxPlatformLength = 10;

    public MapChunk(boolean up, boolean down, boolean left, boolean right, float newX, float newY, boolean isFinish) throws SlickException {
        upExit = up;
        downExit = down;
        leftExit = left;
        rightExit = right;
        startX = newX;
        startY = newY;
        mapFragments = isFinish ? generateFinishChunk() : generateNewChunk();
    }

    public MapChunk(float newX, float newY, boolean isFinish) throws SlickException {
        this(true, true, true, true, newX, newY, isFinish);
    }


    private List<MapFragment> generateFinishChunk() throws SlickException {
        List<MapFragment> temp = new ArrayList<>();
        for(int currentX=0; currentX<GameConstants.CHUNK_SIZE; currentX++) {
            temp.add(new MapFragment(startX + currentX*GameConstants.BLOCK_SIZE, startY + (GameConstants.CHUNK_SIZE-1)*GameConstants.BLOCK_SIZE));
        }
        temp.add(new MapFragment(startX + (GameConstants.CHUNK_SIZE-2)*GameConstants.BLOCK_SIZE, startY + (GameConstants.CHUNK_SIZE-2)*GameConstants.BLOCK_SIZE, true));
        return temp;
    }

    private List<MapFragment> generateNewChunk() throws SlickException {
        List<MapFragment> temp = new ArrayList<>();

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

    public List<MapFragment> getMapFragments() {
        return mapFragments;
    }

    private List<MapFragment> generateRandomPlatform(int minX, int maxX, int minY, int maxY) throws SlickException {
        Random generator = new Random();

        int currentY = generator.nextInt(maxY-minY+minPlatformLength)+minY;
        int rStartX = generator.nextInt(maxX-minX+minPlatformLength)+minX;
        int rEndX = rStartX + generator.nextInt(maxPlatformLength)+minPlatformLength;

        return generatePlatform(rStartX, rEndX, currentY);
    }
    
    private List<MapFragment> generatePlatform(int minX, int maxX, int newY) throws SlickException {
        List<MapFragment> temp = new ArrayList<>();

        for (int currentX=minX; currentX<=maxX; currentX++) {
            temp.add(new MapFragment(startX+currentX*GameConstants.BLOCK_SIZE, startY+newY*GameConstants.BLOCK_SIZE));
        }

        return temp;
    }
}
