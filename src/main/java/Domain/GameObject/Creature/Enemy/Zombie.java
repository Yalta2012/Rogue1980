package Domain.GameObject.Creature.Enemy;

import Domain.GameConstants;
import Domain.GameObject.Creature.Creature;
import Domain.GameVector;
import Domain.Generator;
import Domain.Map.Level;
import Domain.Map.Map;


public class Zombie extends Enemy{

    public Zombie() {
        this(new GameVector(0, 0));
    }

    public Zombie(GameVector vector) {
        super(vector,
                "Zombie",
                GameConstants.Zombie.HEALTH,
                GameConstants.Zombie.AGILITY,
                GameConstants.Zombie.STRENGTH,
                GameConstants.Zombie.HOSTILITY,
                EnemyType.ZOMBIE);
        moves = new GameVector[]{
                new GameVector(1,0),
                new GameVector(-1,0),
                new GameVector(0,1),
                new GameVector(0,-1) };
    }

}
