package Domain.GameObject.Creature.Enemy;

import Domain.GameConstants;
import Domain.GameObject.Creature.Creature;
import Domain.GameObject.Item.Item;
import Domain.GameObject.Item.ItemFabric;
import Domain.GameStatistics;
import Domain.GameVector;

import Domain.Logger.Logger;
import Domain.Map.Level;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Mimic extends Enemy {
    public Mimic() {
        this(new GameVector(0,0));
    }

    public Mimic(GameVector vector) {
        super(
                vector,
                "Mimic",
                GameConstants.Mimic.HEALTH,
                GameConstants.Mimic.AGILITY,
                GameConstants.Mimic.STRENGTH,
                GameConstants.Mimic.HOSTILITY,
                EnemyType.MIMIC);

        disguise = ItemFabric.createRandomItem(position);
        moves = new GameVector[]{
                new GameVector(1,0),
                new GameVector(-1,0),
                new GameVector(0,1),
                new GameVector(0,-1) };
    }

    private Item disguise;
    private boolean isActive = false;

    @Override
    public void attack(Creature defender, Logger logger, GameStatistics statistics) {
        super.attack(defender, logger, statistics);
        hostility = 2;
        isActive = true;
    }

    @Override
    public GameVector nextMove(Level level, GameVector target) {
            var result = toTarget(level,target);
            if(result != null) return result;

        return position;
    }

    public Item getDisguise() {
        return disguise;
    }

    @JsonIgnore
    public boolean isActive() {
        return isActive;
    }

}
