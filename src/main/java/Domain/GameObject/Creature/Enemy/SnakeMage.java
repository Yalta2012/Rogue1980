package Domain.GameObject.Creature.Enemy;

import Domain.GameConstants;
import Domain.GameObject.Creature.Creature;
import Domain.GameObject.Item.Effect.Effect;
import Domain.GameObject.Item.Effect.EffectType;
import Domain.GameStatistics;
import Domain.GameVector;
import Domain.Generator;

import Domain.Logger.Logger;


public class SnakeMage extends Enemy  {
    public SnakeMage() {
        this(new GameVector(0, 0));
    }

    public SnakeMage(GameVector vector) {
        super(vector,
                "Snake mage",
                GameConstants.SnakeMage.HEALTH,
                GameConstants.SnakeMage.AGILITY,
                GameConstants.SnakeMage.STRENGTH,
                GameConstants.SnakeMage.HOSTILITY,
                EnemyType.SNAKE_MAGE);
        moves = new GameVector[]{
                new GameVector(1,1),
                new GameVector(-1,-1),
                new GameVector(-1,1),
                new GameVector(1,-1),
                new GameVector(1,0),
                new GameVector(-1,0),
                new GameVector(0,1),
                new GameVector(0,-1)
        };
    }

    @Override
    public void attack(Creature defender, Logger logger, GameStatistics statistics) {
        super.attack(defender, logger, statistics);
        if(Generator.roll(1,100)/100.0 <= GameConstants.SnakeMage.STUN_CHANCE){
            defender.addEffect(new Effect(EffectType.SNAKE_STUN));
            logger.addLog("You're getting numb");
        }
    }
}
