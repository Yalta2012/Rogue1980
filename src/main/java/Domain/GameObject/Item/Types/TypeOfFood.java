package Domain.GameObject.Item.Types;

public enum TypeOfFood {
    APPLE("Apple", 5),
    BREAD("Bread", 10),
    MEAT("Meat", 20),
    EGG("Egg", 15);

    private final String name;
    private final int healValue;

    TypeOfFood(String name, int healValue) {
        this.name = name;
        this.healValue = healValue;
    }

    public String getName() {
        return name;
    }

    public int getHealValue() {
        return healValue;
    }
}
