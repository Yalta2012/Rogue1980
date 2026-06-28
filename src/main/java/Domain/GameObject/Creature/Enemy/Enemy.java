package Domain.GameObject.Creature.Enemy;

import Domain.GameObject.Creature.Creature;
import Domain.GameVector;
import Domain.Generator;
import Domain.Map.Level;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Zombie.class, name = "ZOMBIE"),
        @JsonSubTypes.Type(value = Ghost.class, name = "GHOST"),
        @JsonSubTypes.Type(value = Mimic.class, name = "MIMIC"),
        @JsonSubTypes.Type(value = Ogr.class, name = "OGR"),
        @JsonSubTypes.Type(value = SnakeMage.class, name = "SNAKE_MAGE"),
        @JsonSubTypes.Type(value = Vampire.class, name = "VAMPIRE")
})
public abstract class Enemy extends Creature {
    protected EnemyType type;
    protected int hostility;
    protected GameVector[] moves;
    protected int gold;
    public Enemy() {
    }

    protected Enemy(GameVector vector, String name, int health, int agility, int strength, int hostility, EnemyType type) {
        super(vector, name, health, agility, strength);
        this.type = type;
        this.hostility = hostility;
    }

    public EnemyType getType() {
        return type;
    }

    public int getHostility() {
        return hostility;
    }

    public void setType(EnemyType type) {
        this.type = type;
    }

    public void setHostility(int hostility) {
        this.hostility = hostility;
    }

    public GameVector nextMove(Level level, GameVector target){
        var result = toTarget(level,target);

        if(result == null){
            result = position.add( moves[Generator.nextInt(0,moves.length - 1)]);
            if(!level.collides(result)){
                return result;
            }
        }
        else{
            return result;
        }

        return position;
    }

    public GameVector[] getMoves() {
        return moves;
    }

    public GameVector toTarget(Level level, GameVector target) {
        var mask = level.getMask();
        Queue<GameVector> queue = new LinkedList<>();
        queue.add(position);
        mask[getY()][getX()] = hostility + 1;
        Map<GameVector, GameVector> prePosition = new HashMap<>();


        boolean isTouchibal = false;

        while (!queue.isEmpty() && !isTouchibal) {
            var point = queue.remove();
            int offset = Generator.nextInt(0, moves.length - 1);
            for (int i = 0; i < moves.length; i++) {
                var move = moves[(i + offset) % moves.length];

                var nextPoint = point.add(move);
                if (point.getY() < 0 || point.getY() >= mask.length || point.getX() < 0 || point.getX() >= mask[0].length)
                    continue;
                if (nextPoint.getY() < 0 || nextPoint.getY() >= mask.length || nextPoint.getX() < 0 || nextPoint.getX() >= mask[0].length)
                    continue;
                if (mask[nextPoint.getY()][nextPoint.getX()] == 0) continue;
                if (target.equals(nextPoint)) {
                    isTouchibal = true;
                    prePosition.put(new GameVector(nextPoint.getX(), nextPoint.getY()), new GameVector(point.getX(), point.getY()));
                }
                int dif = mask[point.getY()][point.getX()] - mask[nextPoint.getY()][nextPoint.getX()];
                if (dif > 1) {
                    mask[nextPoint.getY()][nextPoint.getX()] = mask[point.getY()][point.getX()] - 1;
                    queue.add(nextPoint);
                    prePosition.put(new GameVector(nextPoint.getX(), nextPoint.getY()), new GameVector(point.getX(), point.getY()));

                }

            }


        }

        if (!isTouchibal) return null;

        var pos = target;
        while (pos != null) {
            var prePos = prePosition.get(pos);
            if (prePos.equals(position)) {
                return pos;
            }

            pos = prePos;
        }

        return null;
    }

}
