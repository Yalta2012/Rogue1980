package Domain.GameObject.Creature;

import Domain.GameObject.GameObject;
import Domain.GameObject.Item.Effect.Effect;
import Domain.GameStatistics;
import Domain.GameVector;
import Domain.Generator;

import Domain.Logger.Logger;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public abstract class Creature extends GameObject {
    protected int health, agility, speed, strength, armor = 10;
    protected String name;
    protected List<Effect> effects = new LinkedList<>();
    protected boolean stunned = false;

    public Creature() {}

    public Creature(String name, int health, int agility, int strength) {
        this(0,0,name, health, agility, strength);
    }

    public Creature(int x, int y, String name, int health, int agility, int strength) {
        this(new GameVector(x,y), name, health, agility, strength);
    }

    public Creature(GameVector vector, String name, int health, int agility, int strength) {
        super(vector);
        this.name = name;
        this.health = health;
        this.agility = agility;
        this.strength = strength;
    }

    public int getAgility(){
        return agility;
    }

    public void move(GameVector vector) {
        position = position.add(vector);
    }

    public int getHealth() {
        return health;
    }

    @JsonIgnore
    public void setHp(int health) {
        this.health = health;
    }

    public void setAgility(int agility) {
        this.agility = Math.max(agility, 1);
    }

    public void setStrength(int strength) {
        this.strength = Math.max(strength, 1);
    }

    public int getStrength() {
        return strength;
    }

    public int getArmor(){
        return armor;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public void setEffects(List<Effect> effects) {
        this.effects = effects;
    }

    public List<Effect> getEffects() {
        return effects;
    }

    public String getName(){
        return name;
    }

    public void heal(int cure){
        health += cure;
    }

    public void damage(int attack){
        health -= attack;
    }

    @JsonIgnore
    public int getDamage(){
        return Generator.roll(1,4) + strength;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getDefendVal(){
        return getAgility() + getArmor();
    }
    public void attack(Creature defender, Logger logger, GameStatistics statistics){
        if(Generator.roll(1, 20) + getAgility() > defender.getDefendVal()){
            defender.damage(getDamage());
            logger.addLog((getName()) + " hit " + defender.getName() + ".");
            statistics.setHits(statistics.getHits()+1);
        }
        else{
            logger.addLog(getName() + " miss  " + defender.getName());
            statistics.setMissHits(statistics.getMissHits()+1);
        }
    }


    protected void applyEffect(Effect effect){
        strength += effect.getStrengthDif();
        agility += effect.getAgilityDif();

    }

    protected void canselEffect(Effect effect){
        strength -= effect.getStrengthDif();
        agility -= effect.getAgilityDif();
    }

    public void addEffect(Effect effect){
        for(var e : effects){
            if(e.getName().equals(effect.getName())){
                e.setTurns(Math.max(e.getTurns(),effect.getTurns()));
                return;
            }
        }
        effects.add(effect);
        applyEffect(effect);
    }

    public void tick(){
        Iterator<Effect> it = effects.iterator();
        while(it.hasNext()){
            var next = it.next();
            if(next.tick() <= 0){
                it.remove();
                canselEffect(next);
            }
        }
    }

    public void setStunned(boolean s){
        stunned = s;
    }

    public boolean isStunned() {
        return stunned;
    }
}
