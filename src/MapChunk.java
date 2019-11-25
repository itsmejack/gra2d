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
    //private MapFragment[][] mapFragments;
    private List<MapFragment> mapFragments;

    public MapChunk(boolean up, boolean down, boolean left, boolean right, float newX, float newY) throws SlickException {
        upExit = up;
        downExit = down;
        leftExit = left;
        rightExit = right;
        startX = newX;
        startY = newY;
        mapFragments = generateNewChunk();
    }

    private List<MapFragment> generateNewChunk() throws SlickException {
        //MapFragment[][] temp = new MapFragment[10][10];
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
        List<MapFragment> temp = new ArrayList<>();

        int currentY = generator.nextInt(maxY-minY+1)+minY;
        int rStartX = generator.nextInt(maxX-minX+1)+minX;
        int rEndX = rStartX + generator.nextInt(10)+1;

        return generatePlatform(rStartX, rEndX, currentY);
    }
    
    private List<MapFragment> generatePlatform(int minX, int maxX, int newY) throws SlickException {
        List<MapFragment> temp = new ArrayList<>();

        for (int currentX=minX; currentX<=maxX; currentX++) {
            temp.add(new MapFragment(startX+currentX*64f, startY+newY*64f));
        }

        return temp;
    }

}
