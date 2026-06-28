package Domain.GameObject.Item.Effect;

public class Effect {
    private int turns;
    private String name;
    private EffectType type;
    private int
            maxHpDif,
            agilitiDif,
            strengthDif;


    public Effect(EffectType type) {
        this.name = type.getName();
        this.turns = type.getTurns();
        this.maxHpDif = type.getMaxHpDif();
        this.agilitiDif = type.getAgilitiDif();
        this.strengthDif = type.getStrenghDif();
        this.type = type;
    }

    public int tick() {if (turns > 0) turns--; return  turns;}

    public String getName(){return name;}
    public int getMaxHpDif() {return maxHpDif; }
    public int getAgilityDif() { return agilitiDif;}
    public int getStrengthDif() {return strengthDif;}

    public void setTurns(int turns) {
        this.turns = turns;
    }
    public int getTurns(){
        return turns;
    }

    public EffectType getType() {
        return type;
    }

//    public static Effect SNAKE_STUN = new Effect(EffectType.SNAKE_STUN);
}
