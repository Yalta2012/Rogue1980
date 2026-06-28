package Domain;

import Domain.GameObject.Creature.Creature;
import Domain.GameObject.Creature.Enemy.Enemy;
import Domain.GameObject.Creature.Player.Player;
import Domain.GameObject.Item.*;
import Domain.Logger.Logger;
import Domain.Map.Level;
import Domain.Map.Map;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.*;

import static Domain.GameConstants.Player.MAX_HEALTH;
import static Domain.GameConstants.MAX_LEVEL;
import static Domain.GameConstants.Player.AGILITY;
import static Domain.GameConstants.Player.STRENGTH;

public class GameModel implements IGameModel {
    private Player player;
    private Level level;
    private GameStatistics statistics;
    private boolean endGame;
    private Logger logger = new Logger();

    public GameModel() {
    }

    public GameModel(boolean initNew, String playerName) {
        this.level = new Level(1);
        GameVector startPoint = level.getStartPoint();
        this.player = new Player(startPoint, playerName, AGILITY, STRENGTH, MAX_HEALTH);
        statistics = new GameStatistics(player.getName());
        endGame = false;
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @JsonIgnore
    @Override
    public Map getMap() {
        return level.getMap();
    }

    @Override
    public GameStatistics getStatistics() {
        return statistics;
    }

    @JsonIgnore
    @Override
    public Collection<Item> getItems() {
        return level.getItems().values();
    }

    @Override
    public Logger getLogger() {
        return logger;
    }

    @Override
    public Level getLevel() {
        return level;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public void setStatistics(GameStatistics statistics) {
        this.statistics = statistics;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public boolean tryTurn(GameVector vector) {
        if (!player.isStunned()) {

            var nextPosition = player.getPosition().add(vector);
            if (nextPosition.isEquals(level.getEndPoint())) {
                nextLevel();
                return true;
            }

            var e = level.getEnemy(nextPosition);
            if (e != null) {
                attack(player, e);
            } else if (level.getItems().containsKey(nextPosition)) {
                player.move(vector);
                tryTake(player, level.getItems().get(nextPosition));
            } else if (level.getMap().contains(nextPosition)) {
                player.move(vector);
                statistics.setSteps(statistics.getSteps() + 1);
            }
        }


        updateEnemies();
        level.getMap().setVisit(player.getPosition());
        player.tick();
        if (player.getHealth() <= 0) {
            this.endGame = true;
        }
        return false;
    }

    private void nextLevel() {
        int numberOfNextLevel = this.level.getNumberLevel() + 1;
        if (numberOfNextLevel > MAX_LEVEL) {
            endGame = true;
        }
        this.level = new Level(numberOfNextLevel);
        GameVector startPoint = level.getStartPoint();
        player.setX(startPoint.getX());
        player.setY(startPoint.getY());
        logger.addLog("You went down the stairs");
    }


    private void attack(Creature attacker, Creature defender) {
        attacker.attack(defender, logger, statistics);
    }

    private void updateEnemies() {
        for (var it = level.getEnemies().iterator(); it.hasNext(); ) {
            var entry = it.next();
            if (entry.getHealth() <= 0) {
                it.remove();
                statistics.setKills(statistics.getKills() + 1);
                int gold = player.getBackpack().getGold() + (entry.getHostility() + entry.getStrength()) * level.getNumberLevel();
                player.getBackpack().setGold(gold);
                statistics.setTreasure(gold);
                level.setGold(gold);
            }
        }
        List<Enemy> enemiesCopy = new ArrayList<>(level.getEnemies());
        for (Enemy e : enemiesCopy) {
            tryEnemyMove(e);

        }
    }

    public void tryEnemyMove(Enemy enemy) {
        var nextPosition = enemy.nextMove(level, player.getPosition());
        if (nextPosition.equals(player.getPosition())) {
            attack(enemy, player);
        } else {
            enemy.setPosition(nextPosition);
        }
    }


    private void tryTake(Player player, Item item) {
        boolean isAdded = player.getBackpack().addItemList(item);

        if (isAdded) {
            level.getItems().remove(item.getPosition());
            logger.addLog("You picked up: " + item.getName());
        } else {
            logger.addLog("Your backpack is full! Can't take " + item.getName());
        }
    }

    @Override
    @JsonIgnore
    public Collection<Enemy> getEnemies() {
        return level.getEnemies();
    }

    @Override
    public void useItem(Item selected) {
        if (selected instanceof Weapon) {
            Weapon oldWeapon = player.getWeapon();
            if (oldWeapon != null) {
                oldWeapon.setX(player.getX());
                oldWeapon.setY(player.getY());
                level.getItems().put(oldWeapon.getPosition(), oldWeapon);
            }
        }
        selected.use(player);
        player.getBackpack().getItemList().remove(selected);
        logger.addLog("You used: " + selected.getName());
        updateUsageStatistics(selected);
    }

    private void updateUsageStatistics(Item item) {
        if (item instanceof Potion) {
            statistics.setPotions(statistics.getPotions() + 1);
        } else if (item instanceof Food) {
            statistics.setFoods(statistics.getFoods() + 1);
        } else if (item instanceof Scrolls) {
            statistics.setScrolls(statistics.getScrolls() + 1);
        }
    }

    public boolean getEndGame() {
        return endGame;
    }

    public void setEndGame(boolean endGame) {
        this.endGame = endGame;
    }

    @JsonIgnore
    @Override
    public int getScore() {
        return getStatistics().getTreasure() * 10 + getStatistics().getLevel()*7;
    }
}
