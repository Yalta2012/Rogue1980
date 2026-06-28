package Domain.GameObject.Item.Effect;

import Domain.GameConstants;

public enum EffectType {


    STRENGTH("Strength", GameConstants.POTION_DURATION, 0, 0, GameConstants.STRENGTH_POTION_DIFFERENCE),
    AGILITY("Agility", GameConstants.POTION_DURATION, 0, GameConstants.AGILITY_POTION_DIFFERENCE, 0),
    HEALTH("Health", GameConstants.POTION_DURATION, GameConstants.HEALTH_POTION_DIFFERENCE, 0, 0),
    VAMPIRE_BITE("Vampire bite", GameConstants.Vampire.DEBUFF_DURATION, GameConstants.Vampire.HP_DECREASE, 0 ,0),
    SNAKE_STUN("Stun", 2, 0, 0 ,0);

    private int turns;
    private String name;
    private int
            maxHpDif,
            agilitiDif,
            strenghDif;

    EffectType(String name, int turns, int maxHpDif, int agilitiDif, int strenghDif){
        this.name = name;
        this.turns = turns;
        this.maxHpDif = maxHpDif;
        this.agilitiDif = agilitiDif;
        this.strenghDif = strenghDif;
    }

    public String getName() {
        return name;
    }

    public int getAgilitiDif() {
        return agilitiDif;
    }

    public int getMaxHpDif() {
        return maxHpDif;
    }

    public int getStrenghDif() {
        return strenghDif;
    }

    public int getTurns() {
        return turns;
    }
}
