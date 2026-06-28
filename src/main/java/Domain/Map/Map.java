package Domain.Map;

import Domain.GameConstants;
import Domain.GameObject.GameObject;
import Domain.GameVector;
import Domain.Generator;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Map implements Containable {
    private List<Room> roomList = new LinkedList<>();
    private List<Corridor> corridorList = new LinkedList<>();

    @JsonCreator
    public Map() {}

    public Map(boolean initNew) {
        for (int i = 0; i < GameConstants.ROOM_GRID_HEIGHT; i++) {
            for (int j = 0; j < GameConstants.ROOM_GRID_WIDTH; j++) {
                roomList.add(Room.generateRandomRoom(j, i));
                if (i > 0) {
                    generateCorridorBetween(roomList.get(roomList.size() - GameConstants.ROOM_GRID_WIDTH - 1), roomList.getLast(), GameVector.DOWN);
                    roomList.get(roomList.size() - GameConstants.ROOM_GRID_WIDTH - 1).addDoor(corridorList.getLast().getFirstPoint());
                    roomList.getLast().addDoor(corridorList.getLast().getLastPoint());
                }

                if (j > 0) {
                    generateCorridorBetween(roomList.get(roomList.size() - 1 - 1), roomList.getLast(), GameVector.RIGHT);
                    roomList.get(roomList.size() - 1 - 1).addDoor(corridorList.getLast().getFirstPoint());
                    roomList.getLast().addDoor(corridorList.getLast().getLastPoint());
                }
            }
        }
    }

    public void generateCorridorBetween(Room firstRoom, Room secondRoom, GameVector direction) {
        Random random = new Random();

        if (direction == GameVector.DOWN) {
            corridorList.add(new Corridor(
                    new GameVector(firstRoom.getX() + GameConstants.WALL_THICKNESS + random.nextInt(0, firstRoom.getWidth() - 2), firstRoom.getY() + firstRoom.getHeight() - GameConstants.WALL_THICKNESS),
                    new GameVector(secondRoom.getX() + GameConstants.WALL_THICKNESS + random.nextInt(0, secondRoom.getWidth() - 2), secondRoom.getY()),
                    GameVector.DOWN
            ));
        } else if (direction == GameVector.RIGHT) {
            corridorList.add(new Corridor(
                    new GameVector(firstRoom.getX() + firstRoom.getWidth() - GameConstants.WALL_THICKNESS, firstRoom.getY() + GameConstants.WALL_THICKNESS + random.nextInt(0, firstRoom.getHeight() - 2)),
                    new GameVector(secondRoom.getX(), secondRoom.getY() + GameConstants.WALL_THICKNESS + random.nextInt(0, secondRoom.getHeight() - 2)),
                    GameVector.RIGHT
            ));
        }
    }

    public List<Room> getRoomList() {
        return roomList;
    }

    public List<Corridor> getCorridorList() {
        return corridorList;
    }

    public boolean contains(GameVector point) {
        for (var room : roomList) {
            if (room.contains(point)) return true;
        }
        for (var corridor : corridorList) {
            if (corridor.contains(point)) return true;
        }
        return false;
    }


    public void setVisit(GameVector position) {
        for (var room : roomList) {
            if (room.containsWithWalls(position)) {
                room.setVisited(true);
                break;
            }
        }
        for (var corridor : corridorList) {
            if (corridor.contains(position)) {
                corridor.setVisited(true);
                return;
            }
        }
    }

    public void setCorridorList(List<Corridor> corridorList) {
        this.corridorList = corridorList;
    }

    public void setRoomList(List<Room> roomList) {
        this.roomList = roomList;
    }
}
