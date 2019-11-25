import org.newdawn.slick.SlickException;

import java.util.ArrayList;
import java.util.Random;

public class Map {
    private ArrayList<MapFragment> map = new ArrayList<>();
    //background tutaj

    public Map() {
    }

    public void add(MapFragment mapFragment) {
        map.add(mapFragment);
    }

    public ArrayList<MapFragment> getFragments() {
        return map;
    }

    //is collider colliding with top of the destination, for example player jumping on top of block
    public MapFragment isCollidingWithTop(Creature collider) {
        MapFragment result = null;
        for (MapFragment destination : map) {
            if (collider.getBottom() + collider.speedy > destination.getTop() && collider.getBottom() <= destination.getTop()) {
                if (collider.getRight() >= destination.getLeft() && destination.getLeft() >= collider.getLeft() || collider.getRight() >= destination.getRight() && destination.getRight() >= collider.getLeft()) {
                    if (result == null || result.posy > destination.posy) {
                        result = destination;
                    }
                }
            }
        }
        return result;
    }

    public MapFragment isCollidingWithBottom(Creature collider){
        MapFragment result = null;
        for (MapFragment destination : map) {
            if(collider.getTop()>=destination.getBottom() && collider.getTop()+collider.speedy<destination.getBottom()) {
                if (collider.getRight() >= destination.getLeft() && destination.getLeft() >= collider.getLeft() || collider.getRight() >= destination.getRight() && destination.getRight() >= collider.getLeft()) {
                    if (result == null || result.posy < destination.posy) {
                        result = destination;
                    }
                }
            }
        }
        return result;
    }

    public MapFragment isCollidingWithRight(Creature collider) {
        MapFragment result = null;
        for (MapFragment destination : map) {
            if(collider.getLeft()>=destination.getRight() && collider.getLeft()+collider.speedx<destination.getRight()) {
                if (collider.getBottom() > destination.getTop() && destination.getTop() > collider.getTop() || collider.getBottom() > destination.getBottom() && destination.getBottom() > collider.getTop()) {
                    if (result == null || result.posx < destination.posx) {
                        result = destination;
                    }
                }
            }
        }
        return result;
    }

    public MapFragment isCollidingWithLeft(Creature collider) {
        MapFragment result = null;
        for (MapFragment destination : map) {
            if(collider.getRight()+collider.speedx>destination.getLeft() && collider.getRight()<=destination.getLeft()) {
                if (collider.getBottom() > destination.getTop() && destination.getTop() > collider.getTop() || collider.getBottom() > destination.getBottom() && destination.getBottom() > collider.getTop()) {
                    if (result == null || result.posx > destination.posx) {
                        result = destination;
                    }
                }
            }
        }
        return result;

    }

    public float generateNewMap(int x, int y) throws SlickException {
        int temp[][] = new int[x][y];
        int prev = 10;
        Random generator = new Random();

        /*
        for (int i = 0; i < x; i++) {
            //temp[i][prev] = true;
            map.add(new MapFragment(i*64f, prev*64f));
            prev = prev - 2 + generator.nextInt(9);
            if (prev>=7) {
                prev=0;
            }

        }*/

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


        for(int i=0; i<y; i++) {
            for(int j=0; j<x; j++) {
                if(temp[j][i]==1) {
                    map.add(new MapFragment(j*64f, i*64f));
                }
            }
        }


        //zwroc posy startu?
        return 0f;
    }

    public void generateMap(int x, int y) throws SlickException {
        int temp[][] = new int[x][y];
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
            //prawdopodobnie tutaj juz trzeba chunk stworzyc
        }

        /*
        //additional routes
        for(int k=0; k<4; k++) {
            //wylosuj jakis x
            //wylosuj czy ma byc na gorze czy na dole
            //znajdz odpowiednie y jesli mozliwe

            //wylosuj max dlugosc sciezki
            //wylosuj punkt złamania sciezki




            boolean finished = false;
            boolean failed = false;
            currentX = generator.nextInt(x-3);
            boolean isOverMainRoute = generator.nextBoolean();
            if(isOverMainRoute) {
                if(temp[currentX][0] != 0) {
                    return;
                } else {
                    currentY = 0;
                    while(currentY < y-1 && temp[currentX][currentY+1] == 0) {
                        currentY++;
                    }
                }
            } else {
                if(temp[currentX][y-1] != 0) {
                    return;
                } else {
                    currentY = y-1;
                    while(currentY > 0 && temp[currentX][currentY-1] == 0) {
                        currentY--;
                    }
                }
            }

            int destination = generator.nextInt(x-1-currentX)+currentX;
            int breakRoutePoint = (destination + currentX)/2;

            temp[currentX][currentY] = k;

            while(currentX < destination && !finished) {
                if(generator.nextBoolean()) {
                    currentX++;
                } else {
                    if((currentX > breakRoutePoint && !isOverMainRoute) || (currentX <= breakRoutePoint && isOverMainRoute) ) {
                        if(currentY > 0) {
                            currentY--;
                        }
                    } else {
                        if(currentY < y-1) {
                            currentY++;
                        }
                    }
                }
                if(temp[currentX][currentY] == 0 || temp[currentX][currentY] == k) {
                    temp[currentX][currentY] = k;
                } else {
                    finished = true;
                }
                //prawdopodobnie tutaj juz trzeba chunk stworzyc
            }

            while(!finished) {
                if(temp[currentX][currentY] == 0 || temp[currentX][currentY] == k) {
                    temp[currentX][currentY] = k;
                } else {
                    finished = true;
                }
                currentY = (isOverMainRoute?currentY+1:currentY-1);
            }
        }*/

        for(currentX=1; currentX<x-1; currentX++) {
            for(currentY=1; currentY<y-1; currentY++) {
                if(temp[currentX][currentY] != 0) {
                    MapChunk tempChunk = new MapChunk(
                            (temp[currentX][currentY - 1] != 0),
                            (temp[currentX][currentY + 1] != 0),
                            (temp[currentX - 1][currentY] != 0),
                            (temp[currentX + 1][currentY] != 0),
                            currentX*64f*10,
                            currentY*64f*10);
                    map.addAll(tempChunk.getMapFragments());
                }
            }
        }

    }



}
