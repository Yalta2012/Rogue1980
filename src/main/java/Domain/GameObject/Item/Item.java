package Domain.GameObject.Item;

import Domain.GameObject.Creature.Player.Player;
import Domain.GameObject.GameObject;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Food.class, name = "Food"),
        @JsonSubTypes.Type(value = Potion.class, name = "Potion"),
        @JsonSubTypes.Type(value = Weapon.class, name = "Weapon"),
        @JsonSubTypes.Type(value = Scrolls.class, name = "Scrolls")
})
public abstract class Item extends GameObject {
    protected String name;
    private String type;

    public Item() {}

    public Item(int x, int y, String name, String type){
        super(x, y);
        this.name = name;
        this.type = type;
    }

    public abstract void use(Player player);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
