package Domain.GameObject.Item;

import Domain.GameObject.Creature.Player.Player;
import Domain.GameObject.Item.Types.TypeOfWeapons;
import Domain.GameVector;
import Domain.Generator;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Weapon extends Item {
    private TypeOfWeapons subtype;

    public Weapon() {}

    public Weapon(GameVector point, TypeOfWeapons subtype) {
        super(point.getX(), point.getY(), subtype.getName(), "Weapon");
        this.subtype = subtype;
    }

    @JsonIgnore
    public int getHitDamage() {
        return Generator.roll(subtype.getDiceCount(), subtype.getDiceEdges());
    }

    @Override
    public void use(Player player) {
        player.setWeapon(this);
    }

    public void unUse(Player player) {
        player.setWeapon(null);
    }

    public TypeOfWeapons getSubtype() {
        return subtype;
    }

    public void setSubtype(TypeOfWeapons subtype) {
        this.subtype = subtype;
    }
}
