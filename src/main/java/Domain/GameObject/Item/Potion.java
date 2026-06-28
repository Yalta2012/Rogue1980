package Domain.GameObject.Item;

import Domain.GameObject.Creature.Player.Player;
import Domain.GameObject.Item.Types.TypeOfPotion;
import Domain.GameObject.Item.Types.TypesOfProperty;
import Domain.GameVector;

public class Potion extends Item {
    private TypeOfPotion subtype;

    public Potion() {}

    public Potion(GameVector point, TypeOfPotion subtype) {
        super(point.getX(), point.getY(), subtype.getName(), "Potion");
        this.subtype = subtype;
    }

    @Override
    public void use(Player player) {
        player.addEffect(subtype.getEffect());
    }

    public void unUse(Player player) {

    }

    public void setSubtype(TypeOfPotion subtype) {
        this.subtype = subtype;
    }

    public TypeOfPotion getSubtype() {
        return subtype;
    }

    @Override
    public String getName() {
        return "Potion of " + name;
    }
}
