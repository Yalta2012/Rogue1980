package Domain.GameObject.Item.Types;

import Domain.GameObject.Item.Effect.Effect;
import Domain.GameObject.Item.Effect.EffectType;

public enum TypeOfPotion {
    EXTR_HEAL("Healing", EffectType.HEALTH),
    G_FORCE("Gain strength", EffectType.STRENGTH),
    SPEED("Speed", EffectType.AGILITY);

    private final String name;
    private final Effect effect;

    TypeOfPotion(String name, EffectType effectType) {
        this.name = name;
        this.effect = new Effect(effectType);
    }

    public String getName() {
        return name;
    }

    public Effect getEffect() {
        return effect;
    }
}
