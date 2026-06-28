package Domain.Map;

import Domain.GameConstants;
import Domain.GameObject.GameObject;
import Domain.GameVector;
import Domain.Generator;
import com.fasterxml.jackson.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = "@id")
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Room implements Containable {
    private GameVector position;
    private GameVector size;
    private List<GameVector> doors = new LinkedList<>();
    private boolean isVisited = false;

    public Room(){}

    public int getX() {
        return position.getX();
    }

    public int getY() {
        return position.getY();
    }

    public int getWidth() {
        return size.getX();
    }

    public int getHeight() {
        return size.getY();
    }

    public void addDoor(GameVector door){
        doors.add(door);
    }

    public List<GameVector> getDoors(){
        return doors;
    }

    @JsonIgnore
    public boolean isVisited() {
        return isVisited;
    }

    public void setVisited(boolean val) {
        isVisited = val;
    }

    public Room(int x, int y, int width, int height) {
        position = new GameVector(x, y);
        size = new GameVector(width, height);
    }

    public boolean contains(GameVector vector) {
        return vector.getX() >= position.getX() + GameConstants.WALL_THICKNESS && vector.getX() <= position.getX() + size.getX() - GameConstants.WALL_THICKNESS - 1
                && vector.getY() >= position.getY() + GameConstants.WALL_THICKNESS && vector.getY() <= position.getY() + size.getY() - GameConstants.WALL_THICKNESS - 1;
    }

    public boolean containsWithWalls(GameVector vector) {
        return vector.getX() >= position.getX() && vector.getX() <= position.getX() + size.getX() - 1
                && vector.getY() >= position.getY() && vector.getY() <= position.getY() + size.getY() - 1;
    }


    public static Room generateRandomRoom(int xGridPos, int yGridPos) {
        if (xGridPos < 0 || yGridPos < 0 || xGridPos >= GameConstants.ROOM_GRID_WIDTH || yGridPos >= GameConstants.ROOM_GRID_HEIGHT)
            throw new ArrayIndexOutOfBoundsException("Room out of grid");
        Random random = new Random();
        int width = random.nextInt(GameConstants.ROOM_MIN_WIDTH, GameConstants.ROOM_MAX_WIDTH + 1);
        int height = random.nextInt(GameConstants.ROOM_MIN_HEIGHT, GameConstants.ROOM_MAX_HEIGHT + 1);

        int x = xGridPos * GameConstants.CELL_WIDTH + random.nextInt(0, GameConstants.CELL_WIDTH - width);
        int y = yGridPos * GameConstants.CELL_HEIGHT + random.nextInt(0, GameConstants.CELL_HEIGHT - height);

        return new Room(x, y, width, height);
    }

    @JsonIgnore
    public GameVector getRandomPoint() {
        return Generator.randomPointBetween(position.add(new GameVector(1, 1)), position.add(size).add(new GameVector(-2, -2)));
    }
}
