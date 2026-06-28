package Domain.GameObject.Item.Types;

public enum TypeOfScrolls {
    EXTR_HEAL("Healing", TypesOfProperty.MAXHP, 10),
    G_FORCE("Gain strength", TypesOfProperty.FORCE, 5),
    SPEED("Speed", TypesOfProperty.AGILITY, 5);

    private final String name;
    private final TypesOfProperty type;
    private final int buff;

    TypeOfScrolls(String name, TypesOfProperty type, int buff) {
        this.name = name;
        this.type = type;
        this.buff = buff;
    }

    public String getName() {
        return name;
    }

    public TypesOfProperty getType() {
        return type;
    }

    public int getBuff() {
        return buff;
    }
}
