package Domain.GameObject.Creature.Enemy;

import Domain.GameConstants;
import Domain.GameObject.Creature.Creature;
import Domain.GameStatistics;
import Domain.GameVector;
import Domain.Generator;

import Domain.Logger.Logger;
import Domain.Map.Level;
import Domain.Map.Room;


public class Ghost extends Enemy{

    public Ghost() {
        this(new GameVector(0, 0));
    }

    public Ghost(GameVector vector) {
        super(vector,
                "Ghost",
                GameConstants.Ghost.HEALTH,
                GameConstants.Ghost.AGILITY,
                GameConstants.Ghost.STRENGTH,
                GameConstants.Ghost.HOSTILITY,
                EnemyType.GHOST);
        moves = new GameVector[]{
                new GameVector(1,0),
                new GameVector(-1,0),
                new GameVector(0,1),
                new GameVector(0,-1) };
    }

    public boolean isVisible = false;

    @Override
    public void attack(Creature defender, Logger logger, GameStatistics statistics) {
        isVisible = true;
        super.attack(defender, logger, statistics);
    }

    @Override
    public GameVector nextMove(Level level, GameVector target) {
        var result = toTarget(level, target);
        if (result == null){
            int random = Generator.roll(1,10);
            if(random == 10){
                Room room = null;
                for(var r: level.getMap().getRoomList()){
                    if(r.contains(position)){
                        room = r;
                        break;
                    }
                }
                GameVector newPos = null;
                if(room != null){
                    newPos = room.getRandomPoint();
                }
                if(newPos != null && !level.collides(newPos) && !target.equals(newPos)){
                    return newPos;
                }
            }
            else{
                result = position.add( moves[Generator.nextInt(0,moves.length - 1)]);
                if(!level.collides(result)){
                    return result;
                }
            }
        }
        else{
            isVisible = true;
            return result;
        }

        return position;
    }

    public boolean isVisible() {
        return isVisible;
    }

}
