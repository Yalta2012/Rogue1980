package Domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.ietf.jgss.GSSManager;

import java.util.Vector;

public class GameVector {
    private int x, y;

    public GameVector() {}

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }

    public void setXY(int x, int y){
        this.x = x;
        this.y = y;
    }

    public GameVector(int x, int y){
        this.x = x;
        this.y = y;
    }

    public GameVector add(GameVector vector){
        return new GameVector(x + vector.getX(), y + vector.getY());
    }
    public GameVector sub(GameVector vector) { return new GameVector(x - vector.getX(), y + vector.getY()) ; }
    public GameVector abs() { return new GameVector(Math.abs(x) , Math.abs(y));}
    public GameVector normalize() { return new GameVector(x == 0 ? 0 : x/Math.abs(x) , y == 0 ? 0 : y/Math.abs(y));}

    public static GameVector add(GameVector vector_1, GameVector vector_2){
        return new GameVector(vector_1.getX() + vector_2.getX(), vector_1.getY() + vector_2.getY());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof  GameVector)) return false;
        GameVector vector = (GameVector) obj;
        return x == vector.getX() && y == vector.getY();
    }

    @Override
    public int hashCode() {
        return y << 16 + x;
    }

    public static final GameVector UP = new GameVector(0, -1);
    public static final GameVector DOWN = new GameVector(0, 1);
    public static final GameVector LEFT = new GameVector(-1, 0);
    public static final GameVector RIGHT = new GameVector(1, 0);
    public static final GameVector RIGHTDOWN = new GameVector(1,1);

    public boolean isEquals(GameVector point) {
        if ((x == point.getX()) && (y == point.getY())) {
            return true;
        }
        return false;
    }

    @JsonCreator
    public static GameVector fromString(String value) {
        if (value == null || value.isBlank()) {
            return new GameVector(0, 0);
        }
        try {
            String clean = value.replaceAll("[^0-9,-]", "");
            String[] parts = clean.split(",");

            if (parts.length == 2) {
                int x = Integer.parseInt(parts[0]);
                int y = Integer.parseInt(parts[1]);
                return new GameVector(x, y);
            }
        } catch (Exception e) {
            return new GameVector(0, 0);
        }
        return new GameVector(0, 0);
    }

    @Override
    @JsonValue
    public String toString() {
        return x + "," + y;
    }
}
