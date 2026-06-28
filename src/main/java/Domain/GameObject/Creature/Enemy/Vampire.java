package Domain.GameObject.Creature.Enemy;

import Domain.GameConstants;
import Domain.GameObject.Creature.Creature;
import Domain.GameObject.Item.Effect.Effect;
import Domain.GameObject.Item.Effect.EffectType;
import Domain.GameStatistics;
import Domain.GameVector;

import Domain.Logger.Logger;
import com.fasterxml.jackson.annotation.JsonIgnore;


public class Vampire extends Enemy {

    public Vampire(){
        this(new GameVector(0,0));
    }

    public boolean firstHit = true;
    public Vampire(GameVector vector) {
        super(vector, "Vampire", GameConstants.Vampire.HEALTH, GameConstants.Vampire.AGILITY, GameConstants.Vampire.STRENGTH, GameConstants.Vampire.HOSTILITY, EnemyType.VAMPIRE);
        moves = new GameVector[]{
                new GameVector(1,0),
                new GameVector(-1,0),
                new GameVector(0,1),
                new GameVector(0,-1) };
    }

    @Override
    public void attack(Creature defender, Logger logger, GameStatistics statistics) {
        super.attack(defender, logger, statistics);
        defender.addEffect(new Effect(EffectType.VAMPIRE_BITE));
    }

    @JsonIgnore
    @Override
    public int getDefendVal() {
        if (firstHit){
            firstHit = false;
            return 9999;
        }

        return super.getDefendVal();
    }
}
