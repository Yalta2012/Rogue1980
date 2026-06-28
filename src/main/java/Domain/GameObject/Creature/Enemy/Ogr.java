package Domain.GameObject.Creature.Enemy;

import Domain.GameConstants;
import Domain.GameObject.Creature.Creature;
import Domain.GameStatistics;
import Domain.GameVector;

import Domain.Logger.Logger;
import Domain.Map.Level;

public class Ogr extends Enemy{

    public Ogr() {
        this(new GameVector(0, 0));
    }

    public Ogr(GameVector vector) {
        super(vector,
                "Ogr",
                GameConstants.Ogr.HEALTH,
                GameConstants.Ogr.AGILITY,
                GameConstants.Ogr.STRENGTH,
                GameConstants.Ogr.HOSTILITY,
                EnemyType.OGR);
        moves = new GameVector[]{
                new GameVector(1,0),
                new GameVector(-1,0),
                new GameVector(0,1),
                new GameVector(0,-1)
        };
    }

    private boolean isActive = true;
    @Override
    public void attack(Creature defender, Logger logger, GameStatistics statistics) {
        if (isActive) {
            super.attack(defender, logger, statistics);
        }
        isActive = !isActive;
    }

    @Override
    public GameVector nextMove(Level level, GameVector target) {
        position = super.nextMove(level, target);
        return super.nextMove(level, target);
    }
}
