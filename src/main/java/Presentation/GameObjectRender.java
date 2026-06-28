package Presentation;

import Domain.GameObject.Creature.Enemy.*;
import Domain.GameObject.Creature.Player.Player;
import Domain.GameObject.GameObject;
import Domain.GameObject.Item.Food;
import Domain.GameObject.Item.Potion;
import Domain.GameObject.Item.Scrolls;
import Domain.GameObject.Item.Weapon;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.screen.Screen;

import java.util.HashMap;
import java.util.Map;

public class GameObjectRender {
    private Screen screen;

    private final Map<Class<? extends GameObject>, Character> symbols = Map.ofEntries(
            Map.entry(Player.class, '☺'),

            Map.entry(Zombie.class, 'z'),
            Map.entry(Ogr.class, 'O'),
            Map.entry(Vampire.class, 'v'),
            Map.entry(SnakeMage.class, 's'),
            Map.entry(Mimic.class, 'm'),
            Map.entry(Ghost.class, 'g'),

            Map.entry(Food.class, '%'),
            Map.entry(Potion.class, '!'),
            Map.entry(Scrolls.class, '♪'),
            Map.entry(Weapon.class, '↑'),

            Map.entry(GameObject.class, 'x')


    );
    private final Map<Class<? extends GameObject>, TextColor> color = Map.ofEntries(
            Map.entry(Player.class, TextColor.ANSI.YELLOW),

            Map.entry(Ghost.class, TextColor.ANSI.WHITE),
            Map.entry(Zombie.class, TextColor.ANSI.GREEN),
            Map.entry(Vampire.class, TextColor.ANSI.RED),
            Map.entry(Ogr.class, TextColor.ANSI.YELLOW_BRIGHT),
            Map.entry(SnakeMage.class, TextColor.ANSI.WHITE),
            Map.entry(Mimic.class, TextColor.ANSI.WHITE),

            Map.entry(GameObject.class, TextColor.ANSI.WHITE)

    );

    public GameObjectRender(Screen screen) {
        this.screen = screen;

    }

    public void render(GameObject object) {
        if (object instanceof Mimic mimic) {
            if (mimic.isActive()) {
                drawDefault(mimic);
            } else {
                render(mimic.getDisguise());
            }
        } else if (object instanceof Ghost ghost) {
            if (ghost.isVisible()) {
                drawDefault(ghost);
            }
        } else {
            drawDefault(object);
        }
    }

    private void drawDefault(GameObject obj) {
        screen.setCharacter(obj.getX(), obj.getY(),
                new TextCharacter(symbols.get(obj.getClass()),
                        color.get(obj.getClass()),
                        TextColor.ANSI.DEFAULT));
    }

}
