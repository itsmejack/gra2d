import org.newdawn.slick.SlickException;

import java.util.ArrayList;
import java.util.Random;

public class Map {
    private ArrayList<GameObject> map = new ArrayList<>();
    boolean isFinished = false;
    int collectedCoins = 0;
    boolean isHit = false;

    Map() {}

    public void add(MapFragment mapFragment) {
        map.add(mapFragment);
    }

    ArrayList<GameObject> getFragments() {
        return map;
    }

    //is collider colliding with top of the destination, for example player jumping on top of block
    GameObject isCollidingWithTop(Creature collider) {
        GameObject result = null;
        for (GameObject destination : map) {
            if (collider.getBottom() + collider.speedy > destination.getTop() && collider.getBottom() <= destination.getTop()) {
                if (destination.getRight() > collider.getLeft() && collider.getLeft() > destination.getLeft() || destination.getRight() > collider.getRight() && collider.getRight() > destination.getLeft() || destination.getLeft() == collider.getLeft() && destination.getRight() == collider.getRight()){
                    if(destination.isCollectible) {
                        if(!destination.isCollected) {
                            collectedCoins++;
                            destination.isCollected = true;
                        }
                    } else if(destination.isDangerous) {
                        isHit = true;
                    } else if (result == null || result.posy > destination.posy) {
                        result = destination;
                    }
                    if(destination.isDestinationPoint) {
                        isFinished = true;
                    }
                }
            }
        }
        return result;
    }

    GameObject isCollidingWithBottom(Creature collider){
        GameObject result = null;
        for (GameObject destination : map) {
            if(collider.getTop()>=destination.getBottom() && collider.getTop()+collider.speedy<destination.getBottom()) {
                if (destination.getRight() > collider.getLeft() && collider.getLeft() > destination.getLeft() || destination.getRight() > collider.getRight() && collider.getRight() > destination.getLeft() || destination.getLeft() == collider.getLeft() && destination.getRight() == collider.getRight()) {
                    if(destination.isCollectible) {
                        if(!destination.isCollected) {
                            collectedCoins++;
                            destination.isCollected = true;
                        }
                    } else if(destination.isDangerous) {
                        isHit = true;
                    } else if ((result == null || result.posy < destination.posy) && destination.isSolid) {
                        result = destination;
                    }
                    if(destination.isDestinationPoint) {
                        isFinished = true;
                    }
                }
            }
        }
        return result;
    }

    GameObject isCollidingWithRight(Creature collider) {
        GameObject result = null;
        for (GameObject destination : map) {
            if (collider.getLeft() >= destination.getRight() && collider.getLeft() + collider.speedx < destination.getRight()) {
                if (collider.getBottom() > destination.getTop() && destination.getTop() > collider.getTop() || collider.getBottom() > destination.getBottom() && destination.getBottom() > collider.getTop()) {
                    if(destination.isCollectible) {
                        if(!destination.isCollected) {
                            collectedCoins++;
                            destination.isCollected = true;
                        }
                    } else if(destination.isDangerous) {
                        isHit = true;
                    } else if ((result == null || result.posx < destination.posx) && destination.isSolid) {
                        result = destination;
                    }
                    if(destination.isDestinationPoint) {
                        isFinished = true;
                    }
                }
            }
        }
        return result;
    }

    GameObject isCollidingWithLeft(Creature collider) {
        GameObject result = null;
        for (GameObject destination : map) {
            if (collider.getRight() + collider.speedx > destination.getLeft() && collider.getRight() <= destination.getLeft()) {
                if (collider.getBottom() > destination.getTop() && destination.getTop() > collider.getTop() || collider.getBottom() > destination.getBottom() && destination.getBottom() > collider.getTop()) {
                    if(destination.isCollectible) {
                        if(!destination.isCollected) {
                            collectedCoins++;
                            destination.isCollected = true;
                        }
                    } else if(destination.isDangerous) {
                        isHit = true;
                    } else if ((result == null || result.posx > destination.posx) && destination.isSolid) {
                        result = destination;
                    }
                    if(destination.isDestinationPoint) {
                        isFinished = true;
                    }
                }
            }
        }
        return result;
    }

    float generateMap(int x, int y, int minPlatform, int maxPlatform) throws SlickException {
        return convertChunksToBlocksAndReturnStartHeight(
                minPlatform,
                maxPlatform,
                x, y,
                generateAllChunks(x, y));
    }

    private int[][] generateAllChunks(int x, int y) {
        int[][] temp = new int[x][y];
        Random generator = new Random();

        int currentX = 0;
        int currentY = generator.nextInt(y);

        temp[currentX][currentY] = 1;

        while(currentX < x-1) {
            if(generator.nextBoolean()) {
                currentX++;
            } else {
                if(generator.nextBoolean()) {
                    if(currentY > 0 && temp[currentX][currentY-1] == 0) {
                        currentY--;
                    }
                } else {
                    if(currentY < y-1 && temp[currentX][currentY+1] == 0) {
                        currentY++;
                    }
                }
            }
            temp[currentX][currentY] = 1;
        }
        temp[currentX][currentY] = -1;

        return temp;
    }

    private float convertChunksToBlocksAndReturnStartHeight(int minPlatform, int maxPlatform, int x, int y, int[][] temp) throws SlickException {
        float startHeight = 0;

        for(int currentX =0; currentX <x; currentX++) {
            for(int currentY =0; currentY <y; currentY++) {
                if(temp[currentX][currentY] != 0) {
                    MapChunk tempChunk;
                    if(currentX == 0 || currentX == x-1 || currentY == 0 || currentY == y-1) {
                        tempChunk = generateBoundaryChunk(currentX, currentY, minPlatform, maxPlatform);
                    } else {
                        tempChunk = generateRegularChunk(currentX, currentY, minPlatform, maxPlatform, temp);
                    }
                    if(currentX == 0) {
                        startHeight = currentY *64f*10;
                    }
                    if(temp[currentX][currentY] == -1) {
                        map.addAll(generateFinishChunk(currentX, currentY, minPlatform, maxPlatform).getMapFragments());
                    }
                    map.addAll(tempChunk.getMapFragments());
                }
            }
        }

        return startHeight;
    }

    private MapChunk generateBoundaryChunk(int currentX, int currentY, int minPlatform, int maxPlatform) throws SlickException {
        return new MapChunk(
                currentX*64f*10,
                currentY*64f*10,
                false,
                minPlatform,
                maxPlatform);
    }

    private MapChunk generateRegularChunk(int currentX, int currentY, int minPlatform, int maxPlatform, int[][] temp) throws SlickException {
        return new MapChunk(
                (temp[currentX][currentY - 1] != 0),
                (temp[currentX][currentY + 1] != 0),
                (temp[currentX - 1][currentY] != 0),
                (temp[currentX + 1][currentY] != 0),
                currentX*64f*10,
                currentY*64f*10,
                false,
                minPlatform,
                maxPlatform);
    }

    private MapChunk generateFinishChunk(int currentX, int currentY, int minPlatform, int maxPlatform) throws SlickException {
        return new MapChunk(
                (currentX+1)*64f*10,
                currentY*64f*10,
                true,
                minPlatform,
                maxPlatform);
    }

}
