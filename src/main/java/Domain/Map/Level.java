package Domain.Map;

import Domain.GameConstants;
import Domain.GameObject.Creature.Enemy.Enemy;
import Domain.GameObject.Creature.Enemy.EnemyFabric;
import Domain.GameObject.Item.Item;
import Domain.GameObject.Item.ItemFabric;
import Domain.GameVector;
import Domain.Generator;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.*;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Level {
    private Map map;
    private GameVector startPoint;
    private GameVector endPoint;
    private int numberLevel;
    private int gold;

    private Room startRoom;
    private Room endRoom;

    private List<Enemy> enemies = new LinkedList<>();
    private java.util.Map<GameVector, Item> items = new HashMap<>();

    public Level() {
    }

    public Level(int numberLevel) {
        this.map = new Map(true);
        List<Room> shuffledRooms = new ArrayList<>(map.getRoomList());
        Collections.shuffle(shuffledRooms);
        this.startRoom = shuffledRooms.get(0);
        startRoom.setVisited(true);
        this.endRoom = shuffledRooms.get(1);
        this.startPoint = new GameVector(startRoom.getX() + 3, startRoom.getY() + 3);
        this.endPoint = new GameVector(endRoom.getX() + 2, endRoom.getY() + 2);
        this.numberLevel = numberLevel;
        this.gold = 0;
        generateEnemies();
        generateItems();
    }

    public GameVector getStartPoint() {
        return startPoint;
    }

    public GameVector getEndPoint() {
        return endPoint;
    }

    public int getNumberLevel() {
        return numberLevel;
    }

    public Map getMap() {
        return map;
    }


    public List<Enemy> getEnemies() {
        return enemies;
    }

    ;

    public Enemy getEnemy(GameVector vector) {
        for (var e : enemies) {
            if (e.getPosition().equals(vector)) {
                return e;
            }
        }
        return null;

    }

    public java.util.Map<GameVector, Item> getItems() {
        return items;
    }

    public Room getEndRoom() {
        return endRoom;
    }

    public Room getStartRoom() {
        return startRoom;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public void setEndPoint(GameVector endPoint) {
        this.endPoint = endPoint;
    }

    public void setEndRoom(Room endRoom) {
        this.endRoom = endRoom;
    }

    public void setNumberLevel(int numberLevel) {
        this.numberLevel = numberLevel;
    }

    public void setItems(java.util.Map<GameVector, Item> items) {
        this.items = items;
    }

    public void setStartPoint(GameVector startPoint) {
        this.startPoint = startPoint;
    }

    public void setStartRoom(Room startRoom) {
        this.startRoom = startRoom;
    }

    public boolean collides(GameVector vector) {
        if (vector.equals(endPoint)) ;
        if (!map.contains(vector)) return true;
        for (var e : enemies) if (e.getPosition().equals(vector)) return true;
        if (items.containsKey(vector)) return true;

        return false;

    }

    private void generateEnemies() {
        for (var room : map.getRoomList()) {
            if (room == startRoom) continue;
            var v = room.getRandomPoint();

            double total = 50 + gold * 0.05/numberLevel + numberLevel / 3.0;
            if(Generator.roll(1,100) <= total){
                var e = EnemyFabric.createRandomEnemy(v, numberLevel, gold);
                enemies.add(e);
            }
        }
    }

    private void generateItems() {
        for (var room : map.getRoomList()) {
            if (room == startRoom) continue;
            var v = room.getRandomPoint();
            while (collides(v)) {
                v = room.getRandomPoint();
            }
            var i = ItemFabric.createRandomItem(v);
            items.put(i.getPosition(), i);
        }
    }

    @JsonIgnore
    public int[][] getMask() {
        int[][] result = new int[GameConstants.MAP_HEIGHT][GameConstants.MAP_WIDTH];
        for (var room : map.getRoomList()) {
            for (int i = GameConstants.WALL_THICKNESS; i < room.getHeight() - GameConstants.WALL_THICKNESS; i++) {
                for (int j = GameConstants.WALL_THICKNESS; j < room.getWidth() - GameConstants.WALL_THICKNESS; j++) {
                    result[room.getY() + i][room.getX() + j] = 1;
                }
            }
        }
        for (var corridor : map.getCorridorList()) {
            for (int i = 1; i < 4; i++) {
                GameVector startPoint = corridor.getPoint(i - 1);
                GameVector endPoint = corridor.getPoint(i);

                for (int y = Math.min(startPoint.getY(), endPoint.getY()); y <= Math.max(startPoint.getY(), endPoint.getY()); y++) {
                    for (int x = Math.min(startPoint.getX(), endPoint.getX()); x <= Math.max(startPoint.getX(), endPoint.getX()); x++) {
                        result[y][x] = 1;
                    }
                }
            }
        }

        for (var enemy : enemies) {
            result[enemy.getY()][enemy.getX()] = 0;
        }

        for (var gv : items.keySet()) {
            result[gv.getY()][gv.getX()] = 0;
        }

        result[endPoint.getY()][endPoint.getX()] = 0;

        return result;
    }


    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }
}
