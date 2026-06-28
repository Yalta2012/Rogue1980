package Domain;

import Domain.GameObject.Creature.Enemy.Enemy;
import Domain.GameObject.Creature.Player.Player;
import Domain.GameObject.Item.Item;
import Domain.Logger.Logger;
import Domain.Map.Level;
import Domain.Map.Map;

import java.util.Collection;


public interface IGameModel {

    GameStatistics getStatistics();

    boolean tryTurn(GameVector vector);

    Player getPlayer();

    Map getMap();

    Collection<Item> getItems();

    Logger getLogger();

    Level getLevel();

    Collection<Enemy> getEnemies();

    void useItem(Item selected);

    boolean getEndGame();

    int getScore();


}
