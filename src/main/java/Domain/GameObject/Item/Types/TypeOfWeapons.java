package Domain.GameObject.Item.Types;

public enum TypeOfWeapons {
    MACE("Mace", 3, 6),
    LONGSWORD("Long Sword", 3, 10),
    DAGGER("Dagger",4, 4),
    AXE("Axe", 4, 8);

    private final String name;
    private final int diceCount;
    private final int diceEdges;

    TypeOfWeapons(String name, int diceCount, int diceEdges) {
        this.name = name;
        this.diceCount = diceCount;
        this.diceEdges = diceEdges;
    }

    public String getName() {
        return name;
    }

    public int getDiceCount() {
        return diceCount;
    }

    public int getDiceEdges() {
        return diceEdges;
    }
}
