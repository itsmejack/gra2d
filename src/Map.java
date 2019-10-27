import java.util.ArrayList;

public class Map {
    private ArrayList<MapFragment> map = new ArrayList<>();

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

}
