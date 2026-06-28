package Domain.GameObject.Creature.Enemy;

public enum EnemyType {
    ZOMBIE('z'),
    VAMPIRE('v'),
    OGR('o'),
    SNAKE_MAGE('s'),
    GHOST('g'),
    MIMIC('m');

    private final char symbol;
    EnemyType(char symbol){
        this.symbol = symbol;
    }

    public char getSymbol(){
        return symbol;
    }
}
