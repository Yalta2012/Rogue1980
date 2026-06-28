package Domain.GameObject.Item;

import Domain.GameObject.Item.Types.TypeOfPotion;
import Domain.GameObject.Item.Types.TypeOfFood;
import Domain.GameObject.Item.Types.TypeOfScrolls;
import Domain.GameObject.Item.Types.TypeOfWeapons;
import Domain.GameVector;

import java.util.Random;

public class ItemFabric {
    private static final Random random = new Random();

    public static Item createRandomItem(GameVector point) {
        int categoryChance = random.nextInt(100);

        if (categoryChance < 40) {
            TypeOfFood[] foods = TypeOfFood.values();
            TypeOfFood randomType = foods[random.nextInt(foods.length)];
            return new Food(point, randomType);
        }
        else if (categoryChance < 65) {
            TypeOfPotion[] potions = TypeOfPotion.values();
            TypeOfPotion randomType = potions[random.nextInt(potions.length)];
            return new Potion(point, randomType);
        }
        else if (categoryChance < 85) {
            TypeOfScrolls[] scrolls = TypeOfScrolls.values();
            TypeOfScrolls randomType = scrolls[random.nextInt(scrolls.length)];
            return new Scrolls(point, randomType);
        }
        else {
            TypeOfWeapons[] weapons = TypeOfWeapons.values();
            TypeOfWeapons randomType = weapons[random.nextInt(weapons.length)];
            return new Weapon(point, randomType);
        }
    }
}
