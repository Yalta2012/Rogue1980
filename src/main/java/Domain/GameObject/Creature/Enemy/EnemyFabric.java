package Domain.GameObject.Creature.Enemy;

import Domain.GameVector;
import Domain.Generator;

import java.util.Random;

public class EnemyFabric {
    private static final Random random = new Random();

    public static Enemy createRandomEnemy(GameVector vector, int levelNumber, int gold){
        EnemyType type = rollEnumType(levelNumber);
        Enemy enemy;

        switch (type) {
            case ZOMBIE -> enemy = new Zombie(vector);
            case VAMPIRE -> enemy = new Vampire(vector);
            case OGR -> enemy = new Ogr(vector);
            case SNAKE_MAGE -> enemy = new SnakeMage(vector);
            case GHOST -> enemy = new Ghost(vector);
            default -> enemy = new Mimic(vector);
        }

        scaleStats(enemy, levelNumber, gold);
        return enemy;
    }

    private static void scaleStats(Enemy enemy, int levelNumber, int gold) {
        double levelMod = (levelNumber - 1) * 0.1;
        double treasureMod = gold * 0.05;

        double total = 1.0 + levelMod + treasureMod;
        enemy.setHp((int) (enemy.getHealth() * total));
        enemy.setStrength((int) (enemy.getStrength() * total));
    }

    private static EnemyType rollEnumType(int levelNumber) {
        int zombieWeight = Math.max(0, 100 - (levelNumber * 5));
        int ghostWeight = Math.max(0, 10 + (levelNumber * 2));
        int vampireWeight = Math.max(0, 5 + (levelNumber * 3));
        int mimicWeight = 15;
        int ogrWeight = Math.max(0, levelNumber * 2);
        int snakeMageWeight = Math.max(0, (levelNumber - 5) * 3);

        int totalWeight = zombieWeight + ghostWeight + vampireWeight +
                mimicWeight + ogrWeight + snakeMageWeight;

        int roll = random.nextInt(totalWeight);

        int current = 0;
        current += zombieWeight;
        if (roll < current) return EnemyType.ZOMBIE;

        current += ghostWeight;
        if (roll < current) return EnemyType.GHOST;

        current += vampireWeight;
        if (roll < current) return EnemyType.VAMPIRE;

        current += mimicWeight;
        if (roll < current) return EnemyType.MIMIC;

        current += ogrWeight;
        if (roll < current) return EnemyType.OGR;

        return EnemyType.SNAKE_MAGE;
    }
}
