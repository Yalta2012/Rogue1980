package Domain.GameObject.Item;

import Domain.GameObject.Creature.Player.Player;
import Domain.GameObject.Item.Types.TypeOfFood;
import Domain.GameVector;

public class Food extends Item {
    private TypeOfFood subtype;
    private int healAmount;

    public Food() {}

    public Food(GameVector point, TypeOfFood subtype) {
        super(point.getX(), point.getY(), subtype.getName(), "Food");
        this.subtype = subtype;
        this.healAmount = subtype.getHealValue();
    }

    @Override
    public void use(Player player) {
        int newhp = player.getHealth() + healAmount;
        player.setHp(Math.min(newhp, player.getMaxhp()));
    }

    public int getHealAmount() {
        return healAmount;
    }

    public void setHealAmount(int healAmount) {
        this.healAmount = healAmount;
    }

    public TypeOfFood getSubtype() {
        return subtype;
    }

    public void setSubtype(TypeOfFood subtype) {
        this.subtype = subtype;
    }
}
