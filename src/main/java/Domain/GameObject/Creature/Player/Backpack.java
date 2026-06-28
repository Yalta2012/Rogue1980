package Domain.GameObject.Creature.Player;

import Domain.GameObject.Item.Item;

import java.util.ArrayList;
import java.util.List;

import static Domain.GameConstants.MAX_STACK;

public class Backpack {
    private List<Item> itemList;
    private int gold;

    public Backpack() {}

    public Backpack(boolean initNew) {
        if (initNew) {
            this.itemList = new ArrayList<>();
            this.gold = 0;
        }
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public boolean addItemList(Item newItem) {
        long count = itemList.stream()
                .filter(item -> item.getClass().equals(newItem.getClass())).count();
        if (count > MAX_STACK) {
            return false;
        } else {
            this.itemList.add(newItem);
            return true;
        }
    }

    public int getGold() {
        return gold;
    }

    public void addGold(int gold) {
        this.gold += gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }
}
