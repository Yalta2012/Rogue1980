package Domain.GameObject.Item;


import Domain.GameObject.Creature.Player.Player;
import Domain.GameObject.Item.Types.TypeOfScrolls;
import Domain.GameObject.Item.Types.TypesOfProperty;
import Domain.GameVector;

public class Scrolls extends Item {
    TypeOfScrolls subtype;

    public Scrolls() {}

    public Scrolls(GameVector point, TypeOfScrolls subtype) {
        super(point.getX(), point.getY(), generateRandomName(), "Scrolls");
        this.subtype = subtype;
    }

    @Override
    public void use(Player player) {
        switch (subtype.getType()){
            case TypesOfProperty.MAXHP -> player.buffHelath(subtype.getBuff());
            case TypesOfProperty.AGILITY -> player.setAgility(player.getAgility() + subtype.getBuff());
            case TypesOfProperty.FORCE -> player.setStrength(player.getStrength() + subtype.getBuff());
            default -> throw new IllegalStateException("Unexpected value: " + subtype.getType());
        }
    }

    public void setSubtype(TypeOfScrolls subtype) {
        this.subtype = subtype;
    }

    public TypeOfScrolls getSubtype() {
        return subtype;
    }

    private static String generateRandomName() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder name = new StringBuilder();
        java.util.Random rnd = new java.util.Random();

        int length = 4 + rnd.nextInt(3);
        for (int i = 0; i < length; i++) {
            name.append(chars.charAt(rnd.nextInt(chars.length())));
        }

        return name.toString();
    }

    @Override
    public String getName() {
        return "Scroll " + name;
    }
}
