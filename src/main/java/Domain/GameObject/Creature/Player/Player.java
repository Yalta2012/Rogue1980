package Domain.GameObject.Creature.Player;

import Domain.GameObject.Creature.Creature;
import Domain.GameObject.Item.Effect.Effect;
import Domain.GameObject.Item.Effect.EffectType;
import Domain.GameObject.Item.Potion;
import Domain.GameObject.Item.Weapon;
import Domain.GameVector;
import Domain.Generator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Player extends Creature {
    private int maxhp;
    private Backpack backpack;
    private List<Potion> activeEffeccts;
    private Weapon weapon;

    public Player() {}

    public Player(GameVector vector, String name, int agility, int force, int maxhp){
        super(vector, name, maxhp, agility, force);
        this.maxhp = maxhp;
        this.backpack = new Backpack(true);
        this.activeEffeccts = new ArrayList<>();
    }

    public int getMaxhp() {
        return maxhp;
    }

    public void setMaxhp(int maxhp) {
        this.maxhp = maxhp;
    }

    public void buffHelath(int maxhp) {
        this.maxhp = Math.max(10, this.maxhp + maxhp);
        int nowhp = this.health + maxhp;
        this.health = Math.min(this.maxhp, nowhp);

    }

    @Override
    public int getDamage() {
        if (weapon != null) {
            return strength + weapon.getHitDamage();
        } else {
            return strength + Generator.roll(1,4);
        }
    }

    public Backpack getBackpack() {
        return backpack;
    }

    public void setBackpack(Backpack backpack) {
        this.backpack = backpack;
    }

    public List<Potion> getActiveEffeccts() {
        return activeEffeccts;
    }

    public void setActiveEffeccts(List<Potion> activeEffeccts) {
        this.activeEffeccts = activeEffeccts;
    }

    @Override
    protected void applyEffect(Effect effect) {
        super.applyEffect(effect);
        maxhp += effect.getMaxHpDif();
        health += effect.getMaxHpDif();
        if(effect.getType() == EffectType.SNAKE_STUN){
            stunned = true;
        }

    }

    @Override
    protected void canselEffect(Effect effect) {
        super.canselEffect(effect);
        maxhp -= effect.getMaxHpDif();
        if (health > maxhp)
            health = maxhp;
        if(effect.getType() == EffectType.SNAKE_STUN){

            stunned = false;
        }
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }
}
